package com.calculator.web.wrappers.db.dao.dbMappers;

import static com.calculator.web.wrappers.db.dao.dbMappers.tables.CalculationResultsTable.*;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name=TABLE_NAME)
@NamedQueries({
	@NamedQuery(name="CalculationResult.find", query="SELECT e FROM CalculationResult e WHERE e.email = :" + EMAIL + " AND e.requestId = :" + REQUEST_ID),
	@NamedQuery(name="CalculationResult.findAll", query="SELECT e FROM CalculationResult e WHERE e.email = :" + EMAIL),
	@NamedQuery(name="CalculationResult.findPendingItems", query="SELECT e FROM CalculationResult e WHERE e.status = :" + STATUS),
})
public class CalculationResult {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name=REQUEST_ID) private Integer requestId;
	
	@Column(name=EXPRESSION) private String expression;
	@Column(name=MOMENT) private Timestamp moment;
	@Column(name=EVALUATION) private Double evaluation;
	@Column(name=MESSAGE) private String message;
	@Column(name=STATUS) private Integer status;
	@Column(name=EMAIL) private String email;
	
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
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
}
