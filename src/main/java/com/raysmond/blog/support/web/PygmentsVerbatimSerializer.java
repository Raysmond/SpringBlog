package com.raysmond.blog.support.web;

import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;

/**
 * @author Raysmond
 */
public class PygmentsVerbatimSerializer implements VerbatimSerializer {
    public static final PygmentsVerbatimSerializer INSTANCE = new PygmentsVerbatimSerializer();

    private SyntaxHighlightService syntaxHighlightService = new PygmentsService();

    @Override
    public void serialize(final VerbatimNode node, final Printer printer) {
        printer.print(syntaxHighlightService.highlight(node.getText()));
    }

}