package com.tabber.tabby.service;

import com.tabber.tabby.dto.RankWidgetRequest;
import com.tabber.tabby.entity.RankWidgetEntity;
import com.tabber.tabby.exceptions.RankWidgetExistsException;
import com.tabber.tabby.exceptions.RankWidgetNotExistsException;

public interface RankWidgetService {
    RankWidgetEntity createRankWidget(RankWidgetRequest rankWidgetRequest, Long userId) throws Exception;
    RankWidgetEntity updateRankWidget(RankWidgetRequest rankWidgetRequest, Long userId) throws RankWidgetNotExistsException;
    RankWidgetEntity deleteRankWidget(Integer websiteId, Long userId) throws RankWidgetNotExistsException;

}
