package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.DropboxConfig;
import com.pinas.watchlistService.api.model.AccessToken;
import com.pinas.watchlistService.api.response.ResponseRecords;
import com.pinas.watchlistService.db.entity.Record;
import com.pinas.watchlistService.db.repository.RecordRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BackupHandler {

    private final RecordRepository repository;
    private final DropboxConfig dropboxConfig;

    public BackupHandler(RecordRepository repository, DropboxConfig dropboxConfig) {
        this.repository = repository;
        this.dropboxConfig = dropboxConfig;
    }

    public ResponseRecords restoreEntities(AccessToken dropboxAccessToken) {
        restoreRecords(dropboxAccessToken);
        return getFullResponseRecords();
    }

    public ResponseRecords backupEntities(AccessToken dropboxAccessToken) {
        backupRecords(dropboxAccessToken);
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

    private void backupRecords(AccessToken dropboxAccessToken) {
        List<Record> allRecords = repository.findAll();
        dropboxConfig.uploadBackup(allRecords, dropboxAccessToken.getAccessToken());
    }

    private void restoreRecords(AccessToken dropboxAccessToken) {
        List<Record> records = dropboxConfig.downloadBackup(dropboxAccessToken.getAccessToken());
        if (records != null) {
            repository.deleteAll();
            repository.saveAll(records);
        }
    }
}
