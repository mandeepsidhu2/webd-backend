package com.tabber.tabby.service.admin;

import com.tabber.tabby.entity.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersService {
    public List<UserEntity> getUsersFromLimitAndOffset(Pageable pageable);
    public List<UserEntity> getUsers();
    UserEntity updatePlanId(Integer planId,Long userId);
    public List<UserEntity> getSimilarNameUsers(String name, Pageable pageable);
    public List<UserEntity> getSimilarPlanUsers(Integer planId, Pageable pageable);
    public UserEntity getUserFromEmail(String email);
    public String setViewsManually(Long userId, Long views);
    public UserEntity getUserFromId(Long id);
}
