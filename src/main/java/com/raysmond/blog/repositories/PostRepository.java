package com.raysmond.blog.repositories;

import com.raysmond.blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Raysmond on 9/25/15.
 */

@Repository
@Transactional
public interface PostRepository extends CrudRepository<Post, Long>, PagingAndSortingRepository<Post, Long>{

}

