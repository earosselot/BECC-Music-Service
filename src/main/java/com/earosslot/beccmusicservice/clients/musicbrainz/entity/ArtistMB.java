package com.earosslot.beccmusicservice.clients.musicbrainz.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArtistMB {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistMB.class);

    String id;

    String name;

    private String gender;

    private String country;

    private String disambiguation;

    private List<Relation> relations;

    @JsonAlias("release-groups")
    private List<AlbumMB> releaseGroups;

    public ArtistMB() {
    }

    public ArtistMB(String id, String name, String gender, String country, String disambiguation, List<Relation> relations, List<AlbumMB> releaseGroups) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.disambiguation = disambiguation;
        this.relations = relations;
        this.releaseGroups = releaseGroups;
    }

    /**
     * From all the relations in the objets, delete all but the one having type == relationType.
     * This method is useful to keep only the relation relevant to the application.
     * @param relationType to be kept
     */
    public void keepRelation(String relationType) {
        try {
            relations = relations.stream().filter(
                    relation -> relation.getType().equals(relationType)
            ).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.debug("Failed to keep relation " + relationType);
        }
    }

    /**
     * @param relationType to check
     * @return whether the artist has or not the relationType
     */
    public boolean hasRelation(String relationType) {
        if (relations == null) {
            return false;
        }
        return relations.stream().anyMatch(relation -> relation.getType().equals(relationType));
    }

    /**
     * @param numberOfRelations to check for the artist
     * @return whether the number of relations of the artist equals to numberOfRelations
     */
    public boolean hasRelationsNumber(int numberOfRelations) {
        if (relations == null) {
            return numberOfRelations == 0;
        }
        return relations.size() == numberOfRelations;
    }

    /**
     * The wikidata Entity ID of the artist is found in the wikidata Relation's url.
     * This provides an url to a wikipedia html page.
     * This method extracts the Wikidata Entity ID from that url.
     *
     * @return the wikidata Entity ID of the artist
     */
    public String getWikidataEntityId() {
        try {
            Relation wikidata = findWikidataRelation();
            return extractWikiDataEntity(wikidata);
        } catch (Exception exception) {
            LOGGER.trace(exception.getMessage());
            return "";
        }
    }

    private Relation findWikidataRelation() {
        return relations.stream()
                .filter(relation -> relation.getType().equals("wikidata"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Relation Not Found: " + "wikidata"));
    }

    private static String extractWikiDataEntity(Relation wikidata) {
        String wikidataUrl = wikidata.getUrl().getResource();
        String[] urlSegments = wikidataUrl.split("/");
        return urlSegments[urlSegments.length - 1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistMB artistMB = (ArtistMB) o;

        if (!id.equals(artistMB.id)) return false;
        if (!Objects.equals(name, artistMB.name)) return false;
        if (!Objects.equals(gender, artistMB.gender)) return false;
        if (!Objects.equals(country, artistMB.country)) return false;
        if (!Objects.equals(disambiguation, artistMB.disambiguation))
            return false;
        if (!Objects.equals(relations, artistMB.relations)) return false;
        return Objects.equals(releaseGroups, artistMB.releaseGroups);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public List<AlbumMB> getReleaseGroups() {
        return releaseGroups;
    }

    public void setReleaseGroups(List<AlbumMB> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }


}
