package com.earosslot.beccmusicservice.entity;

import java.util.List;

public class ArtistBuilder {
    private String mbid;
    private String name;
    private String gender;
    private String country;
    private String disambiguation;
    private List<Album> albums;
    private String wikidataId;

    public ArtistBuilder setMbid(String mbid) {
        this.mbid = mbid;
        return this;
    }

    public ArtistBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ArtistBuilder setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public ArtistBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public ArtistBuilder setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
        return this;
    }

    public ArtistBuilder setAlbums(List<Album> albums) {
        this.albums = albums;
        return this;
    }

    public ArtistBuilder setWikidataId(String wikidataId) {
        this.wikidataId = wikidataId;
        return this;
    }

    public Artist createArtist() {
        return new Artist(mbid, name, gender, country, disambiguation, albums, wikidataId);
    }
}