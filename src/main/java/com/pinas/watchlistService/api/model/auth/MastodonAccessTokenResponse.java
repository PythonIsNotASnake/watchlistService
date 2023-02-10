package com.pinas.watchlistService.api.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class MastodonAccessTokenResponse {
    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("scope")
    String scope;

    @JsonProperty("created_at")
    Long createdAt;
}
