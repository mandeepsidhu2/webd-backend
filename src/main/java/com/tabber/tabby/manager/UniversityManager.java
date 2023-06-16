package com.tabber.tabby.manager;

import com.tabber.tabby.entity.UniversityEntity;
import com.tabber.tabby.respository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UniversityManager {

    @Autowired
    UniversityRepository universityRepository;

    public Map<Integer,String> getUniversityList() {
        List<UniversityEntity> universityEntities = universityRepository.findAll();
        Map<Integer,String> map = new HashMap<>();
        for(int i=1;i<=universityEntities.size();i++)
                map.put(i,universityEntities.get(i-1).getName());

        return map;
    }
}
