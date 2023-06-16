package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.service.impl.UniversityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = URIEndpoints.UNIVERSITY)
public class UniversityController {

    @Autowired
    UniversityServiceImpl universityService;

    @GetMapping(value = URIEndpoints.UNIVERSITY_LIST,produces = "application/json")
    public ResponseEntity<Map> getUniversityList() throws Exception {
        return new ResponseEntity<>(universityService.getAllUniversityMap(), HttpStatus.OK);
    }

}
