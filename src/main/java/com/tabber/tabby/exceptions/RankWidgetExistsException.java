package com.tabber.tabby.exceptions;

import javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value =  HttpStatus.BAD_REQUEST)
public class RankWidgetExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RankWidgetExistsException(String message){ super(message);};
}


