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
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", PATH_TO_JAR, "(1+2)*3");
		Process p = pb.start();
		InputStreamReader is = new InputStreamReader(p.getInputStream());
		
		BufferedReader br = new BufferedReader(is);
		
		assertEquals("The expression result is 9.00.", br.readLine());
		
		p.destroy();
	}
}
