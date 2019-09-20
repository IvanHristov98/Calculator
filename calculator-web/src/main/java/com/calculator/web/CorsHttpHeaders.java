package com.calculator.web;

public enum CorsHttpHeaders {
	AccessControlAllowOrigin("Access-Control-Allow-Origin"),
	AccessControlAllowHeaders("Access-Control-Allow-Headers"),
	AccessControlAllowMethods("Access-Control-Allow-Methods");
	
	private final String headerName;
	
	CorsHttpHeaders(final String headerName) {
		this.headerName = headerName;
	}
	
	public String getHeaderName() {
		return headerName;
	}
}
