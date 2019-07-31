package com.calculator.cli;

import com.calculator.cli.coreWrapper.*;
import com.calculator.core.CalculatorFactory;
import com.calculator.core.ICalculator;

public class Main {
	private static CalculatorFactory calculatorFactory = new CalculatorFactory();
	private static ICalculator calculator = calculatorFactory.makeCalculator();
	private static ExceptionWrappingCalculator calculatorWrapper = new ExceptionWrappingCalculator(calculator);
	private static Launcher launcher = new Launcher(calculatorWrapper);

	public static void main(String[] args) {
		launcher.run(args);
	}

	public static void setLauncher(Launcher launcher) throws Exception {
		Main.launcher = launcher;
	}
}