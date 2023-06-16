package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.RankWidgetRequest;
import com.tabber.tabby.entity.RankWidgetEntity;
import com.tabber.tabby.exceptions.RankWidgetExistsException;
import com.tabber.tabby.exceptions.RankWidgetNotExistsException;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.RankWidgetService;
import groovy.util.logging.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = URIEndpoints.RANK_WIDGET)
public class RankWidgetController {

    private static final Logger logger = Logger.getLogger(RankWidgetController.class.getName());

    @Autowired
    RankWidgetService rankWidgetService;

    @Autowired
    CommonService commonService;

    @PostMapping(value = URIEndpoints.CREATE,produces = "application/json")
    public ResponseEntity<RankWidgetEntity> createRankWidget(
            @RequestBody @Validated RankWidgetRequest rankWidgetRequest) throws Exception {
        logger.log(Level.INFO,"Create widget request for rank widget",rankWidgetRequest);
        RankWidgetEntity rankWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            rankWidgetEntity = rankWidgetService.createRankWidget(rankWidgetRequest,userId);
        }
        catch (Exception ex){
            commonService.setLog(RankWidgetController.class.toString(), ex.toString(), rankWidgetEntity==null?null: rankWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot create widget due to exception: {}",ex.toString());
           throw ex;
        }
        return new ResponseEntity<>(rankWidgetEntity, HttpStatus.OK);
    }

    @PutMapping(value = URIEndpoints.UPDATE,produces = "application/json")
    public ResponseEntity<RankWidgetEntity>  updateRankWidget(
            @RequestBody RankWidgetRequest rankWidgetRequest) throws RankWidgetNotExistsException {
        logger.log(Level.INFO,"Update widget request for rank widget:{}",rankWidgetRequest);
        RankWidgetEntity rankWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            rankWidgetEntity = rankWidgetService.updateRankWidget(rankWidgetRequest,userId);
        }
        catch (Exception ex){
            commonService.setLog(RankWidgetController.class.toString(), ex.toString(), rankWidgetEntity==null?null: rankWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot update widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(rankWidgetEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = URIEndpoints.DELETE,produces = "application/json")
    public ResponseEntity<RankWidgetEntity>  deleteRankWidget(
            @RequestParam("website_id") Integer websiteId) throws RankWidgetNotExistsException {
        logger.log(Level.INFO,"Delete widget request for rank widget id:{}",websiteId);
        RankWidgetEntity rankWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            rankWidgetEntity = rankWidgetService.deleteRankWidget(websiteId,userId);
        }
        catch (Exception ex){
            commonService.setLog(RankWidgetController.class.toString(), ex.toString(), rankWidgetEntity==null?null: rankWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot update widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(rankWidgetEntity, HttpStatus.OK);
    }
}
