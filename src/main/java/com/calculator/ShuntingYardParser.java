package com.calculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.calculator.exception.*;
import com.calculator.operator.*;

class ShuntingYardParser extends ExpressionContainer
{
    private ShuntingYardParser()
    {}

    protected ShuntingYardParser(Expression expression)
    {
        super(expression);
    }

    public static ShuntingYardParser constructFromExpression(Expression expression)
    {
        return new ShuntingYardParser(expression);
    }

    public Expression getConvertedExpression() throws CalculatorException
    {
        return Expression.constructFromExpressionContent(
                this.toReversePolishNotation(this.expression.getContent().split(" "))
        );
    }

    private String toReversePolishNotation(String[] tokens) throws OperatorException
    {
        Queue<String> output = new LinkedList<>();
        Stack<String> operators = new Stack<>();

        this.fillOutputQueue(output, operators, tokens);

        return this.popQueueToString(output);
    }

    private void fillOutputQueue(Queue<String> output, Stack<String> operators, String[] tokens) throws OperatorException
    {
        this.readExpression(output, operators, tokens);

        this.moveTokensFromOperatorsStackToOutputQueue(output, operators);
    }

    // TODO Think of a better name.
    private void readExpression(Queue<String> output, Stack<String> operators, String[] tokens) throws OperatorException
    {
        for (String token : tokens)
        {
            if (this.isNumber(token))
            {
                output.add(token);
            }
            else if (this.isArithmeticOperator(token))
            {
                while (!operators.empty() && this.shouldPopFromTheOperatorStack(operators, token))
                {
                    moveTokenFromOperatorStackToOutputQueue(operators, output);
                }

                operators.add(token);
            }
            else if (this.isLeftBracket(this.makeOperator(token)))
            {
                operators.addElement(token);
            }
            else if (this.isRightBracket(this.makeOperator(token)))
            {
                this.moveTokensFromOperatorStackToOutputQueueUntilLeftBracket(operators, output);
            }
        }
    }

    private boolean isNumber(String token)
    {
        try
        {
            Double.parseDouble(token);
        }
        catch (NumberFormatException exception)
        {
            return false;
        }

        return true;
    }

    private boolean isArithmeticOperator(String token)
    {
        // The arithmetic operators are -, +, /, *, ^
        return token.matches("[-+/*^]");
    }

    private boolean shouldPopFromTheOperatorStack(Stack<String> operators, String token) throws InvalidOperatorException
    {
        Operator nextOperator = this.makeOperator(operators.peek());
        Operator current = this.makeOperator(token);

        if (this.isLeftBracket(nextOperator))
        {
            return false;
        }

        boolean result = nextOperator.compareTo(current) > 0;
        result |= (nextOperator.compareTo(current) == 0 &&  ((ArithmeticOperator)nextOperator).isLeftAssociative());

        return result;
    }

    private boolean isBracket(Operator operator)
    {
        return this.isLeftBracket(operator) || this.isRightBracket(operator);
    }

    private boolean isLeftBracket(Operator operator)
    {
        return operator instanceof LeftBracketOperator;
    }

    private boolean isRightBracket(Operator operator)
    {
        return operator instanceof RightBracketOperator;
    }

    private void moveTokenFromOperatorStackToOutputQueue(Stack<String> operators, Queue<String> output)
    {
        String topOperator = operators.pop();
        output.add(topOperator);
    }

    private void moveTokensFromOperatorStackToOutputQueueUntilLeftBracket(Stack<String> operators, Queue<String> output) throws OperatorException
    {
        while (!operators.empty() && !this.isLeftBracket(this.makeOperator(operators.peek())))
        {
            moveTokenFromOperatorStackToOutputQueue(operators, output);
        }

        // Removing the opening bracket
        if (!operators.empty())
        {
            operators.pop();
        }
        else
        {
            throw new BracketsException("Invalid expression - no closing bracket encountered.");
        }
    }

    private void moveTokensFromOperatorsStackToOutputQueue(Queue<String> output, Stack<String> operators) throws OperatorException
    {
        while (!operators.empty())
        {
            Operator topOperator = this.makeOperator(operators.peek());

            if (this.isBracket(topOperator))
            {
                throw new BracketsException("Invalid bracket ordering encountered.");
            }

            this.moveTokenFromOperatorStackToOutputQueue(operators, output);
        }
    }

    private Operator makeOperator(String operator) throws InvalidOperatorException
    {
        return OperatorFactory.makeOperator(operator);
    }

    private String popQueueToString(Queue<String> output)
    {
        StringBuilder result = new StringBuilder();

        while(!output.isEmpty())
        {
            result.append(" ");
            result.append(output.remove());
        }

        return result.toString().trim();
    }
}