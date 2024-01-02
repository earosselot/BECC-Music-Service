package com.earosslot.beccmusicservice.exeptions;

import org.springframework.http.HttpStatus;

public class ArtistNotFoundException extends MusifyException {
    public ArtistNotFoundException(String message) {
        super(message);
    }

    public ArtistNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
