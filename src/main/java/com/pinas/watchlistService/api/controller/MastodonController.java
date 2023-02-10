package com.pinas.watchlistService.api.controller;

import com.pinas.watchlistService.api.model.auth.MastodonAuthorizationCredentials;
import com.pinas.watchlistService.api.model.auth.Toot;
import com.pinas.watchlistService.handler.MastodonAuthorizationHandler;
import com.pinas.watchlistService.handler.MastodonHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("mastodon")
public class MastodonController {

    private final MastodonAuthorizationHandler mastodonAuthorizationHandler;
    private final MastodonHandler mastodonHandler;

    public MastodonController(
            MastodonAuthorizationHandler mastodonAuthorizationHandler,
            MastodonHandler mastodonHandler) {
        this.mastodonAuthorizationHandler = mastodonAuthorizationHandler;
        this.mastodonHandler = mastodonHandler;
    }

    @RequestMapping(value = "/authorized", method = RequestMethod.GET)
    @ResponseBody
    public Boolean authorized() {
        return mastodonAuthorizationHandler.isAuthorized();
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    public Boolean authorize(@RequestBody MastodonAuthorizationCredentials credentials) {
        return mastodonAuthorizationHandler.createAccessToken(credentials);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Boolean logout() {
        return mastodonAuthorizationHandler.logout();
    }

    @RequestMapping(value = "/toot", method = RequestMethod.POST)
    @ResponseBody
    public Boolean toot(@RequestBody Toot toot) {
        return mastodonHandler.toot(toot);
    }
}
