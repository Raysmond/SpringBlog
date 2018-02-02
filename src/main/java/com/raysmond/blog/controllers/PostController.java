package com.raysmond.blog.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.raysmond.blog.error.NotFoundException;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Map;

/**
 * @author Raysmond
 */
@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(value = "posts/archive")
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

    @GetMapping(value = "posts/{permalink}")
    public String show(@PathVariable String permalink, Model model) {
        return showPost(permalink, model, PostType.POST);
    }

    @GetMapping(value = "{permalink}")
    public String page(@PathVariable String permalink, Model model) {
        return showPost(permalink, model, PostType.PAGE);
    }

    private String showPost(String permalink, Model model, PostType postType) {
        Post post;

        try {
            post = postService.getPublishedPostByPermalink(permalink);
        } catch (NotFoundException ex) {
            if (permalink.matches("\\d+") && postType.equals(PostType.POST)) {
                post = postService.getPost(Long.valueOf(permalink));
            } else {
                throw new NotFoundException();
            }
        }

        if (!post.getPostType().equals(postType)) {
            throw new NotFoundException();
        }

        postService.incrementViews(post.getId());

        model.addAttribute("postType", postType.name());
        model.addAttribute("post", post);
        model.addAttribute("tags", postService.getPostTags(post));
        return "posts/post";
    }

}
