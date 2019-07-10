package com.calculator;

import org.junit.Test;
import com.calculator.exception.*;

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
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_misplacedOperatorAtStart_getExpression() throws Exception
	{
		this.getParsedExpression("*1+2");
	}
	
	@Test
	public void test_misplacedRedundantOperatorAtStart_getExpression() throws Exception
	{
		assertTrue("( 1 / 2 )".equals(this.getParsedExpression("+1/2")));
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_manyMisplacedOperatorsAtBeginning_getExpression() throws Exception
	{
		this.getParsedExpression("**/1/2");
	}
	
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_misplacedOperatorsStraightAfterOpeningBracket_getExpression() throws Exception
	{
		this.getParsedExpression("(*3)");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_misplacedOperatorsAtEndsWithinBrackets_getExpression() throws Exception
	{
		this.getParsedExpression("(4+5*)");
	}
	
	@Test(expected = BracketsException.class)
	public void test_noOpeningBracket_getExpression() throws Exception
	{
		this.getParsedExpression("3+5)");
	}
	
	@Test(expected = BracketsException.class)
	public void test_noClosingBracket_getExpression() throws Exception
	{
		this.getParsedExpression("(1+2)+(3+4");
	}
	
	@Test
	public void test_minusAtBeginning_getExpression() throws Exception
	{
		assertTrue("( -1 + 2 )".equals(this.getParsedExpression("-1+2")));
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_manyLegitimateOperatorsAtBeginning_getExpression() throws Exception
	{
		this.getParsedExpression("++1+2");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_consecutiveOperators_getExpression() throws Exception
	{
		this.getParsedExpression("2+*3");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_consecutive_getExpression() throws Exception
	{
		this.getParsedExpression("2+4.55.6");
	}

	@Test
	public void test_minusStraightAfterBracket_getExpression() throws Exception
	{
		assertTrue("( ( -1 + 1 ) )".equals(this.getParsedExpression("(-1+1)")));
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_misplacedOperatorAtEnd_getExpression() throws Exception
	{
		this.getParsedExpression("1/2*");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_manyMisplacedOperatorsAtEnd_getExpression() throws Exception
	{
		this.getParsedExpression("1/2*/*/*");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_invalidTokenException_getExpression() throws Exception
	{
		this.getParsedExpression("1A2");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_emptyBrackets_getExpression() throws Exception
	{
		this.getParsedExpression("1+()");
	}
	
	@Test(expected = BracketsException.class)
	public void test_numberGluedToTheLeftOfABracketedExpression_getExpression() throws Exception
	{
		this.getParsedExpression("2(3+4)");
	}
	
	@Test(expected = BracketsException.class)
	public void test_numberGluedToTheRightOfABracketedExpression_getExpression() throws Exception
	{
		this.getParsedExpression("(3+4)5");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_singleOperator_getExpression() throws Exception
	{
		this.getParsedExpression("+");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void test_singleOperatorWithinBrackets_getExpression() throws Exception
	{
		this.getParsedExpression("(+)");
	}
	
	@Test(expected = EmptyExpressionException.class)
	public void test_emptyExpression_getExpression() throws Exception
	{
		this.getParsedExpression("");
	}
	
	private String getParsedExpression(String content) throws Exception
	{
		Expression exp = Expression.constructFromExpressionContent(content);
		ExpressionParser parser = ExpressionContainer.makeParserFromExpression(exp);
		
		return parser.getParsedExpression().getContent();
	}
}
