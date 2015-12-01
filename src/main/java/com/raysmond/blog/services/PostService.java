package com.raysmond.blog.services;

import com.raysmond.blog.Constants;
import com.raysmond.blog.error.NotFoundException;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.Tag;
import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.repositories.PostRepository;
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

import java.util.*;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    public static final String CACHE_NAME = "cache.post";
    public static final String CACHE_NAME_ARCHIVE = CACHE_NAME + ".archive";
    public static final String CACHE_NAME_PAGE = CACHE_NAME + ".page";
    public static final String CACHE_NAME_TAGS = CACHE_NAME + ".tag";
    public static final String CACHE_NAME_COUNTS = CACHE_NAME + ".counts_tags";

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Cacheable(CACHE_NAME)
    public Post getPost(Long postId) {
        logger.debug("Get post " + postId);

        Post post = postRepository.findOne(postId);

        if (post == null) {
            throw new NotFoundException("Post with id " + postId + " is not found.");
        }

        return post;
    }

    @Cacheable(CACHE_NAME)
    public Post getPublishedPostByPermalink(String permalink) {
        logger.debug("Get post with permalink " + permalink);

        Post post = postRepository.findByPermalinkAndPostStatus(permalink, PostStatus.PUBLISHED);

        if (post == null) {
            throw new NotFoundException("Post with permalink '" + permalink + "' is not found.");
        }

        return post;
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_COUNTS, allEntries = true)
    })
    public Post createPost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
        }

        return postRepository.save(post);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME, key = "#post.permalink", condition = "#post.permalink != null"),
            @CacheEvict(value = CACHE_NAME_TAGS, key = "#post.id.toString().concat('-tags')"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_COUNTS, allEntries = true)
    })
    public Post updatePost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
        }

        return postRepository.save(post);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME, key = "#post.permalink", condition = "#post.permalink != null"),
            @CacheEvict(value = CACHE_NAME_TAGS, key = "#post.id.toString().concat('-tags')"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_COUNTS, allEntries = true)
    })
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Cacheable(value = CACHE_NAME_ARCHIVE, key = "#root.method.name")
    public List<Post> getArchivePosts() {
        logger.debug("Get all archive posts from database.");

        Iterable<Post> posts = postRepository.findAllByPostTypeAndPostStatus(
                PostType.POST,
                PostStatus.PUBLISHED,
                new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt"));

        List<Post> cachedPosts = new ArrayList<>();
        posts.forEach(post -> cachedPosts.add(extractPostMeta(post)));

        return cachedPosts;
    }

    @Cacheable(value = CACHE_NAME_TAGS, key = "#post.id.toString().concat('-tags')")
    public List<Tag> getPostTags(Post post) {
        logger.debug("Get tags of post " + post.getId());

        List<Tag> tags = new ArrayList<>();

        // Load the post first. If not, when the post is cached before while the tags not,
        // then the LAZY loading of post tags will cause an initialization error because
        // of not hibernate connection session
        postRepository.findOne(post.getId()).getTags().forEach(tags::add);
        return tags;
    }


    private Post extractPostMeta(Post post) {
        Post archivePost = new Post();
        archivePost.setId(post.getId());
        archivePost.setTitle(post.getTitle());
        archivePost.setPermalink(post.getPermalink());
        archivePost.setCreatedAt(post.getCreatedAt());

        return archivePost;
    }

    @Cacheable(value = CACHE_NAME_PAGE, key = "T(java.lang.String).valueOf(#page).concat('-').concat(#pageSize)")
    public Page<Post> getAllPublishedPostsByPage(int page, int pageSize) {
        logger.debug("Get posts by page " + page);

        return postRepository.findAllByPostTypeAndPostStatus(
                PostType.POST,
                PostStatus.PUBLISHED,
                new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    }

    public Post createAboutPage() {
        logger.debug("Create default about page");

        Post post = new Post();
        post.setTitle(Constants.ABOUT_PAGE_PERMALINK);
        post.setContent(Constants.ABOUT_PAGE_PERMALINK.toLowerCase());
        post.setPermalink(Constants.ABOUT_PAGE_PERMALINK);
        post.setUser(userService.getSuperUser());
        post.setPostFormat(PostFormat.MARKDOWN);

        return createPost(post);
    }

    public Set<Tag> parseTagNames(String tagNames) {
        Set<Tag> tags = new HashSet<>();

        if (tagNames != null && !tagNames.isEmpty()) {
            tagNames = tagNames.toLowerCase();
            String[] names = tagNames.split("\\s*,\\s*");
            for (String name : names) {
                tags.add(tagService.findOrCreateByName(name));
            }
        }

        return tags;
    }

    public String getTagNames(Set<Tag> tags) {
        if (tags == null || tags.isEmpty())
            return "";

        StringBuilder names = new StringBuilder();
        tags.forEach(tag -> names.append(tag.getName()).append(","));
        names.deleteCharAt(names.length() - 1);

        return names.toString();
    }

    // cache or not?
    public Page<Post> findPostsByTag(String tagName, int page, int pageSize) {
        return postRepository.findByTag(tagName, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    }

    @Cacheable(value = CACHE_NAME_COUNTS, key = "#root.method.name")
    public List<Object[]> countPostsByTags() {
        logger.debug("Count posts group by tags.");

        return postRepository.countPostsByTags(PostStatus.PUBLISHED);
    }
}
