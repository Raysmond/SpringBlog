package com.raysmond.blog.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.raysmond.blog.error.NotFoundException;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Map;

/**
 * @author Raysmond
 */
@Controller
@RequestMapping("posts")
public class PostController {
    @Autowired
    private PostService postService;

    @RequestMapping(value = "archive", method = GET)
    public String archive(Model model) {
        Map<Integer, List<Post>> posts = Maps.newHashMap();
        postService.getArchivePosts().forEach(post -> {
            if (!posts.containsKey(post.getCreatedAt().getYear())) {
                posts.put(post.getCreatedAt().getYear(), Lists.newArrayList());
            }
            posts.get(post.getCreatedAt().getYear()).add(post);
        });
        model.addAttribute("posts", posts);
        return "posts/archive";
    }

    @RequestMapping(value = "{permalink}", method = GET)
    public String show(@PathVariable String permalink, Model model) {
        Post post = null;

        try {
            post = postService.getPublishedPostByPermalink(permalink);
        } catch (NotFoundException ex) {
            if (permalink.matches("\\d+")) {
                post = postService.getPost(Long.valueOf(permalink));
            }
        }

        if (post == null) {
            throw new NotFoundException("Post with permalink " + permalink + " is not found");
        }

        model.addAttribute("post", post);
        model.addAttribute("tags", postService.getPostTags(post));

        postService.incrementViews(post.getId());

        return "posts/post";
    }

}
