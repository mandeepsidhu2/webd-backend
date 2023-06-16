package com.tabber.tabby.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value =  HttpStatus.BAD_REQUEST)
public class FrontendConfigurationExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FrontendConfigurationExistsException(String message){ super(message);};
}
