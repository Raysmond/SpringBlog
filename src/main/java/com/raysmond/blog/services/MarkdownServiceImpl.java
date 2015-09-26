package com.raysmond.blog.services;

import com.raysmond.blog.support.web.PygmentsVerbatimSerializer;
import org.pegdown.*;
import org.pegdown.ast.RootNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Created by Raysmond on 9/25/15.
 */
@Service
@Qualifier("pegdown")
public class MarkdownServiceImpl implements MarkdownService {

    private final PegDownProcessor pegdown;

    public MarkdownServiceImpl() {
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
