package com.calculator.core;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.*;

public class NumberCheckerTest {
	private NumberChecker numberChecker;

	@Before
	public void setUp() {
		numberChecker = new NumberChecker();
	}

	@Test
	public void nonNumber_isNumber() {
		assertThat(numberChecker.isNumber("+"), equalTo(false));
	}

	@Test
	public void integer_isNumber() {
		assertThat(numberChecker.isNumber("1"), equalTo(true));
	}

	@Test
	public void floatingPointNumber_isNumber() {
		assertThat(numberChecker.isNumber("1.2"), equalTo(true));
	}

	@Test
	public void invalidFloatingPointNumber_isNumber() {
		assertThat(numberChecker.isNumber("1.2.3"), equalTo(false));
	}
}
