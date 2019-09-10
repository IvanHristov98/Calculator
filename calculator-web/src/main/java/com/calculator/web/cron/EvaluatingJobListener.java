package com.calculator.web.cron;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.calculator.core.CalculatorFactory;
import com.calculator.web.wrappers.calculator.CalculatorAdapter;
import com.calculator.web.wrappers.calculator.ICalculator;
import com.calculator.web.wrappers.db.DatabaseConnection;
import com.calculator.web.wrappers.db.DatabaseUri;
import com.calculator.web.wrappers.db.JdbcCredentials;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;

public class EvaluatingJobListener implements ServletContextListener {

	public static final String JOB_NAME = "EvaluatingJob";
	public static final String JOB_GROUP = "CalculatorJobs";
	public static final String TRIGGER_NAME = "EvaluatingTrigger";
	public static final String TRIGGER_GROUP = "CalculatorTriggers";
	
	public static final int TRIGGER_INTERVAL_IN_SECONDS = 5;
	
	private static Scheduler scheduler;
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		try {
			scheduler.shutdown();
		} catch (SchedulerException exception) {
			exception.printStackTrace();
		}
	}
	
	
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		try {
			scheduler = getStandardScheduler();
			scheduler.start();
			
			JobBuilder jobBuilder = JobBuilder.newJob(EvaluatingJob.class);
			JobDataMap dataMap = new JobDataMap();
			dataMap.put(EvaluatingJob.CALCULATION_RESULTS_DAO, makeCalculationResultsDao());
			dataMap.put(EvaluatingJob.CALCULATOR, makeCalculator());
			
			JobDetail jobDetail = jobBuilder.usingJobData(dataMap).withIdentity(JOB_NAME, JOB_GROUP).build();
			
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, TRIGGER_GROUP)
					.startNow()
					.withSchedule(getInfiniteScheduleWithInterval(TRIGGER_INTERVAL_IN_SECONDS))
					.build();
			scheduler.scheduleJob(jobDetail, trigger);
			
		} catch (SchedulerException | SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	private Scheduler getStandardScheduler() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		return schedulerFactory.getScheduler();
	}
	
	private CalculationResultsDao makeCalculationResultsDao() throws SQLException {
		DatabaseConnection dbConnection = makeDatabaseConnection();
		EntityManager entityManager = dbConnection.getEntityManager();
		
		return new CalculationResultsDao(entityManager);
	}
	
	private DatabaseConnection makeDatabaseConnection() throws SQLException {
		DatabaseUri dbUri = makeDatabaseUri();
		return DatabaseConnection.getInstance(dbUri);
	}
	
	private DatabaseUri makeDatabaseUri() {
		JdbcCredentials dbCredentials = makeJdbcCredentials();
		DriverFactory driverFactory = makeDriverFactory();
		return new DatabaseUri(dbCredentials, driverFactory);
	}
	
	private JdbcCredentials makeJdbcCredentials() {
		return new JdbcCredentials();
	}
	
	private DriverFactory makeDriverFactory() {
		return new DriverFactory();
	}

	private ICalculator makeCalculator() {
		CalculatorFactory calculatorFactory = makeCalculatorFactory();
		return new CalculatorAdapter(calculatorFactory.makeCalculator());
	}
	
	private CalculatorFactory makeCalculatorFactory() {
		return new CalculatorFactory();
	}

	private SimpleScheduleBuilder getInfiniteScheduleWithInterval(int seconds) {
		return SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(seconds);
	}
}
