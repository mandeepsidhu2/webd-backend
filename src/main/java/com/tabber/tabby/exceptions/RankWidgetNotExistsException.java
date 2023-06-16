package com.tabber.tabby.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value =  HttpStatus.BAD_REQUEST)
public class RankWidgetNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RankWidgetNotExistsException(String message){ super(message);};
}