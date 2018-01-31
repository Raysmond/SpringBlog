package com.raysmond.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * User pages
 *
 * @author Raysmond
 */
@Controller
public class UserController {

    @RequestMapping("login")
    public String signin(Principal principal, RedirectAttributes ra) {
        return principal == null ? "users/login" : "redirect:/";
    }
}