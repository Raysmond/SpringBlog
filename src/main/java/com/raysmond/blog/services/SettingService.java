package com.raysmond.blog.services;

import java.io.Serializable;

/**
 * Created by Raysmond on 9/27/15.
 */
public interface SettingService {
    public Serializable get(String key);
    public Serializable get(String key, Serializable defaultValue);
    public void put(String key, Serializable value);
}
