package com.calculator.cli;

import com.calculator.core.*;

public class Main {
	public static int VALID_NUMBER_OF_ARGUMENTS = 1;
	
	public static void main(String[] args) {
		try {
			validateNumberOfArguments(args);
		} catch (Exception exception) {
			System.err.print(exception.getMessage());
		}
	}
	
	private static void validateNumberOfArguments(String[] args) throws Exception {
		if (args.length != VALID_NUMBER_OF_ARGUMENTS) {
			throw new Exception("Invalid number of arguments. Only one argument is expected.");
		}
	}
}
