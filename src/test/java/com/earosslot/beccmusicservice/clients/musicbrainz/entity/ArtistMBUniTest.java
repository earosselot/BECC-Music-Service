package com.earosslot.beccmusicservice.clients.musicbrainz.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistMBUniTest {


    public static final String WIKIDATA_RELATION_TYPE = "wikidata";
    public static final String GENERIC_REALATION_TYPE = "generic";

    @Test
    @DisplayName("01. keepRelation only keeps the artist's pointed relation")
    void test01OnlyKeepsDesiredRelation() {
        ArtistMB artistMB = artistWithWikidataRelation();

        assertTrue(artistMB.hasRelationsNumber(2));

        artistMB.keepRelation(WIKIDATA_RELATION_TYPE);

        assertTrue(artistMB.hasRelation(WIKIDATA_RELATION_TYPE));
        assertFalse(artistMB.hasRelation(GENERIC_REALATION_TYPE));
        assertTrue(artistMB.hasRelationsNumber(1));
    }

    @Test
    @DisplayName("02. keepRelation does not keep anything if pointed relation does not exist")
    void test02IfNoDesiredDirectionThenRelationWillBeEmpty() {
        ArtistMB artistMB = artistWithoutWikidataRelation();

        artistMB.keepRelation(WIKIDATA_RELATION_TYPE);

        assertTrue(artistMB.hasRelationsNumber(0));
    }

    @Test
    @DisplayName("03. keepRelation has no effect is relations is empty")
    void test03IfArtistHasNoRelationsKeepsNothing() {
        ArtistMB artistMB = artistWithoutRelations();

        artistMB.keepRelation(WIKIDATA_RELATION_TYPE);

        assertTrue(artistMB.hasRelationsNumber(0));
    }

    @Test
    @DisplayName("04. keepRelation has no effect is relations is null")
    void test04IfArtistRelationsAreNullKeepsNull() {
        ArtistMB artistMB = new ArtistMBBuilder().createArtistMB();

        artistMB.keepRelation(WIKIDATA_RELATION_TYPE);

        assertNull(artistMB.getRelations());
        assertTrue(artistMB.hasRelationsNumber(0));
        assertFalse(artistMB.hasRelation(WIKIDATA_RELATION_TYPE));
    }

    @Test
    @DisplayName("05. getWikidataEntityId returns the last part of wikidata relation url")
    void test05getWikidataEntityIDCorrectly() {
        String wikidataId = "REL1234";
        ArtistMB artistMB = artistWithWikidataRelationAndWikiId(wikidataId);

        String wikidataEntityId = artistMB.getWikidataEntityId();

        assertEquals(wikidataId, wikidataEntityId);
    }

    @Test
    @DisplayName("06. getWikidataEntityId returns empty String if no wikidata relation")
    void test06WhenNoWikidataRelationGetsEmptyString() {
        ArtistMB artistMB = artistWithoutWikidataRelation();

        String wikidataEntityId = artistMB.getWikidataEntityId();

        assertTrue(wikidataEntityId.isEmpty());
    }

    @Test
    @DisplayName("06. getWikidataEntityId returns empty String if no relation")
    void test07WhenNoRelationsGetsEmptyString() {
        ArtistMB artistMB = artistWithoutRelations();

        String wikidataEntityId = artistMB.getWikidataEntityId();

        assertTrue(wikidataEntityId.isEmpty());
    }

    private ArtistMB artistWithWikidataRelationAndWikiId(String wikidataId) {
        List<Relation> relations = relationsWith(createRelation(WIKIDATA_RELATION_TYPE, wikidataId), createRelation(GENERIC_REALATION_TYPE));
        return artistWithRelations(relations);
    }

    private ArtistMB artistWithWikidataRelation() {
        return artistWithWikidataRelationAndWikiId("genericId");
    }

    private ArtistMB artistWithoutWikidataRelation() {
        List<Relation> relations = relationsWith(createRelation(GENERIC_REALATION_TYPE), createRelation(GENERIC_REALATION_TYPE));
        return artistWithRelations(relations);
    }

    private static ArtistMB artistWithRelations(List<Relation> relations) {
        return new ArtistMBBuilder()
                .setRelations(relations)
                .createArtistMB();
    }

    private List<Relation> relationsWith(Relation relation1, Relation relation2) {
        return List.of(relation1, relation2);
    }

    private ArtistMB artistWithoutRelations() {
        List<Relation> relations = new ArrayList<>();
        return artistWithRelations(relations);
    }

    private Relation createRelation(String relationType) {
        return createRelation(relationType, "genericID");
    }

    private Relation createRelation(String relationType, String wikidataID) {
        String url = "https://wikidata.link/Entity/"+ wikidataID;
        return new Relation(relationType, new Url(url));
    }
}