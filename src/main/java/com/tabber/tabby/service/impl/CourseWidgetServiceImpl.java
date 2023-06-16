package com.tabber.tabby.service.impl;

import com.tabber.tabby.dto.CourseWidgetRequest;
import com.tabber.tabby.entity.CourseWidgetEntity;
import com.tabber.tabby.entity.PlanEntity;
import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.exceptions.CourseWidgetNotExistsException;
import com.tabber.tabby.manager.PlansManager;
import com.tabber.tabby.respository.CourseWidgetRepository;
import com.tabber.tabby.service.CourseWidgetService;
import com.tabber.tabby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseWidgetServiceImpl implements CourseWidgetService {

    @Autowired
    UserService userService;

    @Autowired
    CourseWidgetRepository courseWidgetRepository;

    @Autowired
    PlansManager plansManager;

    @Override
    public CourseWidgetEntity createCourseWidget(CourseWidgetRequest courseWidgetRequest, Long userId) throws Exception {
        UserEntity userEntity = userService.getUserFromUserId(userId);
        PlanEntity planEntity = plansManager.findPlanById(userEntity.getPlanId());
        if(userEntity.getCourses().size() >= planEntity.getCoursesLimit())
            throw new Exception("Course widget size limit is reached");
        CourseWidgetEntity courseWidgetEntity = new CourseWidgetEntity()
                .toBuilder()
                .courseName(courseWidgetRequest.getCourseName())
                .certificateLink(courseWidgetRequest.getCertificateLink())
                .issuer(courseWidgetRequest.getIssuer())
                .userId(userId)
                .build();
        courseWidgetRepository.saveAndFlush(courseWidgetEntity);
        userEntity.getCourses().add(courseWidgetEntity);
        userEntity = userService.setResumePresent(userEntity);
        return courseWidgetEntity;
    }

    @Override
    public CourseWidgetEntity updateCourseWidget(CourseWidgetRequest courseWidgetRequest, Long courseId, Long userId) throws CourseWidgetNotExistsException {
        if(courseId == null){
            throw new CourseWidgetNotExistsException("Widget Id not specified");
        }
        UserEntity userEntity = userService.getUserFromUserId(userId);
        CourseWidgetEntity courseWidgetEntity = getCourseById(courseId,userEntity);
        if(courseWidgetEntity==null){
            throw new CourseWidgetNotExistsException("Doesn't exist for user "+userId+" for course id "+courseId);
        }
        userEntity.getCourses().remove(courseWidgetEntity);
        courseWidgetEntity = courseWidgetEntity.toBuilder()
                .courseName(courseWidgetRequest.getCourseName())
                .certificateLink(courseWidgetRequest.getCertificateLink())
                .issuer(courseWidgetRequest.getIssuer())
                .invisible(courseWidgetRequest.getInvisible())
                .build();
        courseWidgetRepository.saveAndFlush(courseWidgetEntity);
        userEntity.getCourses().add(courseWidgetEntity);
        return courseWidgetEntity;
    }

    @Override
    public CourseWidgetEntity deleteCourseWidget(Long courseId, Long userId) throws CourseWidgetNotExistsException {
        UserEntity userEntity = userService.getUserFromUserId(userId);
        if(courseId == null){
            throw new CourseWidgetNotExistsException("Widget Id not specified");
        }
        CourseWidgetEntity courseWidget = getCourseById(courseId, userEntity);
        if(courseWidget==null){
            throw new CourseWidgetNotExistsException("Doesn't exist for user "+userId+" for course id "+ courseId);
        }
        courseWidgetRepository.delete(courseWidget);
        userEntity.getCourses().remove(courseWidget);
        userEntity = userService.setResumePresent(userEntity);
        return courseWidget;
    }

    private CourseWidgetEntity getCourseById(Long courseId,UserEntity userEntity){
        List<CourseWidgetEntity> courseWidgetEntities = userEntity.getCourses();
        for(CourseWidgetEntity courseWidget:courseWidgetEntities){
            if(courseWidget.getCourseId().equals(courseId)){
                return courseWidget;
            }
        }
        return null;
    }
}
