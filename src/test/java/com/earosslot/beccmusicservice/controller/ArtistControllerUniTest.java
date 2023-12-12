package com.earosslot.beccmusicservice.controller;

import com.earosslot.beccmusicservice.entity.Artist;
import com.earosslot.beccmusicservice.entity.ArtistBuilder;
import com.earosslot.beccmusicservice.exeptions.ArtistNotFoundException;
import com.earosslot.beccmusicservice.service.ArtistAggregator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistControllerUniTest {

    public static final String MJ_MBID = "f27ec8db-af05-4f36-916e-3d57f91ecf5e";

    public static final String NOBODY_MBID = "f27ec8db-af05-4f34-916e-3d57f91ecf5e";
    @Mock
    ArtistAggregator artistAggregator;
    private ArtistController artistController;

    @BeforeEach
    void setUp() {
        artistController = new ArtistController(artistAggregator);
    }

    @Test
    @DisplayName("01. Given an invalid MBID, when attempt to get Artist details, then throws ResponseStatusException")
    void test01InvalidUUID() {

        ResponseStatusException responseStatusException =
                assertThrows(ResponseStatusException.class, () -> artistController.getDetails("invalid-uuid"));

        assertTrue(responseStatusException.getMessage().contains(ArtistController.MBID_FORMAT_NOT_VALID_ERROR_MSG));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
    }

    @Test
    @DisplayName("02. Given a valid MBID, when request for details, then details are retrieved")
    void test02() {

        Artist mickelJackson = new ArtistBuilder().setMbid(MJ_MBID).createArtist();
        when(artistAggregator.getArtist(MJ_MBID))
                .thenReturn(mickelJackson);

        ResponseEntity<Artist> details = artistController.getDetails(MJ_MBID);

        assertEquals(mickelJackson ,details.getBody());
    }

    @Test
    @DisplayName("03. Given a valid MBID with no artist associated, when request for details, then throws Response Status Exception")
    void test03() {
        String errorMessage = "Artist Not Found";
        when(artistAggregator.getArtist(NOBODY_MBID)).thenThrow(new ArtistNotFoundException(errorMessage));

        ResponseStatusException responseStatusException =
                assertThrows(ResponseStatusException.class, () -> artistController.getDetails(NOBODY_MBID));

        assertTrue(responseStatusException.getMessage().contains(errorMessage));
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());

    }

}