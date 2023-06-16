package com.tabber.tabby.service.impl;

import com.tabber.tabby.constants.TabbyConstants;
import com.tabber.tabby.dto.ContestWidgetRequest;
import com.tabber.tabby.entity.ContestWidgetEntity;
import com.tabber.tabby.entity.PlanEntity;
import com.tabber.tabby.entity.RankWidgetEntity;
import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.exceptions.ContestWidgetNotExistsException;
import com.tabber.tabby.manager.PlansManager;
import com.tabber.tabby.respository.ContestWidgetRepository;
import com.tabber.tabby.service.ContestWidgetService;
import com.tabber.tabby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestWidgetServiceImpl implements ContestWidgetService {

    @Autowired
    UserService userService;

    @Autowired
    ContestWidgetRepository contestWidgetRepository;

    @Autowired
    PlansManager plansManager;

    @Override
    public ContestWidgetEntity createContestWidget(ContestWidgetRequest contestWidgetRequest, Long userId)
            throws Exception {
        UserEntity userEntity = userService.getUserFromUserId(userId);
        PlanEntity planEntity = plansManager.findPlanById(userEntity.getPlanId());
        if(userEntity.getContestWidgets().size() >= planEntity.getContestWidgetMax())
            throw new Exception("Contest widget size limit is reached");
        ContestWidgetEntity contestWidgetEntity = new ContestWidgetEntity()
                .toBuilder()
                .websiteId(contestWidgetRequest.getWebsiteId())
                .websiteUsername(contestWidgetRequest.getUsername())
                .rank(contestWidgetRequest.getRank())
                .userId(userId)
                .contestName(contestWidgetRequest.getContestName())
                .build();
        contestWidgetRepository.saveAndFlush(contestWidgetEntity);
        userEntity.getContestWidgets().add(contestWidgetEntity);
        userEntity = userService.setResumePresent(userEntity);
        return contestWidgetEntity;
    }

    @Override
    public ContestWidgetEntity updateContestWidget(ContestWidgetRequest contestWidgetRequest,Long contestId ,Long userId) throws ContestWidgetNotExistsException{
        if(contestId == null){
            throw new ContestWidgetNotExistsException("Widget Id not specified");
        }
        UserEntity userEntity = userService.getUserFromUserId(userId);
        ContestWidgetEntity contestWidget = getContestById(contestId,userEntity);
        if(contestWidget==null){
            throw new ContestWidgetNotExistsException("Doesn't exist for user "+userId+" for website id "+contestWidgetRequest.getWebsiteId());
        }
        userEntity.getContestWidgets().remove(contestWidget);
        contestWidget = contestWidget.toBuilder()
                .rank(contestWidgetRequest.getRank())
                .websiteId(contestWidgetRequest.getWebsiteId())
                .websiteUsername(contestWidgetRequest.getUsername())
                .contestName(contestWidgetRequest.getContestName())
                .invisible(contestWidgetRequest.getInvisible())
                .build();
        contestWidgetRepository.saveAndFlush(contestWidget);
        userEntity.getContestWidgets().add(contestWidget);
        return contestWidget;
    }

    @Override
    public ContestWidgetEntity deleteContestWidget(Long contestId, Long userId) throws ContestWidgetNotExistsException {
        UserEntity userEntity = userService.getUserFromUserId(userId);
        if(contestId == null){
            throw new ContestWidgetNotExistsException("Widget Id not specified");
        }
        ContestWidgetEntity contestWidget = getContestById(contestId,userEntity);
        if(contestWidget==null){
            throw new ContestWidgetNotExistsException("Doesn't exist for user "+userId+" for website id "+contestId);
        }
        contestWidgetRepository.delete(contestWidget);
        userEntity.getContestWidgets().remove(contestWidget);
        userEntity = userService.setResumePresent(userEntity);
        return contestWidget;
    }

    private ContestWidgetEntity getContestById(Long contestId,UserEntity userEntity){
        List<ContestWidgetEntity> contestWidgetEntities = userEntity.getContestWidgets();
        for(ContestWidgetEntity contestWidget:contestWidgetEntities){
            if(contestWidget.getContestWidgetId().equals(contestId)){
                return contestWidget;
            }
        }
        return null;
    }
}
