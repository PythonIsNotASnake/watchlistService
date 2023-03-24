package com.pinas.watchlistService.api.controller;

import com.pinas.watchlistService.api.model.AccessToken;
import com.pinas.watchlistService.db.entity.Record;
import com.pinas.watchlistService.handler.BackupHandler;
import com.pinas.watchlistService.api.response.ResponseRecords;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("backups")
public class BackupController {

    private final BackupHandler backupHandler;

    public BackupController(BackupHandler backupHandler) {
        this.backupHandler = backupHandler;
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    @ResponseBody
    public ResponseRecords restoreBackup(@RequestBody AccessToken dropboxAccessToken) {
        return backupHandler.restoreEntities(dropboxAccessToken);
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    @ResponseBody
    public ResponseRecords storeBackup(@RequestBody AccessToken dropboxAccessToken) {
        return backupHandler.backupEntities(dropboxAccessToken);
    }
}
