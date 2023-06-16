package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.UserBasicRespone;
import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = URIEndpoints.USER)
public class UserDetailsController {

    private static final Logger logger = Logger.getLogger(UserDetailsController.class.getName());

    @Autowired
    UserService userService;

    @Autowired
    CommonService commonService;

    @GetMapping("/update_user_type")
    private ResponseEntity<String> updateUserType(@RequestParam String userType) throws Exception {
        logger.log(Level.WARNING, "Inside Admin User Controller :: updateUserType");
        try {
            Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            userService.updateUserType(userId,userType);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        catch (Exception ex){
            logger.log(Level.SEVERE,"Cannot fetch users from limit and offset due to exception: {} : " + ex.toString());
            throw ex;
        }
    }
    @GetMapping(value = URIEndpoints.GUEST_RESUME,produces = "application/json")
    public ResponseEntity<Object> getGuestUserResume(@RequestParam("id") Long userId, @RequestParam(value = "group", required = false) String group, @RequestParam(value = "trakingId", required = false) String trackingId) throws Exception {
        Object userEntity = null;
        try {
            userEntity = userService.getUserFromCustomLink(userId,group,trackingId, true);
        }
        catch (Exception ex){
            commonService.setLog(UserDetailsController.class.toString(), ex.toString(), userId);
            logger.log(Level.SEVERE,"Cannot get user due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.RESUME,produces = "application/json")
    public ResponseEntity<Object> getUserResume() throws Exception {
        Object userEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            userEntity = userService.getEnrichedUserData(userId, null,false);
        }
        catch (Exception ex){
            commonService.setLog(UserDetailsController.class.toString(), ex.toString(), null);
            logger.log(Level.SEVERE,"Cannot get user due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.USER_INFO,produces = "application/json")
    public ResponseEntity<UserBasicRespone> getUserInfo() throws Exception {
        UserBasicRespone userBasicRespone = null;
        UserEntity userEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            userEntity = userService.getUserFromUserId(userId);
            userBasicRespone=new UserBasicRespone().toBuilder()
                    .email(userEntity.getEmail())
                    .locale(userEntity.getLocale())
                    .pictureUrl(userEntity.getPictureUrl())
                    .name(userEntity.getName())
                    .userId(userEntity.getUserId())
                    .resumePresent(userEntity.getResumePresent())
                    .sub(userEntity.getSub())
                    .build();

        }
        catch (Exception ex){
            commonService.setLog(UserDetailsController.class.toString(), ex.toString(), userEntity==null?null:userEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot get user due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(userBasicRespone, HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.GET_USER_COOKIE,produces = "application/json")
    public ResponseEntity<Boolean> getUserCookie() throws Exception {
        UserEntity userEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            userEntity = userService.getUserFromUserId(userId);
        }
        catch (Exception ex){
            commonService.setLog(UserDetailsController.class.toString(), ex.toString(), userEntity==null?null:userEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot get user due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(userEntity.getCookieAccepted(), HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.UPDATE_USER_COOKIE,produces = "application/json")
    public ResponseEntity<String> updateUserCookie() throws Exception {
        UserEntity userEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            userService.updateUserCookiePermission(userId);
        }
        catch (Exception ex){
            commonService.setLog(UserDetailsController.class.toString(), ex.toString(), userEntity==null?null:userEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot get user due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>("User cookie permission updated Successfully", HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.UPDATE_USER_NAME,produces = "application/json")
    public ResponseEntity<String> updateUserName(@RequestParam String userName) throws Exception {
        UserEntity userEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            userService.updateUserName(userId,userName);
        }
        catch (Exception ex){
            commonService.setLog(UserDetailsController.class.toString(), ex.toString(), userEntity==null?null:userEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot get user due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>("User name updated Successfully", HttpStatus.OK);
    }
}

