package com.raysmond.blog.admin.controllers;

import com.raysmond.blog.forms.SettingsForm;
import com.raysmond.blog.services.AppSetting;
import com.raysmond.blog.support.web.MessageHelper;
import com.raysmond.blog.utils.DTOUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Raysmond
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    private AppSetting appSetting;

    @Autowired
    public AdminController(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

    @GetMapping("")
    public String index() {
        return "admin/home/index";
    }

    @GetMapping(value = "settings")
    public String settings(Model model) {
        SettingsForm settingsForm = DTOUtil.map(appSetting, SettingsForm.class);

        model.addAttribute("settings", settingsForm);
        return "admin/home/settings";
    }

    @PostMapping(value = "settings")
    public String updateSettings(@Valid SettingsForm settingsForm, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "admin/settings";
        } else {
            appSetting.setSiteName(settingsForm.getSiteName());
            appSetting.setSiteSlogan(settingsForm.getSiteSlogan());
            appSetting.setPageSize(settingsForm.getPageSize());

            MessageHelper.addSuccessAttribute(ra, "Update settings successfully.");

            return "redirect:settings";
        }
    }
}
