package com.tabber.tabby.service.admin;

import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.exceptions.UnauthorisedException;
import com.tabber.tabby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class AdminCommonService {

    @Autowired
    UserService userService;

    public void verifyAdmin(Long adminUserId) throws Exception {
        UserEntity userEntity = null;
        try {
            userEntity = userService.getUserFromUserId(adminUserId);
        } catch(Exception ex) {
            throw new Exception("Exception occur while fetching admin entity from admin id in " + AdminCommonService.class);
        }
        String userType = userEntity.getUserType();
        if(userType == null || !Objects.equals(userType, "admin")) {
            throw new UnauthorisedException("Invalid Admin");
        }
    }
}
