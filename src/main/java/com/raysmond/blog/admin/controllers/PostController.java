package com.raysmond.blog.admin.controllers;

import com.raysmond.blog.config.interceptors.RequestProcessingTimeInterceptor;
import com.raysmond.blog.forms.PostForm;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.User;
import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.repositories.UserRepository;
import com.raysmond.blog.services.MarkdownService;
import com.raysmond.blog.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Raysmond on 9/25/15.
 *
 * TODO place all view templates into sub dirs under /admin/
 */
@Controller(value = "adminPostController")
@RequestMapping(value = "/admin/posts")
public class PostController {

    @Autowired
    private PostRepository posts;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository users;

    private static final int PAGE_SIZE = 50;

    @RequestMapping(value = "")
    public String index(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Post> _posts = posts.findAll(new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id"));
        model.addAttribute("totalPages", _posts.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("posts", _posts);
        return "admin/posts_index";
    }

    @RequestMapping(value = "new")
    public String newPost(Model model){
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("postFormats", PostFormat.values());
        return "admin/posts_new";
    }

    @RequestMapping(value = "{postId:[0-9]+}/edit")
    public String editPost(@PathVariable Long postId, Model model){
        Post post = posts.findOne(postId);
        model.addAttribute("post", post);
        model.addAttribute("postForm", new PostForm(post));
        model.addAttribute("postFormats", PostFormat.values());
        return "admin/posts_edit";
    }

    @RequestMapping(value = "{postId:[0-9]+}/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deletePost(@PathVariable Long postId){
        posts.delete(postId);
        return "redirect:/admin/posts";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(Principal principal, @Valid PostForm postForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("postFormats", PostFormat.values());
            return "admin/posts_new";
        } else {
            User user = users.findByEmail(principal.getName());
            Post post = postForm.toPost();

            postService.createPost(user, post.getTitle(), post.getContent(), post.getPostFormat(), PostStatus.PUBLISHED);

            return "redirect:/admin/posts";
        }
    }

    @RequestMapping(value = "{postId:[0-9]+}", method = {RequestMethod.PUT, RequestMethod.POST})
    public String update(@PathVariable Long postId, @Valid PostForm postForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("postFormats", PostFormat.values());
            return "admin/posts_edit";
        } else {
            Post post = posts.findOne(postId);
            post.setTitle(postForm.getTitle());
            post.setContent(postForm.getContent());
            post.setPostFormat(postForm.getPostFormat());

            postService.updatePost(post);
            return "redirect:/admin/posts";
        }
    }

}
