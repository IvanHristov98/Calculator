package com.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionParserTest
{
	@Test
	public void condensed_process() throws Exception
	{
		assertEquals("( 3 + 4 + 5 )", this.getParsedExpression("3+4+5"));
	}
	
	@Test
	public void condensedBrackets_process() throws Exception
	{
		assertEquals("( 3 * ( 4 + 5 ) )", this.getParsedExpression("3*(4+5)"));
	}
	
	@Test
	public void floatingPointNumbers_process() throws Exception
	{
		assertEquals("( 3.5 + 123.4567 )", this.getParsedExpression("3.5+123.4567"));
	}
	
	@Test
	public void sparseExpression_process() throws Exception
	{
		assertEquals("( 1 + 1 + 2 )", this.getParsedExpression("1 +1+ 2"));
	}
	
	@Test
	public void misplacedRedundantOperatorAtStart_process() throws Exception
	{
		assertEquals("( 1 / 2 )", this.getParsedExpression("+1/2"));
	}
	
	@Test
	public void minusAtBeginning_process() throws Exception
	{
		assertEquals("( -1 + 2 )", this.getParsedExpression("-1+2"));
	}

	@Test
	public void minusStraightAfterBracket_process() throws Exception
	{
		assertEquals("( ( -1 + 1 ) )", this.getParsedExpression("(-1+1)"));
	}

	private String getParsedExpression(String content) throws Exception
	{
		Expression exp = new Expression(content);
		ExpressionParser parser = new ExpressionParser(exp);
		
		return parser.process().getContent();
	}
}
