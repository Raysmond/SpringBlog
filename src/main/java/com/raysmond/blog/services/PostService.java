package com.raysmond.blog.services;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.User;
import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raysmond on 9/26/15.
 */
@Service
public class PostService {
    @Autowired
    private PostRepository posts;

    @Autowired
    private UserRepository users;

    @Autowired
    private MarkdownService markdown;

    public static final String CACHE_NAME = "cache.post";
    public static final String CACHE_NAME_ARCHIVE = CACHE_NAME+".archive";
    public static final String CACHE_NAME_PAGE = CACHE_NAME+".page";

    //public static final Class CACHE_TYPE = Post.class;
    //public static final String CACHE_TTL = "${cache.post.timetolive:60}";

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Cacheable(CACHE_NAME)
    public Post getPost(Long postId) {
        logger.info("Get post " + postId + " from database");
        return posts.findOne(postId);
    }

    public Post createPage(User user, String title, String content, PostFormat format, PostStatus status) {
        return _createPost(user, title, content, format, status, PostType.PAGE);
    }

    public Post createPost(User user, String title, String content, PostFormat format, PostStatus status) {
        return _createPost(user, title, content, format, status, PostType.POST);
    }

    public Post _createPost(User user, String title, String content, PostFormat format, PostStatus status, PostType type) {
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setPostFormat(format);
        post.setPostStatus(status);
        post.setPostType(type);

        return createPost(post);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true)
    })
    public Post createPost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(markdown.renderToHtml(post.getContent()));
        }
        return posts.save(post);
    }

    @Caching( evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true)
    })
    public Post updatePost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(markdown.renderToHtml(post.getContent()));
        }
        return posts.save(post);
    }

    @Caching( evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true)
    })
    public void deletePost(Post post) {
        posts.delete(post);
    }

    @Cacheable(value = CACHE_NAME_ARCHIVE, key = "#root.method.name")
    public List<Post> getArchivePosts(){
        logger.info("Get all archive posts from database.");
        Iterable<Post> _posts =  posts.findAll(new Sort(Sort.Direction.DESC,"id"));
        List<Post> cachedPosts = new ArrayList<>();
        for (Post post : _posts){
            Post _post = new Post();
            _post.setId(post.getId());
            _post.setTitle(post.getTitle());
            _post.setCreatedAt(post.getCreatedAt());
            cachedPosts.add(_post);
        }

        return cachedPosts;
    }

    @Cacheable(value = CACHE_NAME_PAGE, key = "T(java.lang.String).valueOf(#page).concat('-').concat(#pageSize)")
    public Page<Post> getAllPostsByPage(int page, int pageSize){
        logger.info("Get posts by page " + page + " from database");
        return posts.findAllByPostType(
               PostType.POST,
               new PageRequest(page, pageSize, Sort.Direction.DESC, "id"));
    }

    public Post createAboutPage(){
        return  createPage(
                users.findByEmail("admin@raysmond.com"),
                "About",
                "about me...",
                PostFormat.MARKDOWN,
                PostStatus.PUBLISHED);
    }
}
