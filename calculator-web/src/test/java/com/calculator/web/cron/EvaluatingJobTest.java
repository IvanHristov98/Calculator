package com.calculator.web.cron;

import java.sql.SQLException;
import java.util.*;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import static org.mockito.MockitoAnnotations.*;
import static org.mockito.Mockito.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.quartz.*;

import com.calculator.web.wrappers.calculator.ICalculator;
import com.calculator.web.wrappers.calculator.exception.WebCalculatorException;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.calculator.web.wrappers.db.dao.dbMappers.*;


public class EvaluatingJobTest {
	
	public static final int CALCULATION_RESULT_DAO_SAVE_ITEM_ARGUMENT_POSITION = 0;
	
	@Mock private CalculationResultsDao calculationResultsDao;
	@Mock private ICalculator calculator;
	@Mock private JobExecutionContext jobContext;
	@Mock private JobDetail jobDetail;
	@Mock private JobDataMap jobDataMap;
	private List<CalculationResult> calculationResults;
	private EvaluatingJob evaluatingJob;
	
	@Before
	public void setUp() throws SQLException {
		initMocks(this);
		
		evaluatingJob = new EvaluatingJob();
		
		when(jobContext.getJobDetail()).thenReturn(jobDetail);
		when(jobDetail.getJobDataMap()).thenReturn(jobDataMap);
		when(jobDataMap.get(EvaluatingJob.CALCULATION_RESULTS_DAO)).thenReturn(calculationResultsDao);
		when(jobDataMap.get(EvaluatingJob.CALCULATOR)).thenReturn(calculator);
		
		calculationResults = new ArrayList<>();
		when(calculationResultsDao.getPendingItems()).thenReturn(calculationResults);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void verifyValidPendingExpressionEvaluation() throws JobExecutionException, WebCalculatorException, SQLException {
		CalculationResult calculationResult = new CalculationResult();
		calculationResults.add(calculationResult);
		
		final Double evaluation = 2.0d;
		
		when(calculator.calculate(any())).thenReturn(evaluation);
		
		doAnswer((Answer) (invocation) -> {
			CalculationResult result = (CalculationResult) invocation.getArgument(CALCULATION_RESULT_DAO_SAVE_ITEM_ARGUMENT_POSITION);
			assertThat(result.getEvaluation(), equalTo(evaluation));
			return invocation;
		}).when(calculationResultsDao).update(calculationResult);
		
		evaluatingJob.execute(jobContext);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void verifyInvalidPendingExpressionEvaluation() throws WebCalculatorException, SQLException, JobExecutionException {
		CalculationResult calculationResult = new CalculationResult();
		calculationResults.add(calculationResult);
		
		final String mockedMessage = "A mocked message!";
		
		when(calculator.calculate(any())).thenThrow(new WebCalculatorException("A mocked message!"));
		
		doAnswer((Answer) (invocation) -> {
			CalculationResult result = (CalculationResult) invocation.getArgument(CALCULATION_RESULT_DAO_SAVE_ITEM_ARGUMENT_POSITION);
			assertThat(result.getMessage(), equalTo(mockedMessage));
			return invocation;
		}).when(calculationResultsDao).update(calculationResult);
		
		evaluatingJob.execute(jobContext);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void verifyPendingExpressionCompletion() throws WebCalculatorException, SQLException, JobExecutionException {
		CalculationResult calculationResult = new CalculationResult();
		calculationResults.add(calculationResult);
		
		doAnswer((Answer) (invocation) -> {
			CalculationResult result = (CalculationResult) invocation.getArgument(CALCULATION_RESULT_DAO_SAVE_ITEM_ARGUMENT_POSITION);
			assertThat(result.getStatus(), equalTo(CalculationStatus.COMPLETE.getStatusValue()));
			return invocation;
		}).when(calculationResultsDao).update(calculationResult);
		
		evaluatingJob.execute(jobContext);
	}
}
