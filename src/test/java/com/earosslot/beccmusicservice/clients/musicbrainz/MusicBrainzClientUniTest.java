package com.earosslot.beccmusicservice.clients.musicbrainz;

import com.earosslot.beccmusicservice.clients.musicbrainz.entity.ArtistMB;
import com.earosslot.beccmusicservice.clients.musicbrainz.entity.ArtistMBBuilder;
import com.earosslot.beccmusicservice.exeptions.ArtistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MusicBrainzClientUniTest {

    public static final String MBID = "MBID";
    @Mock
    private RestTemplate restTemplate;
    private MusicBrainzClient musicBrainzClient;

    @BeforeEach
    void setUp() {
        String url = "https://musicbrainz.com/artist%s";
        MusicBrainzConfig musicBrainzConfig = new MusicBrainzConfig();
        musicBrainzConfig.setArtistUrl(url);
        musicBrainzClient = new MusicBrainzClient(restTemplate, musicBrainzConfig);
    }

    @Test
    @DisplayName("01. Given an MBID, when MB answers correctly, then return Artist")
    void test01() {
        ArtistMB expectedArtistMB = new ArtistMBBuilder().setId(MBID).createArtistMB();
        mockOkResponse(expectedArtistMB);

        ArtistMB actualArtistMB = musicBrainzClient.getArtist(MBID);

        assertEquals(expectedArtistMB, actualArtistMB);
    }

    @Test
    @DisplayName("02. Given an MBID, when MB throws an Exception, then throw ArtistNotFoundException")
    void test02() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(ArtistMB.class)))
          .thenThrow(new RestClientException("Http Error"));

        assertThrows(ArtistNotFoundException.class, () -> musicBrainzClient.getArtist(MBID));
    }

    @Test
    @DisplayName("03. Given an MBID, when MB body is null, then throw ArtistNotFoundException")
    void test03() {
        mockOkResponse(null);

        assertThrows(ArtistNotFoundException.class, () -> musicBrainzClient.getArtist(MBID));
    }

    private void mockOkResponse(ArtistMB expectedArtistMB) {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(ArtistMB.class)))
          .thenReturn(new ResponseEntity<>(expectedArtistMB, HttpStatus.OK));
    }


}