package com.raysmond.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Raysmond on 9/25/15.
 */
@Controller
public class UserController {

    @RequestMapping(value = "signin")
    public String signin() {
        return "users/signin";
    }
}