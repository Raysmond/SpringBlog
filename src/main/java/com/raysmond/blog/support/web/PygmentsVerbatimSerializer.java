package com.raysmond.blog.support.web;

import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Raysmond on 9/26/15.
 */
public class PygmentsVerbatimSerializer implements VerbatimSerializer {
    public static final PygmentsVerbatimSerializer INSTANCE = new PygmentsVerbatimSerializer();

    private SyntaxHighlightService syntaxHighlightService = new PygmentsService();

    @Override
    public void serialize(final VerbatimNode node, final Printer printer) {
        printer.print(syntaxHighlightService.highlight(node.getText()));

        // String type = node.getType();
        // String text = node.getText();
//
//        printer.println().print("<pre><code");
//        String className = "prettyprint";
//        if (!StringUtils.isEmpty(node.getType())) {
//            className = className.concat(" " + node.getType());
//        }
//        printAttribute(printer, "class", className);
//        printer.print(">");
//        String text = node.getText();
//        // print HTML breaks for all initial newlines
//        while (text.charAt(0) == '\n') {
//            printer.print("<br/>");
//            text = text.substring(1);
//        }
//        printer.printEncoded(text);
//        printer.print("</code></pre>");

    }

}