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
		
		this.setUpOutputStream();
		this.setUpErrorStream();
		
		this.launcher = new Launcher(this.calculatorWrapper);
	}
	
	@After
	public void tearDown() {
		this.tearDownOutputStream();
		this.tearDownErrorStream();
	}
	
	@Test
	public void setUpLocale_run() {
		this.launcher.run(new String[] {"1"});
		
		assertEquals(Locale.US, Locale.getDefault());
	}
	
	@Test
	public void invalidNumberOfArguments_run() {
		this.launcher.run(new String[] {"1", "2"});
		
		String expectedMessage = "Invalid number of arguments. Only one argument is expected.";
		assertEquals(expectedMessage, this.errorByteStream.toString());
	}
	
	@Test
	public void verifyCalculation_run() throws Exception {
		when(this.calculatorWrapper.calculate(any())).thenReturn(Double.valueOf(1.0d));
		
		this.launcher.run(new String [] {"1"});
		
		String expectedMessage = "The expression result is 1.00.";
		assertEquals(expectedMessage, this.outputByteStream.toString());
	}
	
	private void setUpOutputStream() {
		this.outputByteStream = new ByteArrayOutputStream();
		this.testingOutputPrintStream = new PrintStream(this.outputByteStream);
		this.originaOutputlPrintStream = System.out;
		
		System.setOut(this.testingOutputPrintStream);;
	}
	
	private void tearDownOutputStream() {
		System.out.flush();
		System.setOut(this.originaOutputlPrintStream);
	}
	
	private void setUpErrorStream() {
		this.errorByteStream = new ByteArrayOutputStream();
		this.testingErrorPrintStream = new PrintStream(this.errorByteStream);
		this.originalErrorPrintStream = System.err;
		
		System.setErr(this.testingErrorPrintStream);
	}
	
	private void tearDownErrorStream() {
		System.err.flush();
		System.setErr(this.originalErrorPrintStream);
	}
}
