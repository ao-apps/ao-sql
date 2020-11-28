/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2020  AO Industries, Inc.
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

import com.aoindustries.util.concurrent.ExecutionExceptions;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * Utilities for working with {@link SQLException}.
 *
 * @see  ExecutionExceptions
 */
final public class SQLExceptions {

	/**
	 * Make no instances.
	 */
	private SQLExceptions() {}

	/**
	 * Wraps and throws an {@link ExecutionException} when its {@linkplain ExecutionException#getCause() cause} is a
	 * {@link SQLException}.
	 * <p>
	 * When an {@link ExecutionException} occurs, unwrapping the {@linkplain ExecutionException#getCause() cause} may
	 * lose important stack trace information, since the cause is likely processed on a different thread and will not
	 * have the full caller stack trace.
	 * </p>
	 * <p>
	 * Furthermore, it is desirable to be able to maintain expected exception types.  This wrapping will help maintain
	 * exception types while not losing critical stack trace information.
	 * </p>
	 * 
	 * <p>
	 * This is expected to typically used within a catch block, to maintain exception types:
	 * </p>
	 * <pre>try {
	 *   …
	 *   return future.get();
	 * } catch(ExecutionException ee) {
	 *   wrapAndThrowSQLException(ee);
	 *   throw ee;
	 * }</pre>
	 *
	 * @throws  WrappedSQLException  When cause is an instance of {@link WrappedSQLException}, throws {@code ee} wrapped via
	 *                               {@link WrappedSQLException#WrappedSQLException(java.lang.String, java.lang.String, int, java.lang.Throwable, java.lang.String)}
	 * @throws  SQLException  When cause is an instance of {@link SQLException}, throws {@code ee} wrapped via
	 *                        {@link SQLException#SQLException(java.lang.String, java.lang.String, int, java.lang.Throwable)}.
	 *
	 * @see  ExecutionExceptions#wrapAndThrow(java.util.concurrent.ExecutionException, java.lang.Class, java.util.function.BiFunction)
	 */
	public static <X extends Throwable> void wrapAndThrowSQLException(ExecutionException ee) throws WrappedSQLException, SQLException {
		ExecutionExceptions.wrapAndThrow(ee, SQLException.class, SQLException::new);
	}
}
