package com.calculator.web.tests;

import com.calculator.web.tests.authorization.StubAuthorizationHeader;
import com.calculator.web.tests.authorization.JwtGenerator;
import com.calculator.web.tests.pageObjects.resources.*;

import static com.calculator.web.tests.pageObjects.db.CalculationResultsTable.*;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EvaluatingCronJobIT extends ResourceIT {
	
	public static final int EVALUATING_JOB_PERIOD_IN_MILISECONDS = 5000;
	public static final int ADDITIONAL_TIME_FOR_EVALUATING_JOB_IN_MILISECONDS = 500;
	
	private CalculationResultResourcePage calculationResultPage;
	private CalculateResourcePage calculatePage;
	private JwtGenerator jwtGenerator;
	private StubAuthorizationHeader authorizationHeader;
	
	@Before
	public void setUp() throws Exception {
		jwtGenerator = new JwtGenerator();
		authorizationHeader = new StubAuthorizationHeader(jwtGenerator);
		
		calculationResultPage = new CalculationResultResourcePage(baseUrl, authorizationHeader);
		calculatePage = new CalculateResourcePage(baseUrl, authorizationHeader);
		dbPage.useDataSet(Datasets.EMPTY_DATA_SET.getPath());
	}
	
	@After
	public void tearDown() throws SQLException {
		dbPage.restartAutoIncrementation();
	}
	
	@Test
	public void verifyCorrectExpressionSaving() throws Exception {
		final String expression = "2*2 + 5";
		JSONObject calculationResult = getCalculatedExpression(expression);
		
		verifyExpression(calculationResult, expression);
	}
	
	@Test
	public void verifyCorrectCalculation() throws Exception {
		final String expression = "(1+2)*3 + 2^2 + 4/2";
    	JSONObject calculationResult = getCalculatedExpression(expression);
    	
    	final Double expectedEvaluation = 15.0d;
    	verifyEvaluation(calculationResult, expectedEvaluation);
	}
	
	@Test
	public void verifyCalculationCompletion() throws Exception {
		final String expression = "1+1";
    	JSONObject calculationResult = getCalculatedExpression(expression);
    	
    	final int expectedStatus = 2;
    	verifyStatus(calculationResult, expectedStatus);
	}
	
	@Test
	public void verifyDivisionByZeroErrorMessage() throws Exception {
		final String expression = "1/0";
    	JSONObject calculationResult = getCalculatedExpression(expression);
    	
    	final String expectedMessage = "Expression error. Division by zero encountered.";
    	verifyMessage(calculationResult, expectedMessage);
	}
	
	@Test
	public void verifyBracketsErrorMessage() throws Exception {
		final String expression = "(1+2";
    	JSONObject calculationResult = getCalculatedExpression(expression);
    	
    	final String expectedMessage = "Expression error. Brackets misplacement has been encountered.";
    	verifyMessage(calculationResult, expectedMessage);
	}
	
	@Test
	public void verifyOperatorMisplacementErrorMessage() throws Exception {
		final String expression = "1+2+";
    	JSONObject calculationResult = getCalculatedExpression(expression);
    	
    	final String expectedMessage = "Expression error. Operator misplacement has been encountered.";
    	verifyMessage(calculationResult, expectedMessage);
	}
	
	@Test
	public void verifyEmptyExpressionErrorMessage() throws Exception {
		final String expression = "";
    	JSONObject calculationResult = getCalculatedExpression(expression);
    	
    	final String expectedMessage = "Expression error. Empty expressions are not permitted.";
    	verifyMessage(calculationResult, expectedMessage);
	}
	
	@Test
	public void verifyInvalidOperatorErrorMessage() throws Exception {
		final String expression = "1A2";
    	JSONObject calculationResult = getCalculatedExpression(expression);
    	
    	final String expectedMessage = "Expression error. An invalid operator has been encountered.";
    	verifyMessage(calculationResult, expectedMessage);
	}
	
	@Test
	public void verifyNumberMisplacementErrorMessage() throws Exception {
		final String expression = "1 2";
    	JSONObject calculationResult = getCalculatedExpression(expression);
    	
    	final String expectedMessage = "Expression error. An invalid number ordering has been encountered.";
    	verifyMessage(calculationResult, expectedMessage);
	}
	
	private JSONObject getCalculatedExpression(String expression) throws Exception {
		String calculationResultId = callCalculateResource(expression);
    	waitForEvaluatingJob();
    	return callCalculationResultResource(calculationResultId);
	}
	
	private String callCalculateResource(String expression) throws IOException {
		calculatePage.setExpressionParameter(expression);
    	Response calculateResponse = calculatePage.getResourceContent();
    	return calculateResponse.readEntity(String.class);
	}
	
	private void waitForEvaluatingJob() throws InterruptedException {
		Thread.sleep(EVALUATING_JOB_PERIOD_IN_MILISECONDS+ADDITIONAL_TIME_FOR_EVALUATING_JOB_IN_MILISECONDS);
	}
	
	private JSONObject callCalculationResultResource(String calculationResultId) throws Exception {
		calculationResultPage.setIdParameter(calculationResultId);
    	Response calculationResultResponse = calculationResultPage.getResourceContent();
    	String calculationResultResponseContent = calculationResultResponse.readEntity(String.class);
    	
    	return new JSONObject(calculationResultResponseContent);
	}
	
	private void verifyExpression(JSONObject calculationResult, String expectedExpression) {
		assertThat(calculationResult.getString(EXPRESSION), equalTo(expectedExpression));
	}
	
	private void verifyEvaluation(JSONObject calculationResult, Double expectedEvaluation) {
		final Double acceptableError = 0.0001;
		
		assertThat(calculationResult.getDouble(EVALUATION), closeTo(expectedEvaluation, acceptableError));
	}
	
	private void verifyStatus(JSONObject calculationResult, int expectedStatus) {
		assertThat(calculationResult.getInt(STATUS), equalTo(expectedStatus));
	}
	
	private void verifyMessage(JSONObject calculationResult, String message) {
		assertThat(calculationResult.getString(MESSAGE), equalTo(message));
	}
}
