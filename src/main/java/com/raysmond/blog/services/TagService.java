package com.raysmond.blog.services;

import com.raysmond.blog.models.Tag;
import com.raysmond.blog.repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Service
public class TagService {
    private TagRepository tagRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

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

}
