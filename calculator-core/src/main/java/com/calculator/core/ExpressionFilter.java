package com.calculator.core;

import com.calculator.core.exception.*;

public abstract class ExpressionFilter
{
    protected Expression expression;

    public ExpressionFilter(Expression expression)
    {
        this.setExpression(expression);
    }

    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    public abstract Expression process() throws CalculatorException;
}
