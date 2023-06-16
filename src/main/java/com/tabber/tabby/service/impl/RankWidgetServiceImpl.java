package com.tabber.tabby.service.impl;

import com.tabber.tabby.constants.TabbyConstants;
import com.tabber.tabby.dto.RankWidgetRequest;
import com.tabber.tabby.entity.PlanEntity;
import com.tabber.tabby.entity.RankWidgetEntity;
import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.exceptions.RankWidgetExistsException;
import com.tabber.tabby.exceptions.RankWidgetNotExistsException;
import com.tabber.tabby.manager.PlansManager;
import com.tabber.tabby.respository.RankWidgetRepository;
import com.tabber.tabby.service.RankWidgetService;
import com.tabber.tabby.service.UserService;
import com.tabber.tabby.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RankWidgetServiceImpl implements RankWidgetService {

    @Autowired
    RankWidgetRepository rankWidgetRepository;

    @Autowired
    UserService userService;

    @Autowired
    PlansManager plansManager;

    @Override
    public RankWidgetEntity createRankWidget(RankWidgetRequest rankWidgetRequest, Long userId) throws Exception {
        UserEntity userEntity = userService.getUserFromUserId(userId);
        PlanEntity planEntity = plansManager.findPlanById(userEntity.getPlanId());
        if(rankWidgetExistsForWebsite(userEntity,rankWidgetRequest.getWebsiteId())!=null){
            throw new RankWidgetExistsException("Exists for user "+userId+" for website id "+rankWidgetRequest.getWebsiteId());
        }

        if(userEntity.getRankWidgets().size() >= planEntity.getRankWidgetMax())
            throw new Exception("Rank widget size limit is reached");
        RankWidgetEntity rankWidgetEntity = new RankWidgetEntity()
                .toBuilder()
                .websiteId(rankWidgetRequest.getWebsiteId())
                .websiteUsername(rankWidgetRequest.getUsername())
                .rank(rankWidgetRequest.getRank())
                .userId(userId)
                .link(StringUtil.addHttpsToURI(rankWidgetRequest.getLink()))
                .build();
        rankWidgetRepository.saveAndFlush(rankWidgetEntity);
        userEntity.getRankWidgets().add(rankWidgetEntity);
        userEntity = userService.setResumePresent(userEntity);
        return rankWidgetEntity;
    }

    @Override
    public RankWidgetEntity updateRankWidget(RankWidgetRequest rankWidgetRequest, Long userId) throws RankWidgetNotExistsException{
        UserEntity userEntity = userService.getUserFromUserId(userId);
        RankWidgetEntity rankWidget = rankWidgetExistsForWebsite(userEntity,rankWidgetRequest.getWebsiteId());
        if(rankWidget==null){
            throw new RankWidgetNotExistsException("Doesn't exist for user "+userId+" for website id "+rankWidgetRequest.getWebsiteId());
        }
        userEntity.getRankWidgets().remove(rankWidget);
        rankWidget = rankWidget.toBuilder()
                .rank(rankWidgetRequest.getRank())
                .websiteId(rankWidgetRequest.getWebsiteId())
                .websiteUsername(rankWidgetRequest.getUsername())
                .invisible(rankWidgetRequest.getInvisible())
                .link(rankWidgetRequest.getLink())
                .build();
        rankWidgetRepository.saveAndFlush(rankWidget);
        userEntity.getRankWidgets().add(rankWidget);
        return rankWidget;
    }

    @Override
    public RankWidgetEntity deleteRankWidget(Integer websiteId, Long userId) throws RankWidgetNotExistsException{
        UserEntity userEntity = userService.getUserFromUserId(userId);
        RankWidgetEntity rankWidget = rankWidgetExistsForWebsite(userEntity,websiteId);
        if(rankWidget==null){
            throw new RankWidgetNotExistsException("Doesn't exist for user "+userId+" for website id "+websiteId);
        }
        rankWidgetRepository.delete(rankWidget);
        userEntity.getRankWidgets().remove(rankWidget);
        userEntity = userService.setResumePresent(userEntity);
        return rankWidget;
    }


    private RankWidgetEntity rankWidgetExistsForWebsite(UserEntity userEntity,Integer websiteId){
        List<RankWidgetEntity> rankWidgetEntities = userEntity.getRankWidgets();
        for(RankWidgetEntity rankWidget:rankWidgetEntities){
            if(rankWidget.getWebsiteId().equals(websiteId)){
                return rankWidget;
            }
        }
        return null;
    }

}
