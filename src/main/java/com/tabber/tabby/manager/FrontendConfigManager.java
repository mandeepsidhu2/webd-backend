package com.tabber.tabby.manager;

import com.tabber.tabby.entity.FrontendConfigurationEntity;
import com.tabber.tabby.respository.FrontendConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrontendConfigManager {

    @Autowired
    FrontendConfigurationRepository frontendConfigurationRepository;

    public FrontendConfigurationEntity findFeConfigurationByPageTypeAndKey(String pageType, String key){
        return frontendConfigurationRepository.getTopByPageTypeAndKey(pageType,key);
    }

    public List<FrontendConfigurationEntity> findAllFEConfiguration(){
        return frontendConfigurationRepository.getAll();
    }
}
