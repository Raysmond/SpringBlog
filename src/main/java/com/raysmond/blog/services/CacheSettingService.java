package com.raysmond.blog.services;

import com.raysmond.blog.models.Setting;
import com.raysmond.blog.repositories.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Service
public class CacheSettingService implements SettingService {

    private SettingRepository settingRepository;

    private static final String CACHE_NAME = "cache.settings";

    @Autowired
    public CacheSettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(SettingService.class);

    @Override
    public Serializable get(String key) {
        Setting setting = settingRepository.findByKey(key);
        Serializable value = null;
        try {
            value = setting == null ? null : setting.getValue();
        } catch (Exception ex) {
            logger.info("Cannot deserialize setting value with key = " + key);
        }

        logger.info("Get setting " + key + " from database. Value = " + value);

        return value;
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#key")
    public Serializable get(String key, Serializable defaultValue) {
        Serializable value = get(key);
        return value == null ? defaultValue : value;
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#key")
    public void put(String key, Serializable value) {
        logger.info("Update setting " + key + " to database. Value = " + value);

        Setting setting = settingRepository.findByKey(key);
        if (setting == null) {
            setting = new Setting();
            setting.setKey(key);
        }
        try {
            setting.setValue(value);
            settingRepository.save(setting);
        } catch (Exception ex) {
            
            logger.info("Cannot save setting value with type: " + value.getClass() + ". key = " + key);
        }
    }
}
