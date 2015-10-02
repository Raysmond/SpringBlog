package com.raysmond.blog.repositories;

import com.raysmond.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
public interface TagRepository extends JpaRepository<Tag, Long>{
    Tag findByName(String name);
}
