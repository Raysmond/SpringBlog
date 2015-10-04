package com.raysmond.blog.support.web;

import org.pegdown.*;
import org.pegdown.ast.RootNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Service
@Qualifier("pegdown")
public class PegDownMarkdownService implements MarkdownService {

    private final PegDownProcessor pegdown;

    public PegDownMarkdownService() {
        pegdown = new PegDownProcessor(Extensions.ALL);
    }

    @Override
    public String renderToHtml(String markdownSource) {
        // synchronizing on pegdown instance since neither the processor nor the underlying parser is thread-safe.
        synchronized (pegdown) {
            RootNode astRoot = pegdown.parseMarkdown(markdownSource.toCharArray());
            ToHtmlSerializer serializer = new ToHtmlSerializer(new LinkRenderer(),
                    Collections.singletonMap(VerbatimSerializer.DEFAULT, PygmentsVerbatimSerializer.INSTANCE));
            return serializer.toHtml(astRoot);
        }
    }
}
