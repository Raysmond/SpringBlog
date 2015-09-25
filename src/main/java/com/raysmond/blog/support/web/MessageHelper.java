package com.raysmond.blog.support.web;

import static com.raysmond.blog.support.web.Message.MESSAGE_ATTRIBUTE;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public final class MessageHelper {

    private MessageHelper() {

    }

    public static void addSuccessAttribute(RedirectAttributes ra, String message, Object... args) {
        addAttribute(ra, message, Message.Type.SUCCESS, args);
    }

    public static void addErrorAttribute(RedirectAttributes ra, String message, Object... args) {
        addAttribute(ra, message, Message.Type.DANGER, args);
    }

    public static void addInfoAttribute(RedirectAttributes ra, String message, Object... args) {
        addAttribute(ra, message, Message.Type.INFO, args);
    }

    public static void addWarningAttribute(RedirectAttributes ra, String message, Object... args) {
        addAttribute(ra, message, Message.Type.WARNING, args);
    }

    private static void addAttribute(RedirectAttributes ra, String message, Message.Type type, Object... args) {
        ra.addFlashAttribute(MESSAGE_ATTRIBUTE, new Message(message, type, args));
    }

    public static void addSuccessAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, Message.Type.SUCCESS, args);
    }

    public static void addErrorAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, Message.Type.DANGER, args);
    }

    public static void addInfoAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, Message.Type.INFO, args);
    }

    public static void addWarningAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, Message.Type.WARNING, args);
    }

    private static void addAttribute(Model model, String message, Message.Type type, Object... args) {
        model.addAttribute(MESSAGE_ATTRIBUTE, new Message(message, type, args));
    }
}
