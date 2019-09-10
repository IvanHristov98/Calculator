package com.calculator.web.cron;

import java.sql.SQLException;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.calculator.core.Expression;
import com.calculator.web.wrappers.calculator.ICalculator;
import com.calculator.web.wrappers.calculator.exception.WebCalculatorException;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationStatus;

public class EvaluatingJob implements Job {
	
	public static String CALCULATION_RESULTS_DAO = "calculationResultsDao";
	public static String CALCULATOR = "calculator";
	
	private CalculationResultsDao calculationResultsDao;
	private ICalculator calculator;

	@Override
	public synchronized void execute(JobExecutionContext jobContext) throws JobExecutionException {
		calculationResultsDao = getItemFromJobDataMap(jobContext, CALCULATION_RESULTS_DAO);
		calculator = getItemFromJobDataMap(jobContext, CALCULATOR);
		
		try {
			List<CalculationResult> pendingCalculationResults = calculationResultsDao.getPendingItems();
			
			for (CalculationResult calculationResult : pendingCalculationResults) {
				calculationResult = getCompletedCalculationResult(calculationResult);
				calculationResultsDao.update(calculationResult);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getItemFromJobDataMap(JobExecutionContext jobContext, String parameter) {
		return (T) jobContext.getJobDetail().getJobDataMap().get(parameter);
	}
	
	private CalculationResult getCompletedCalculationResult(CalculationResult calculationResult) { 
		try {
			Double expressionEvaluation = calculate(calculationResult.getExpression());
			calculationResult.setEvaluation(expressionEvaluation);
			
			return calculationResult;
		} catch (WebCalculatorException exception) {
			calculationResult.setMessage(exception.getMessage());
			
			return calculationResult;
		} finally {
			calculationResult.setStatus(CalculationStatus.COMPLETE);
		}
	}
	
	private Double calculate(String expessionContent) throws WebCalculatorException  {
		Expression expression = new Expression(expessionContent);
		return calculator.calculate(expression);
	}
}