/*
 * ao-sql - SQL and JDBC utilities.
 * Copyright (C) 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2013, 2016, 2018, 2019, 2020, 2021  AO Industries, Inc.
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
import com.aoapps.lang.util.CalendarUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.Function;

/**
 * SQL utilities.
 *
 * @author  AO Industries, Inc.
 */
public class SQLUtility {

	private SQLUtility() {
	}

	/**
	 * Enables the printing of unicode based tables instead of ASCII.
	 * This is hard-coded for now, but this decision might be affected
	 * later when readline is integrated.  There may be a way to automatically
	 * determine which table format is best instead of assuming UNICODE.
	 * <p>
	 * When true, is similar to <code>psql</code> with <code>\pset linestyle unicode</code>
	 * and <code>\pset border 2</code>
	 * </p>
	 * <p>
	 * When false, is similar to <code>psql</code> with <code>\pset linestyle old-ascii</code>
	 * and <code>\pset border 1</code>
	 * </p>
	 */
	private static final boolean UNICODE_TABLES = true;

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatDate(java.util.Calendar)
	 */
	public static String formatDate(long time, TimeZone timeZone) {
		GregorianCalendar gcal = timeZone == null ? new GregorianCalendar() : new GregorianCalendar(timeZone);
		gcal.setTimeInMillis(time);
		return CalendarUtils.formatDate(gcal);
	}

