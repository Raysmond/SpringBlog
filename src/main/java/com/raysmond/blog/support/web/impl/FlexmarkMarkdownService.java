package com.raysmond.blog.support.web.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.raysmond.blog.support.web.MarkdownService;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用flexmark-java解析markdown
 * 参考：https://github.com/vsch/flexmark-java
 *
 * @author Raysmond
 */
@Service("flexmark")
@Slf4j
public class FlexmarkMarkdownService implements MarkdownService {
    @Override
    public String renderToHtml(String content) {
        MutableDataSet options = new MutableDataSet();

        options.set(Parser.EXTENSIONS,
                Arrays.asList(TablesExtension.create(),
                        AutolinkExtension.create(),
                        StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(content);
        return renderer.render(document);
    }
}
