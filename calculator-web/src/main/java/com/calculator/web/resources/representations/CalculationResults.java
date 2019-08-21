package com.calculator.web.resources.representations;

import java.util.Collection;
import java.util.ArrayList;

public class CalculationResults {
	
	private Collection<CalculationResult> results;
	
	public CalculationResults() {
		this.results = new ArrayList<>();
	}

	public Collection<CalculationResult> getResults() {
		return results;
	}

	public void setResults(Collection<CalculationResult> results) {
		this.results = results;
	}
	
	public void addResult(CalculationResult result) {
		this.results.add(result);
	}
	
	public void removeResult(CalculationResult result) {
		this.results.remove(result);
	}
}
