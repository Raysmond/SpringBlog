package com.raysmond.blog.admin.controllers;

import com.raysmond.blog.forms.SettingsForm;
import com.raysmond.blog.services.BlogSetting;
import com.raysmond.blog.support.web.MessageHelper;
import com.raysmond.blog.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Raysmond<jiankunlei@gmail.com>
 */
@Controller
@RequestMapping(value = "/admin")
//@Secured("ROLE_ADMIN")
public class AdminController {

    private BlogSetting blogSetting;

    @Autowired
    public AdminController( BlogSetting blogSetting){
        this.blogSetting = blogSetting;
    }

    @RequestMapping("")
    public String index(){
        return "admin/index";
    }

    @RequestMapping(value = "settings")
    public String settings(Model model){
        SettingsForm settingsForm = DTOUtil.map(blogSetting, SettingsForm.class);

        model.addAttribute("settings", settingsForm);
        return "admin/settings";
    }

    @RequestMapping(value = "settings", method = RequestMethod.POST)
    public String updateSettings(@Valid SettingsForm settingsForm, Errors errors, Model model, RedirectAttributes ra){
        if (errors.hasErrors()){
            return "admin/settings";
        } else {
            blogSetting.setSiteName(settingsForm.getSiteName());
            blogSetting.setSiteSlogan(settingsForm.getSiteSlogan());
            blogSetting.setPageSize(settingsForm.getPageSize());

            MessageHelper.addSuccessAttribute(ra, "Update settings successfully.");

            return "redirect:/admin/settings";
        }
    }
}
