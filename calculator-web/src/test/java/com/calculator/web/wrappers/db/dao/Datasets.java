package com.calculator.web.wrappers.db.dao;

public enum Datasets {
	EMPTY_DATA_SET("/datasets/emptyDataset.xml"),
	PENDING_SINGLE_ITEM_DATA_SET("/datasets/pendingSingleItemDataset.xml"),
	PENDING_TWO_ITEMS_DATA_SET("/datasets/pendingTwoItemsDataset.xml"),
	PENDING_WRONG_SINGLE_ITEM_DATA_SET("/datasets/pendingWrongSingleItemDataset.xml");
	
	final String path;
	
	Datasets(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
