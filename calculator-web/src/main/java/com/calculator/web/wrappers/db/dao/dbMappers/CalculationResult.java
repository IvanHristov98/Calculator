package com.calculator.web.wrappers.db.dao.dbMappers;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@Table(name="calculation_results")
@NamedQuery(name="CalculationResult.findAll", query="SELECT e FROM CalculationResult e")
public class CalculationResult {
	
	@Id @Column(name="expression") private String expression;
	@Column(name="moment") private Timestamp moment;
	@Column(name="evaluation") private double evaluation;
	@Column(name="message") private String message;
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public Timestamp getMoment() {
		return moment;
	}
	
	public void setMoment(Timestamp time) {
		this.moment = time;
	}
	
	public double getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(double result) {
		this.evaluation = result;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
