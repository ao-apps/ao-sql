/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2008, 2011, 2013, 2016, 2020  AO Industries, Inc.
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

import com.aoindustries.i18n.Resources;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Encapsulates an interval in milliseconds.
 *
 * @author  AO Industries, Inc.
 */
final public class MilliInterval implements Serializable, Comparable<MilliInterval> {

	private static final Resources RESOURCES = Resources.getResources(MilliInterval.class);

	private static final long serialVersionUID = 1;

	final private long milliInterval;

	public MilliInterval(long milliInterval) {
		this.milliInterval = milliInterval;
	}

	/**
	 * Gets the interval in milliseconds.
	 */
	public long getIntervalMillis() {
		return milliInterval;
	}

	@Override
	public String toString() {
		return toString(milliInterval);
	}

	public static String toString(long interval) {
		return RESOURCES.getMessage("toString", BigDecimal.valueOf(interval, 3));
	}

	@Override
	public int compareTo(MilliInterval o) {
		if(milliInterval<o.milliInterval) return -1;
		if(milliInterval>o.milliInterval) return 1;
		return 0;
	}
}
