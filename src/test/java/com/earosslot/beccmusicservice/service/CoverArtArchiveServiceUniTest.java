package com.earosslot.beccmusicservice.service;

import com.earosslot.beccmusicservice.clients.coverartarchive.CoverArtArchiveClient;
import com.earosslot.beccmusicservice.entity.Album;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoverArtArchiveServiceUniTest {

    @Mock
    private CoverArtArchiveClient coverArtClientMock;
    private CoverArtArchiveService coverArtArchiveService;

    @BeforeEach
    void setUp() {

        coverArtArchiveService = new CoverArtArchiveService(coverArtClientMock);
    }

    @Test
    @DisplayName("01. Fills correctly the album url")
    void test01FillsAlbumUrlCorrectly() {

        String urlToAlbumImage = "https://image.link/to-cover";
        when(coverArtClientMock.getAlbumImage(anyString())).thenReturn(urlToAlbumImage);

        Album album = albumWithoutCoverImage();
        coverArtArchiveService.fillImageLink(album);

        assertEquals(urlToAlbumImage, album.getImageUrl());
    }

    @Test
    @DisplayName("02. The album remains unchanged if failed do obtain cover image")
    void test02AlbumDoesNotChangeWhenFailedToObtainCoverImage() {

        when(coverArtClientMock.getAlbumImage(anyString()))
                .thenThrow(new RuntimeException("Filed to obtain link to album cover"));

        Album album = albumWithoutCoverImage();
        coverArtArchiveService.fillImageLink(album);

        assertEquals(albumWithoutCoverImage(), album);
    }

    private static Album albumWithoutCoverImage() {
        return new Album("album-id", "Title", null);
    }


}