package com.raysmond.blog.utils;

import com.raysmond.blog.support.web.MarkdownService;
import com.raysmond.blog.support.web.impl.PegDownMarkdownService;

/**
 * A Markdown processing util class
 *
 * @author Raysmond
 */
public class Markdown {

    private static final MarkdownService MARKDOWN_SERVICE = new PegDownMarkdownService();

    public static String markdownToHtml(String content) {
        return MARKDOWN_SERVICE.renderToHtml(content);
    }
}
