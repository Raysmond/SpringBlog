package com.raysmond.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Raysmond<jiankunlei@gmail.com>
 */
@Controller
public class UserController {

    @RequestMapping(value = "signin")
    public String signin() {
        return "users/signin";
    }
}