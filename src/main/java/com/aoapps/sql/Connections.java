/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2020, 2021  AO Industries, Inc.
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
package com.aoapps.sql;

import java.sql.Connection;

/**
 * Utilities for working with {@link Connection}.
 *
 * @author  AO Industries, Inc.
 */
public class Connections {

	private Connections() {}

	/**
	 * The default {@linkplain Connection#getTransactionIsolation() transaction isolation}.
	 * It is expected that all new {@link Connection} will have this as a default, as well
	 * as all pooled connections be reset to this default.
	 *
	 * @see  Connection#TRANSACTION_READ_COMMITTED
	 */
	public static final int DEFAULT_TRANSACTION_ISOLATION = Connection.TRANSACTION_READ_COMMITTED;
}
