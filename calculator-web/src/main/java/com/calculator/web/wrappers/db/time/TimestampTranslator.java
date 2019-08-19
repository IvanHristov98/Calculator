package com.calculator.web.wrappers.db.time;

import java.sql.Timestamp;
import java.time.Instant;

public class TimestampTranslator {
	
	public TimestampTranslator() {
	}
	
	public Instant toInstant(Timestamp timestamp) {
		return timestamp.toInstant();
	}
	
	public Timestamp toTimestamp(Instant instant) {
		return new Timestamp(instant.toEpochMilli());
	}
}
