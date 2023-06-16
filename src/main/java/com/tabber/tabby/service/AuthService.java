package com.tabber.tabby.service;

import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.exceptions.UnauthorisedException;

public interface AuthService {

    String login(String idTokenString) throws UnauthorisedException;
    String githubLogin(String idTokenString) throws UnauthorisedException;
    boolean isNewUser();

}
