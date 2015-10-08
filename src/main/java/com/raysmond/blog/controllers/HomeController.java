package com.raysmond.blog.controllers;

import com.raysmond.blog.Constants;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.services.AppSetting;
import com.raysmond.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private AppSetting appSetting;

    @RequestMapping(value = "", method = GET)
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {
        page = page < 1 ? 0 : page - 1;
        Page<Post> posts = postService.getAllPublishedPostsByPage(page, appSetting.getPageSize());

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);

        return "home/index";
    }

    @RequestMapping(value = "about", method = GET)
    public String about(Model model) {
        Post post = postService.getPublishedPostByPermalink(Constants.ABOUT_PAGE_PERMALINK);

        if (post == null) {
            post = postService.createAboutPage();
        }

        model.addAttribute("about", post);
        return "home/about";
    }

}
