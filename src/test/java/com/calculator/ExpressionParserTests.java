package com.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionParserTests 
{
	@Test
	public void test_condensed_getExpression() throws Exception
	{
		assertEquals("( 3 + 4 + 5 )", this.getParsedExpression("3+4+5"));
	}
	
	@Test
	public void test_condensedBrackets_getExpression() throws Exception
	{
		assertEquals("( 3 * ( 4 + 5 ) )", this.getParsedExpression("3*(4+5)"));
	}
	
	@Test
	public void test_floatingPointNumbers_getExpression() throws Exception
	{
		assertEquals("( 3.5 + 123.4567 )", this.getParsedExpression("3.5+123.4567"));
	}
	
	@Test
	public void test_sparseExpression_getExpression() throws Exception
	{
		assertEquals("( 1 + 1 + 2 )", this.getParsedExpression("1 +1+ 2"));
	}
	
	@Test
	public void test_misplacedRedundantOperatorAtStart_getExpression() throws Exception
	{
		assertEquals("( 1 / 2 )", this.getParsedExpression("+1/2"));
	}
	
	@Test
	public void test_minusAtBeginning_getExpression() throws Exception
	{
		assertEquals("( -1 + 2 )", this.getParsedExpression("-1+2"));
	}

	@Test
	public void test_minusStraightAfterBracket_getExpression() throws Exception
	{
		assertEquals("( ( -1 + 1 ) )", this.getParsedExpression("(-1+1)"));
	}

	private String getParsedExpression(String content) throws Exception
	{
		Expression exp = new Expression(content);
		ExpressionParser parser = new ExpressionParser(exp);
		
		return parser.getParsedExpression().getContent();
	}
}
