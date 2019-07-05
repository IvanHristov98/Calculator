package com.calculator;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ExpressionParserTests 
{
	@Test
	public void condensed_getExpression() throws Exception
	{
		assertTrue("3 + 4 + 5".equals(this.getExpression("3+4+5")));
	}
	
	@Test
	public void condensedBrackets_getExpression() throws Exception
	{
		assertTrue("3 * ( 4 + 5 )".equals(this.getExpression("3*(4+5)")));
	}
	
	@Test
	public void floatingPointNumbers_getExpression() throws Exception
	{
		assertTrue("3.5 + 123.4567".equals(this.getExpression("3.5+123.4567")));
	}
	
	@Test
	public void sparseExpression_getExpression() throws Exception
	{
		assertTrue("1 + 1 + 2".equals(this.getExpression("1 +1+ 2")));
	}
	
	private String getExpression(String expression) throws Exception
	{
		ExpressionParser parser = ExpressionParser.constructFromExpression(expression);
		
		return parser.getExression();
	}
}
