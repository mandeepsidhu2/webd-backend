package com.tabber.tabby.manager;

import com.tabber.tabby.constants.TabbyConstants;
import com.tabber.tabby.entity.CustomLinkEntity;
import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.respository.UserRepository;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.CustomLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResumeManager {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomLinkService customLinkService;


    @Autowired
    CommonService commonService;

    public UserEntity findUserById(Long userId){
        UserEntity userEntity = null;
        userEntity = userRepository.getTopByUserId(userId);
        return userEntity;
    }


    public Long getCustomLinkUserId(String groupId, Long id) {
        if(groupId == null)
            return id;

           CustomLinkEntity customLinkEntity = customLinkService.getCustomLinkEntity(groupId,id,"GROUP");
           if(customLinkEntity == null)
               return null;
           return customLinkEntity.getUser().getUserId();

    }

}
