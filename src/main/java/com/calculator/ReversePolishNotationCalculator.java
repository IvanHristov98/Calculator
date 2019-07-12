package com.calculator;

import com.calculator.exception.InvalidOperatorException;
import com.calculator.operator.ArithmeticOperator;
import com.calculator.operator.Operator;
import com.calculator.operator.OperatorChecker;
import com.calculator.operator.OperatorFactory;

import java.util.Stack;

public class ReversePolishNotationCalculator extends ExpressionContainer
{
    public ReversePolishNotationCalculator(Expression expression)
    {
        super(expression);
    }

    // TODO empty stack exception
    public Double getExpressionResult () throws InvalidOperatorException
    {
        Stack<Double> numbers = new Stack<>();

        for (String token : this.getExpressionTokens())
        {
            if (this.isNumber(token))
            {
                numbers.add(this.toNumber(token));
            }
            else if (OperatorChecker.isArithmeticOperator(OperatorFactory.makeOperator(token)))
            {
                Double rightNumber = numbers.pop();
                Double leftNumber = numbers.pop();
                ArithmeticOperator operator = (ArithmeticOperator) OperatorFactory.makeOperator(token);

                Double operationResult = operator.operate(leftNumber, rightNumber);
                numbers.push(operationResult);
            }
            else
            {
                throw new InvalidOperatorException("Unexpected operator has been received.");
            }
        }

        return numbers.peek();
    }

    private String[] getExpressionTokens()
    {
        return this.expression.getContent().split(" ");
    }
}
