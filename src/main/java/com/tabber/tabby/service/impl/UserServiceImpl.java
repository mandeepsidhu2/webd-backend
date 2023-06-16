package com.tabber.tabby.service.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.tabber.tabby.constants.TabbyConstants;
import com.tabber.tabby.entity.*;
import com.tabber.tabby.exceptions.BadRequestException;
import com.tabber.tabby.exceptions.UnauthorisedException;
import com.tabber.tabby.manager.FrontendConfigManager;
import com.tabber.tabby.manager.UserResumeManager;
import com.tabber.tabby.respository.UserRepository;
import com.tabber.tabby.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserResumeManager userResumeManager;

    @Autowired
    UniversityService universityService;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    RankWidgetService rankWidgetService;

    @Autowired
    ContestWidgetService contestWidgetService;

    @Autowired
    PersonalProjectService personalProjectService;

    @Autowired
    ExperienceWidgetService experienceWidgetService;

    @Autowired
    CourseWidgetService courseWidgetService;

    @Autowired
    CustomLinkService customLinkService;

    @Autowired
    FrontendConfigManager frontendConfigManager;

    @Autowired
    CommonService commonService;

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());


    @Override
    public Long save(UserEntity userEntity){
        if(userEntity==null) return null;
        userRepository.saveAndFlush(userEntity);
        return userEntity.getUserId();
    }


    @Override
    public UserEntity getUserFromUserId(Long userId){
        UserEntity userEntity= userResumeManager.findUserById(userId);
        return userEntity;
    }

    // This method uses repository, not cache, this is not customer facing
    @Override
    public UserEntity getUserFromEmail(String emailId){
        return userRepository.getTopByEmailId(emailId);
    }


    @Override
    public UserEntity getUserFromSub(String sub){
        return userRepository.getTopBySub(sub);
    }

    @Override
    public UserEntity setResumePresent(UserEntity userEntity){
        if(userEntity.getPortfolio() == null && userEntity.getContestWidgets().size() == 0
        && userEntity.getRankWidgets().size()==0 && userEntity.getPersonalProjects().size()==0 && userEntity.getExperienceWidgets().size()==0
                && userEntity.getCourses().size()==0 && userEntity.getResumePresent()){
            userEntity.setResumePresent(false);
            userRepository.saveAndFlush(userEntity);
            return userEntity;
        }
        else if(!(userEntity.getPortfolio() == null && userEntity.getContestWidgets().size() == 0
                && userEntity.getRankWidgets().size()==0 && userEntity.getPersonalProjects().size()==0 && userEntity.getExperienceWidgets().size()==0
                && userEntity.getCourses().size()==0)
                && !userEntity.getResumePresent()){
            userEntity.setResumePresent(true);
            userRepository.saveAndFlush(userEntity);
            return userEntity;
        }
        return userEntity;
    }

    @Override
    public void updateUserName(Long userId, String userName){
        UserEntity userEntity= userResumeManager.findUserById(userId);
        userEntity.setName(userName);
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public void updateUserCookiePermission(Long userId){
        UserEntity userEntity= userResumeManager.findUserById(userId);
        userEntity.setCookieAccepted(true);
        userRepository.saveAndFlush(userEntity);

    }

    @Override
    public void updateUserType(Long userId, String userType) {
        UserEntity userEntity= userResumeManager.findUserById(userId);
        userEntity.setUserType(userType);
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public Object getUserFromCustomLink(Long id, String groupId, String trackingId, Boolean considerViews){
        Long userId = userResumeManager.getCustomLinkUserId(groupId,id);
        if(userId == null)
            return null;
        return getEnrichedUserData(userId, trackingId, considerViews);
    }

    @Override
    public Object getEnrichedUserData(Long userId, String trackingId, Boolean considerViews){
        UserEntity userEntity= userResumeManager.findUserById(userId);
        FrontendConfigurationEntity portfolioStringMap = frontendConfigManager.findFeConfigurationByPageTypeAndKey("portfolio", "education_level");
        if(userEntity == null){
            throw new BadRequestException("User doesn't exist");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        String portfolioLink = "http://localhost:3000/d/";
        if(userEntity.getCustomLinkEntity()!=null){
            portfolioLink += userEntity.getCustomLinkEntity().getLinkGroup() + '/' + userEntity.getCustomLinkEntity().getGroupId();
        }
        else{
            portfolioLink += userEntity.getUserId();
        }
        Object userEnrichedData;
        try {
            userEnrichedData = objectMapper.readValue(objectMapper.writeValueAsString(userEntity), Object.class);
            LinkedHashMap<String , String> portfolioObject= (LinkedHashMap)((LinkedHashMap) userEnrichedData).get("portfolio");
            if(((LinkedHashMap) userEnrichedData).get("portfolio")!=null) {
                Object collegeIndex = ((LinkedHashMap) ((LinkedHashMap) userEnrichedData).get("portfolio")).get("college");
                String collegeName;
                if ((Integer) collegeIndex == -1) {
                    collegeName = portfolioObject.get("college_others");
                } else {
                    collegeName = universityService.getAllUniversityMap().get(collegeIndex);
                }
                ((LinkedHashMap) ((LinkedHashMap) userEnrichedData).get("portfolio")).put("college_num", collegeIndex);
                ((LinkedHashMap) ((LinkedHashMap) userEnrichedData).get("portfolio")).put("college", collegeName);
                ((LinkedHashMap) ((LinkedHashMap) userEnrichedData).get("portfolio")).remove("college_others");
            }
            ((LinkedHashMap) userEnrichedData).remove("custom_link_entity",portfolioLink);
            ((LinkedHashMap) userEnrichedData).put("portfolio_link",portfolioLink);
            ((LinkedHashMap) userEnrichedData).put("string_map",portfolioStringMap.getValue());
            return userEnrichedData;
        }catch(Exception e){
            commonService.setLog(UserServiceImpl.class.toString(), e.toString(), userId);
            logger.log(Level.INFO,"Error in user data enriching  "+e);
        }
        return null;
    }

    @Override
    public void deleteUser(Long userId,Long deleteUserId){
        UserEntity user= userResumeManager.findUserById(userId);
        if(!TabbyConstants.admins.contains(user.getEmail())){
            throw new UnauthorisedException("Email id is not of admin");
        }
        UserEntity userEntity= userResumeManager.findUserById(deleteUserId);
        if(userEntity.getPortfolio() != null) {
            portfolioService.deletePortfolio(userEntity.getPortfolio());
        }
        if(userEntity.getCustomLinkEntity() != null) {
            customLinkService.deleteCustomLink(userEntity.getCustomLinkEntity());
        }
        for(RankWidgetEntity rankWidgetEntity :userEntity.getRankWidgets()){
            rankWidgetService.deleteRankWidget(rankWidgetEntity.getWebsiteId(),deleteUserId);
        }
        for(ContestWidgetEntity contestWidgetEntity :userEntity.getContestWidgets()){
            contestWidgetService.deleteContestWidget(contestWidgetEntity.getContestWidgetId(),deleteUserId);
        }
        for(PersonalProjectEntity personalProjectEntity :userEntity.getPersonalProjects()){
            personalProjectService.deletePersonalProject(personalProjectEntity.getPersonalProjectId(),deleteUserId);
        }
        for(ExperienceWidgetEntity experienceWidgetEntity :userEntity.getExperienceWidgets()){
            experienceWidgetService.deleteExperienceWidget(experienceWidgetEntity.getExperienceId(),deleteUserId);
        }
        for(CourseWidgetEntity courseWidgetEntity :userEntity.getCourses()){
            courseWidgetService.deleteCourseWidget(courseWidgetEntity.getCourseId(),deleteUserId);
        }
        userRepository.deleteById(userEntity.getUserId());

    }
}
