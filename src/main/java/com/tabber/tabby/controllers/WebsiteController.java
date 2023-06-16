package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.entity.WebsiteEntity;
import com.tabber.tabby.manager.WebsiteManager;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.TrendingProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebsiteController {

    @Autowired
    WebsiteManager websiteManager;

    @Autowired
    TrendingProfileService trendingProfileService;

    @Autowired
    CommonService commonService;

    @GetMapping(value = URIEndpoints.WEBSITE,produces = "application/json")
    public ResponseEntity<WebsiteEntity> getWebsite(
           @RequestParam(value = "id") Integer id) throws Exception {
        WebsiteEntity websiteEntity=
                websiteManager.findWebsiteById(id);
        if(websiteEntity == null){
            throw new Exception("Website doesn't exist for this id");
        }
        return new ResponseEntity<>(websiteEntity, HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.ALL_WEBSITE,produces = "application/json")
    public ResponseEntity<List<WebsiteEntity>> getAllWebsite() throws Exception {
        List<WebsiteEntity> websiteEntities=
                websiteManager.findAllWebsites();
        if(websiteEntities == null){
            throw new Exception("Websites don't exist");
        }
        return new ResponseEntity<>(websiteEntities, HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.TRENDING_PROFILES,produces = "application/json")
    public ResponseEntity<Object> getTrenidingProfiles() throws Exception {
        Object result = trendingProfileService.getTrendingProfiles();
        return new ResponseEntity<>( result.toString(), HttpStatus.OK);
    }
}
