package com.calculator.cli;

import com.calculator.core.Calculator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {
	@Mock
	Calculator calculator;
	
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
	}
	
	@After
	public void tearDown() {
		this.tearDownOutputStream();
		this.tearDownErrorStream();
	}
	
	@Test
	public void invalidNumberOfArguments_main() {
		Main.main(new String[] {"1", "2"});
		
		String expectedMessage = "Invalid number of arguments. Only one argument is expected.";
		assertEquals(expectedMessage, this.errorByteStream.toString());
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
