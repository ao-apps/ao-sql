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

import com.aoindustries.io.stream.Streamable;
import com.aoindustries.io.stream.StreamableInput;
import com.aoindustries.io.stream.StreamableOutput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Functions for sending SQL-related objects in support of {@link Streamable}.
 *
 * @author  AO Industries, Inc.
 */
public class SQLStreamables {

	private SQLStreamables() {
	}

	/**
	 * Reads a {@link Timestamp}, maintaining the full nanosecond precision.
	 * Time zone offset is not maintained.
	 * <p>
	 * See  {@link #writeTimestamp(java.sql.Timestamp, java.io.DataOutputStream)} for wire protocol details.
	 * </p>
	 */
	public static Timestamp readTimestamp(DataInputStream in) throws IOException {
		long seconds = in.readLong();
		int nanos = StreamableInput.readCompressedInt(in);
		return SQLUtility.newTimestamp(seconds, nanos, IOException::new);
	}

	/**
	 * Reads a possibly-{@code null} {@link Timestamp}.
	 *
	 * @see  #readTimestamp(java.io.DataInputStream)
	 */
	public static Timestamp readNullTimestamp(DataInputStream in) throws IOException {
		return in.readBoolean() ? readTimestamp(in) : null;
	}

	/**
	 * Reads an {@link UnmodifiableTimestamp}, maintaining the full nanosecond precision.
	 * Time zone offset is not maintained.
	 * <p>
	 * See  {@link #writeTimestamp(java.sql.Timestamp, java.io.DataOutputStream)} for wire protocol details.
	 * </p>
	 */
	public static UnmodifiableTimestamp readUnmodifiableTimestamp(DataInputStream in) throws IOException {
		long seconds = in.readLong();
		int nanos = StreamableInput.readCompressedInt(in);
		return SQLUtility.newUnmodifiableTimestamp(seconds, nanos, IOException::new);
	}

	/**
	 * Reads a possibly-{@code null} {@link UnmodifiableTimestamp}.
	 *
	 * @see  #readUnmodifiableTimestamp(java.io.DataInputStream)
	 */
	public static UnmodifiableTimestamp readNullUnmodifiableTimestamp(DataInputStream in) throws IOException {
		return in.readBoolean() ? readUnmodifiableTimestamp(in) : null;
	}

	/**
	 * Writes a {@link Timestamp}, maintaining the full nanosecond precision.
	 * Time zone offset is not maintained.
	 * <p>
	 * The wire protocol is {@link DataOutputStream#writeLong(long)} number of seconds followed
	 * by {@link StreamableOutput#writeCompressedInt(int) compressed int} number of nanoseconds.
	 * </p>
	 * <p>
	 * This is deliberately compatible with {@link Instant} that is part of Java 8.
	 * Once Java 8 is our minimum Java version, many uses of {@link Timestamp} will
	 * change to {@link Instant}.
	 * </p>
	 */
	public static void writeTimestamp(Timestamp ts, DataOutputStream out) throws IOException {
		// Java 1.8: Math.floorDiv
		long millis = ts.getTime();
		long seconds = millis / 1000;
		if((millis % 1000) < 0) seconds--;
		out.writeLong(seconds);
		assert StreamableOutput.MAX_COMPRESSED_INT_VALUE >= 999999999 : "All nano range (0 - 999999999) will fit in compressed ints";
		StreamableOutput.writeCompressedInt(ts.getNanos(), out);
	}

	/**
	 * Writes a possibly-{@code null} {@link Timestamp}.
	 *
	 * @see  #writeTimestamp(java.sql.Timestamp, java.io.DataOutputStream)
	 */
	public static void writeNullTimestamp(Timestamp ts, DataOutputStream out) throws IOException {
		out.writeBoolean(ts != null);
		if(ts != null) writeTimestamp(ts, out);
	}
}
