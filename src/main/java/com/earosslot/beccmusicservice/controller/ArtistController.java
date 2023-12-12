package com.earosslot.beccmusicservice.controller;

import com.earosslot.beccmusicservice.entity.Artist;
import com.earosslot.beccmusicservice.exeptions.ArtistNotFoundException;
import com.earosslot.beccmusicservice.service.ArtistAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/music-artist")
public class ArtistController implements ArtistControllerInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistController.class);
    public static final String MBID_FORMAT_NOT_VALID_ERROR_MSG = "MBID Format not Valid";

    static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private final ArtistAggregator artistAggregator;

    @Autowired
    ArtistController(ArtistAggregator artistAggregator) {
        this.artistAggregator = artistAggregator;
    }

    @Override
    @GetMapping(value = "/details/{mbid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artist> getDetails(@PathVariable String mbid) {

        validateUUID(mbid);
        try {
            Artist artist = artistAggregator.getArtist(mbid);
            LOGGER.trace(artist.toString());
            return ResponseEntity.ok().body(artist);

        } catch (ArtistNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    private static void validateUUID(String mbid) {
        if (!UUID_REGEX.matcher(mbid).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MBID_FORMAT_NOT_VALID_ERROR_MSG);
        }
    }

}
