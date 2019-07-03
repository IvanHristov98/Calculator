package com.testCalculator;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		
		Double left = in.nextDouble();
		String operation = in.next();
		Double right = in.nextDouble();
		
		try 
		{
			System.out.println(Calculator.calculate(left, right, operation).doubleValue());
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
}
