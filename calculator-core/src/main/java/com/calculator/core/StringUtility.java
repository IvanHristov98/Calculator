package com.calculator.core;

class StringUtility 
{
	protected static String stripSpaces(String string)
    {
        return string.replaceAll(" ", "");
    }
    
    protected static String wrapStringWithBrackets(String string)
    {
    	return "(" + string + ")";
    }
}
