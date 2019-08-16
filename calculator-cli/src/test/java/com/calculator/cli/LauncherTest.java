package com.calculator.cli;

import java.io.*;
import java.util.Locale;

import org.mockito.Mock;

import com.calculator.cli.wrappers.calculator.CalculatorAdapter;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class LauncherTest {
	@Mock
	CalculatorAdapter calculatorAdapter;
	Launcher launcher;

	ByteArrayOutputStream outputByteStream;
	PrintStream testingOutputPrintStream;
	PrintStream originaOutputlPrintStream;

	ByteArrayOutputStream errorByteStream;
	PrintStream testingErrorPrintStream;
	PrintStream originalErrorPrintStream;

	@Before
	public void setUp() {
		initMocks(this);

		setUpOutputStream();
		setUpErrorStream();

		launcher = new Launcher(calculatorAdapter);
	}

	@After
	public void tearDown() {
		tearDownOutputStream();
		tearDownErrorStream();
	}

	@Test
	public void setUpLocale_run() {
		launcher.run(new String[] { "1" });

		assertThat(Locale.getDefault(), equalTo(Locale.US));
	}

	@Test
	public void invalidNumberOfArguments_run() {
		launcher.run(new String[] { "1", "2" });

		String expectedMessage = "Invalid number of arguments. Only one argument is expected.";
		assertThat(errorByteStream.toString(), equalTo(expectedMessage));
	}

	@Test
	public void verifyCalculation_run() throws Exception {
		when(calculatorAdapter.calculate(any())).thenReturn(Double.valueOf(1.0d));

		launcher.run(new String[] { "1" });

		String expectedMessage = "The expression result is 1.00.";
		assertThat(outputByteStream.toString(), equalTo(expectedMessage));
	}

	private void setUpOutputStream() {
		outputByteStream = new ByteArrayOutputStream();
		testingOutputPrintStream = new PrintStream(outputByteStream);
		originaOutputlPrintStream = System.out;

		System.setOut(testingOutputPrintStream);
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
