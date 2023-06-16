package com.tabber.tabby.service.impl;

import com.tabber.tabby.manager.UniversityManager;
import com.tabber.tabby.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityManager universityManager;

    @Override
    public Map<Integer, String> getAllUniversityMap() throws Exception{
        return universityManager.getUniversityList();
    }

}
