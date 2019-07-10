package com.calculator;

import com.calculator.exception.*;

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

        this.validateIfAnyConsecutiveNumbers(this.expression.getContent()); //
       // this.validateNumbersFormat(expression); //
    }

    private void validateIfAnyConsecutiveNumbers(String expression) throws OperatorMisplacementException
    {
        if (this.hasConsecutiveNumbers(expression))
        {
            throw new OperatorMisplacementException("Invalid expression. Numbers should be separated by a valid operator.");
        }
    }

    private boolean hasConsecutiveNumbers(String expression)
    {
        return expression.matches(".*[0-9.][ ]+[0-9.].*");
    }


}
