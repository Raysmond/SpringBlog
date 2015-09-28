package com.raysmond.blog.controllers;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.AppSetting;
import com.raysmond.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private PostRepository postRepository;

    private static Long ABOUT_PAGE_ID = null;

    @RequestMapping(value = "/")
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {
        if (page <= 0) {
            page = 1;
        }

        Page<Post> posts = postService.getAllPostsByPage(page - 1, appSetting.getPageSize());

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        return "home/index";
    }

    @RequestMapping(value = "/about")
    public String about(Model model) {
        if (ABOUT_PAGE_ID == null || postService.getPost(ABOUT_PAGE_ID) == null) {
            Post post = postRepository.findByTitleAndPostType("About", PostType.PAGE);
            if (post == null) {
                post = postService.createAboutPage();
            }

            ABOUT_PAGE_ID = post.getId();
        }

        model.addAttribute("about", postService.getPost(ABOUT_PAGE_ID));
        return "home/about";
    }

}
