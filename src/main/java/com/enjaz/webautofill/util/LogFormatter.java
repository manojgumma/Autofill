/**
 * LogFormatter.java
 */
package com.enjaz.webautofill.util;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
	@Override
    public String format(LogRecord record) {
		return new Date(record.getMillis())+"\nClass Name : " +record.getLoggerName()+"\n"         
                +record.getMessage()+"\n";
    }
}
