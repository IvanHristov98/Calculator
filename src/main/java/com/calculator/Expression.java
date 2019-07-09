package com.calculator;

class Expression 
{
	private String content;
	
	public static Expression constructEmptyExpression()
	{
		return new Expression();
	}
	
	private Expression()
	{}
	
	public static Expression constructFromExpressionContent(String content)
	{
		return new Expression(content);
	}
	
	private Expression(String content)
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
