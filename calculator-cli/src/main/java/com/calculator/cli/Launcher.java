package com.calculator.cli;

import java.util.Locale;

import com.calculator.cli.coreWrapper.ExceptionWrappingCalculator;

public class Launcher {
	public static final int VALID_NUMBER_OF_ARGUMENTS = 1;
	public static final int EXPRESSION_CONTENT_POSITION_IN_ARGUMENTS = 0;
	
	private ExceptionWrappingCalculator calculatorWrapper;
	
	public Launcher(ExceptionWrappingCalculator calculatorWrapper) {
		this.calculatorWrapper = calculatorWrapper;
	}
	
	public void run(String[] args) {
		setUpLocale();
		
		try {
			validateNumberOfArguments(args);
			
			String expressionContent = args[EXPRESSION_CONTENT_POSITION_IN_ARGUMENTS];
			double calculationResult = this.calculatorWrapper.calculate(expressionContent);
			
			System.out.printf("The expression result is %.2f.", calculationResult);
		} catch (Exception exception) {
			System.err.print(exception.getMessage());
		}
	}
	
	private void setUpLocale() {
		Locale.setDefault(Locale.US);
	}
	
	private void validateNumberOfArguments(String[] args) throws Exception {
		if (args.length != VALID_NUMBER_OF_ARGUMENTS) {
			throw new Exception("Invalid number of arguments. Only one argument is expected.");
		}
	}
}
