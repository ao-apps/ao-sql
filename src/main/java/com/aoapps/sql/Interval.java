/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2014, 2016, 2020, 2021  AO Industries, Inc.
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

import com.aoapps.lang.Strings;
import java.io.Serializable;

/**
 * Encapsulates an interval in seconds.
 *
 * @author  AO Industries, Inc.
 */
public final class Interval implements Serializable, Comparable<Interval> {

	private static final long serialVersionUID = 1;

	private final long interval;

	public Interval(long seconds) {
		this.interval = seconds;
	}

	public Interval(long minutes, long seconds) {
		this(
			minutes * 60
			+ seconds
		);
	}

	public Interval(long hours, long minutes, long seconds) {
		this(
			hours * 3600
			+ minutes * 60
			+ seconds
		);
	}

	/**
	 * Gets the interval in seconds.
	 */
	public long getInterval() {
		return interval;
	}

	@Override
	public String toString() {
		return toString(interval);
	}

	public static String toString(long interval) {
		return Strings.getDecimalTimeLengthString(interval*1000, false);
	}

	@Override
	public int compareTo(Interval o) {
		if(interval<o.interval) return -1;
		if(interval>o.interval) return 1;
		return 0;
	}
}
