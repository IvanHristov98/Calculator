package com.calculator.cli;

import com.calculator.cli.wrappers.calculator.CalculatorAdapter;
import com.calculator.core.Expression;

import java.util.Locale;

public class Launcher {
	public static final int VALID_NUMBER_OF_ARGUMENTS = 1;
	public static final int EXPRESSION_CONTENT_POSITION_IN_ARGUMENTS = 0;

	private CalculatorAdapter calculator;

	public Launcher(CalculatorAdapter calculator) {
		this.calculator = calculator;
	}

	public void run(String[] args) {
		setUpLocale();

		try {
			validateNumberOfArguments(args);

			String expressionContent = args[EXPRESSION_CONTENT_POSITION_IN_ARGUMENTS];
			Expression expression = new Expression(expressionContent);

			double calculationResult = calculator.calculate(expression);

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
