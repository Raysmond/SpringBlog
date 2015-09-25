package com.raysmond.blog.controllers;

import java.security.Principal;

import com.raysmond.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

	@Autowired
	private PostRepository posts;

	private static final int PAGE_SIZE = 5;
	
	@RequestMapping(value = "/")
	public String index(@RequestParam(defaultValue = "0") int page, Model model) {
		model.addAttribute("posts", posts.findAll(new PageRequest(page, PAGE_SIZE)));
		return "home/index";
	}

	@RequestMapping(value = "/about")
	public String about(){
		return "home/about";
	}

}
