package com.earosslot.beccmusicservice.service;

import com.earosslot.beccmusicservice.clients.musicbrainz.MusicBrainzClient;
import com.earosslot.beccmusicservice.clients.musicbrainz.entity.ArtistMB;
import com.earosslot.beccmusicservice.entity.Album;
import com.earosslot.beccmusicservice.entity.Artist;
import com.earosslot.beccmusicservice.entity.ArtistBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MusicBrainzService {

    private final MusicBrainzClient musicBrainzClient;

    @Autowired
    MusicBrainzService(MusicBrainzClient musicBrainzClient) {
        this.musicBrainzClient = musicBrainzClient;
    }

    public Artist getArtist(String mbid) {

        ArtistMB artistMB = musicBrainzClient.getArtist(mbid);
        return new ArtistBuilder()
                .setMbid(artistMB.getId())
                .setName(artistMB.getName())
                .setGender(artistMB.getGender())
                .setCountry(artistMB.getCountry())
                .setDisambiguation(artistMB.getDisambiguation())
                .setWikidataId(artistMB.getWikidataEntityId())
                .setAlbums(artistMB.getReleaseGroups().stream().map(Album::new).collect(Collectors.toList()))
                .createArtist();
    }
}
