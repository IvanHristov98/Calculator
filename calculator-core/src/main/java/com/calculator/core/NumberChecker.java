package com.calculator.core;

public class NumberChecker
{
    private NumberChecker()
    {}

    public boolean isNumber(String token)
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
