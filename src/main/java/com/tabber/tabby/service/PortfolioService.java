package com.tabber.tabby.service;

import com.tabber.tabby.dto.PortfolioRequest;
import com.tabber.tabby.dto.SocialWebsiteDto;
import com.tabber.tabby.entity.PortfolioEntity;

import java.util.ArrayList;

public interface PortfolioService {
    PortfolioEntity createPortfolio(PortfolioRequest portfolioRequest, Long userId);

    PortfolioEntity updatePortfolio(PortfolioRequest portfolioRequest, Long userId);

    void updateResumeLink(String cloudLink, Long userId);

    ArrayList<SocialWebsiteDto> addSocialWeblink(String websiteName, String link, Long userId) throws Exception;

    ArrayList<SocialWebsiteDto> updateSocialWeblink(String websiteName, String link, Long userId) throws Exception;

    void deletePortfolio(PortfolioEntity portfolioEntity);

}


