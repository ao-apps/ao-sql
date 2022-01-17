/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2016, 2017, 2018, 2020, 2021, 2022  AO Industries, Inc.
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
 * along with ao-sql.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.aoapps.sql;

import com.aoapps.lang.Throwables;
import com.aoapps.lang.util.ErrorPrinter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Wraps a <code>SQLException</code> to include its source SQL statement.
 *
 * @deprecated  We have been increasingly using custom subtypes of {@link SQLException} to represent different
 *              conditions, such as when no row is returned.  This allows the simple use of standard catch blocks to
 *              handle different conditions accordingly.  However, wrapping the original exception defeats this, as it
 *              just become a generic {@link SQLException}, albeit with SQL STATE and such copied from the original
 *              exception.
 *              <p>
 *              The primary purpose of wrapping exceptions was to attach the SQL statement being processed at the time
 *              of failure.  This statement is only used in error reporting, specifically via {@link ErrorPrinter}.  We
 *              have created a new way to accomplish this, without altering or wrapping any exceptions:
 *              {@linkplain ErrorPrinter#addSQL(java.lang.Throwable, java.sql.PreparedStatement) register an exception and corresponding SQL statement}
 *              directly with {@link ErrorPrinter}, which will then automatically be included in error reports.
 *              </p>
 *
 * @author  AO Industries, Inc.
 */
@Deprecated
public class WrappedSQLException extends SQLException {

	private static final long serialVersionUID = 1884080138318429559L;

	private final String sqlString;

	/**
	 * @deprecated  Please use {@link ErrorPrinter#addSQL(java.lang.Throwable, java.sql.PreparedStatement)} instead of
	 *              wrapping exceptions.
	 */
	@Deprecated
	public WrappedSQLException(
		SQLException cause,
		PreparedStatement pstmt
	) {
		this(cause, pstmt.toString());
	}

	/**
	 * @deprecated  Please use {@link ErrorPrinter#addSQL(java.lang.Throwable, java.lang.String)} instead of
	 *              wrapping exceptions.
	 */
	@Deprecated
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

	/**
	 * @deprecated  Please use {@link ErrorPrinter#addSQL(java.lang.Throwable, java.lang.String)} instead of
	 *              wrapping exceptions.
	 */
	@Deprecated
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
