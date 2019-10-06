package com.calculator.web.tests;

public enum Datasets {
	EMPTY_DATA_SET("/datasets/emptyDataset.xml"),
	PENDING_SINGLE_ITEM_DATA_SET("/datasets/pendingSingleItemDataset.xml"),
	COMPLETED_SINGLE_ITEM_DATA_SET("/datasets/completedSingleItemDataset.xml"),
	PENDING_POPULATED_DATA_SET("/datasets/pendingPopulatedDataset.xml"),
	WRONG_EXPRESSION_DATA_SET("/datasets/wrongExpressionDataset.xml");
	
	private final String datasetPath;
	
	Datasets(String datasetPath) {
		this.datasetPath = datasetPath;
	}
	
	public String getPath() {
		return datasetPath;
	}
}
