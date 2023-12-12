package com.earosslot.beccmusicservice.service;

import com.earosslot.beccmusicservice.clients.wikidata.WikidataClient;
import com.earosslot.beccmusicservice.clients.wikidata.entity.ArtistInfo;
import com.earosslot.beccmusicservice.clients.wikipedia.WikipediaClient;
import com.earosslot.beccmusicservice.entity.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WikipediaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikipediaService.class);
    public static final String SITELINK_ENWIKI = "enwiki";

    private final WikipediaClient wikipediaClient;

    private final WikidataClient wikidataClient;

    @Autowired
    public WikipediaService(WikipediaClient wikipediaClient, WikidataClient wikidataClient) {
        this.wikipediaClient = wikipediaClient;
        this.wikidataClient = wikidataClient;
    }

    public void fillDescription(Artist artist) {

        try {
            String artistTitle = getArtistTitleFromWikidata(artist);
            getAndFillDescriptionFromWikipedia(artist, artistTitle);

            LOGGER.debug("Wikipedia END");
        } catch (Exception e) {
            LOGGER.error("Description not found for artist : " + artist);
        }

    }

    private String getArtistTitleFromWikidata(Artist artist) {
        ArtistInfo artistInfo = wikidataClient.getArtistInfo(artist.getWikidataId());
        String artistTitle = artistInfo.getSitelinkTitle(SITELINK_ENWIKI);
        LOGGER.trace("Artist title obtained from Wikidata: " + artistTitle);
        return artistTitle;

    }

    private void getAndFillDescriptionFromWikipedia(Artist artist, String artistTitle) {
        String description = wikipediaClient.getArtistDescription(artistTitle);
        artist.setDescription(description);
        LOGGER.trace("Artist " + artist + " description obtained from Wikipedia and updated for wikidata title: " + artistTitle);
    }
}
