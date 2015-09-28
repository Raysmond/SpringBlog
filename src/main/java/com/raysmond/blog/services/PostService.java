package com.raysmond.blog.services;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.repositories.UserRepository;
import com.raysmond.blog.utils.Markdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public static final String CACHE_NAME = "cache.post";
    public static final String CACHE_NAME_ARCHIVE = CACHE_NAME + ".archive";
    public static final String CACHE_NAME_PAGE = CACHE_NAME + ".page";

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Cacheable(CACHE_NAME)
    public Post getPost(Long postId) {
        logger.info("Get post " + postId + " from database");

        return postRepository.findOne(postId);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true)
    })
    public Post createPost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
        }

        return postRepository.save(post);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true)
    })
    public Post updatePost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
        }

        return postRepository.save(post);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true)
    })
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Cacheable(value = CACHE_NAME_ARCHIVE, key = "#root.method.name")
    public List<Post> getArchivePosts() {
        logger.info("Get all archive posts from database.");

        Iterable<Post> archivePosts = postRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        List<Post> cachedPosts = new ArrayList<>();
        for (Post post : archivePosts) {
            Post cachedPost = new Post();
            cachedPost.setId(post.getId());
            cachedPost.setTitle(post.getTitle());
            cachedPost.setCreatedAt(post.getCreatedAt());
            cachedPosts.add(cachedPost);
        }

        return cachedPosts;
    }

    @Cacheable(value = CACHE_NAME_PAGE, key = "T(java.lang.String).valueOf(#page).concat('-').concat(#pageSize)")
    public Page<Post> getAllPostsByPage(int page, int pageSize) {
        logger.info("Get posts by page " + page + " from database");

        return postRepository.findAllByPostType(
                PostType.POST,
                new PageRequest(page, pageSize, Sort.Direction.DESC, "id"));
    }

    public Post createAboutPage() {
        logger.info("Create default about page");

        Post post = new Post();
        post.setTitle("About");
        post.setContent("about page");
        post.setUser(userRepository.findByEmail("user@raysmond.com"));
        post.setPostFormat(PostFormat.MARKDOWN);

        return createPost(post);
    }
}
