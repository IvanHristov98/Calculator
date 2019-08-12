package com.calculator.cli.tests;

import com.calculator.cli.tests.pageObjects.CliPage;

import java.io.IOException;

import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class IncorrectUserInputMainIT {
	private CliPage cliPage;
	
	@Before
	public void setUpClass() {
		cliPage = new CliPage();
	}

	@Test
	public void invalidNumberOfArguments_main() throws IOException {
		verifyCliError("Invalid number of arguments. Only one argument is expected.", "1", "2");
	}

	@Test
	public void operatorMisplacement_main() throws IOException {
		verifyCliError("Expression error. Operator misplacement error encountered.", "1+*2");
	}

	@Test
	public void bracketRelatedError_main() throws IOException {
		verifyCliError("Expression error. Brackets error encountered.", "(1))");
	}

	@Test
	public void divisionByZero_main() throws IOException {
		verifyCliError("Expression error. Division by zero encountered.", "1/0");
	}

	@Test
	public void emptyExpression_main() throws IOException {
		verifyCliError("Expression error. Empty expression encountered.", " ");
	}

	@Test
	public void invalidOperator_main() throws IOException {
		verifyCliError(
				"Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.",
				"1A5"
				);
	}

	@Test
	public void numberMisplacement_main() throws IOException {
		verifyCliError(
				"Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.",
				"1 2"
				);
	}
	
	private void verifyCliError(String expected, String... commandArguments) throws IOException {
		String cliError = cliPage.getCliError(commandArguments);
		assertThat(expected, equalTo(cliError));
	}
}
