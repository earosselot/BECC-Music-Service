package com.earosslot.beccmusicservice.exeptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MusifyException extends RuntimeException {

    private final HttpStatus httpStatus;

    public MusifyException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public MusifyException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
