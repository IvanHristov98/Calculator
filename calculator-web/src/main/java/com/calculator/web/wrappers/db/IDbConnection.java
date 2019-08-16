package com.calculator.web.wrappers.db;

import java.sql.Connection;

public interface IDbConnection {
	public Connection getConnection();
}
