package com.raysmond.blog.repositories;

import com.raysmond.blog.models.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Raysmond on 9/27/15.
 */
@Repository
@Transactional
public interface SettingRepository extends CrudRepository<Setting, Long> {
    Setting findByKey(String key);
}
