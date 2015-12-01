package com.raysmond.blog.controllers;

import com.raysmond.blog.error.NotFoundException;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.Tag;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.repositories.TagRepository;
import com.raysmond.blog.services.AppSetting;
import com.raysmond.blog.services.PostService;
import com.raysmond.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Controller
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @Autowired
    private AppSetting appSetting;

    @RequestMapping(value = "", method = GET)
    public String index(Model model){
        model.addAttribute("tags", postService.countPostsByTags());
        return "tags/index";
    }

    @RequestMapping(value = "{tagName}", method = GET)
    public String showTag(@PathVariable String tagName, @RequestParam(defaultValue = "1") int page, Model model) {
        Tag tag = tagService.getTag(tagName);

        if (tag == null) {
            throw new NotFoundException("Tag " + tagName + " is not found.");
        }

        page = page < 1 ? 0 : page - 1;
        Page<Post> posts = postService.findPostsByTag(tagName, page, appSetting.getPageSize());

        model.addAttribute("tag", tag);
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);
        model.addAttribute("totalPages", posts.getTotalPages());

        return "tags/show";
    }
}
