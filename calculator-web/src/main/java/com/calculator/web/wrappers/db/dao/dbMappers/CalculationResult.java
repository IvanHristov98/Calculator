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
	@Column(name="date") private Timestamp date;
	@Column(name="result") private double result;
	@Column(name="message") private String message;
	
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