	/**
	 * @see  CalendarUtils#formatDate(java.util.Calendar)
	 */
	public static String formatDate(long time) {
		return formatDate(time, null);
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatDate(java.util.Calendar)
	 */
	public static String formatDate(Long time, TimeZone timeZone) {
		return time == null ? null : formatDate(time.longValue(), timeZone);
	}

	/**
	 * @see  CalendarUtils#formatDate(java.util.Calendar)
	 */
	public static String formatDate(Long time) {
		return time == null ? null : formatDate(time.longValue());
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatDate(java.util.Calendar)
	 */
	public static String formatDate(java.util.Date date, TimeZone timeZone) {
		return date == null ? null : formatDate(date.getTime(), timeZone);
	}

	/**
	 * @see  CalendarUtils#formatDate(java.util.Calendar)
	 */
	public static String formatDate(java.util.Date date) {
		return date == null ? null : formatDate(date.getTime());
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#parseDate(java.lang.String, java.util.TimeZone)
	 */
	public static Date parseDate(String yyyy_mm_dd, TimeZone timeZone) throws IllegalArgumentException {
		if(yyyy_mm_dd == null) return null;
		return new Date(CalendarUtils.parseDate(yyyy_mm_dd, timeZone).getTimeInMillis());
	}

	/**
	 * @see  CalendarUtils#parseDate(java.lang.String)
	 */
	public static Date parseDate(String yyyy_mm_dd) throws IllegalArgumentException {
		return parseDate(yyyy_mm_dd, null);
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatDateTime(java.util.Calendar)
	 */
	public static String formatDateTime(long time, TimeZone timeZone) {
		GregorianCalendar gcal = timeZone == null ? new GregorianCalendar() : new GregorianCalendar(timeZone);
		gcal.setTimeInMillis(time);
		return CalendarUtils.formatDateTime(gcal);
	}

	/**
	 * @see  CalendarUtils#formatDateTime(java.util.Calendar)
	 */
	public static String formatDateTime(long time) {
		return formatDateTime(time, null);
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatDateTime(java.util.Calendar)
	 */
	public static String formatDateTime(Long time, TimeZone timeZone) {
		return time == null ? null : formatDateTime(time.longValue(), timeZone);
	}

	/**
	 * @see  CalendarUtils#formatDateTime(java.util.Calendar)
	 */
	public static String formatDateTime(Long time) {
		return time == null ? null : formatDateTime(time.longValue());
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatDateTime(java.util.Calendar)
	 */
	public static String formatDateTime(java.util.Date date, TimeZone timeZone) {
		return date == null ? null : formatDateTime(date.getTime(), timeZone);
	}

	/**
	 * @see  CalendarUtils#formatDateTime(java.util.Calendar)
	 */
	public static String formatDateTime(java.util.Date date) {
		return date == null ? null : formatDateTime(date.getTime());
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#parseDateTime(java.lang.String, java.util.TimeZone, com.aoapps.lang.util.CalendarUtils.DateTimeProducer)
	 */
	public static Timestamp parseDateTime(String dateTime, TimeZone timeZone) throws IllegalArgumentException {
		if(dateTime == null) return null;
		return CalendarUtils.parseDateTime(
			dateTime,
			timeZone,
			(GregorianCalendar gcal, int nanos) -> {
				long millis = gcal.getTimeInMillis();
				long seconds = millis / 1000;
				if((millis % 1000) < 0) seconds--;
				return newTimestamp(seconds, nanos);
			}
		);
	}

	/**
	 * @see  CalendarUtils#parseDateTime(java.lang.String)
	 */
	public static Timestamp parseDateTime(String dateTime) throws IllegalArgumentException {
		return parseDateTime(dateTime, null);
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatTime(java.util.Calendar)
	 */
	public static String formatTime(long time, TimeZone timeZone) {
		GregorianCalendar gcal = timeZone == null ? new GregorianCalendar() : new GregorianCalendar(timeZone);
		gcal.setTimeInMillis(time);
		return CalendarUtils.formatTime(gcal);
	}

	/**
	 * @see  CalendarUtils#formatTime(java.util.Calendar)
	 */
	public static String formatTime(long time) {
		return formatTime(time, null);
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatTime(java.util.Calendar)
	 */
	public static String formatTime(Long time, TimeZone timeZone) {
		return time == null ? null : formatTime(time.longValue(), timeZone);
	}

	/**
	 * @see  CalendarUtils#formatTime(java.util.Calendar)
	 */
	public static String formatTime(Long time) {
		return time == null ? null : formatTime(time.longValue());
	}

	/**
	 * @param timeZone  The time zone to use or {@code null} to use the default time zone
	 *
	 * @see  CalendarUtils#formatTime(java.util.Calendar)
	 */
	public static String formatTime(java.util.Date date, TimeZone timeZone) {
		return date == null ? null : formatTime(date.getTime(), timeZone);
	}

	/**
	 * @see  CalendarUtils#formatTime(java.util.Calendar)
	 */
	public static String formatTime(java.util.Date date) {
		return date == null ? null : formatTime(date.getTime());
	}

	/**
	 * Converts a number of pennies into decimal representation.
	 */
	public static String formatDecimal2(int pennies) {
		StringBuilder sb = new StringBuilder(12);
		if(pennies < 0) {
			sb.append('-');
			pennies = -pennies;
		}
		sb
			.append(pennies / 100)
			.append('.')
		;
		pennies %= 100;
		if(pennies < 10) sb.append('0');
		return sb
			.append(pennies)
			.toString()
		;
	}

	/**
	 * Converts a number of pennies into decimal representation.
	 */
	public static String formatDecimal2(long pennies) {
		StringBuilder sb = new StringBuilder(21);
		if(pennies < 0) {
			sb.append('-');
			pennies = -pennies;
		}
		sb
			.append(pennies / 100)
			.append('.')
		;
		int i = (int)(pennies % 100);
		if(i < 10) sb.append('0');
		return sb
			.append(i)
			.toString()
		;
	}

	/**
	 * Gets the number of pennies represented by a <code>String</code> containing a decimal(?,2) type.
	 */
	// TODO: Parse in a way with less/no internal string concatenation
	public static int parseDecimal2(String decimal2) {
		// Get the sign first, treat as negative, then apply the sign
		boolean isNegative;
		if(decimal2.length()>0 && decimal2.charAt(0)=='-') {
			isNegative=true;
			decimal2=decimal2.substring(1);
		} else isNegative=false;

		// Add zero to beginning if starts with .
		if(decimal2.length()>0 && decimal2.charAt(0)=='.') decimal2='0'+decimal2;

		// Allow for incomplete data like 2, 2., and 2.3
		if(decimal2.indexOf('.')==-1) decimal2 += ".00";
		else if(decimal2.charAt(decimal2.length()-1)=='.') decimal2 += "00";
		else if(decimal2.length()>=2 && decimal2.charAt(decimal2.length()-2)=='.') decimal2 += '0';

		int len = decimal2.length();
		int dollars = Integer.parseInt(decimal2.substring(0, len - 3));
		int pennies = Integer.parseInt(decimal2.substring(len - 2));
		long result = (isNegative?-1l:1l)*(dollars * 100l + pennies);
		if(result<Integer.MIN_VALUE || result>Integer.MAX_VALUE) throw new NumberFormatException("Out of range during conversion");
		return (int)result;
	}

	/**
	 * Gets the number of pennies represented by a <code>String</code> containing a decimal(?,2) type.
	 */
	// TODO: Parse in a way with less/no internal string concatenation
	public static long parseLongDecimal2(String decimal2) {
		// Get the sign first, treat as negative, then apply the sign
		boolean isNegative;
		if(decimal2.length()>0 && decimal2.charAt(0)=='-') {
			isNegative=true;
			decimal2=decimal2.substring(1);
		} else isNegative=false;

		// Add zero to beginning if starts with .
		if(decimal2.length()>0 && decimal2.charAt(0)=='.') decimal2='0'+decimal2;

		// Allow for incomplete data like 2, 2., and 2.3
		if(decimal2.indexOf('.')==-1) decimal2 += ".00";
		else if(decimal2.charAt(decimal2.length()-1)=='.') decimal2 += "00";
		else if(decimal2.length()>=2 && decimal2.charAt(decimal2.length()-2)=='.') decimal2 += '0';

		int len = decimal2.length();
		long dollars = Long.parseLong(decimal2.substring(0, len - 3));
		int pennies = Integer.parseInt(decimal2.substring(len - 2));
		return (isNegative?-1:1)*(dollars * 100 + pennies);
	}

	/**
	 * Converts a number of millis into decimal representation.
	 */
	public static String formatDecimal3(int millis) {
		StringBuilder sb = new StringBuilder(10);
		if(millis < 0) {
			sb.append('-');
			millis = -millis;
		}
		sb
			.append(millis / 1000)
			.append('.')
		;
		millis %= 1000;
		if(millis < 10) sb.append("00");
		else if(millis < 100) sb.append('0');
		return sb
			.append(millis)
			.toString()
		;
	}

	/**
	 * Converts a number of millis into decimal representation.
	 */
	public static String formatDecimal3(long millis) {
		StringBuilder sb = new StringBuilder(10);
		if(millis < 0) {
			sb.append('-');
			millis = -millis;
		}
		sb
			.append(millis / 1000)
			.append('.')
		;
		millis %= 1000;
		if(millis < 10) sb.append("00");
		else if(millis < 100) sb.append('0');
		return sb
			.append(millis)
			.toString()
		;
	}

	/**
	 * Gets the number of millis represented by a <code>String</code> containing a decimal(?,3) type.
	 */
	// TODO: Parse in a way with less/no internal string concatenation
	public static int parseDecimal3(String decimal3) {
		// Get the sign first, treat as negative, then apply the sign
		boolean isNegative;
		if (decimal3.length() > 0 && decimal3.charAt(0) == '-') {
			isNegative = true;
			decimal3 = decimal3.substring(1);
		} else isNegative = false;

		// Add zero to beginning if starts with .
		if(decimal3.length()>0 && decimal3.charAt(0)=='.') decimal3='0'+decimal3;

		// Allow for incomplete data like 2, 2., 2.3, and 2.34
		if(decimal3.indexOf('.')==-1) decimal3 += ".000";
		else if(decimal3.charAt(decimal3.length()-1)=='.') decimal3 += "000";
		else if(decimal3.length()>=2 && decimal3.charAt(decimal3.length()-2)=='.') decimal3 += "00";
		else if(decimal3.length()>=3 && decimal3.charAt(decimal3.length()-3)=='.') decimal3 += '0';

		int len = decimal3.length();
		int whole = Integer.parseInt(decimal3.substring(0, len - 4));
		int millis = Integer.parseInt(decimal3.substring(len - 3));
		long result = (isNegative?-1L:1L)*(whole * 1000L + millis);
		if(result<Integer.MIN_VALUE || result>Integer.MAX_VALUE) throw new NumberFormatException("Out of range during conversion");
		return (int)result;
	}

	/**
	 * Gets the number of millis represented by a <code>String</code> containing a decimal(?,3) type.
	 */
	// TODO: Parse in a way with less/no internal string concatenation
	public static long parseLongDecimal3(String decimal3) {
		// Get the sign first, treat as negative, then apply the sign
		boolean isNegative;
		if (decimal3.length() > 0 && decimal3.charAt(0) == '-') {
			isNegative = true;
			decimal3 = decimal3.substring(1);
		} else isNegative = false;

		// Add zero to beginning if starts with .
		if(decimal3.length()>0 && decimal3.charAt(0)=='.') decimal3='0'+decimal3;

		// Allow for incomplete data like 2, 2., 2.3, and 2.34
		if(decimal3.indexOf('.')==-1) decimal3 += ".000";
		else if(decimal3.charAt(decimal3.length()-1)=='.') decimal3 += "000";
		else if(decimal3.length()>=2 && decimal3.charAt(decimal3.length()-2)=='.') decimal3 += "00";
		else if(decimal3.length()>=3 && decimal3.charAt(decimal3.length()-3)=='.') decimal3 += '0';

		int len = decimal3.length();
		long whole = Long.parseLong(decimal3.substring(0, len - 4));
		int millis = Integer.parseInt(decimal3.substring(len - 3));
		return (isNegative?-1L:1L)*(whole * 1000L + millis);
	}

	private static final String EOL = System.lineSeparator();

	/**
	 * Gets the width for a string, handling newlines.
	 */
	private static int getWidth(String value) {
		int widest = 0;
		if(value != null) {
			int width = 0;
			for (int i = 0, len = value.length(), codePoint; i < len; i += Character.charCount(codePoint)) {
				codePoint = value.codePointAt(i);
				if(codePoint != '\r') {
					if(codePoint == '\n') {
						if(width > widest) widest = width;
						width = 0;
					} else {
						width++;
					}
				}
			}
			if(width > widest) widest = width;
		}
		return widest;
	}

	/**
	 * Prints a single row of interactive output
	 *
	 * @param  alignRights Will print all cells centered when this is {@code null} (used for header row)
	 */
	private static void printRow(
		Object[] row,
		Appendable out,
		boolean[] alignRights,
		String[] toStrings,
		int[] lineCounts,
		int[] lineValueIndexes,
		int[] widest
	) throws IOException {
		int numCols = lineCounts.length;

		// Figure out how many lines of output this row will be
		int maxLineCount = 1;
		for(int col = 0; col < numCols; col++) {
			int lineCount = 1;
			String toString = Objects.toString(row[col], null);
			if(toString != null) {
				for(int i = 0, len = toString.length(); i < len; i++) {
					if(toString.charAt(i) == '\n') lineCount++;
				}
			}
			toStrings[col] = toString;
			lineCounts[col] = lineCount;
			lineValueIndexes[col] = 0;
			if(lineCount > maxLineCount) maxLineCount = lineCount;
		}

		StringBuilder cell = new StringBuilder();
		for(int line = 0; line < maxLineCount; line++) {
			if(UNICODE_TABLES) {
				out.append("│ ");
			} else {
				out.append(' ');
			}
			for(int col = 0; col < numCols; col++) {
				int width = widest[col];
				String toString = line < lineCounts[col] ? toStrings[col] : null;
				int printed;
				boolean cellNewline = false;
				if(toString == null) {
					printed = 0;
				} else {
					int toStringLen = toString.length();
					if(toStringLen == 0) {
						printed = 0;
					} else {
						// Print just this line of the output
						int pos = lineValueIndexes[col];
						cell.setLength(0);
						int cellWidth = 0;
						while(pos < toStringLen) {
							int codePoint = toString.codePointAt(pos);
							pos += Character.charCount(codePoint);
							if(codePoint == '\r') {
								// Skip
							} else if(codePoint == '\n') {
								cellNewline = true;
								break;
							} else {
								cell.appendCodePoint(codePoint);
								cellWidth++;
							}
						}
						lineValueIndexes[col] = pos;
						if(cellWidth == 0) {
							printed = 0;
						} else {
							if(alignRights == null) {
								// Print centered
								int before = (width - cellWidth) / 2;
								for(int d = 0; d < before; d++) {
									out.append(' ');
								}
								out.append(cell);
								printed = before + cellWidth;
							} else if(alignRights[col]) {
								// Right align
								for(int e = cellWidth; e < width; e++) {
									out.append(' ');
								}
								out.append(cell);
								printed = width;
							} else {
								// Left align
								out.append(cell);
								printed = cellWidth;
							}
						}
					}
				}
				boolean hasMoreColumns = col < (numCols - 1);
				if(UNICODE_TABLES || hasMoreColumns) {
					for(int e = printed; e < width; e++) {
						out.append(' ');
					}
					out.append(UNICODE_TABLES && cellNewline ? '↵' : ' ');
					out.append(UNICODE_TABLES ? '│' : (line < lineCounts[col + 1]) ? '|' : ' ');
					if(hasMoreColumns) out.append(' ');
				}
			}
			if(UNICODE_TABLES && numCols == 0) out.append(" │");
			out.append(EOL);
		}
	}

	/**
	 * Prints a table.
	 *
	 * @param titles  Optional titles to display
	 *
	 * @param rows  Iterated once in non-interactive mode.
	 *              Iterated twice in interactive mode (first to find widest columns, then to display output).
	 *              Must provide consistent output when iterated twice for interactive mode.
	 */
	public static void printTable(Object[] titles, Iterable<? extends Object[]> rows, Appendable out, boolean isInteractive, boolean[] alignRights) throws IOException {
		int numCols = alignRights.length;
		if(titles != null && titles.length != numCols) throw new IllegalArgumentException("Wrong number of titles: " + titles.length + " != " + numCols);
		if(isInteractive) {
			// Find the widest for each column, taking the line wraps into account and skipping the '\r' characters
			int[] widest = new int[numCols];
			// Titles first
			if(titles != null) {
				for(int col = 0; col < numCols; col++) {
					widest[col] = getWidth(Objects.toString(titles[col], null));
				}
			}
			// Then rows
			for(Object[] row : rows) {
				if(row.length != numCols) throw new IllegalArgumentException("Wrong number of columns in row: " + row.length + " != " + numCols);
				for(int col = 0; col < numCols; col++) {
					int width = getWidth(Objects.toString(row[col], null));
					if(width > widest[col]) widest[col] = width;
				}
			}

			// Arrays reused for each row
			String[] toStrings = new String[numCols];
			int[] lineCounts = new int[numCols];
			int[] lineValueIndexes = new int[numCols];

			if(UNICODE_TABLES) {
				// Write top row
				out.append("┌─");
				for(int c = 0; c < numCols; c++) {
					if(c > 0) out.append("─┬─");
					int width = widest[c];
					for(int d = 0; d < width; d++) {
						out.append('─');
					}
				}
				out.append("─┐");
				out.append(EOL);
			}
			// The title is printed centered in its place
			if(titles != null) {
				if(titles.length > 0) {
					printRow(titles, out, null, toStrings, lineCounts, lineValueIndexes, widest);
				}

				if(UNICODE_TABLES || titles.length > 0) {
					// Print the spacer line
					if(UNICODE_TABLES) {
						out.append("├─");
					} else {
						out.append('-');
					}
					for(int c = 0; c < numCols; c++) {
						if(c > 0) out.append(UNICODE_TABLES ? "─┼─" : "-+-");
						int width = widest[c];
						for(int d = 0; d < width; d++) {
							out.append(UNICODE_TABLES ? '─' : '-');
						}
					}
					if(UNICODE_TABLES) {
						out.append("─┤");
					} else {
						out.append('-');
					}
					out.append(EOL);
				}
			}

			// Print the values
			int rowCount = 0;
			for(Object[] row : rows) {
				if(row.length != numCols) throw new IllegalArgumentException("Wrong number of columns in row: " + row.length + " != " + numCols);
				rowCount++;
				printRow(row, out, alignRights, toStrings, lineCounts, lineValueIndexes, widest);
			}
			if(UNICODE_TABLES) {
				// Write bottom row
				out.append("└─");
				for(int c = 0; c < numCols; c++) {
					if(c > 0) out.append("─┴─");
					int width = widest[c];
					for(int d = 0; d < width; d++) {
						out.append('─');
					}
				}
				out.append("─┘");
				out.append(EOL);
			}
			out.append("(");
			out.append(rowCount == 0 ? "No" : Integer.toString(rowCount));
			out.append(rowCount == 1?" row)":" rows)");
			out.append(EOL);
			if(UNICODE_TABLES || rowCount > 0) out.append(EOL);
		} else {
			// This output simply prints stuff in a way that can be read back in, using single quotes

			// Print the values
			for(Object[] row : rows) {
				if(row.length != numCols) throw new IllegalArgumentException("Wrong number of columns in row: " + row.length + " != " + numCols);
				for(int col = 0; col < numCols; col++) {
					Object value = row[col];
					String toString = Objects.toString(value, "");
					int len = toString.length();

					boolean needsQuotes = len == 0; // Always quote the empty string
					if(!needsQuotes) {
						for(int e = 0; e < len; e++) {
							char ch = toString.charAt(e);
							if(ch <= ' ' || ch == '\\' || ch == '\'' || ch == '"') {
								needsQuotes = true;
								break;
							}
						}
					}

					if(needsQuotes) {
						out.append('\'');
						for(int e = 0; e < len; e++) {
							char ch = toString.charAt(e);
							if(ch == '\'') out.append('\\');
							out.append(ch);
						}
						out.append('\'');
					} else {
						out.append(toString);
					}
					if(col < (numCols - 1)) out.append(' ');
				}
				out.append(EOL);
			}
		}
	}

	/**
	 * @param values  One element for each row and column
	 *
	 * @deprecated  Please use {@link #printTable(java.lang.Object[], java.lang.Iterable, java.lang.Appendable, boolean, boolean[])}
	 *              when possible, as it may provide for more efficiency on large datasets.
	 */
	@Deprecated
	public static void printTable(Object[] titles, final Collection<Object> values, Appendable out, boolean isInteractive, final boolean[] alignRights) throws IOException {
		final int numCols = alignRights.length;
		printTable(
			titles,
			// Java 9: new Iterator<>
			() -> new Iterator<Object[]>() {
				private final Iterator<Object> valuesIter = values.iterator();

				@Override
				public boolean hasNext() {
					return valuesIter.hasNext();
				}

				@Override
				public Object[] next() {
					Object[] row = new Object[numCols];
					for(int i = 0; i < numCols; i++) {
						row[i] = valuesIter.next();
					}
					return row;
				}
			},
			out,
			isInteractive,
			alignRights
		);
	}

	/**
	 * @param values  One element for each row and column
	 *
	 * @deprecated  Please use {@link #printTable(java.lang.Object[], java.lang.Iterable, java.lang.Appendable, boolean, boolean[])}
	 *              when possible, as it may provide for more efficiency on large datasets.
	 */
	@Deprecated
	public static void printTable(Object[] titles, final Object[] values, Appendable out, boolean isInteractive, boolean[] alignRights) throws IOException {
		final int numCols = alignRights.length;
		printTable(
			titles,
			// Java 9: new Iterator<>
			() -> new Iterator<Object[]>() {
				private int index = 0;

				@Override
				public boolean hasNext() {
					return index < values.length;
				}

				@Override
				public Object[] next() throws NoSuchElementException {
					if(!hasNext()) throw new NoSuchElementException();
					Object[] row = new Object[numCols];
					System.arraycopy(values, index, row, 0, numCols);
					index += numCols;
					return row;
				}
			},
			out,
			isInteractive,
			alignRights
		);
	}

	/**
	 * The maximum number of seconds in a {@link Timestamp}.
	 */
	public static final long MAX_TIMESTAMP_SECONDS = Long.MAX_VALUE / 1000;

	/**
	 * The minimum number of seconds in a {@link Timestamp}.
	 */
	public static final long MIN_TIMESTAMP_SECONDS = Long.MIN_VALUE / 1000;

	/**
	 * Shared implementation of creating exception through reflection.
	 *
	 * @deprecated  This is only used by old, deprecated implementations.
	 */
	@Deprecated
	private static <X extends Throwable> X newException(Class<? extends X> xClass, String message) {
		try {
			try {
				return xClass.getConstructor(String.class).newInstance(message);
			} catch(InvocationTargetException e) {
				// Unwrap cause for more direct stack traces
				Throwable cause = e.getCause();
				throw (cause == null) ? e : cause;
			}
		} catch(Throwable t) {
			if(xClass.isInstance(t)) return xClass.cast(t);
			throw Throwables.wrap(
				t,
				IllegalArgumentException.class,
				cause -> new IllegalArgumentException(message, cause)
			);
		}
	}

	/**
	 * Converts a number of seconds and nanoseconds into a given {@link Timestamp}.
	 */
	public static <X extends Throwable> void toTimestamp(long seconds, int nanos, Timestamp ts, Function<? super String, ? extends X> xSupplier) throws X {
		String message;
		// Avoid underflow or overflow on conversion to millis
		if(seconds > MAX_TIMESTAMP_SECONDS) {
			message = "seconds overflow: " + seconds + " > " + MAX_TIMESTAMP_SECONDS;
		} else if(seconds < MIN_TIMESTAMP_SECONDS) {
			message = "seconds underflow: " + seconds + " < " + MAX_TIMESTAMP_SECONDS;
		} else {
			ts.setTime(seconds * 1000);
			ts.setNanos(nanos);
			return;
		}
		throw xSupplier.apply(message);
	}

	/**
	 * Converts a number of seconds and nanoseconds into a given {@link Timestamp}.
	 *
	 * @deprecated  Please use {@link #toTimestamp(long, int, java.sql.Timestamp, java.util.function.Function)},
	 *              typically with lambda constructor reference
	 */
	@Deprecated
	public static <X extends Throwable> void toTimestamp(long seconds, int nanos, Timestamp ts, Class<? extends X> xClass) throws X {
		toTimestamp(seconds, nanos, ts, message -> newException(xClass, message));
	}

	/**
	 * Converts a number of seconds and nanoseconds into a given {@link Timestamp}.
	 */
	public static void toTimestamp(long seconds, int nanos, Timestamp ts) {
		toTimestamp(seconds, nanos, ts, IllegalArgumentException::new);
	}

	/**
	 * Converts a number of seconds and nanoseconds into a new {@link Timestamp}.
	 */
	public static <X extends Throwable> Timestamp newTimestamp(long seconds, int nanos, Function<? super String, ? extends X> xSupplier) throws X {
		Timestamp ts = new Timestamp(0);
		toTimestamp(seconds, nanos, ts, xSupplier);
		return ts;
	}

	/**
	 * Converts a number of seconds and nanoseconds into a new {@link Timestamp}.
	 *
	 * @deprecated  Please use {@link #newTimestamp(long, int, java.util.function.Function)},
	 *              typically with lambda constructor reference
	 */
	@Deprecated
	public static <X extends Throwable> Timestamp newTimestamp(long seconds, int nanos, Class<? extends X> xClass) throws X {
		return newTimestamp(seconds, nanos, message -> newException(xClass, message));
	}

	/**
	 * Converts a number of seconds and nanoseconds into a new {@link Timestamp}.
	 */
	public static Timestamp newTimestamp(long seconds, int nanos) throws IllegalArgumentException {
		return newTimestamp(seconds, nanos, IllegalArgumentException::new);
	}

	/**
	 * Converts a number of seconds and nanoseconds into a new {@link UnmodifiableTimestamp}.
	 */
	public static <X extends Throwable> UnmodifiableTimestamp newUnmodifiableTimestamp(long seconds, int nanos, Function<? super String, ? extends X> xSupplier) throws X {
		String message;
		// Avoid underflow or overflow on conversion to millis
		if(seconds > MAX_TIMESTAMP_SECONDS) {
			message = "seconds overflow: " + seconds + " > " + MAX_TIMESTAMP_SECONDS;
		} else if(seconds < MIN_TIMESTAMP_SECONDS) {
			message = "seconds underflow: " + seconds + " < " + MAX_TIMESTAMP_SECONDS;
		} else {
			return new UnmodifiableTimestamp(seconds * 1000, nanos);
		}
		throw xSupplier.apply(message);
	}

	/**
	 * Converts a number of seconds and nanoseconds into a new {@link UnmodifiableTimestamp}.
	 *
	 * @deprecated  Please use {@link #newUnmodifiableTimestamp(long, int, java.util.function.Function)},
	 *              typically with lambda constructor reference
	 */
	@Deprecated
	public static <X extends Throwable> UnmodifiableTimestamp newUnmodifiableTimestamp(long seconds, int nanos, Class<? extends X> xClass) throws X {
		return newUnmodifiableTimestamp(seconds, nanos, message -> newException(xClass, message));
	}

	/**
	 * Converts a number of seconds and nanoseconds into a new {@link UnmodifiableTimestamp}.
	 */
	public static UnmodifiableTimestamp newUnmodifiableTimestamp(long seconds, int nanos) throws IllegalArgumentException {
		return newUnmodifiableTimestamp(seconds, nanos, IllegalArgumentException::new);
	}
}
