package com.calculator.cli.tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.calculator.cli.Main;

public class UserErrorMainIT {
	ByteArrayOutputStream errorByteStream;
	PrintStream testingErrorPrintStream;
	PrintStream originalErrorPrintStream;
	
	@Before
	public void setUp() {
		this.errorByteStream = new ByteArrayOutputStream();
		this.testingErrorPrintStream = new PrintStream(this.errorByteStream);
		this.originalErrorPrintStream = System.err;
		
		System.setErr(this.testingErrorPrintStream);
	}
	
	@After
	public void tearDown() {
		System.err.flush();
		System.setErr(this.originalErrorPrintStream);
	}
	
	@Test
	public void invalidNumberOfArguments_main() {
		Main.main(new String[] {"1", "2"});
		
		assertEquals(
				"Invalid number of arguments. Only one argument is expected.", 
				this.errorByteStream.toString()
				);
	}
	
	@Test
	public void operatorMisplacement_main() {
		Main.main(new String[] {"1+*2"});
		
		assertEquals(
				"Expression error. Operator misplacement error encountered.",
				this.errorByteStream.toString()
				);
	}
	
	@Test
	public void bracketRelatedError_main() {
		Main.main(new String[] {"(1))"});
		
		assertEquals(
				"Expression error. Brackets error encountered.",
				this.errorByteStream.toString()
				);
	}
	
	@Test
	public void divisionByZero_main() {
		Main.main(new String[] {"1/0"});
		
		assertEquals(
				"Expression error. Division by zero encountered.",
				this.errorByteStream.toString()
				);
	}
	
	@Test
	public void emptyExpression_main() {
		Main.main(new String [] {""});
		
		assertEquals(
				"Expression error. Empty expression encountered.",
				this.errorByteStream.toString()
				);
	}
	
	@Test
	public void invalidOperator_main() {
		Main.main(new String [] {"1A5"});
		
		assertEquals(
				"Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.",
				this.errorByteStream.toString()
				);
	}
	
	@Test
	public void numberMisplacement_main() {
		Main.main(new String [] {"1 2"});
		
		assertEquals(
				"Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.",
				this.errorByteStream.toString()
				);
	}
}
