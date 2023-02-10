package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.api.model.auth.MastodonStatusesResponse;
import com.pinas.watchlistService.api.model.auth.Toot;
import com.pinas.watchlistService.db.entity.Record;
import com.pinas.watchlistService.db.repository.RecordRepository;
import com.pinas.watchlistService.helper.AccessTokenHelper;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class MastodonHandler {

    private final AccessTokenHelper accessTokenHelper;
    private final RecordRepository recordRepository;

    public MastodonHandler(AccessTokenHelper accessTokenHelper, RecordRepository recordRepository) {
        this.accessTokenHelper = accessTokenHelper;
        this.recordRepository = recordRepository;
    }

    public boolean toot(Toot toot) {
        Optional<Record> entity = recordRepository.findById(toot.getRecordId());
        if (entity.isEmpty()) {
            return false;
        }
        String tootBody = buildToot(entity.get());

        String mastodonUrl = accessTokenHelper.getMastodonUrl();
        String accessToken = accessTokenHelper.getMastodonAccessToken();

        return sendToot(tootBody, mastodonUrl, accessToken);
    }

    private boolean sendToot(String toot, String mastodonUrl, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("status", toot);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        mastodonUrl = mastodonUrl.endsWith("/") ? mastodonUrl : mastodonUrl + "/";

        ResponseEntity<MastodonStatusesResponse> response = restTemplate.exchange(
                mastodonUrl + "api/v1/statuses",
                HttpMethod.POST,
                request,
                MastodonStatusesResponse.class
        );
        HttpStatus statusCode = response.getStatusCode();
        String createdAt = response.getBody() != null ? response.getBody().getCreatedAt() : null;
        return (statusCode == HttpStatus.OK || statusCode == HttpStatus.CREATED)
                && createdAt != null
                && !createdAt.isEmpty();
    }

    private String buildToot(Record record) {
        String trailer = record.getLink().replace("embed/", "watch?v=");

        var toot = new StringBuilder();
        toot.append("Title: " + record.getTitle() + "\n");
        toot.append("Genre: " + record.getGenre() + "\n");
        toot.append("Trailer: " + trailer + "\n");

        toot.append("\nCreated by Watchlist\n");
        toot.append("https://github.com/PythonIsNotASnake/watchlistFrontend\n");
        toot.append("https://github.com/PythonIsNotASnake/watchlistService\n");
        toot.append("#watchlist #mastodonapi");

        return toot.toString();
    }
}
