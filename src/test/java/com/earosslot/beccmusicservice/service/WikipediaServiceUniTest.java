package com.earosslot.beccmusicservice.service;

import com.earosslot.beccmusicservice.clients.wikidata.WikidataClient;
import com.earosslot.beccmusicservice.clients.wikidata.entity.ArtistInfo;
import com.earosslot.beccmusicservice.clients.wikidata.entity.SiteLink;
import com.earosslot.beccmusicservice.clients.wikidata.entity.SiteLinksMap;
import com.earosslot.beccmusicservice.clients.wikipedia.WikipediaClient;
import com.earosslot.beccmusicservice.entity.Artist;
import com.earosslot.beccmusicservice.entity.ArtistBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WikipediaServiceUniTest {

    @Mock
    private WikidataClient wikidataClient;
    @Mock
    private WikipediaClient wikipediaClient;
    private WikipediaService wikipediaService;

    @BeforeEach
    void setUp() {
        wikipediaService = new WikipediaService(wikipediaClient, wikidataClient);
    }

    @Test
    @DisplayName("01. Fills correctly the artist description")
    void test01TheArtistDescriptionIsCompleted() {

        String artistTitle = "artist Title";

        ArtistInfo artistInfo = artistInfoWithEnwikiSitelink(artistTitle);
        when(wikidataClient.getArtistInfo(anyString())).thenReturn(artistInfo);

        String artistDescription = "Description for Artist.";
        when(wikipediaClient.getArtistDescription(artistTitle)).thenReturn(artistDescription);

        Artist artist = artistWithoutDescription();
        wikipediaService.fillDescription(artist);
        assertEquals(artistDescription, artist.getDescription());
    }

    @Test
    @DisplayName("02. The artist remains unchanged if failed to obtain artistInfo from wikidata")
    void test02ArtistDoesNotChangeOnWikidataError() {
        when(wikidataClient.getArtistInfo(anyString()))
                .thenThrow(new RuntimeException("Failed to obtain artist info"));

        Artist artist = artistWithoutDescription();
        wikipediaService.fillDescription(artist);
        assertEquals(artistWithoutDescription(), artist);
    }

    @Test
    @DisplayName("03. The artist remains unchanged if failed to obtain artistInfo from wikipedia")
    void test03ArtistDoesNotChangeOnWikipediaError() {

        String artistTitle = "artist Title";

        ArtistInfo artistInfo = artistInfoWithEnwikiSitelink(artistTitle);
        when(wikidataClient.getArtistInfo(anyString())).thenReturn(artistInfo);
        when(wikipediaClient.getArtistDescription(artistTitle))
                .thenThrow(new RuntimeException("Failed to obtain artist description"));

        Artist artist = artistWithoutDescription();
        wikipediaService.fillDescription(artist);
        assertEquals(artistWithoutDescription(), artist);
    }


    private static ArtistInfo artistInfoWithEnwikiSitelink(String artistTitle) {
        SiteLinksMap sitelinks = new SiteLinksMap();
        SiteLink siteLink = new SiteLink(WikipediaService.SITELINK_ENWIKI, artistTitle);
        sitelinks.setSiteLinkHashMap(WikipediaService.SITELINK_ENWIKI, siteLink);

        ArtistInfo artistInfo = new ArtistInfo();
        artistInfo.setSitelinks(sitelinks);
        return artistInfo;
    }

    private static Artist artistWithoutDescription() {
        return new ArtistBuilder().setMbid("mbid").setName("Artist Name").setGender("Artist gender").setCountry("Artist country").setDisambiguation("Artist disanbiguation").setAlbums(new ArrayList<>()).setWikidataId("wikiDataId").createArtist();
    }

}