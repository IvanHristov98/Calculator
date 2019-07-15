package com.calculator;

import com.calculator.exception.CalculatorException;
import com.calculator.exception.InvalidOperatorException;
import com.calculator.exception.NumberMisplacementException;
import com.calculator.operator.ArithmeticOperator;
import com.calculator.operator.OperatorChecker;
import com.calculator.operator.OperatorFactory;

import java.util.EmptyStackException;
import java.util.Stack;

public class ReversePolishNotationCalculator extends ExpressionContainer
{
    public ReversePolishNotationCalculator(Expression expression)
    {
        super(expression);
    }

    public Expression process () throws CalculatorException
    {
        Stack<Double> numbers = new Stack<>();

        try
        {
            numbers = this.getReversePolishNotationValue(numbers);
        }
        catch (EmptyStackException exception)
        {
            throw new NumberMisplacementException("Invalid number of numbers has been received.");
        }

        if (numbers.size() > 1)
        {
            throw new NumberMisplacementException("Invalid number of numbers has been received.");
        }

        return new Expression(numbers.peek().toString());
    }

    private Stack<Double> getReversePolishNotationValue(Stack<Double> numbers) throws CalculatorException
    {
        for (String token : this.getExpressionTokens())
        {
            if (NumberValidator.isNumber(token))
            {
                numbers = this.addNumberToNumbersStack(numbers, token);
            }
            else if (OperatorChecker.isArithmeticOperator(OperatorFactory.makeOperator(token)))
            {
                numbers = this.operateWithNumbersFromStackTop(numbers, token);
            }
            else
            {
                throw new InvalidOperatorException("Unexpected operator has been received.");
            }
        }

        return numbers;
    }

    private Stack<Double> addNumberToNumbersStack(Stack<Double> numbers, String token)
    {
        numbers.add(NumberValidator.toNumber(token));
        return numbers;
    }

    private Stack<Double> operateWithNumbersFromStackTop(Stack<Double> numbers, String token) throws CalculatorException
    {
        Double rightNumber = numbers.pop();
        Double leftNumber = numbers.pop();
        ArithmeticOperator operator = (ArithmeticOperator) OperatorFactory.makeOperator(token);

        Double operationResult = operator.operate(leftNumber, rightNumber);
        numbers.push(operationResult);

        return numbers;
    }

    private String[] getExpressionTokens()
    {
        return this.expression.getContent().split(" ");
    }
}
