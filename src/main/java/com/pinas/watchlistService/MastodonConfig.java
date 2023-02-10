package com.pinas.watchlistService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MastodonConfig {

    @Value("${mastodon.appkey}")
    private String appKey;

    @Value("${mastodon.appsecret}")
    private String appSecret;

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public MastodonConfig() {
    }
}
