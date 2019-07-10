package com.calculator;

import com.calculator.exception.*;

import java.util.regex.Pattern;

public class ExpressionValidator extends ExpressionContainer
{
    private ExpressionValidator()
    {}

    protected ExpressionValidator(Expression expression)
    {
        super(expression);
    }

    public void validateExpression() throws CalculatorException
    {
        String expressionContent = this.expression.getContent();

        this.validateIfAnyConsecutiveNumbers(expressionContent);
        this.validateNumbersFormat(expressionContent);
    }

    private void validateIfAnyConsecutiveNumbers(String expression) throws NumberMisplacementException
    {
        if (this.hasConsecutiveNumbers(expression))
        {
            throw new NumberMisplacementException("Invalid expression. Numbers should be separated by a valid operator.");
        }
    }

    private boolean hasConsecutiveNumbers(String expression)
    {
        return expression.matches(".*[0-9.][ ]+[0-9.].*");
    }

    private void validateNumbersFormat(String expression) throws NumberMisplacementException
    {
        if (this.isNotAValidNumber(expression))
        {
            throw new NumberMisplacementException("The given expression contains consecutive numbers.");
        }
    }

    private boolean isNotAValidNumber(String expression)
    {
        // checks if there are two floating point numbers one after another
        // following the mantissa_1.exponent_1mantissa_2.exponent_2
        return Pattern.matches(".*[0-9]+\\.[0-9]*\\..*", expression);
    }


}
