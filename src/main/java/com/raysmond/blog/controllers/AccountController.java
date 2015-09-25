package com.raysmond.blog.controllers;

import java.security.Principal;

import com.raysmond.blog.models.User;
import com.raysmond.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Secured("ROLE_USER")
class AccountController {

    private UserRepository userRepository;

    @Autowired
    public AccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "account/current", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public User accounts(Principal principal) {
        Assert.notNull(principal);
        return userRepository.findByEmail(principal.getName());
    }
}
