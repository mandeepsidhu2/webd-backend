package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.CourseWidgetRequest;
import com.tabber.tabby.dto.ExperienceWidgetRequest;
import com.tabber.tabby.entity.CourseWidgetEntity;
import com.tabber.tabby.entity.ExperienceWidgetEntity;
import com.tabber.tabby.exceptions.CourseWidgetNotExistsException;
import com.tabber.tabby.exceptions.ExperienceWidgetNotExistsException;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.CourseWidgetService;
import com.tabber.tabby.service.ExperienceWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = URIEndpoints.EXPERIENCE_WIDGET)
public class ExperienceWidgetController {

    private static final Logger logger = Logger.getLogger(ExperienceWidgetController.class.getName());

    @Autowired
    ExperienceWidgetService experienceWidgetService;

    @Autowired
    CommonService commonService;

    @PostMapping(value = URIEndpoints.CREATE,produces = "application/json")
    public ResponseEntity<ExperienceWidgetEntity> createExperienceWidget(
            @RequestBody @Validated ExperienceWidgetRequest experienceWidgetRequest) throws Exception {
        logger.log(Level.INFO,"Create widget request for experience widget",experienceWidgetRequest);
        ExperienceWidgetEntity experienceWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            experienceWidgetEntity = experienceWidgetService.createExperienceWidget(experienceWidgetRequest, userId);
        }
        catch (Exception ex){
            commonService.setLog(ExperienceWidgetController.class.toString(), ex.toString(), experienceWidgetEntity==null? null:experienceWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot create widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(experienceWidgetEntity, HttpStatus.OK);
    }

    @PutMapping(value = URIEndpoints.UPDATE,produces = "application/json")
    public ResponseEntity<ExperienceWidgetEntity>  updateExperienceWidget(
            @RequestBody ExperienceWidgetRequest experienceWidgetRequest,
            @RequestParam("id") Long id) throws ExperienceWidgetNotExistsException {
        logger.log(Level.INFO,"Update widget request for experience widget",experienceWidgetRequest);
        ExperienceWidgetEntity experienceWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            experienceWidgetEntity = experienceWidgetService.updateExperienceWidget(experienceWidgetRequest,id,userId);
        }
        catch (Exception ex){
            commonService.setLog(ExperienceWidgetController.class.toString(), ex.toString(), experienceWidgetEntity==null? null:experienceWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot update widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(experienceWidgetEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = URIEndpoints.DELETE,produces = "application/json")
    public ResponseEntity<ExperienceWidgetEntity>  deleteExperienceWidget(
            @RequestParam("id") Long id) throws ExperienceWidgetNotExistsException {
        logger.log(Level.INFO,"Delete widget request for course widget:{}",id);
        ExperienceWidgetEntity experienceWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            experienceWidgetEntity = experienceWidgetService.deleteExperienceWidget(id,userId);
        }
        catch (Exception ex){
            commonService.setLog(ExperienceWidgetController.class.toString(), ex.toString(), experienceWidgetEntity==null? null:experienceWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot delete widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(experienceWidgetEntity, HttpStatus.OK);
    }

}
