package com.calculator.cli.tests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

import org.junit.Test;

public class IncorrectUserInputMainIT {
	public static String PATH_TO_JAR = "./target/lib/calculator-cli-1.0-SNAPSHOT.jar";
	
	@Test
	public void invalidNumberOfArguments_main() throws IOException {
		testCalculatorErrorStream("Invalid number of arguments. Only one argument is expected.", "1", "2");
	}
	
	@Test
	public void operatorMisplacement_main() throws IOException {
		testCalculatorErrorStream("Expression error. Operator misplacement error encountered.", "1+*2");
	}
	
	@Test
	public void bracketRelatedError_main() throws IOException {
		testCalculatorErrorStream("Expression error. Brackets error encountered.", "(1))");
	}
	
	@Test
	public void divisionByZero_main() throws IOException {
		testCalculatorErrorStream("Expression error. Division by zero encountered.", "1/0");
	}
	
	@Test
	public void emptyExpression_main() throws IOException {
		testCalculatorErrorStream("Expression error. Empty expression encountered.", " ");
	}
	
	@Test
	public void invalidOperator_main() throws IOException {
		testCalculatorErrorStream("Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.", "1A5");
	}
	
	@Test
	public void numberMisplacement_main() throws IOException {
		testCalculatorErrorStream("Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.", "1 2");
	}
	
	public void testCalculatorErrorStream(String expectedResult, String... commandArguments) throws IOException {
		String[] defaultJarCallingPrefixArguments = new String[] {"java", "-jar", PATH_TO_JAR};
		commandArguments = mergeStringArrays(defaultJarCallingPrefixArguments, commandArguments);
		
		ProcessBuilder processBuilder = new ProcessBuilder(commandArguments);
		Process process = processBuilder.start();
		InputStreamReader streamReader = new InputStreamReader(process.getErrorStream());
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		
		assertEquals(expectedResult, bufferedReader.readLine());
		
		process.destroy();
	}
	
	public String[] mergeStringArrays(String[] firstArray, String[] secondArray) {
		String[] resultArray = (String[]) Array.newInstance(String.class, firstArray.length + secondArray.length);
		
		for (int i = 0; i < firstArray.length; ++i) {
			resultArray[i] = firstArray[i];
		}
		
		for (int i = firstArray.length; i < firstArray.length + secondArray.length; ++i) {
			resultArray[i] = secondArray[i - firstArray.length];
		}
		
		return resultArray;
	}
	
}
