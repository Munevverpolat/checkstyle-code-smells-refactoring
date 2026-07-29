package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Refactored handler for array index operations.
 */
public class IndexHandler extends AbstractExpressionHandler {

    /**
     * Creates a new handler instance.
     *
     * @param indentCheck the indentation check
     * @param ast the abstract syntax tree node
     * @param parent the parent expression handler
     */
    public IndexHandler(IndentationCheck indentCheck,
                        DetailAST ast,
                        AbstractExpressionHandler parent) {
        super(indentCheck, "index op", ast, parent);
    }

    @Override
    public void checkIndentation() {
        final int arrayStartColumn = getMainAst().getColumnNo();
        final int expectedIndentation = getIndentCheck().getBasicOffset();
        final int actualIndentation = getLineStart(getMainAst().getLineNo());

        if (actualIndentation < arrayStartColumn + expectedIndentation) {
            logError(getMainAst(), "Array index not properly indented.");
        }
    }

    private void logError(DetailAST ast, String message) {
        getIndentCheck().log(ast, message);
    }
}
