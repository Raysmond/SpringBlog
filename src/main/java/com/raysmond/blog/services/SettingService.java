package com.raysmond.blog.services;

import java.io.Serializable;

/**
 * @author Raysmond<i@raysmond.com>
 */
public interface SettingService {
    Serializable get(String key);
    Serializable get(String key, Serializable defaultValue);
    void put(String key, Serializable value);
}
