package com.tabber.tabby.manager;

import com.tabber.tabby.entity.EmailEntity;
import com.tabber.tabby.respository.EmailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class EmailsManager {

    @Autowired
    EmailsRepository emailsRepository;


    public EmailEntity getEmailByProfileId(Long profileId){
        return emailsRepository.getEmailDataByProfileId(profileId);
    }



}
