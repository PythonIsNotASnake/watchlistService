package com.pinas.watchlistService.api.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class DropboxAccessTokenResponse {

    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("expires_in")
    Long expiresIn;

    @JsonProperty("scope")
    String scope;

    @JsonProperty("uid")
    String uid;

    @JsonProperty("account_id")
    String accountId;
}
