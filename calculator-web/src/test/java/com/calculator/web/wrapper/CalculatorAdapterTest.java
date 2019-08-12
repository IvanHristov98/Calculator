package com.calculator.web.wrapper;

import com.calculator.core.Expression;
import com.calculator.core.exception.*;
import com.calculator.web.wrapper.exception.WebCalculatorException;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.closeTo;

public class CalculatorAdapterTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Mock
	private com.calculator.core.ICalculator calculator;
	@Mock
	private Expression expression;
	private CalculatorAdapter calculatorAdapter;
	
	@Before
	public void setUp() {
		initMocks(this);
		
		calculatorAdapter = new CalculatorAdapter(calculator);
	}
	
	@Test
	public void verifyCalculation() throws Exception {
		when(calculator.calculate(any())).thenReturn(1.0d);
		assertThat(calculatorAdapter.calculate(expression), closeTo(1.0d, 0.0d));
	}
	
	@Test
	public void verifyOperatorMisplacement() throws Exception {
		mockCalculatorToReturnException(OperatorMisplacementException.class);
		verifyExpectedException(WebCalculatorException.class, "Expression error. Operator misplacement has been encountered.");
	}
	
	@Test
	public void verifyBracketsException() throws Exception {
		mockCalculatorToReturnException(BracketsException.class);
		verifyExpectedException(WebCalculatorException.class, "Expression error. Brackets misplacement has been encountered.");
	}
	
	@Test
	public void verifyDivisionByZeroException() throws Exception {
		mockCalculatorToReturnException(DivisionByZeroException.class);
		verifyExpectedException(WebCalculatorException.class, "Expression error. Division by zero encountered.");
	}
	
	@Test
	public void verifyEmptyExpressionException() throws Exception {
		mockCalculatorToReturnException(EmptyExpressionException.class);
		verifyExpectedException(WebCalculatorException.class, "Expression error. Empty expressions are not permitted.");
	}
	
	@Test
	public void verifyInvalidOperatorException() throws Exception {
		mockCalculatorToReturnException(InvalidOperatorException.class);
		verifyExpectedException(WebCalculatorException.class, "Expression error. An invalid operator has been encountered.");
	}
	
	@Test
	public void verifyNumberMisplacementException() throws Exception {
		mockCalculatorToReturnException(NumberMisplacementException.class);
		verifyExpectedException(WebCalculatorException.class, "Expression error. An invalid number ordering has been encountered.");
	}
	
	@Test
	@Ignore
	public void verifyCalculatorException() throws Exception {
		mockCalculatorToReturnException(CalculatorException.class);
		verifyExpectedException(WebCalculatorException.class, "Expression error. Invalid expression given.");
	}
	
	private void mockCalculatorToReturnException(Class<? extends Throwable> typeToThrow) throws CalculatorException {
		when(calculator.calculate(any())).thenThrow(typeToThrow);
	}
	
	private void verifyExpectedException(Class<? extends Throwable> expectedType, String message) throws Exception {
		expectedException.expect(expectedType);
		expectedException.expectMessage(message);
		
		calculatorAdapter.calculate(expression);
	}
}
