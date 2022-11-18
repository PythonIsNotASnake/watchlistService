package com.pinas.watchlistService.api.controller;

import com.pinas.watchlistService.handler.AuthorizationHandler;
import com.pinas.watchlistService.api.model.auth.AuthorizationCode;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("dropbox")
public class DropboxController {

    private final AuthorizationHandler authorizationHandler;

    public DropboxController(AuthorizationHandler authorizationHandler) {
        this.authorizationHandler = authorizationHandler;
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    public Boolean authorize(@RequestBody AuthorizationCode authCode) {
        return authorizationHandler.createAccessToken(authCode);
    }
}
