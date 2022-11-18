package com.pinas.watchlistService;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinas.watchlistService.db.entity.Record;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class DropboxConfig {

    @Value("${dropbox.appkey}")
    private String appKey;

    @Value("${dropbox.appsecret}")
    private String appSecret;

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    private final DbxRequestConfig config;

    public DropboxConfig() {
        this.config = DbxRequestConfig.newBuilder("dropbox/watchlist-service").build();
    }

    public void uploadBackup(List<Record> records, String accessToken) {
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

    public List<Record> downloadBackup(String accessToken) {
        DbxClientV2 client = new DbxClientV2(config, accessToken);

        try (OutputStream out = new FileOutputStream("./data/restorerecords.txt")) {
            client.files().downloadBuilder("/records.txt").download(out);
        } catch (DbxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Record> records = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            records = objectMapper.readValue(new File("./data/restorerecords.txt"), new TypeReference<List<Record>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }
}
