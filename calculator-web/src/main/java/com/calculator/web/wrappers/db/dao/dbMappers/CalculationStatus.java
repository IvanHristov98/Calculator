package com.calculator.web.wrappers.db.dao.dbMappers;

public enum CalculationStatus {
	PENDING(1),
	COMPLETE(2);
	
	private final int value;
	
	CalculationStatus(final int value) {
		this.value = value;
	}
	
	public int getStatusValue() {
		return value;
	}
}
