package com.raysmond.blog.repositories;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);

    Post findByTitleAndPostType(String title, PostType postType);

    Post findByPostStatus(PostStatus postStatus);

    Post findByPermalinkAndPostStatus(String permalink, PostStatus postStatus);

    Page<Post> findAllByPostType(PostType postType, Pageable pageRequest);

    Page<Post> findAllByPostTypeAndPostStatus(PostType postType, PostStatus postStatus, Pageable pageRequest);
}

