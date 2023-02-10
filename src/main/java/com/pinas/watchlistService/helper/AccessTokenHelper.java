package com.pinas.watchlistService.helper;

import com.pinas.watchlistService.db.entity.Authorization;
import com.pinas.watchlistService.db.repository.AuthorizationRepository;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenHelper {

    private final AuthorizationRepository repository;

    public AccessTokenHelper(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public String getDropboxAccessToken() {
        return decodeAccessToken(getAccessToken("DropboxAccessToken"));
    }

    public String getMastodonAccessToken() {
        return decodeAccessToken(getAccessToken("MastodonAccessToken"));
    }

    private String getAccessToken(String key) {
        Authorization accessToken = repository.findById(key).orElseThrow();
        return accessToken.getValue();
    }

    public String getMastodonUrl() {
        Authorization mastodonUrl = repository.findById("MastodonUrl").orElseThrow();
        return mastodonUrl.getValue();
    }

    public String decodeAccessToken(String encodedAccessToken) {
        return new String(Base64.getDecoder().decode(encodedAccessToken));
    }

    public String encodeAccessToken(String decodedAccessToken) {
        return Base64.getEncoder().encodeToString(decodedAccessToken.getBytes());
    }

}
