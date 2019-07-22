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

    public abstract Expression process() throws CalculatorException;
}
