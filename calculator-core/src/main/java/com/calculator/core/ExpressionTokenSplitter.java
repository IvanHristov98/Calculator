package com.calculator.core;

import com.calculator.core.exception.UnformattedExpressionException;

public class ExpressionTokenSplitter 
{
	public ExpressionTokenSplitter()
	{}
	
    public String[] getExpressionTokens(Expression expression)
    {
    	this.validateIfExpressionIsFormatted(expression.getContent());
    	
        return expression.getContent().split(" ");
    }
    
    private void validateIfExpressionIsFormatted(String expressionContent)
    {
    	// A formatted expression has it's tokens separated by white spaces
    	if (!expressionContent.matches("^(-*[0-9]+(\\.[0-9]+)*|[-+\\/*^()])( (-*[0-9]+(\\.[0-9]+)*|[-+\\/*^()]))*$"))
    	{
    		throw new UnformattedExpressionException("Unproperly formatted exception passed in.");
    	}
    }
}
