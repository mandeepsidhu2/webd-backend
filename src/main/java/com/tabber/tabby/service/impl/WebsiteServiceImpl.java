package com.tabber.tabby.service.impl;

import com.tabber.tabby.entity.WebsiteEntity;
import com.tabber.tabby.manager.WebsiteManager;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    WebsiteManager websiteManager;

    @Autowired
    CommonService commonService;

    private static final Logger logger = Logger.getLogger(WebsiteServiceImpl.class.getName());

    @Override
    public WebsiteEntity getWebsiteById(Integer websiteId){
        WebsiteEntity websiteEntity = null;
        try{
            websiteEntity = websiteManager.findWebsiteById(websiteId);
        }
        catch (Exception ex){
            commonService.setLog(WebsiteServiceImpl.class.toString(), ex.toString(), null);
            logger.log(Level.INFO,"Cannot get website due to error: {}",ex.toString());
        }
        return websiteEntity;
    }

    @Override
    public WebsiteEntity getWebsiteByNameAndType(String name, String type){
        WebsiteEntity websiteEntity = null;
        try{
            websiteEntity = websiteManager.findWebsiteByNameAndType(name,type);
        }
        catch (Exception ex){
            commonService.setLog(WebsiteServiceImpl.class.toString(), ex.toString(), null);
            logger.log(Level.INFO,"Cannot get website due to error: {}",ex.toString());
        }
        return websiteEntity;
    }

}
