package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.PortfolioRequest;
import com.tabber.tabby.entity.PortfolioEntity;
import com.tabber.tabby.exceptions.PortfolioExistsException;
import com.tabber.tabby.exceptions.PortfolioNotExistsException;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = URIEndpoints.PORTFOLIO)
public class PortfolioController {

    private static final Logger logger = Logger.getLogger(PortfolioController.class.getName());

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    CommonService commonService;

    @PostMapping(value = URIEndpoints.UPLOAD_CLOUD_RESUME_LINK,produces = "application/json")
    public ResponseEntity<Boolean> uploadCloudResumeLink(
            @RequestParam("cloud_link")  String cloudLink) throws Exception {
        logger.log(Level.INFO,"Uploading cloud link",cloudLink);
        Boolean linkUpdated = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            portfolioService.updateResumeLink(cloudLink,userId);
        }
        catch (Exception ex){
            commonService.setLog(PortfolioController.class.toString(), ex.toString(), null);
            logger.log(Level.SEVERE,"Cannot create widget due to exception: {}",ex.toString());
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping(value = URIEndpoints.CREATE,produces = "application/json")
    public ResponseEntity<PortfolioEntity> createPortfolio(
            @RequestBody @Validated PortfolioRequest portfolioRequest) throws PortfolioExistsException {
        logger.log(Level.INFO,"Create portfolio request :",portfolioRequest);
        PortfolioEntity portfolioEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            portfolioEntity = portfolioService.createPortfolio(portfolioRequest,userId);
        }
        catch (Exception ex){
            commonService.setLog(PortfolioController.class.toString(), ex.toString(), portfolioEntity==null?null:portfolioEntity.getUser().getUserId());
            logger.log(Level.SEVERE,"Cannot create portfolio due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(portfolioEntity, HttpStatus.OK);
    }

    @PutMapping(value = URIEndpoints.UPDATE,produces = "application/json")
    public ResponseEntity<PortfolioEntity>  updatePortfolio(
            @RequestBody PortfolioRequest portfolioRequest) throws PortfolioNotExistsException {
        logger.log(Level.INFO,"Update portfolio for portfolio:{}",portfolioRequest);
        PortfolioEntity portfolioEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
             portfolioEntity = portfolioService.updatePortfolio(portfolioRequest,userId);
        }
        catch (Exception ex){
            commonService.setLog(PortfolioController.class.toString(), ex.toString(), portfolioEntity==null?null:portfolioEntity.getUser().getUserId());
            logger.log(Level.SEVERE,"Cannot update portfolio due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(portfolioEntity, HttpStatus.OK);
    }

}
