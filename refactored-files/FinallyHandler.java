package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Refactored handler for finally blocks.
 */
public class FinallyHandler extends BlockParentHandler {

    /**
     * Creates a new handler instance.
     *
     * @param indentCheck the indentation check
     * @param ast the abstract syntax tree node
     * @param parent the parent expression handler
     */
    public FinallyHandler(IndentationCheck indentCheck,
                          DetailAST ast,
                          AbstractExpressionHandler parent) {
        super(indentCheck, "finally", ast, parent);
    }

    @Override
    public void checkIndentation() {
        super.checkIndentation();
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return getIndent();
    }
}
