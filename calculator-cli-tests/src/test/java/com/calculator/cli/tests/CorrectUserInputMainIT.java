package com.calculator.cli.tests;

import com.calculator.cli.tests.pageObjects.CliPage;

import java.io.IOException;
import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class CorrectUserInputMainIT {
	private CliPage cliPage;
	
	@Before
	public void setUp() {
		cliPage = new CliPage();
	}

	@Test
	public void calculateCorrectExpression() throws IOException {		
		verifyCliOutput("The expression result is 9.00.", "(1+2)*3");
	}
	
	private void verifyCliOutput(String expected, String... commandArguments) throws IOException {
		String cliOutput = cliPage.getCliOutput(commandArguments);
		assertThat(expected, equalTo(cliOutput));
	}
}
