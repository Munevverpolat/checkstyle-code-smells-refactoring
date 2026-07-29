package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Refactored handler for try blocks.
 */
public class TryHandler extends BlockParentHandler {

    /**
     * Creates a new handler instance.
     *
     * @param indentCheck the indentation check
     * @param ast the abstract syntax tree node
     * @param parent the parent expression handler
     */
    public TryHandler(IndentationCheck indentCheck,
                      DetailAST ast,
                      AbstractExpressionHandler parent) {
        super(indentCheck, "try", ast, parent);
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        if (child instanceof CatchHandler
                || isFinallyChild(child)
                || (child instanceof NewHandler && isTryBlocksResourceSpecification(child))) {
            return getIndent();
        }
        return super.getSuggestedChildIndent(child);
    }

    @Override
    public void checkIndentation() {
        super.checkIndentation();

        if (getMainAst().getFirstChild().getType() == TokenTypes.RESOURCE_SPECIFICATION) {
            checkTryResParen(getTryResLparen(), "lparen");
            checkTryResParen(getTryResRparen(), "rparen");
            checkTryResources(getMainAst().getFirstChild());
        }
    }

    private DetailAST getTryResLparen() {
        return getMainAst().getFirstChild().getFirstChild();
    }

    private DetailAST getTryResRparen() {
        return getMainAst().getFirstChild().getLastChild();
    }

    private void checkTryResParen(final DetailAST parenAst, final String subType) {
        if (isOnStartOfLine(parenAst)) {
            final IndentLevel expectedIndent = new IndentLevel(
                    getIndent(),
                    0,
                    getIndentCheck().getLineWrappingIndentation());
            checkChildIndentation(parenAst, subType, expectedIndent);
        }
    }

    private void checkChildIndentation(DetailAST ast, String subType, IndentLevel expectedIndent) {
        if (getIndentCheck().isForceStrictCondition()) {
            if (!expectedIndent.isAcceptable(expandedTabsColumnNo(ast))) {
                logError(ast, subType, expandedTabsColumnNo(ast), expectedIndent);
            }
        }
        else if (expandedTabsColumnNo(ast) < expectedIndent.getFirstIndentLevel()) {
            logError(ast, subType, expandedTabsColumnNo(ast), expectedIndent);
        }
    }

    private void checkTryResources(final DetailAST resourcesSpecAst) {
        final DetailAST resourcesAst = resourcesSpecAst.findFirstToken(TokenTypes.RESOURCES);
        final int indentation = getIndent().getFirstIndentLevel()
                + getIndentCheck().getLineWrappingIndentation();
        final IndentLevel expectedResourceIndent = new IndentLevel(indentation);
        final String subType = "resource";

        DetailAST resourceAst = resourcesAst.getFirstChild();
        while (resourceAst != null) {
            if (resourceAst.getType() == TokenTypes.RESOURCE) {
                final DetailAST nextSibling = resourceAst.getNextSibling() == null
                        ? getTryResRparen()
                        : resourceAst.getNextSibling();

                if (isOnStartOfLine(resourceAst)) {
                    checkChildIndentation(resourceAst, subType, expectedResourceIndent);
                    checkWrappingIndentation(resourceAst,
                            nextSibling,
                            getIndentCheck().getLineWrappingIndentation(),
                            expectedResourceIndent.getFirstIndentLevel(),
                            true);
                }
                else {
                    checkWrappingIndentation(resourceAst, nextSibling);
                }
            }
            resourceAst = resourceAst.getNextSibling();
        }
    }

    private static boolean isTryBlocksResourceSpecification(AbstractExpressionHandler expression) {
        boolean isResourceSpecificationExpression = false;

        DetailAST ast = expression.getMainAst();
        while (ast.getType() != TokenTypes.LITERAL_TRY) {
            if (ast.getType() == TokenTypes.RESOURCE_SPECIFICATION) {
                isResourceSpecificationExpression = true;
                break;
            }
            ast = ast.getParent();
        }

        return isResourceSpecificationExpression;
    }

    private static boolean isFinallyChild(AbstractExpressionHandler child) {
        return child instanceof BlockParentHandler
                && "finally".equals(((BlockParentHandler) child).getHandlerName());
    }
}
