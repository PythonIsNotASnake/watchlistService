package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.DropboxConfig;
import com.pinas.watchlistService.api.model.auth.DropboxAccessTokenResponse;
import com.pinas.watchlistService.api.model.auth.DropboxAuthorizationCode;
import com.pinas.watchlistService.db.entity.Authorization;
import com.pinas.watchlistService.db.repository.AuthorizationRepository;
import com.pinas.watchlistService.helper.AccessTokenHelper;
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
public class DropboxAuthorizationHandler {

    private final AuthorizationRepository repository;
    private final AccessTokenHelper accessTokenHelper;
    private final DropboxConfig dropboxConfig;

    public DropboxAuthorizationHandler(AuthorizationRepository repository, AccessTokenHelper accessTokenHelper, DropboxConfig dropboxConfig) {
        this.repository = repository;
        this.accessTokenHelper = accessTokenHelper;
        this.dropboxConfig = dropboxConfig;
    }

    public Boolean existsAccessToken() {
        try {
            accessTokenHelper.getDropboxAccessToken();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean createAccessToken(DropboxAuthorizationCode authCode) {
        ResponseEntity<DropboxAccessTokenResponse> response = exchangeAccessToken(authCode);
        return saveAccessToken(response);
    }

    private ResponseEntity<DropboxAccessTokenResponse> exchangeAccessToken(DropboxAuthorizationCode authCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(dropboxConfig.getAppKey(), dropboxConfig.getAppSecret());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", authCode.getAuthorizationCode());
        map.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<DropboxAccessTokenResponse> response = restTemplate.exchange(
                "https://api.dropbox.com/oauth2/token",
                HttpMethod.POST,
                request,
                DropboxAccessTokenResponse.class
        );
        return response;
    }

    private Boolean saveAccessToken(ResponseEntity<DropboxAccessTokenResponse> response) {
        response.getBody().getAccessToken();
        if (response.getBody() != null && response.getBody().getAccessToken() != null) {
            String encode = accessTokenHelper.encodeAccessToken(response.getBody().getAccessToken());
            Authorization authorization = new Authorization();
            authorization.setKey("DropboxAccessToken");
            authorization.setValue(encode);
            Authorization savedAuthorization = repository.save(authorization);
            return savedAuthorization != null;
        }

        return false;
    }
}
