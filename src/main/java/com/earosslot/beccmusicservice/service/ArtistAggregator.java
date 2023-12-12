package com.earosslot.beccmusicservice.service;

import com.earosslot.beccmusicservice.entity.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Service
public class ArtistAggregator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistAggregator.class);

    private final MusicBrainzService musicBrainz;
    private final WikipediaService wikipediaService;
    private final CoverArtArchiveService coverArtArchiveService;
    private final Executor wikipediaExecutor;
    private final Executor albumExecutor;

    @Autowired
    ArtistAggregator(MusicBrainzService musicBrainz,
                     WikipediaService wikipediaService,
                     CoverArtArchiveService coverArtArchiveService) {
        this.musicBrainz = musicBrainz;
        this.wikipediaService = wikipediaService;
        this.coverArtArchiveService = coverArtArchiveService;
        wikipediaExecutor = Executors.newCachedThreadPool();
        albumExecutor = Executors.newCachedThreadPool();
    }

    public Artist getArtist(String mbid) {

        Artist artist = musicBrainz.getArtist(mbid);

        CompletableFuture<Void> future = fillDescriptionAsync(artist);
        Stream<CompletableFuture<Void>> futureStream = fillImagesAsync(artist);

        waitForCompletion(future, futureStream);

        return artist;
    }

    private CompletableFuture<Void> fillDescriptionAsync(Artist artist) {
        return CompletableFuture.runAsync(() ->
                wikipediaService.fillDescription(artist)
        , wikipediaExecutor);
    }

    private Stream<CompletableFuture<Void>> fillImagesAsync(Artist artist) {
        return artist.getAlbums().stream()
                .parallel()
                .map(album -> CompletableFuture.runAsync(
                        () -> coverArtArchiveService.fillImageLink(album)
                , albumExecutor));
    }

    private static void waitForCompletion(CompletableFuture<Void> future, Stream<CompletableFuture<Void>> futureStream) {
        futureStream.forEach(ArtistAggregator::wait);
        wait(future);
    }

    private static void wait(CompletableFuture<Void> voidFuture) {
        try {
            voidFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Unable to complete future: ", e);
        }
    }
}
