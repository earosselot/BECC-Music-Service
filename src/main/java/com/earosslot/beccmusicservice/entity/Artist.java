package com.earosslot.beccmusicservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Artist {

    private final String mbid;
    private final String name;
    private final String gender;
    private final String country;
    private final String disambiguation;
    private String description;
    private final List<Album> albums;
    @JsonIgnore
    private final String wikidataId;

    public Artist(String mbid, String name, String gender, String country, String disambiguation, List<Album> albums, String wikidataId) {
        this.mbid = mbid;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.disambiguation = disambiguation;
        this.albums = albums;
        this.wikidataId = wikidataId;
    }

    @Override
    public String toString() {
        return "Artist {" + "\n" +
                " mbid='" + mbid + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", gender='" + gender + '\'' + "\n" +
                ", country='" + country + '\'' + "\n" +
                ", disambiguation='" + disambiguation + '\'' + "\n" +
                ", description='" + description + '\'' + "\n" +
                ", albums=" + albums + "\n" +
                ", wikidataId='" + wikidataId + '\'' + "\n" +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (!mbid.equals(artist.mbid)) return false;
        if (!Objects.equals(name, artist.name)) return false;
        if (!Objects.equals(gender, artist.gender)) return false;
        if (!Objects.equals(country, artist.country)) return false;
        return Objects.equals(disambiguation, artist.disambiguation);
    }

    @Override
    public int hashCode() {
        return mbid.hashCode();
    }

    public String getMbid() {
        return mbid;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public String getDescription() {
        return description;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWikidataId() {
        return wikidataId;
    }
}
