package com.calculator.core;

public class NumberChecker
{
    public NumberChecker()
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
