package com.raysmond.blog.services;

import com.raysmond.blog.models.Tag;
import com.raysmond.blog.repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Raysmond<i@raysmond.com>.
 */
@Service
public class TagService {
    private TagRepository tagRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public static final String CACHE_NAME = "cache.tag";
    public static final String CACHE_NAME_TAGS = "cache.tag.all";

    public static final String CACHE_TYPE = "'_Tag_'";
    public static final String CACHE_KEY = CACHE_TYPE + " + #tagName";
    public static final String CACHE_TAG_KEY = CACHE_TYPE + " + #tag.name";

    @Autowired
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public Tag findOrCreateByName(String name){
        Tag tag = tagRepository.findByName(name);
        if (tag == null){
            tag = tagRepository.save(new Tag(name));
        }
        return tag;
    }

    @Cacheable(value = CACHE_NAME, key = CACHE_KEY)
    public Tag getTag(String tagName) {
        return tagRepository.findByName(tagName);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = CACHE_TAG_KEY),
            @CacheEvict(value = CACHE_NAME_TAGS, allEntries = true)
    })
    public void deleteTag(Tag tag){
        tagRepository.delete(tag);
    }

    @Cacheable(value = CACHE_NAME_TAGS, key = "#root.method.name")
    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

}
