package com.tabber.tabby.service.admin.impl;

import com.tabber.tabby.controllers.AuthController;
import com.tabber.tabby.entity.PortfolioEntity;
import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.respository.PortfolioRepository;
import com.tabber.tabby.respository.UserRepository;
import com.tabber.tabby.service.UserService;
import com.tabber.tabby.service.admin.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PortfolioRepository portfolioRepository;


    @Autowired
    UserService userService;


    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Override
    public List<UserEntity> getUsersFromLimitAndOffset(Pageable pageable) {
        try {
            List<UserEntity> users = userRepository.getUsersFromLimitAndOffset(pageable);
            return users;
        }
         catch (Exception ex) {
            logger.log(Level.WARNING, "Exception occur Inside Admin User Service :: getUsersFromLimitAndOffset :: " + ex);
            throw ex;
         }
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> getSimilarNameUsers(String name, Pageable pageable) {
        try {
            List<UserEntity> users = userRepository.getSimilarNameUsers(name, pageable);
            return users;
        }
        catch (Exception ex) {
            logger.log(Level.WARNING, "Exception occur Inside Admin User Service :: getSimilarNameUsers :: " + ex);
            throw ex;
        }
    }

    @Override
    public List<UserEntity> getSimilarPlanUsers(Integer planId, Pageable pageable) {
        try {
            List<UserEntity> users = userRepository.getSimilarPlanUsers(planId, pageable);
            return users;
        }
        catch (Exception ex) {
            logger.log(Level.WARNING, "Exception occur Inside Admin User Service :: getSimilarPlanUsers :: " + ex);
            throw ex;
        }
    }

    @Override
    public UserEntity getUserFromEmail(String email) {
        try {
            UserEntity users = userRepository.getUserFromEmail(email);
            return users;
        }
        catch (Exception ex) {
            logger.log(Level.WARNING, "Exception occur Inside Admin User Service :: getUserFromEmail :: " + ex);
            throw ex;
        }
    }

    public String setViewsManually(Long userId, Long views) {
            try {
                UserEntity userEntity = userRepository.getTopByUserId(userId);
                PortfolioEntity portfolioEntity = portfolioRepository.getTopByPortfolioUserId(userId);
                portfolioEntity.setViews(views);
                portfolioRepository.saveAndFlush(portfolioEntity);
                userEntity.setPortfolio(portfolioEntity);
                return "Updated Views";
            }
            catch (Exception ex) {
                logger.log(Level.WARNING, "Exception occur Inside Admin User Service :: setViewsManually :: " + ex);
                throw ex;
            }
    }

    @Override
    public UserEntity updatePlanId(Integer planId,Long userId){
        UserEntity userEntity= userRepository.getTopByUserId(userId);
        userEntity.setPlanId(planId);
        userRepository.saveAndFlush(userEntity);
        return userEntity;
    }


    @Override
    public UserEntity getUserFromId(Long id) {
        try {
            UserEntity users = userRepository.getUserFromId(id);
            return users;
        }
        catch (Exception ex) {
            logger.log(Level.WARNING, "Exception occur Inside Admin User Service :: getUserFromId :: " + ex);
            throw ex;
        }
    }

}
