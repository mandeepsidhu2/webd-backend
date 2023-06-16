package com.tabber.tabby.service.admin;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.devicefarm.model.Test;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tabber.tabby.entity.CustomLinkEntity;
import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.respository.CustomLinkRepository;
import com.tabber.tabby.service.CommonService;
import com.tabber.tabby.service.CustomLinkService;
import com.tabber.tabby.service.UserService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;

@Service
public class UploadExcelSheetService {

    @Autowired
    UserService userService;

    @Autowired
    CustomLinkService customLinkService;

    @Autowired
    CommonService commonService;

    public void uploadExcelSheetForCustomLink(MultipartFile multipartFile) throws Exception{
        String exceptionString = "";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);
            for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
                String linkGroup = worksheet.getRow(i).getCell(0).getStringCellValue();
                Double collegeIdentifierNumber = worksheet.getRow(i).getCell(1).getNumericCellValue();
                String studentEmail = worksheet.getRow(i).getCell(2).getStringCellValue();
                UserEntity userEntity = userService.getUserFromEmail(studentEmail);
                if(linkGroup == null || collegeIdentifierNumber == null || studentEmail == null){
                    continue;
                }
                if(userEntity == null){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("email",studentEmail) ;
                    jsonObject.put("linkGroup",linkGroup) ;
                    jsonObject.put("collegeIdentifierNumber",collegeIdentifierNumber);
                    exceptionString+= (jsonObject.toString()+'\n');
                    continue;
                }
                if(customLinkService.getCustomLinkEntityFromUserId(userEntity.getUserId())!=null){
                    continue;
                }

                CustomLinkEntity customLinkEntity = new CustomLinkEntity().toBuilder()
                        .groupId(collegeIdentifierNumber.longValue())
                        .linkGroup(linkGroup)
                        .user(userEntity)
                        .linkType("CUSTOM")
                        .build();
                customLinkService.saveAndFlush(customLinkEntity);
                userEntity.setCustomLinkEntity(customLinkEntity);
            }
        }
        catch (Exception ex){
            throw ex;
        }
        commonService.setLog(UploadExcelSheetService.class.toString(),exceptionString,null);
    }
}
