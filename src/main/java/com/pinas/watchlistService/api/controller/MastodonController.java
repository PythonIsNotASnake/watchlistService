package com.pinas.watchlistService.api.controller;

import com.pinas.watchlistService.api.model.auth.AuthorizationCode;
import com.pinas.watchlistService.handler.AuthorizationHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("dropbox")
public class DropboxController {

    private final AuthorizationHandler authorizationHandler;

    public DropboxController(AuthorizationHandler authorizationHandler) {
        this.authorizationHandler = authorizationHandler;
    }

    @RequestMapping(value = "/authorized", method = RequestMethod.GET)
    @ResponseBody
    public Boolean authorized() {
        return authorizationHandler.existsAccessToken();
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    public Boolean authorize(@RequestBody AuthorizationCode authCode) {
        return authorizationHandler.createAccessToken(authCode);
    }
}
