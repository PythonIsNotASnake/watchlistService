package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.DropboxConfig;
import com.pinas.watchlistService.db.entity.Record;
import com.pinas.watchlistService.helper.AccessTokenHelper;
import com.pinas.watchlistService.db.repository.RecordRepository;
import com.pinas.watchlistService.api.response.ResponseRecords;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BackupHandler {

    private final RecordRepository repository;
    private final AccessTokenHelper accessTokenHelper;
    private final DropboxConfig dropboxConfig;

    public BackupHandler(RecordRepository repository, AccessTokenHelper accessTokenHelper, DropboxConfig dropboxConfig) {
        this.repository = repository;
        this.accessTokenHelper = accessTokenHelper;
        this.dropboxConfig = dropboxConfig;
    }

    public ResponseRecords restoreEntities() {
        restoreRecords();
        return getFullResponseRecords();
    }

    public ResponseRecords backupEntities() {
        backupRecords();
        return getFullResponseRecords();
    }

    private ResponseRecords getFullResponseRecords() {
        List<Record> records = repository.findAll();
        long total = repository.count();

        return buildResponseRecords(records, total);
    }

    private ResponseRecords buildResponseRecords(List<Record> records, long total) {
        ResponseRecords response = new ResponseRecords();
        response.setData(records);
        response.setCount(records.size());
        response.setTotal(total);
        return response;
    }

    private void backupRecords() {
        String accessToken = accessTokenHelper.getAccessToken();
        List<Record> allRecords = repository.findAll();
        dropboxConfig.uploadBackup(allRecords, accessToken);
    }

    private void restoreRecords() {
        String accessToken = accessTokenHelper.getAccessToken();
        List<Record> records = dropboxConfig.downloadBackup(accessToken);
        if (records != null) {
            repository.deleteAll();
            repository.saveAll(records);
        }
    }
}
