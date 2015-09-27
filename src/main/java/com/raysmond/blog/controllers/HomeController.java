package com.raysmond.blog.controllers;

import java.security.Principal;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.repositories.UserRepository;
import com.raysmond.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository posts;

    private static final int PAGE_SIZE = 5;

    private static Long ABOUT_PAGE_ID = null;

    @RequestMapping(value = "/")
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {
        if (page <= 0) {
            page = 1;
        }

        Page<Post> _posts = postService.getAllPostsByPage(page - 1, PAGE_SIZE);

        model.addAttribute("totalPages", _posts.getTotalPages());
        model.addAttribute("posts", _posts);
        model.addAttribute("page", page);
        return "home/index";
    }

    @RequestMapping(value = "/about")
    public String about(Model model) {
        if (ABOUT_PAGE_ID == null || postService.getPost(ABOUT_PAGE_ID) == null) {
            Post post = posts.findByTitleAndPostType("About", PostType.PAGE);
            if (post == null) {
                post = postService.createAboutPage();
            }

            ABOUT_PAGE_ID = post.getId();
        }

        model.addAttribute("about", postService.getPost(ABOUT_PAGE_ID));
        return "home/about";
    }

}
