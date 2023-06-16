package com.tabber.tabby.service;

import com.tabber.tabby.entity.UserEntity;
import liquibase.pro.packaged.L;

public interface UserService {
    Long save(UserEntity userEntity);
    UserEntity getUserFromUserId(Long userId);
    UserEntity getUserFromEmail(String emailId);
    UserEntity getUserFromSub(String sub);
    UserEntity setResumePresent(UserEntity userEntity);
    void updateUserName(Long userId, String userName);
    Object getEnrichedUserData(Long userId, String trackingId, Boolean considerViews);
    Object getUserFromCustomLink(Long id, String groupId, String trackingId, Boolean considerViews);
    void deleteUser(Long userId, Long deleteUserId);
    void updateUserCookiePermission(Long userId);
    void updateUserType(Long userId,String userType);
}
