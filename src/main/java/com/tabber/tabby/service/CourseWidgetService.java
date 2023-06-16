package com.tabber.tabby.service;

import com.tabber.tabby.dto.CourseWidgetRequest;
import com.tabber.tabby.entity.CourseWidgetEntity;
import com.tabber.tabby.exceptions.CourseWidgetNotExistsException;

public interface CourseWidgetService {

    CourseWidgetEntity createCourseWidget(CourseWidgetRequest courseWidgetRequest, Long userId) throws Exception;

    CourseWidgetEntity updateCourseWidget(CourseWidgetRequest courseWidgetRequest, Long courseId, Long userId) throws CourseWidgetNotExistsException;

    CourseWidgetEntity deleteCourseWidget(Long courseId, Long userId) throws CourseWidgetNotExistsException;
}
