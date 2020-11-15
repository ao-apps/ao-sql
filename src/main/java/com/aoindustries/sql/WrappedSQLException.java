/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2016, 2017, 2018, 2020  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of ao-sql.
 *
 * ao-sql is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ao-sql is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ao-sql.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.sql;

import com.aoindustries.lang.Throwables;
import com.aoindustries.util.ErrorPrinter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Wraps a <code>SQLException</code> to include its source SQL statement.
 *
 * @author  AO Industries, Inc.
 */
public class WrappedSQLException extends SQLException {

	private static final long serialVersionUID = 1884080138318429559L;

	final private String sqlString;

	public WrappedSQLException(
		SQLException cause,
		PreparedStatement pstmt
	) {
		this(cause, pstmt.toString());
	}

	public WrappedSQLException(
		SQLException cause,
		String sqlString
	) {
		this(
			cause.getMessage() + System.lineSeparator() + "SQL:" + System.lineSeparator() + sqlString,
			cause.getSQLState(),
			cause.getErrorCode(),
			cause,
			sqlString
		);
	}

	public WrappedSQLException(String reason, String sqlState, int vendorCode, Throwable cause, String sqlString) {
		super(reason, sqlState, vendorCode, cause);
		this.sqlString = sqlString;
	}

	public String getSqlString() {
		return sqlString;
	}

	static {
		ErrorPrinter.addCustomMessageHandler((Throwable thrown, Appendable out, int indent) -> {
			if(thrown instanceof WrappedSQLException) {
				ErrorPrinter.CustomMessageHandler.printMessage(out, indent, "SQL Statement.....: ", ((WrappedSQLException)thrown).getSqlString());
			}
		});
		Throwables.registerSurrogateFactory(WrappedSQLException.class, (template, cause) ->
			new WrappedSQLException(
				template.getMessage(),
				template.getSQLState(),
				template.getErrorCode(),
				cause,
				template.sqlString
			)
		);
	}
}
