package com.calculator.cli;

import com.calculator.cli.coreWrapper.*;

public class Main {
	private static CalculatorSupplier calculatorSupplier = new CalculatorSupplier();
	private static CalculatorWrapper calculatorWrapper = new CalculatorWrapper(calculatorSupplier);
	private static Launcher launcher = new Launcher(calculatorWrapper);
	
	public static void main(String[] args) {
		launcher.run(args);
	}
	
	public static void setLauncher(Launcher launcher) throws Exception {
		Main.launcher = launcher;
	}
}