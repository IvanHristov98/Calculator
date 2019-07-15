package com.calculator;

public class NumberValidator
{
    private NumberValidator()
    {}

    protected static boolean isNumber(String token)
    {
        try
        {
            NumberValidator.toNumber(token);
        }
        catch (NumberFormatException exception)
        {
            return false;
        }

        return true;
    }

    protected static Double toNumber(String token) throws NumberFormatException
    {
        return Double.parseDouble(token);
    }
}
