package com.calculator.cli.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.calculator.cli.Main;

public class CorrectUserInputMainIT {
	ByteArrayOutputStream outputByteStream;
	PrintStream testingOutputPrintStream;
	PrintStream originalOutputStream;
	
	@Before
	public void setUp() {
		this.outputByteStream = new ByteArrayOutputStream();
		this.testingOutputPrintStream = new PrintStream(this.outputByteStream);
		this.originalOutputStream = System.out;
		
		System.setOut(this.testingOutputPrintStream);
	}
	
	@After
	public void tearDown() {
		System.out.flush();
		System.setOut(this.originalOutputStream);
	}
	
	@Test
	public void calculateCorrectExpression() {
		Main.main(new String[] {"(1+2)*3"});
		
		assertEquals("The expression result is 9,00.", this.outputByteStream.toString());
	}
}
