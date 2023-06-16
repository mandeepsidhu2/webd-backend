package com.tabber.tabby.service;

import com.tabber.tabby.entity.LogEntity;
import com.tabber.tabby.respository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CommonService {

    @Autowired
    LogsRepository logsRepository;

    private static Logger logger = Logger.getLogger(CommonService.class.getName());

    public void setLog(String className, String exception, Long userId) {
        try {
            LogEntity logEntity = new LogEntity();
            logEntity.setClassName(className);
            logEntity.setUserId(userId);
            logEntity.setType(exception);
            logsRepository.save(logEntity);
        }
        catch (Exception ex) {
            logger.log(Level.WARNING, "Cannot set log", ex);
        }
    }
}
