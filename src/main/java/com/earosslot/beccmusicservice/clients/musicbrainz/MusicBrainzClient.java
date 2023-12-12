package com.earosslot.beccmusicservice.clients.musicbrainz;

import com.earosslot.beccmusicservice.clients.RestTemplateErrorHandler;
import com.earosslot.beccmusicservice.clients.musicbrainz.entity.ArtistMB;
import com.earosslot.beccmusicservice.exeptions.ArtistNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class MusicBrainzClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicBrainzClient.class);

    private final String url;
    private final RestTemplate restTemplate;
    private final HttpEntity<Void> voidHttpEntity;

    @Autowired
    public MusicBrainzClient(@Value("${musify.client.music-brainz.artist-url}") String url,
                             @Value("${musify.client.music-brainz.user-agent-header}")String userAgentHeader) {
        restTemplate = new RestTemplateBuilder()
                .errorHandler(new RestTemplateErrorHandler())
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgentHeader);
        voidHttpEntity = new HttpEntity<>(headers);

        this.url = url;
    }

    public MusicBrainzClient(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
        voidHttpEntity = new HttpEntity<>(new HttpHeaders());
    }

    @Cacheable("artist-mb")
    public ArtistMB getArtist(String mbid) {
        try {
            String url = String.format(this.url, mbid);

            ResponseEntity<ArtistMB> responseEntity = restTemplate.exchange(url, HttpMethod.GET, voidHttpEntity, ArtistMB.class);
            ArtistMB artistMB = responseEntity.getBody();

            artistMB.keepRelation("wikidata");
            return artistMB;

        } catch (RestClientException exception) {
            LOGGER.debug("Error {} getting Artist info from Music Brainz {} \n {}", exception.getClass().getName(), exception.getMessage(), exception.getStackTrace());
            throw new ArtistNotFoundException("Artist Not Found");
        } catch (Exception exception) {
            LOGGER.warn("Error {} getting Artist info from Music Brainz {} \n {}", exception.getClass().getName(), exception.getMessage(), exception.getStackTrace());
            throw new ArtistNotFoundException("Artist Not Found");
        }
    }
}
