package com.tabber.tabby.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.tabber.tabby.entity.FrontendConfigurationEntity;
import com.tabber.tabby.manager.FrontendConfigManager;
import com.tabber.tabby.service.impl.UserServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;


@Service
public class TrendingProfileService {

    @Autowired
    FrontendConfigManager frontendConfigManager;

    @Autowired
    UserServiceImpl userService;


    public JSONObject getTrendingProfiles(){

        FrontendConfigurationEntity frontendConfigurationEntity = frontendConfigManager.findFeConfigurationByPageTypeAndKey("Home", "TrendingProfile");
        JsonNode userIds = frontendConfigurationEntity.getValue();
        JSONObject result = new JSONObject();
        for (final JsonNode objNode : userIds.get("userIds")) {
            Long userId = Long.parseLong(objNode.toString());
            Object userObject= userService.getEnrichedUserData(userId,null,false);
            ((LinkedHashMap) userObject).remove("string_map");
            ((LinkedHashMap) userObject).remove("rank_widgets");
            ((LinkedHashMap) userObject).remove("course_widgets");
            ((LinkedHashMap) userObject).remove("contest_widgets");
            ((LinkedHashMap) userObject).remove("experience_widgets");
            result.put(userId.toString(),userObject);
        }
        return result;
    }
}