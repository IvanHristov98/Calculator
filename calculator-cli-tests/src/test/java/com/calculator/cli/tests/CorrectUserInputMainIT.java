package com.calculator.cli.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CorrectUserInputMainIT {
	public static String PATH_TO_JAR = "../calculator-cli/target/calculator-cli-1.0-SNAPSHOT-jar-with-dependencies.jar";
	
	@Test
	public void calculateCorrectExpression() throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", PATH_TO_JAR, "(1+2)*3");
		Process process = processBuilder.start();
		InputStreamReader streamReader = new InputStreamReader(process.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		
		assertEquals("The expression result is 9.00.", bufferedReader.readLine());
		
		process.destroy();
	}
}
