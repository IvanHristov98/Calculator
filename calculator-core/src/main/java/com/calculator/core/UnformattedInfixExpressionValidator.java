package com.calculator.core;

import com.calculator.core.exception.*;

import java.util.regex.Pattern;

public class UnformattedInfixExpressionValidator extends ExpressionContainer
{
    public UnformattedInfixExpressionValidator(Expression expression)
    {
        super(expression);
    }

    public Expression process() throws CalculatorException
    {
        String wrappedExpressionContent = this.wrapStringWithBrackets(this.expression.getContent());

        this.validateIfAnyConsecutiveNumbers(wrappedExpressionContent);
        this.validateNumbersFormat(wrappedExpressionContent);

        wrappedExpressionContent = this.stripSpaces(wrappedExpressionContent);

        this.validateIfExpressionHasEmptySubexpressions(wrappedExpressionContent);
        this.validateOperatorSequence(wrappedExpressionContent);
        this.validateTokens(wrappedExpressionContent);
        this.validateIfAnyNumbersAreGluedAroundBracketedExpression(wrappedExpressionContent);
        this.validateSubexpressionEnds(this.wrapStringWithBrackets(wrappedExpressionContent));

        return this.expression;
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
        if (this.hasAnInvalidValidNumber(expression))
        {
            throw new NumberMisplacementException("The given expression contains invalid numbers.");
        }
    }

    private boolean hasAnInvalidValidNumber(String expression)
    {
        // checks if there are two floating point numbers one after another
        // following the mantissa_1.exponent_1mantissa_2.exponent_2
        return Pattern.matches(".*[0-9]+\\.[0-9]*\\..*", expression);
    }

    private void validateIfExpressionHasEmptySubexpressions(String expression) throws EmptyExpressionException
    {
        if (expression.contains("()"))
        {
            throw new EmptyExpressionException("An empty expression is not a valid one.");
        }
    }

    private void validateOperatorSequence(String expression) throws OperatorMisplacementException
    {
        if (this.hasConsecutiveOperators(expression))
        {
            throw new OperatorMisplacementException("The given expression contains consecutive operators.");
        }
    }

    private boolean hasConsecutiveOperators(String expression)
    {
        // There should be no two consecutive operators -, +, *, / or ^
        return  Pattern.matches(".*[-+*/^]{2,}.*", expression);
    }

    private void validateTokens(String expression) throws InvalidOperatorException
    {
        if (this.hasInvalidTokens(expression))
        {
            throw new InvalidOperatorException("The given expression contains invalid tokens.");
        }
    }

    private boolean hasInvalidTokens(String expression)
    {
        // An expression should only contain the symbols -, +, *, /, ^, (, ), .,  0-9
        return Pattern.matches(".*[^-+*/^()0-9.].*", expression);
    }

    private void validateIfAnyNumbersAreGluedAroundBracketedExpression(String expression) throws BracketsException
    {
        if (this.hasNumbersGluedAroundBracketExpression(expression))
        {
            throw new BracketsException(
                    "It is not allowed to have a number right before a '(' symbol or right after a ')' symbol."
            );
        }
    }

    private boolean hasNumbersGluedAroundBracketExpression(String expression)
    {
        // Prevents having expressions with the format number1([sub_expression])number2
        return expression.matches(".*([0-9.]\\(|\\)[0-9]).*");
    }
    
    private void validateSubexpressionEnds(String expressionContent) throws CalculatorException
    {
        this.validateExpressionAfterOpeningBrackets(expressionContent);
        this.validateExpressionBeforeClosingBrackets(expressionContent);
    }
    
    private void validateExpressionAfterOpeningBrackets(String expression) throws OperatorMisplacementException
    {
    	// there shouldn't be an *, / or ^ symbol right after an opening bracket
    	this.validateStringByPattern(expression, ".*\\([*\\/^].*");
    }

    private void validateExpressionBeforeClosingBrackets(String expression) throws OperatorMisplacementException
    {
    	// A there should be no arithmetic operators right before a closing bracket
    	this.validateStringByPattern(expression, ".*[-+*\\/^]\\).*");
    }
    
    private void validateStringByPattern(String expression, String invalidPattern) throws OperatorMisplacementException
    {
        if (Pattern.matches(invalidPattern, expression))
        {
            throw new OperatorMisplacementException(
                    "Expression is not ordered properly."
            );
        }
    }
}
