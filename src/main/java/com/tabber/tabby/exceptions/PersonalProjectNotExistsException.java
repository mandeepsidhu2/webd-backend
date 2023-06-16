package com.tabber.tabby.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value =  HttpStatus.BAD_REQUEST)
public class PersonalProjectNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PersonalProjectNotExistsException(String message){ super(message);};
}