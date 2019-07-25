package com.calculator.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NumberCheckerTest {
	private NumberChecker numberChecker;

	@Before
	public void setUp() {
		this.numberChecker = new NumberChecker();
	}

	@Test
	public void nonNumber_isNumber() {
		assertEquals(false, this.numberChecker.isNumber("+"));
	}

	@Test
	public void integer_isNumber() {
		assertEquals(true, this.numberChecker.isNumber("1"));
	}

	@Test
	public void floatingPointNumber_isNumber() {
		assertEquals(true, this.numberChecker.isNumber("1.2"));
	}

	@Test
	public void invalidFloatingPointNumber_isNumber() {
		assertEquals(false, this.numberChecker.isNumber("1.2.3"));
	}
}
