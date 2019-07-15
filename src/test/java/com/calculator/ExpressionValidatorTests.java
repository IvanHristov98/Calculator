package com.calculator;

import org.junit.Test;
import com.calculator.exception.*;

public class ExpressionValidatorTests
{
    @Test(expected = NumberMisplacementException.class)
    public void spacedConsecutiveNumbers_process() throws Exception
    {
        this.validateExpression("1 2");
    }

    @Test(expected = NumberMisplacementException.class)
    public void consecutive_process() throws Exception
    {
        this.validateExpression("2+4.55.6");
    }

    @Test(expected = EmptyExpressionException.class)
    public void emptyExpression_process() throws Exception
    {
        this.validateExpression("");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void manyLegitimateOperatorsAtBeginning_process() throws Exception
    {
        this.validateExpression("++1+2");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void consecutiveOperators_process() throws Exception
    {
        this.validateExpression("2+*3");
    }

    @Test(expected = InvalidOperatorException.class)
    public void invalidTokenException_process() throws Exception
    {
        this.validateExpression("1A2");
    }

    @Test(expected = BracketsException.class)
    public void numberGluedToTheLeftOfABracketedExpression_process() throws Exception
    {
        this.validateExpression("2(3+4)");
    }

    @Test(expected = BracketsException.class)
    public void numberGluedToTheRightOfABracketedExpression_process() throws Exception
    {
        this.validateExpression("(3+4)5");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void misplacedOperatorAtStart_process() throws Exception
    {
        this.validateExpression("*1+2");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void manyMisplacedOperatorsAtBeginning_process() throws Exception
    {
        this.validateExpression("**/1/2");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void misplacedOperatorAtEnd_process() throws Exception
    {
        this.validateExpression("1/2*");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void manyMisplacedOperatorsAtEnd_process() throws Exception
    {
        this.validateExpression("1/2*/*/*");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void singleOperator_process() throws Exception
    {
        this.validateExpression("+");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void misplacedOperatorsStraightAfterOpeningBracket_process() throws Exception
    {
        this.validateExpression("(*3)");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void misplacedOperatorsAtEndsWithinBrackets_process() throws Exception
    {
        this.validateExpression("(4+5*)");
    }

    @Test(expected = BracketsException.class)
    public void noClosingBracket_process() throws Exception
    {
        this.validateExpression("(1+2)+(3+4");
    }

    @Test(expected = BracketsException.class)
    public void noOpeningBracket_process() throws Exception
    {
        this.validateExpression("3+5)");
    }

    @Test(expected = BracketsException.class)
    public void notEnoughClosingBrackets_process() throws Exception
    {
        this.validateExpression("(()");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void emptyBrackets_process() throws Exception
    {
        this.validateExpression("1+()");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void singleOperatorWithinBrackets_process() throws Exception
    {
        this.validateExpression("(+)");
    }

    private void validateExpression(String expressionContent) throws Exception
    {
        Expression expression = new Expression(expressionContent);
        ExpressionValidator validator = new ExpressionValidator(expression);

        validator.process();
    }
}
