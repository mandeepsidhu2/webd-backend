package com.tabber.tabby.service;

import com.tabber.tabby.dto.PersonalProjectRequest;
import com.tabber.tabby.entity.PersonalProjectEntity;

public interface PersonalProjectService {
    PersonalProjectEntity createPersonalProject(PersonalProjectRequest personalProjectRequest , Long userId) throws Exception;
    PersonalProjectEntity updatePersonalProject(PersonalProjectRequest personalProjectRequest, Long projectId, Long userId);
    PersonalProjectEntity deletePersonalProject(Long projectId, Long userId);
}
