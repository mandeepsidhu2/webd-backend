package com.tabber.tabby.controllers;

import com.tabber.tabby.constants.URIEndpoints;
import com.tabber.tabby.dto.admin.FrontendConfigRequest;
import com.tabber.tabby.entity.FrontendConfigurationEntity;
import com.tabber.tabby.exceptions.FrontendConfigurationExistsException;
import com.tabber.tabby.exceptions.FrontendConfigurationNotExistsException;
import com.tabber.tabby.manager.FrontendConfigManager;
import com.tabber.tabby.respository.FrontendConfigurationRepository;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.UserService;
import com.tabber.tabby.service.admin.UploadExcelSheetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(URIEndpoints.ADMIN)
@RestController
public class AdminController {

    @Autowired
    FrontendConfigManager frontendConfigManager;

    @Autowired
    FrontendConfigurationRepository frontendConfigurationRepository;

    @Autowired
    UserService userService;

    @Autowired
    CommonService commonService;

    @Autowired
    UploadExcelSheetService uploadExcelSheetService;


    @PostMapping(value = URIEndpoints.FRONTEND_CONFIG_CREATE,produces = "application/json")
    public ResponseEntity<FrontendConfigurationEntity> createFeConfiguration(
            @RequestBody @Validated FrontendConfigRequest frontendConfigRequest) throws FrontendConfigurationExistsException {
        FrontendConfigurationEntity frontendConfigurationEntity=
                frontendConfigManager.findFeConfigurationByPageTypeAndKey(frontendConfigRequest.getPageType(), frontendConfigRequest.getKey());
        if(frontendConfigurationEntity != null){
            throw new FrontendConfigurationExistsException("Configuration already exists for this page type and key, try updating");
        }
         frontendConfigurationEntity= new FrontendConfigurationEntity().toBuilder()
                .key(frontendConfigRequest.getKey())
                .pageType(frontendConfigRequest.getPageType())
                .value(frontendConfigRequest.getValue())
                .build();
        frontendConfigurationRepository.saveAndFlush(frontendConfigurationEntity);
        return new ResponseEntity<>(frontendConfigurationEntity,HttpStatus.OK);
    }

    @PutMapping(value = URIEndpoints.FRONTEND_CONFIG_UPDATE,produces = "application/json")
    public ResponseEntity<FrontendConfigurationEntity> updateFeConfiguration(
            @RequestBody @Validated FrontendConfigRequest frontendConfigRequest) throws FrontendConfigurationNotExistsException {
        FrontendConfigurationEntity frontendConfigurationEntity=
                frontendConfigManager.findFeConfigurationByPageTypeAndKey(frontendConfigRequest.getPageType(), frontendConfigRequest.getKey());
        if(frontendConfigurationEntity == null){
            throw new FrontendConfigurationNotExistsException("Configuration doesn't exist for this page type and key, try creating first");
        }
        frontendConfigurationEntity.setValue(frontendConfigRequest.getValue());
        frontendConfigurationRepository.saveAndFlush(frontendConfigurationEntity);
        return new ResponseEntity<>(frontendConfigurationEntity,HttpStatus.OK);
    }

    @DeleteMapping(value = URIEndpoints.FRONTEND_CONFIG_DELETE,produces = "application/json")
    public ResponseEntity<FrontendConfigurationEntity> deleteFeConfiguration(
         @RequestParam(value = "page_type") String pageType,@RequestParam(value = "key") String key) throws FrontendConfigurationNotExistsException {
        FrontendConfigurationEntity frontendConfigurationEntity=
                frontendConfigManager.findFeConfigurationByPageTypeAndKey(pageType, key);
        if(frontendConfigurationEntity == null){
            throw new FrontendConfigurationNotExistsException("Configuration doesn't exist for this page type and key, try creating first");
        }
        frontendConfigurationRepository.delete(frontendConfigurationEntity);
        return new ResponseEntity<>(frontendConfigurationEntity,HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.FRONTEND_CONFIG_GET,produces = "application/json")
    public ResponseEntity<FrontendConfigurationEntity> getFeConfiguration(
            @RequestParam(value = "page_type") String pageType,@RequestParam(value = "key") String key) throws FrontendConfigurationNotExistsException {
        FrontendConfigurationEntity frontendConfigurationEntity=
                frontendConfigManager.findFeConfigurationByPageTypeAndKey(pageType, key);
        if(frontendConfigurationEntity == null){
            throw new FrontendConfigurationNotExistsException("Configuration doesn't exist for this page type and key, try creating first");
        }
        return new ResponseEntity<>(frontendConfigurationEntity,HttpStatus.OK);
    }

    @GetMapping(value = URIEndpoints.FRONTEND_CONFIG_ALL,produces = "application/json")
    public ResponseEntity<List<FrontendConfigurationEntity>> getAllFeConfigurations() {
        List<FrontendConfigurationEntity> frontendConfigurationEntities=
                frontendConfigManager.findAllFEConfiguration();
        return new ResponseEntity<>(frontendConfigurationEntities,HttpStatus.OK);
    }

    @DeleteMapping(value = "delete_user",produces = "application/json")
    public ResponseEntity deleteUser(@RequestParam Long deleteUserId) {
        try {
            Long userId= Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            userService.deleteUser(userId,deleteUserId);
        }
        catch (Exception ex){
            commonService.setLog(AdminController.class.toString(), ex.toString(), deleteUserId);
            throw ex;
        }
        return new ResponseEntity<>(true,HttpStatus.OK);

    }

    @PostMapping(value = "upload_custom_link_sheet",produces = "application/json")
    public ResponseEntity uploadCustomLinkSheet(@RequestParam("file") MultipartFile multipartFile) throws Exception{
        try {
            uploadExcelSheetService.uploadExcelSheetForCustomLink(multipartFile);
        }
        catch (Exception ex){
            throw ex;
        }
        return new ResponseEntity<>(true,HttpStatus.OK);

    }
}
