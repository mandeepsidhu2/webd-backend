package com.tabber.tabby.service;

import com.tabber.tabby.entity.CustomLinkEntity;
import com.tabber.tabby.entity.PortfolioEntity;
import com.tabber.tabby.respository.CustomLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomLinkService {

    @Autowired
    CustomLinkRepository customLinkRepository;

    public CustomLinkEntity getCustomLinkEntity(String group, Long id, String linkType){
        return customLinkRepository.getCustomLink(group,id,linkType);
    }

    public CustomLinkEntity getCustomLinkEntityFromUserId(Long userId){
        return customLinkRepository.getCustomLinkFromUserId(userId);
    }


    public void deleteCustomLink(CustomLinkEntity customLinkEntity){
        customLinkRepository.delete(customLinkEntity);
    }

    public void saveAndFlush(CustomLinkEntity customLinkEntity){
        customLinkRepository.saveAndFlush(customLinkEntity);
    }
}
