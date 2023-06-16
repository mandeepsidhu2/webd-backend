package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.CourseWidgetRequest;
import com.tabber.tabby.entity.CourseWidgetEntity;
import com.tabber.tabby.exceptions.CourseWidgetNotExistsException;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.CourseWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = URIEndpoints.COURSE_WIDGET)
public class CourseWidgetController {

    private static final Logger logger = Logger.getLogger(CourseWidgetController.class.getName());

    @Autowired
    CourseWidgetService courseWidgetService;

    @Autowired
    CommonService commonService;

    @PostMapping(value = URIEndpoints.CREATE,produces = "application/json")
    public ResponseEntity<CourseWidgetEntity> createCourseWidget(
            @RequestBody @Validated CourseWidgetRequest courseWidgetRequest) throws Exception {
        logger.log(Level.INFO,"Create widget request for course widget",courseWidgetRequest);
        CourseWidgetEntity courseWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            courseWidgetEntity = courseWidgetService.createCourseWidget(courseWidgetRequest, userId);
        }
        catch (Exception ex){
            commonService.setLog(CourseWidgetService.class.toString(), ex.toString(), courseWidgetEntity==null?null: courseWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot create widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(courseWidgetEntity, HttpStatus.OK);
    }

    @PutMapping(value = URIEndpoints.UPDATE,produces = "application/json")
    public ResponseEntity<CourseWidgetEntity>  updateCourseWidget(
            @RequestBody CourseWidgetRequest courseWidgetRequest,
            @RequestParam("id") Long id) throws CourseWidgetNotExistsException {
        logger.log(Level.INFO,"Update widget request for course widget",courseWidgetRequest);
        CourseWidgetEntity courseWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            courseWidgetEntity = courseWidgetService.updateCourseWidget(courseWidgetRequest,id,userId);
        }
        catch (Exception ex){
            commonService.setLog(CourseWidgetService.class.toString(), ex.toString(), courseWidgetEntity==null?null: courseWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot update widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(courseWidgetEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = URIEndpoints.DELETE,produces = "application/json")
    public ResponseEntity<CourseWidgetEntity>  deleteCourseWidget(
            @RequestParam("id") Long id) throws CourseWidgetNotExistsException {
        logger.log(Level.INFO,"Delete widget request for course widget:{}",id);
        CourseWidgetEntity courseWidgetEntity = null;
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            courseWidgetEntity = courseWidgetService.deleteCourseWidget(id,userId);
        }
        catch (Exception ex){
            commonService.setLog(CourseWidgetService.class.toString(), ex.toString(), courseWidgetEntity==null?null: courseWidgetEntity.getUserId());
            logger.log(Level.SEVERE,"Cannot delete widget due to exception: {}",ex.toString());
            throw ex;
        }
        return new ResponseEntity<>(courseWidgetEntity, HttpStatus.OK);
    }

}
