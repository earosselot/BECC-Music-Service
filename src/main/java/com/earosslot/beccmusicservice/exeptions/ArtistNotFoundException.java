package com.earosslot.beccmusicservice.exeptions;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException(String artistNotFound) {
        super(artistNotFound);
    }
}
