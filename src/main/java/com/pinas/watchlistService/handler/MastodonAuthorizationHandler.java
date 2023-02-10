package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.MastodonConfig;
import com.pinas.watchlistService.api.model.auth.MastodonAccessTokenResponse;
import com.pinas.watchlistService.api.model.auth.MastodonAuthorizationCredentials;
import com.pinas.watchlistService.db.entity.Authorization;
import com.pinas.watchlistService.db.repository.AuthorizationRepository;
import com.pinas.watchlistService.helper.AccessTokenHelper;
import java.util.List;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class MastodonAuthorizationHandler {

    private final AuthorizationRepository repository;
    private final AccessTokenHelper accessTokenHelper;
    private final MastodonConfig mastodonConfig;

    public MastodonAuthorizationHandler(AuthorizationRepository repository, AccessTokenHelper accessTokenHelper, MastodonConfig mastodonConfig) {
        this.repository = repository;
        this.accessTokenHelper = accessTokenHelper;
        this.mastodonConfig = mastodonConfig;
    }

    public Boolean isAuthorized() {
        return existsAccessToken() && existsMastodonUrl();
    }

    private Boolean existsAccessToken() {
        try {
            accessTokenHelper.getMastodonAccessToken();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean existsMastodonUrl() {
        try {
            accessTokenHelper.getMastodonUrl();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean logout() {
        try {
            repository.deleteById("MastodonAccessToken");
            repository.deleteById("MastodonUrl");
        } catch (Exception e) {

        }
        return !repository.existsByKeyIn(List.of("MastodonAccessToken", "MastodonUrl"));
    }

    public Boolean createAccessToken(MastodonAuthorizationCredentials credentials) {
        ResponseEntity<MastodonAccessTokenResponse> response = exchangeAccessToken(credentials);
        if (saveAccessToken(response)) {
            return saveMastodonUrl(credentials);
        }
        return false;
    }

    private ResponseEntity<MastodonAccessTokenResponse> exchangeAccessToken(MastodonAuthorizationCredentials credentials) {
        if (credentials.getMastodonUrl() == null || !credentials.getMastodonUrl().startsWith("http")) {
            throw new InvalidPropertyException(MastodonAuthorizationCredentials.class, "mastodonUrl", "Mastodon url must be start with 'http'");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", credentials.getAuthorizationCode());
        map.add("grant_type", "authorization_code");
        map.add("client_id", mastodonConfig.getAppKey());
        map.add("client_secret", mastodonConfig.getAppSecret());
        map.add("redirect_uri", "urn:ietf:wg:oauth:2.0:oob");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String mastodonUrl = credentials.getMastodonUrl().endsWith("/") ? credentials.getMastodonUrl() : credentials.getMastodonUrl() + "/";

        ResponseEntity<MastodonAccessTokenResponse> response = restTemplate.exchange(
                mastodonUrl + "oauth/token",
                HttpMethod.POST,
                request,
                MastodonAccessTokenResponse.class
        );
        return response;
    }

    private Boolean saveAccessToken(ResponseEntity<MastodonAccessTokenResponse> response) {
        if (response.getBody() != null && response.getBody().getAccessToken() != null) {
            String encode = accessTokenHelper.encodeAccessToken(response.getBody().getAccessToken());
            Authorization authorization = new Authorization();
            authorization.setKey("MastodonAccessToken");
            authorization.setValue(encode);
            Authorization savedAuthorization = repository.save(authorization);
            return savedAuthorization != null;
        }

        return false;
    }

    private Boolean saveMastodonUrl(MastodonAuthorizationCredentials credentials) {
        if (credentials.getMastodonUrl() != null && !credentials.getMastodonUrl().isEmpty()) {
            String url = credentials.getMastodonUrl().endsWith("/") ? credentials.getMastodonUrl() : credentials.getMastodonUrl() + "/";
            Authorization mastodonUrl = new Authorization();
            mastodonUrl.setKey("MastodonUrl");
            mastodonUrl.setValue(url);
            Authorization savedMastodonUrl = repository.save(mastodonUrl);
            return savedMastodonUrl != null;
        }

        return false;
    }
}
