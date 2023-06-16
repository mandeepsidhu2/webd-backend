package com.tabber.tabby.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value =  HttpStatus.UNAUTHORIZED)
public class UnauthorisedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UnauthorisedException(String message){ super(message);};
}

