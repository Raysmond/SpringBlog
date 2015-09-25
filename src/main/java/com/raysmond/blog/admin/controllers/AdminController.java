package com.raysmond.blog.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Raysmond on 9/25/15.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "")
    public String index(){
        return "admin/index";
    }

    @RequestMapping(value = "settings")
    public String settings(){
        return "admin/settings";
    }
}
