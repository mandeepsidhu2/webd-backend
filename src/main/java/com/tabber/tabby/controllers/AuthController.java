package com.tabber.tabby.controllers;

import com.tabber.tabby.exceptions.UnauthorisedException;
import com.tabber.tabby.service.AuthService;
import com.tabber.tabby.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    AuthService authService;

    @Autowired
    CommonService commonService;

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String idTokenString) throws UnauthorisedException {
        String accessToken = null;
        HashMap<String, String> map = new HashMap<>();
        try {
            accessToken = authService.login(idTokenString);
        }
        catch (Exception ex){
            logger.log(Level.WARNING,"Unable to verify user for identity token :{}",idTokenString.concat("for token ").concat(ex.toString()));
            commonService.setLog(AuthController.class.toString(), ex.toString(), null);
            map.put("response","Forbidden");
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }
        map.put("access_token",accessToken);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("github/login")
    public ResponseEntity<Map<String, String>> githubLogin(@RequestParam String idTokenString) throws UnauthorisedException {
        String accessToken = null;
        HashMap<String, String> map = new HashMap<>();
        try {
            accessToken = authService.githubLogin(idTokenString);
        }
        catch (Exception ex){
            logger.log(Level.WARNING,"Unable to verify user for identity token :{}",idTokenString.concat("for token ").concat(ex.toString()));
            commonService.setLog(AuthController.class.toString(), ex.toString(), null);
            map.put("response","Forbidden");
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }
        map.put("access_token",accessToken);
        map.put("new_user",String.valueOf(authService.isNewUser()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
