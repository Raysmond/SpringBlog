package com.raysmond.blog.controllers;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Raysmond on 9/26/15.
 */
@Controller
@RequestMapping("posts")
public class PostController {
    @Autowired
    private PostService postService;

    private static final int MAX_SIZE = 100;

    @RequestMapping(value = "archive")
    public String archive(Model model){
        List<Post> _posts = postService.getArchivePosts();
        model.addAttribute("posts", _posts);
        return "posts/archive";
    }

    @RequestMapping(value = "{postId:[0-9]+}")
    public String show(@PathVariable Long postId, Model model){
        Post post = postService.getPost(postId);
        model.addAttribute("post", post);
        return "posts/show";
    }
}
