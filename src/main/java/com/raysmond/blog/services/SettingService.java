package com.raysmond.blog.services;

import java.io.Serializable;

/**
 * @author Raysmond<i@raysmond.com>
 */
public interface SettingService {
    public Serializable get(String key);
    public Serializable get(String key, Serializable defaultValue);
    public void put(String key, Serializable value);
}
