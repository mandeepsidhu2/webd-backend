package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.entity.FrontendConfigurationEntity;
import com.tabber.tabby.exceptions.FrontendConfigurationNotExistsException;
import com.tabber.tabby.manager.FrontendConfigManager;
import com.tabber.tabby.respository.FrontendConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrontendConfiguration {

    @Autowired
    FrontendConfigManager frontendConfigManager;

    @GetMapping(value = URIEndpoints.FRONTEND_CONFIG_GET,produces = "application/json")
    public ResponseEntity<FrontendConfigurationEntity> getFeConfiguration(
            @RequestParam(value = "page_type") String pageType, @RequestParam(value = "key") String key) throws FrontendConfigurationNotExistsException {
        FrontendConfigurationEntity frontendConfigurationEntity=
                frontendConfigManager.findFeConfigurationByPageTypeAndKey(pageType, key);
        if(frontendConfigurationEntity == null){
            throw new FrontendConfigurationNotExistsException("Configuration doesn't exist for this page type and key, try creating first");
        }
        return new ResponseEntity<>(frontendConfigurationEntity, HttpStatus.OK);
    }
}
