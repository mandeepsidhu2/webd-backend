package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.PersonalProjectRequest;
import com.tabber.tabby.entity.PersonalProjectEntity;
import com.tabber.tabby.exceptions.PersonalProjectNotExistsException;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.PersonalProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(URIEndpoints.PERSONAL_PROJECT)
public class PersonalProjectController {

    private static final Logger logger = Logger.getLogger(PersonalProjectController.class.getName());

    @Autowired
    PersonalProjectService personalProjectService;

    @Autowired
    CommonService commonService;

    @PostMapping(value = URIEndpoints.CREATE,produces = "application/json")
    public ResponseEntity<PersonalProjectEntity> createPersonalProject(
            @RequestBody @Validated PersonalProjectRequest personalProjectRequest) throws Exception {
        logger.log(Level.INFO,"Create widget request for rank widget",personalProjectRequest);
        PersonalProjectEntity personalProjectEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            personalProjectEntity = personalProjectService.createPersonalProject(personalProjectRequest,userId);
        }
        catch (Exception ex){
            commonService.setLog(PersonalProjectController.class.toString(), ex.toString(), personalProjectEntity==null?null:personalProjectEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot create project due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(personalProjectEntity, HttpStatus.OK);
    }

    @PutMapping(value = URIEndpoints.UPDATE,produces = "application/json")
    public ResponseEntity<PersonalProjectEntity>  updatePersonalProject(
            @RequestBody PersonalProjectRequest personalProjectRequest,
            @RequestParam("id") Long id) throws PersonalProjectNotExistsException {
        logger.log(Level.INFO,"Update widget request for rank widget:{}",personalProjectRequest);
        PersonalProjectEntity personalProjectEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            personalProjectEntity = personalProjectService.updatePersonalProject(personalProjectRequest,id,userId);
        }
        catch (Exception ex){
            commonService.setLog(PersonalProjectController.class.toString(), ex.toString(), personalProjectEntity==null?null:personalProjectEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot update project due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(personalProjectEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = URIEndpoints.DELETE,produces = "application/json")
    public ResponseEntity<PersonalProjectEntity>  deletePersonalProject(
            @RequestParam("id") Long id) throws PersonalProjectNotExistsException {
        logger.log(Level.INFO,"Delete widget request project:{}",id);
        PersonalProjectEntity personalProjectEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            personalProjectEntity = personalProjectService.deletePersonalProject(id,userId);
        }
        catch (Exception ex){
            commonService.setLog(PersonalProjectController.class.toString(), ex.toString(), personalProjectEntity==null?null:personalProjectEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot update project due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(personalProjectEntity, HttpStatus.OK);
    }
}
