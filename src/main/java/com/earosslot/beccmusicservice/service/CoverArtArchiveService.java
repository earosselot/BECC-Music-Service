package com.earosslot.beccmusicservice.service;

import com.earosslot.beccmusicservice.clients.coverartarchive.CoverArtArchiveClient;
import com.earosslot.beccmusicservice.entity.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoverArtArchiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoverArtArchiveService.class);

    final CoverArtArchiveClient coverArtArchiveClient;

    @Autowired
    public CoverArtArchiveService(CoverArtArchiveClient coverArtArchiveClient) {
        this.coverArtArchiveClient = coverArtArchiveClient;
    }

    public void fillImageLink(Album album) {
        try {
            String albumImage = coverArtArchiveClient.getAlbumImage(album.getId());
            album.setImageUrl(albumImage);
            LOGGER.trace("album " + album.getId() + "completed with cover link: " + album.getImageUrl());
        } catch (Exception e) {
            LOGGER.debug("album " + album.getId() + " cover not found.");
        }
    }
}
