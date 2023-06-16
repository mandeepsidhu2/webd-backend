package com.tabber.tabby.service;

import com.tabber.tabby.entity.WebsiteEntity;

public interface WebsiteService {
    WebsiteEntity getWebsiteById(Integer websiteId);
    WebsiteEntity getWebsiteByNameAndType(String name, String type);
}
