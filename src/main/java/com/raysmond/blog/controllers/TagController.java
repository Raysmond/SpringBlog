package com.raysmond.blog.controllers;

import com.raysmond.blog.models.Tag;
import com.raysmond.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Controller
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

//    @RequestMapping("{tagName}")
//    public String showTag(@PathVariable String tagName, Model model){
//        Tag tag = tagService.getTag(tagName);
//        return "tags/show";
//    }
}
