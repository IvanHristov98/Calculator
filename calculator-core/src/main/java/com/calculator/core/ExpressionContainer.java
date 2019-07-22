package com.calculator.core;

import com.calculator.core.exception.*;

public abstract class ExpressionContainer
{
    protected Expression expression;

    public ExpressionContainer(Expression expression)
    {
        this.setExpression(expression);
    }

    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    public Expression getExpression()
    {
        return this.expression.clone();
    }

    protected String stripSpaces(String string)
    {
        return string.replaceAll(" ", "");
    }
    
    protected String wrapStringWithBrackets(String string)
    {
    	return "(" + string + ")";
    }

    protected String operateOnExpression(String expressionContent, ExceptionThrowingFunction<String, String, CalculatorException> action) throws CalculatorException
    {
        final char OPENING_BRACKET = '(';
        final char CLOSING_BRACKET = ')';

        expressionContent = action.apply(expressionContent);

        for (int i = 0; i < expressionContent.length(); ++i)
        {
            if (expressionContent.charAt(i) == CLOSING_BRACKET)
            {
                throw new BracketsException("Invalid bracket ordering - no opening bracket found.");
            }

            if (expressionContent.charAt(i) == OPENING_BRACKET)
            {
                int openingBracketPosition = i;
                int closingBracketPosition = i + 1;

                int openingBracketsCount = 0;

                while (closingBracketPosition < expressionContent.length() && !(expressionContent.charAt(closingBracketPosition) == CLOSING_BRACKET && openingBracketsCount == 0))
                {
                    if (expressionContent.charAt(closingBracketPosition) == OPENING_BRACKET)
                    {
                        ++openingBracketsCount;
                    }

                    if (expressionContent.charAt(closingBracketPosition) == CLOSING_BRACKET)
                    {
                        --openingBracketsCount;
                    }

                    ++closingBracketPosition;
                }

                if (closingBracketPosition >= expressionContent.length())
                {
                    throw new BracketsException("Invalid bracket ordering - no closing bracket found.");
                }

                String subExpressionContent = this.operateOnExpression(expressionContent.substring(openingBracketPosition + 1, closingBracketPosition), action);
                expressionContent = expressionContent.substring(0, openingBracketPosition + 1) + subExpressionContent + expressionContent.substring(closingBracketPosition);

                i = openingBracketPosition + subExpressionContent.length() + 1;// test
            }
        }

        return expressionContent;
    }

    public abstract Expression process() throws CalculatorException;
}
