package com.calculator.cli;

import com.calculator.cli.coreWrapper.ExceptionWrappingCalculator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LauncherTest {
	@Mock
	ExceptionWrappingCalculator calculatorWrapper;
	Launcher launcher;

	ByteArrayOutputStream outputByteStream;
	PrintStream testingOutputPrintStream;
	PrintStream originaOutputlPrintStream;

	ByteArrayOutputStream errorByteStream;
	PrintStream testingErrorPrintStream;
	PrintStream originalErrorPrintStream;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		setUpOutputStream();
		setUpErrorStream();

		launcher = new Launcher(calculatorWrapper);
	}

	@After
	public void tearDown() {
		tearDownOutputStream();
		tearDownErrorStream();
	}

	@Test
	public void setUpLocale_run() {
		launcher.run(new String[] { "1" });

		assertEquals(Locale.US, Locale.getDefault());
	}

	@Test
	public void invalidNumberOfArguments_run() {
		launcher.run(new String[] { "1", "2" });

		String expectedMessage = "Invalid number of arguments. Only one argument is expected.";
		assertEquals(expectedMessage, errorByteStream.toString());
	}

	@Test
	public void verifyCalculation_run() throws Exception {
		when(calculatorWrapper.calculate(any())).thenReturn(Double.valueOf(1.0d));

		launcher.run(new String[] { "1" });

		String expectedMessage = "The expression result is 1.00.";
		assertEquals(expectedMessage, outputByteStream.toString());
	}

	private void setUpOutputStream() {
		outputByteStream = new ByteArrayOutputStream();
		testingOutputPrintStream = new PrintStream(outputByteStream);
		originaOutputlPrintStream = System.out;

		System.setOut(testingOutputPrintStream);
		;
	}

	private void tearDownOutputStream() {
		System.out.flush();
		System.setOut(originaOutputlPrintStream);
	}

	private void setUpErrorStream() {
		errorByteStream = new ByteArrayOutputStream();
		testingErrorPrintStream = new PrintStream(errorByteStream);
		originalErrorPrintStream = System.err;

		System.setErr(testingErrorPrintStream);
	}

	private void tearDownErrorStream() {
		System.err.flush();
		System.setErr(originalErrorPrintStream);
	}
}
