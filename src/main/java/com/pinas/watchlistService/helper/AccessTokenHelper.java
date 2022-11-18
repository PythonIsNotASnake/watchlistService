package com.pinas.watchlistService.helper;

import com.pinas.watchlistService.db.entity.Authorization;
import com.pinas.watchlistService.db.repository.AuthorizationRepository;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class AccessTokenHelper {

    private final AuthorizationRepository repository;

    public AccessTokenHelper(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public String getAccessToken() {
        Authorization accessToken = repository.findById("AccessToken").orElseThrow();
        return decodeAccessToken(accessToken.getValue());
    }

    public String decodeAccessToken(String encodedAccessToken) {
        return new String(Base64.getDecoder().decode(encodedAccessToken));
    }

    public String encodeAccessToken(String decodedAccessToken) {
        return Base64.getEncoder().encodeToString(decodedAccessToken.getBytes());
    }

}
