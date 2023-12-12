package com.earosslot.beccmusicservice.clients.wikipedia;

import com.earosslot.beccmusicservice.clients.wikipedia.entity.WikipediaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WikipediaClient {

    private final String url;
    private final RestTemplate restTemplate;


    public WikipediaClient(@Value("${musify.client.wikipedia.summary-url}") String url) {
        this.url = url;
        restTemplate = new RestTemplate();
    }

    @Cacheable("artist-wikipedia")
    public String getArtistDescription(String artistTitle) {

        String wikipediaUrl = String.format(url, artistTitle);

        ResponseEntity<WikipediaResponse> responseEntity = restTemplate.getForEntity(wikipediaUrl, WikipediaResponse.class);
        WikipediaResponse artistWiki = responseEntity.getBody();

        return artistWiki.getExtractHtml();
    }
}
