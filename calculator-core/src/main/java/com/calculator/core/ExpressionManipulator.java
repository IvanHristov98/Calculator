package com.calculator.core;

public class ExpressionManipulator 
{
	public ExpressionManipulator()
	{}
	
	public Expression getExpressionWrappedWithBrackets(Expression expression)
	{
		String wrappedContent = "(" + expression.getContent() + ")";
		expression.setContent(wrappedContent);
		
		return expression;
	}
	
	public Expression getExpressionWithStrippedWhiteSpaces(Expression expression)
	{
		String strippedContent = expression.getContent().replaceAll(" ", "");
		expression.setContent(strippedContent);
		
		return expression;
	}
}
