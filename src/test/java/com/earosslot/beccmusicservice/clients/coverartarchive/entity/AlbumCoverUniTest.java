package com.earosslot.beccmusicservice.clients.coverartarchive.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumCoverUniTest {

    public static final String BACK_COVER = "Back";
    public static final String LINK_TO_FRONT = "https://link.to/front";
    public static final String LINK_TO_BACK = "https://link.to/back";

    @Test
    @DisplayName("01. Given an album with front cover, when front image link asked, image link retrieved")
    void test01() {
        AlbumCover albumCover = albumWithFrontAndBackCover();

        String frontImageLink = albumCover.getFrontImage();

        assertEquals(LINK_TO_FRONT, frontImageLink);
    }

    @Test
    @DisplayName("02. Given an album without front cover, when front image link asked, throws RuntimeException")
    void test02() {
        AlbumCover albumCover = albumWithoutFrontCover();

        RuntimeException albumNotFoundExc = assertThrows(RuntimeException.class, albumCover::getFrontImage);
        assertEquals(AlbumCover.ALBUM_IMAGE_NOT_FOUND, albumNotFoundExc.getMessage());
    }

    @Test
    @DisplayName("03. Given an album without any cover, when front image link asked, throws RuntimeException")
    void test03() {
        AlbumCover albumCover = albumWithoutCovers();

        RuntimeException albumNotFoundExc = assertThrows(RuntimeException.class, albumCover::getFrontImage);
        assertEquals(AlbumCover.ALBUM_IMAGE_NOT_FOUND, albumNotFoundExc.getMessage());
    }

    @Test
    @DisplayName("04. Given an album whit cover null, when front image link asked, throws RuntimeException")
    void test04() {
        AlbumCover albumCover = new AlbumCover();

        RuntimeException albumNotFoundExc = assertThrows(RuntimeException.class, albumCover::getFrontImage);
        assertEquals(AlbumCover.ALBUM_IMAGE_NOT_FOUND, albumNotFoundExc.getMessage());
    }

    public AlbumCover albumWithoutCovers() {
        return new AlbumCover(new ArrayList<>());
    }

    public AlbumCover albumWithoutFrontCover() {
        List<AlbumCover.Image> images = List.of(backImage(), backImage());
        return new AlbumCover(images);
    }

    public AlbumCover albumWithFrontAndBackCover() {
        List<AlbumCover.Image> images = List.of(frontImage(), backImage());
        return new AlbumCover(images);
    }

    private static AlbumCover.Image backImage() {
        return new AlbumCover.Image(List.of(BACK_COVER), LINK_TO_BACK);
    }

    private static AlbumCover.Image frontImage() {
        return new AlbumCover.Image(List.of(AlbumCover.FRONT_COVER), LINK_TO_FRONT);
    }

}