package com.earosslot.beccmusicservice.clients.musicbrainz.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
            LOGGER.error("Failed to keep relation " + relationType, e);
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
}
