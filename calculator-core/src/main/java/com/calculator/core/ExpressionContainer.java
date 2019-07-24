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

    public abstract Expression process() throws CalculatorException;
}
