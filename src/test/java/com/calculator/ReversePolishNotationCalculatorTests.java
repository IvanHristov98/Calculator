package com.calculator;

import com.calculator.exception.InvalidOperatorException;
import org.junit.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ReversePolishNotationCalculatorTests
{
    ReversePolishNotationCalculator calculator;
    Expression expression;

    @Test
    public void twoOperators_getExpressionResult() throws InvalidOperatorException
    {
        this.expression = new Expression("1 2 +");
        this.calculator = new ReversePolishNotationCalculator(this.expression);

        assertThat(this.calculator.getExpressionResult(), equalTo(3.0));
    }
}
