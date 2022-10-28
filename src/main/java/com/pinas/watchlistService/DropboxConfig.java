package com.pinas.watchlistService;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinas.watchlistService.entity.Record;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DropboxConfig {

    @Value("${dropbox.accesstoken}")
    private String accessToken;

    private final DbxRequestConfig config;

    public DropboxConfig() {
        this.config = DbxRequestConfig.newBuilder("dropbox/watchlist-service").build();
    }

    public FullAccount getAccountInfo() throws DbxException {
        DbxClientV2 client = new DbxClientV2(config, accessToken);
        FullAccount account = client.users().getCurrentAccount();
        return account;
    }

    public void uploadBackup(List<Record> records) {
        DbxClientV2 client = new DbxClientV2(config, accessToken);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("./data/records.txt"), records);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (InputStream in = new FileInputStream("./data/records.txt")) {
            client.files().uploadBuilder("/records.txt")
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
        } catch (UploadErrorException e) {
            throw new RuntimeException(e);
        } catch (DbxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
