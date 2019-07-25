package com.calculator.core;

public class NumberValidator
{
    private NumberValidator()
    {}

    protected static boolean isNumber(String token)
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
}
