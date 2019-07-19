package com.calculator.core;

class Expression 
{
	private String content;
	
	public Expression(String content)
	{
		this.content = content;
	}
	
	@Override
	public Expression clone()
	{
		return new Expression(this);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Expression))
		{
			return false;
		}
		
		return this.content.equals(((Expression)obj).getContent());
	}
	
	private Expression(Expression other)
	{
		// String objects are immutable so it is safe
		this(other.content);
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return this.content;
	}
}
