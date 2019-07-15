package com.calculator;

import com.calculator.exception.*;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.regex.Pattern;

public class ExpressionValidator extends ExpressionContainer
{
    public ExpressionValidator(Expression expression)
    {
        super(expression);
    }

    public void validateExpression() throws CalculatorException
    {
        String expressionContent = this.expression.getContent();

        this.validateIfAnyConsecutiveNumbers(expressionContent);
        this.validateNumbersFormat(expressionContent);

        expressionContent = this.stripSpaces(expressionContent);

        this.validateIfEmpty(expressionContent);
        this.validateOperatorSequence(expressionContent);
        this.validateTokens(expressionContent);
        this.validateIfAnyNumbersAreGluedAroundBracketedExpression(expressionContent);

        this.operateOnExpression(expressionContent, this::validateEnds);
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

    private void validateIfEmpty(String expression) throws EmptyExpressionException
    {
        if (expression.length() == 0)
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
        // There should be no two consecutive operators -, /, +, ^
        return  Pattern.matches(".*[-*/+^]{2,}.*", expression);
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
                    "It is not allowed to have numbers straight before or after a bracket symbol within an expression."
            );
        }
    }

    private boolean hasNumbersGluedAroundBracketExpression(String expression)
    {
        // Prevents having expressions with the format number1([sub_expression])number2
        return expression.matches(".*([0-9.]\\(|\\)[0-9]).*");
    }

    private String validateEnds(String expressionContent) throws CalculatorException
    {
        this.validateExpressionBeginning(expressionContent);
        this.validateExpressionAtEnd(expressionContent);

        return expressionContent; // todo explain reason
    }

    private void validateExpressionBeginning(String expression) throws OperatorMisplacementException
    {
        // An expression should start only with -, +, (, 0-9
        String validExpressionBeginningPattern = "^[-+(0-9]+.*";

        this.validateStringByPattern(expression, validExpressionBeginningPattern);
    }

    private void validateStringByPattern(String expression, String validPattern) throws OperatorMisplacementException
    {
        if (!Pattern.matches(validPattern, expression))
        {
            throw new OperatorMisplacementException(
                    "Expression is not ordered properly."
            );
        }
    }

    private void validateExpressionAtEnd(String expression) throws OperatorMisplacementException
    {
        // And expression should end only with 0-9, )
        String validEndPattern = ".*[0-9)]$";

        this.validateStringByPattern(expression, validEndPattern);
    }
}
