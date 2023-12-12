package com.earosslot.beccmusicservice.service;

import com.earosslot.beccmusicservice.entity.Album;
import com.earosslot.beccmusicservice.entity.Artist;
import com.earosslot.beccmusicservice.entity.ArtistBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistAggregatorUniTest {

    @Mock
    private MusicBrainzService musicBrainz;
    @Mock
    private WikipediaService wikipediaService;
    @Mock
    private CoverArtArchiveService coverArtArchiveService;


    @Test
    @DisplayName("01. given artist request, when has one album, description and album lookout are parallelized")
    void testParallelApiCalls1() throws InterruptedException {

        Set<Long> threadIds = new HashSet<>();

        int numberOfAlbums = 1;
        CountDownLatch latch = new CountDownLatch(numberOfAlbums + 1);

        when(musicBrainz.getArtist("MBID")).thenReturn(artistWithNumberOfAlbums(numberOfAlbums));

        doAnswer(invocation -> {
            System.out.println("Thread ID: " + Thread.currentThread().getId());
            threadIds.add(Thread.currentThread().getId());
            latch.countDown();
            return null; // For void methods
        }).when(coverArtArchiveService).fillImageLink(any());

        doAnswer(invocation -> {
            System.out.println("Thread ID: " + Thread.currentThread().getId());
            threadIds.add(Thread.currentThread().getId());
            latch.countDown();
            return null;
        }).when(wikipediaService).fillDescription(any());

        ArtistAggregator artistAggregator = new ArtistAggregator(musicBrainz, wikipediaService, coverArtArchiveService);
        artistAggregator.getArtist("MBID");

        // Wait for all threads to finish
        latch.await();

        verify(coverArtArchiveService, times(numberOfAlbums)).fillImageLink(any());
        assertTrue(threadIds.size() > 1);
    }


    @Test
    @DisplayName("01. given artist request, when has more than one album, the description and album requests are parallelized")
    void testParallelApiCalls() throws InterruptedException {

        Set<Long> threadIds = new HashSet<>();

        int numberOfAlbums = 4;
        CountDownLatch latch = new CountDownLatch(numberOfAlbums + 1);

        when(musicBrainz.getArtist("MBID")).thenReturn(artistWithNumberOfAlbums(numberOfAlbums));

        doAnswer(invocation -> {
            System.out.println("Thread ID: " + Thread.currentThread().getId());
            threadIds.add(Thread.currentThread().getId());
            latch.countDown();
            return null; // For void methods
        }).when(coverArtArchiveService).fillImageLink(any());

        doAnswer(invocation -> {
            System.out.println("Thread ID: " + Thread.currentThread().getId());
            threadIds.add(Thread.currentThread().getId());
            latch.countDown();
            return null;
        }).when(wikipediaService).fillDescription(any());

        ArtistAggregator artistAggregator = new ArtistAggregator(musicBrainz, wikipediaService, coverArtArchiveService);
        artistAggregator.getArtist("MBID");

        // Wait for all threads to finish
        latch.await();

        verify(coverArtArchiveService, times(numberOfAlbums)).fillImageLink(any());
        assertTrue(threadIds.size() > 1);
    }

    private static Artist artistWithNumberOfAlbums(int numberOfAlbums) {
        List<Album> albums = new ArrayList<>();
        for (int i = 0; i < numberOfAlbums; i++) {
            String id = String.valueOf(i);
            Album album = new Album(id, "Title" + id, null);
            albums.add(album);
        }
        return new ArtistBuilder().setAlbums(albums).createArtist();
    }
}