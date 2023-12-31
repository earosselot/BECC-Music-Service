package com.earosslot.beccmusicservice.clients.wikidata;

import com.earosslot.beccmusicservice.clients.wikidata.entity.ArtistInfo;
import com.earosslot.beccmusicservice.clients.wikidata.entity.WikidataResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class WikidataClient {

    private final String url;
    private final RestTemplate restTemplate;

    public WikidataClient(@Value("${musify.client.wikidata.entity-data-url}") String url) {
        restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(2))
                .build();
        this.url = url;
    }

    @Cacheable("artist-wikidata")
    public ArtistInfo getArtistInfo(String wikidataEntityId) {

        String wikidataUrl = String.format(url, wikidataEntityId);

        ResponseEntity<WikidataResponse> responseEntity = restTemplate.getForEntity(wikidataUrl, WikidataResponse.class);
        WikidataResponse wikidataResponse = responseEntity.getBody();

        return wikidataResponse.getEntities().get(wikidataEntityId);
    }
}
