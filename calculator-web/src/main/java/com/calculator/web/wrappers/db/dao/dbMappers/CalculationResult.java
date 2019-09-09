package com.calculator.web.wrappers.db.dao.dbMappers;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@Table(name="calculation_results")
@NamedQuery(name="CalculationResult.findAll", query="SELECT e FROM CalculationResult e")
public class CalculationResult {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="request_id") private Integer requestId;
	
	@Column(name="expression") private String expression;
	@Column(name="moment") private Timestamp moment;
	@Column(name="evaluation") private Double evaluation;
	@Column(name="message") private String message;
	@Column(name="status") private Integer status;
	
	public Integer getRequestId() {
		return requestId;
	}
	
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	
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
	
	public Double getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(Double result) {
		this.evaluation = result;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setStatus(CalculationStatus status) {
		this.status = status.getStatusValue();
	}
	
	public Integer getStatus() {
		return status;
	}
}
