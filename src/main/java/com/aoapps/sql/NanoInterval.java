/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2008, 2011, 2013, 2016, 2020, 2021  AO Industries, Inc.
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

import com.aoapps.lang.i18n.Resources;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * Encapsulates an interval in nanoseconds.
 *
 * @author  AO Industries, Inc.
 */
public final class NanoInterval implements Serializable, Comparable<NanoInterval> {

	private static final Resources RESOURCES = Resources.getResources(ResourceBundle::getBundle, NanoInterval.class);

	private static final long serialVersionUID = 1;

	private final long intervalNanos;

	public NanoInterval(long intervalNanos) {
		this.intervalNanos = intervalNanos;
	}

	/**
	 * Gets the interval in nanoseconds.
	 */
	public long getIntervalNanos() {
		return intervalNanos;
	}

	@Override
	public String toString() {
		return toString(intervalNanos);
	}

	public static String toString(long intervalNanos) {
		if(intervalNanos < 1000000) return RESOURCES.getMessage("toString.micro", BigDecimal.valueOf(intervalNanos, 3));
		if(intervalNanos < 1000000000) return RESOURCES.getMessage("toString.milli", BigDecimal.valueOf(intervalNanos/1000, 3));
		return RESOURCES.getMessage("toString.second", BigDecimal.valueOf(intervalNanos/1000000, 3));
	}

	@Override
	public int compareTo(NanoInterval o) {
		if(intervalNanos<o.intervalNanos) return -1;
		if(intervalNanos>o.intervalNanos) return 1;
		return 0;
	}
}
