package com.earosslot.beccmusicservice.clients.musicbrainz.entity;

import java.util.List;

public class ArtistMBBuilder {
    private String id;
    private String name;
    private String gender;
    private String country;
    private String disambiguation;
    private List<Relation> relations;
    private List<AlbumMB> releaseGroups;

    public ArtistMBBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ArtistMBBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ArtistMBBuilder setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public ArtistMBBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public ArtistMBBuilder setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
        return this;
    }

    public ArtistMBBuilder setRelations(List<Relation> relations) {
        this.relations = relations;
        return this;
    }

    public ArtistMBBuilder setReleaseGroups(List<AlbumMB> releaseGroups) {
        this.releaseGroups = releaseGroups;
        return this;
    }

    public ArtistMB createArtistMB() {
        return new ArtistMB(id, name, gender, country, disambiguation, relations, releaseGroups);
    }
}