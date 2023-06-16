package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.ContestWidgetRequest;
import com.tabber.tabby.entity.ContestWidgetEntity;
import com.tabber.tabby.exceptions.ContestWidgetNotExistsException;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.ContestWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = URIEndpoints.CONTEST_WIDGET)
public class ContestWidgetController {

    private static final Logger logger = Logger.getLogger(ContestWidgetController.class.getName());

    @Autowired
    ContestWidgetService contestWidgetService;

    @Autowired
    CommonService commonService;

    @PostMapping(value = URIEndpoints.CREATE,produces = "application/json")
    public ResponseEntity<ContestWidgetEntity> createContestWidget(
            @RequestBody @Validated ContestWidgetRequest contestWidgetRequest) throws Exception {
        logger.log(Level.INFO,"Create widget request for contest widget",contestWidgetRequest);
        ContestWidgetEntity contestWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            contestWidgetEntity = contestWidgetService.createContestWidget(contestWidgetRequest,userId);
        }
        catch (Exception ex){
            commonService.setLog(ContestWidgetService.class.toString(), ex.toString(), contestWidgetEntity==null?null: contestWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot create widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(contestWidgetEntity, HttpStatus.OK);
    }

    @PutMapping(value = URIEndpoints.UPDATE,produces = "application/json")
    public ResponseEntity<ContestWidgetEntity>  updateContestWidget(
            @RequestBody ContestWidgetRequest contestWidgetRequest,
            @RequestParam("id") Long id) throws ContestWidgetNotExistsException {
        logger.log(Level.INFO,"Update widget request for rank widget",contestWidgetRequest);
        ContestWidgetEntity contestWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            contestWidgetEntity = contestWidgetService.updateContestWidget(contestWidgetRequest,id,userId);
        }
        catch (Exception ex){
            commonService.setLog(ContestWidgetService.class.toString(), ex.toString(), contestWidgetEntity==null?null: contestWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot update widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(contestWidgetEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = URIEndpoints.DELETE,produces = "application/json")
    public ResponseEntity<ContestWidgetEntity>  deleteContestWidget(
            @RequestParam("id") Long id) throws ContestWidgetNotExistsException {
        logger.log(Level.INFO,"Delete widget request for contest widget:{}",id);
        ContestWidgetEntity contestWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            contestWidgetEntity = contestWidgetService.deleteContestWidget(id,userId);
        }
        catch (Exception ex){
            commonService.setLog(ContestWidgetService.class.toString(), ex.toString(), contestWidgetEntity==null?null: contestWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot update widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(contestWidgetEntity, HttpStatus.OK);
    }
}
