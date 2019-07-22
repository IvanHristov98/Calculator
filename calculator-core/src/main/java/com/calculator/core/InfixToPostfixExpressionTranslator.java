package com.calculator.core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.calculator.core.exception.*;
import com.calculator.core.operator.*;

class InfixToPostfixExpressionTranslator extends ExpressionContainer
{
    public InfixToPostfixExpressionTranslator(Expression expression)
    {
        super(expression);
    }

    public static InfixToPostfixExpressionTranslator constructFromExpression(Expression expression)
    {
        return new InfixToPostfixExpressionTranslator(expression);
    }

    public Expression process() throws CalculatorException
    {
        return new Expression(
                this.toPostfixExpression(this.expression.getContent().split(" "))
        );
    }

    private String toPostfixExpression(String[] tokens) throws OperatorException
    {
        Queue<String> output = new LinkedList<>();
        Stack<String> operators = new Stack<>();

        this.readTokens(output, operators, tokens);
        this.moveTokensFromOperatorsStackToOutputQueue(output, operators);

        return this.popQueueToString(output);
    }

    private void readTokens(Queue<String> output, Stack<String> operators, String[] tokens) throws OperatorException
    {
        for (String token : tokens)
        {
            this.distributeToken(output, operators, token);
        }
    }

    private void distributeToken(Queue<String> output, Stack<String> operators, String token) throws OperatorException
    {
        if (NumberValidator.isNumber(token))
        {
            this.addItemToOutput(output, token);
        }
        else if (OperatorChecker.isArithmeticOperator(this.convertToOperator(token)))
        {
            this.addArithmeticOperatorToOperatorStack(output, operators, token);
        }
        else if (OperatorChecker.isLeftBracket(this.convertToOperator(token)))
        {
            this.addItemToOperatorStack(operators, token);
        }
        else if (OperatorChecker.isRightBracket(this.convertToOperator(token)))
        {
            this.moveTokensFromOperatorStackToOutputUntilLeftBracket(operators, output);
        }
    }

    private void addItemToOutput(Queue<String> output, String token)
    {
        output.add(token);
    }

    private Operator convertToOperator(String operator) throws InvalidOperatorException
    {
        return OperatorFactory.makeOperator(operator);
    }

    private void addArithmeticOperatorToOperatorStack(Queue<String> output, Stack<String> operators, String token) throws OperatorException
    {
        while (!operators.empty() && this.shouldPopFromTheOperatorStack(operators, token))
        {
            this.moveTokenFromOperatorStackToOutput(operators, output);
        }

        this.addItemToOperatorStack(operators, token);
    }

    private boolean shouldPopFromTheOperatorStack(Stack<String> operators, String token) throws InvalidOperatorException
    {
        Operator nextOperator = this.convertToOperator(operators.peek());
        Operator current = this.convertToOperator(token);

        if (OperatorChecker.isLeftBracket(nextOperator))
        {
            return false;
        }

        boolean result = nextOperator.compareTo(current) > 0;
        result |= (nextOperator.compareTo(current) == 0 &&  ((ArithmeticOperator)nextOperator).isLeftAssociative());

        return result;
    }

    private void moveTokenFromOperatorStackToOutput(Stack<String> operators, Queue<String> output)
    {
        this.addItemToOutput(output, operators.pop());
    }

    private void addItemToOperatorStack(Stack<String> operators, String token)
    {
        operators.add(token);
    }

    private void moveTokensFromOperatorStackToOutputUntilLeftBracket(Stack<String> operators, Queue<String> output) throws OperatorException
    {
        while (!operators.empty() && !OperatorChecker.isLeftBracket(this.convertToOperator(operators.peek())))
        {
            moveTokenFromOperatorStackToOutput(operators, output);
        }

        // Removing the opening bracket
        if (!operators.empty())
        {
            operators.pop();
        }
        else
        {
            throw new BracketsException("Brackets error encountered - missing opening bracket.");
        }
    }

    private void moveTokensFromOperatorsStackToOutputQueue(Queue<String> output, Stack<String> operators) throws OperatorException
    {
        while (!operators.empty())
        {
            Operator topOperator = this.convertToOperator(operators.peek());

            if (OperatorChecker.isBracket(topOperator))
            {
                throw new BracketsException("Brackets error encountered - missing closing bracket.");
            }

            this.moveTokenFromOperatorStackToOutput(operators, output);
        }
    }

    private String popQueueToString(Queue<String> output)
    {
        StringBuilder outputString = new StringBuilder();
        final String WHITE_SPACE = " ";

        while(!output.isEmpty())
        {
            outputString.append(WHITE_SPACE).append(output.remove());
        }

        // result with surrounding whitespaces removed
        return outputString.toString().trim();
    }
}