package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.SocialWebsiteDto;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = URIEndpoints.SOCIAL_WEBSITE)
public class SocialWebsiteController {

    private static final Logger logger = Logger.getLogger(SocialWebsiteController.class.getName());

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    CommonService commonService;

    @PostMapping(value = URIEndpoints.CREATE,produces = "application/json")
    public ResponseEntity<ArrayList<SocialWebsiteDto> > addSocialProfileLink(
            @RequestBody @Validated SocialWebsiteDto socialWebsiteDto) throws Exception {
        logger.log(Level.INFO,"Create social website link request :",socialWebsiteDto);
        ArrayList<SocialWebsiteDto> socialWebsiteDtoArrayList = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            socialWebsiteDtoArrayList = portfolioService.addSocialWeblink(socialWebsiteDto.getWebsite_name(),socialWebsiteDto.getLink(),userId);
        }
        catch (Exception ex){
            commonService.setLog(SocialWebsiteController.class.toString(), ex.toString(), null);
            logger.log(Level.SEVERE,"Cannot create social link due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(socialWebsiteDtoArrayList, HttpStatus.OK);
    }

    @PutMapping(value = URIEndpoints.UPDATE,produces = "application/json")
    public ResponseEntity<ArrayList<SocialWebsiteDto> > updateSocialProfileLink(
            @RequestBody @Validated SocialWebsiteDto socialWebsiteDto) throws Exception {
        logger.log(Level.INFO,"Create social website link request :",socialWebsiteDto);
        ArrayList<SocialWebsiteDto> socialWebsiteDtoArrayList = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            socialWebsiteDtoArrayList = portfolioService.updateSocialWeblink(socialWebsiteDto.getWebsite_name(),socialWebsiteDto.getLink(),userId);
        }
        catch (Exception ex){
            commonService.setLog(SocialWebsiteController.class.toString(), ex.toString(), null);
            logger.log(Level.SEVERE,"Cannot create social link due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(socialWebsiteDtoArrayList, HttpStatus.OK);
    }
}
