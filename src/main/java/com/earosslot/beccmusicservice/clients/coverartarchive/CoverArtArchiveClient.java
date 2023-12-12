package com.earosslot.beccmusicservice.clients.coverartarchive;

import com.earosslot.beccmusicservice.clients.coverartarchive.entity.AlbumCover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class CoverArtArchiveClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoverArtArchiveClient.class);

    private final String url;
    private final RestTemplate restTemplate;

    @Autowired
    public CoverArtArchiveClient(@Value("${musify.client.cover-art-archive.cover-url}") String url) {
        this.url = url;
        restTemplate = new RestTemplateBuilder()
           .setConnectTimeout(Duration.ofSeconds(2))
           .setReadTimeout(Duration.ofSeconds(2))
           .build();
    }

    public CoverArtArchiveClient(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Cacheable("album-image")
    public String getAlbumImage(String id) {

        LOGGER.debug("album " + id);

        String coverArtArchiveUrl = String.format(url, id);

        ResponseEntity<AlbumCover> responseEntity = restTemplate.getForEntity(coverArtArchiveUrl, AlbumCover.class);
        AlbumCover albumCover = responseEntity.getBody();

        return albumCover.getFrontImage();
    }
}


