package com.calculator.web.resources.representations;

import org.mockito.Mock;

import org.junit.*;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class CalculationResultsTest {
	@Mock
	private CalculationResult calculationResult;
	private CalculationResults calculationResults;
	
	@Before
	public void setUp() {
		calculationResults = new CalculationResults();
	}
	
	@Test
	public void verifyElementAdding() {
		assertThat(calculationResults.getResults().size(), equalTo(0));
		
		calculationResults.addResult(calculationResult);
		
		assertThat(calculationResults.getResults().size(), equalTo(1));
	}
	
	@Test
	public void verifyElementRemoval() {
		calculationResults.addResult(calculationResult);
		assertThat(calculationResults.getResults().size(), equalTo(1));
		
		calculationResults.removeResult(calculationResult);
		assertThat(calculationResults.getResults().size(), equalTo(0));
	}
}
