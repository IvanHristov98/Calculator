package com.calculator;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ExpressionParserTests 
{
	@Test
	public void test_condensed_getExpression() throws Exception
	{
		assertTrue("( 3 + 4 + 5 )".equals(this.getParsedExpression("3+4+5")));
	}
	
	@Test
	public void test_condensedBrackets_getExpression() throws Exception
	{
		assertTrue("( 3 * ( 4 + 5 ) )".equals(this.getParsedExpression("3*(4+5)")));
	}
	
	@Test
	public void test_floatingPointNumbers_getExpression() throws Exception
	{
		assertTrue("( 3.5 + 123.4567 )".equals(this.getParsedExpression("3.5+123.4567")));
	}
	
	@Test
	public void test_sparseExpression_getExpression() throws Exception
	{
		assertTrue("( 1 + 1 + 2 )".equals(this.getParsedExpression("1 +1+ 2")));
	}
	
	@Test
	public void test_misplacedRedundantOperatorAtStart_getExpression() throws Exception
	{
		assertTrue("( 1 / 2 )".equals(this.getParsedExpression("+1/2")));
	}
	
	@Test
	public void test_minusAtBeginning_getExpression() throws Exception
	{
		assertTrue("( -1 + 2 )".equals(this.getParsedExpression("-1+2")));
	}

	@Test
	public void test_minusStraightAfterBracket_getExpression() throws Exception
	{
		assertTrue("( ( -1 + 1 ) )".equals(this.getParsedExpression("(-1+1)")));
	}

	private String getParsedExpression(String content) throws Exception
	{
		Expression exp = Expression.constructFromExpressionContent(content);
		ExpressionParser parser = ExpressionContainer.makeParserFromExpression(exp);
		
		return parser.getParsedExpression().getContent();
	}
}
