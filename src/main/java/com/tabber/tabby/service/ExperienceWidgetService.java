package com.tabber.tabby.service;

import com.tabber.tabby.dto.ExperienceWidgetRequest;
import com.tabber.tabby.entity.ExperienceWidgetEntity;
import com.tabber.tabby.exceptions.ExperienceWidgetNotExistsException;
import org.springframework.stereotype.Service;

@Service
public interface ExperienceWidgetService {

    ExperienceWidgetEntity createExperienceWidget(ExperienceWidgetRequest experienceWidgetRequest, Long userId) throws Exception;

    ExperienceWidgetEntity updateExperienceWidget(ExperienceWidgetRequest experienceWidgetRequest, Long experienceId, Long userId) throws ExperienceWidgetNotExistsException;

    ExperienceWidgetEntity deleteExperienceWidget(Long experienceId, Long userId) throws ExperienceWidgetNotExistsException;
}
