package com.tabber.tabby.manager;

import com.tabber.tabby.entity.PlanEntity;
import com.tabber.tabby.respository.PlansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlansManager {

    @Autowired
    PlansRepository plansRepository;

    public PlanEntity findPlanById(Integer planId){
        if(planId==null)
            return null;
        return plansRepository.getTopByPlanId(planId);
    }
}
