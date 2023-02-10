package com.pinas.watchlistService.api.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class MastodonStatusesResponse {
    @JsonProperty("id")
    String id;

    @JsonProperty("created_at")
    String createdAt;
}
