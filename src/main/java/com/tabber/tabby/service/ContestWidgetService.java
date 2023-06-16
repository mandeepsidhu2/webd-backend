package com.tabber.tabby.service;

import com.tabber.tabby.dto.ContestWidgetRequest;
import com.tabber.tabby.entity.ContestWidgetEntity;
import com.tabber.tabby.exceptions.ContestWidgetExistsException;
import com.tabber.tabby.exceptions.ContestWidgetNotExistsException;

public interface ContestWidgetService {
    ContestWidgetEntity createContestWidget(ContestWidgetRequest contestWidgetRequest, Long userId) throws Exception;

    ContestWidgetEntity updateContestWidget(ContestWidgetRequest contestWidgetRequest, Long contestId,Long userId) throws ContestWidgetNotExistsException;

    ContestWidgetEntity deleteContestWidget(Long contestId, Long userId) throws ContestWidgetNotExistsException;
}
