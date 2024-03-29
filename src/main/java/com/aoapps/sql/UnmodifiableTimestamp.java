/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2011, 2013, 2016, 2017, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

import java.sql.Timestamp;

/**
 * An unmodifiable Timestamp that may be used in return values and shared between threads.
 *
 * @author  AO Industries, Inc.
 */
public final class UnmodifiableTimestamp extends Timestamp implements Cloneable {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new UnmodifiableTimestamp from a Timestamp.
   * <ol>
   * <li>If the wrapped timestamp is null, returns null.</li>
   * <li>If the wrapped timestamp is already unmodifiable, returns it.</li>
   * <li>Otherwise returns a new timestamp with the same value.</li>
   * </ol>
   */
  public static UnmodifiableTimestamp valueOf(Timestamp other) {
    if (other == null) {
      return null;
    }
    if (other instanceof UnmodifiableTimestamp) {
      return (UnmodifiableTimestamp) other;
    }
    return new UnmodifiableTimestamp(other.getTime(), other.getNanos());
  }

  public UnmodifiableTimestamp(long time, int nanos) {
    super(time);
    super.setNanos(nanos);
  }

  public UnmodifiableTimestamp(long time) {
    super(time);
  }

  /**
   * @deprecated instead use the constructor <code>Timestamp(long millis)</code>
   */
  @Deprecated
  public UnmodifiableTimestamp(int year, int month, int date, int hour, int minute, int second, int nano) {
    super(year, month, date, hour, minute, second, nano);
  }

  /**
   * Return a copy of this object.
   */
  @Override
  public UnmodifiableTimestamp clone() {
    return (UnmodifiableTimestamp) super.clone();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated As of JDK version 1.1,
   */
  @Deprecated
  @Override
  public void setYear(int year) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated As of JDK version 1.1,
   */
  @Deprecated
  @Override
  public void setMonth(int month) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated As of JDK version 1.1,
   */
  @Deprecated
  @Override
  public void setDate(int month) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated As of JDK version 1.1,
   */
  @Deprecated
  @Override
  public void setHours(int month) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated As of JDK version 1.1,
   */
  @Deprecated
  @Override
  public void setMinutes(int month) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated As of JDK version 1.1,
   */
  @Deprecated
  @Override
  public void setSeconds(int month) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTime(long time) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setNanos(int n) {
    throw new UnsupportedOperationException();
  }
}
