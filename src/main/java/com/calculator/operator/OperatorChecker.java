package com.calculator.operator;

public class OperatorChecker
{

    public static boolean isPlus(Operator operator)
    {
        return operator instanceof PlusOperator;
    }

    public static boolean isMinus(Operator operator)
    {
        return operator instanceof MinusOperator;
    }

    public static boolean isProduct(Operator operator)
    {
        return operator instanceof ProductOperator;
    }

    public static boolean isDivision(Operator operator)
    {
        return operator instanceof DivisionOperator;
    }

    public static boolean isPow(Operator operator)
    {
        return operator instanceof PowOperator;
    }

    public static boolean isLeftBracket(Operator operator)
    {
        return operator instanceof LeftBracketOperator;
    }

    public static boolean isRightBracket(Operator operator)
    {
        return operator instanceof RightBracketOperator;
    }

    public static boolean isBracket(Operator operator)
    {
        return isLeftBracket(operator) || isRightBracket(operator);
    }

    public static boolean isArithmeticOperator(Operator operator)
    {
        return operator instanceof ArithmeticOperator;
    }

    private OperatorChecker()
    {}
}
