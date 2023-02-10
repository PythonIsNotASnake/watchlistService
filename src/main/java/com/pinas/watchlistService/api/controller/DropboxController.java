package com.pinas.watchlistService.api.controller;

import com.pinas.watchlistService.api.model.auth.DropboxAuthorizationCode;
import com.pinas.watchlistService.handler.DropboxAuthorizationHandler;
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

    private final DropboxAuthorizationHandler dropboxAuthorizationHandler;

    public DropboxController(DropboxAuthorizationHandler dropboxAuthorizationHandler) {
        this.dropboxAuthorizationHandler = dropboxAuthorizationHandler;
    }

    @RequestMapping(value = "/authorized", method = RequestMethod.GET)
    @ResponseBody
    public Boolean authorized() {
        return dropboxAuthorizationHandler.existsAccessToken();
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    public Boolean authorize(@RequestBody DropboxAuthorizationCode authCode) {
        return dropboxAuthorizationHandler.createAccessToken(authCode);
    }
}
