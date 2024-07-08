package com.mobile.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataRequestException extends RuntimeException {
    public InvalidDataRequestException(String msg) {
        super(msg);
    }
}
