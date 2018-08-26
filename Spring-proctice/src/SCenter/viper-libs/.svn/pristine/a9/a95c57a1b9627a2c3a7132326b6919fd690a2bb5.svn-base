package com.A.vm.util.converter.unmarshall;

import java.util.Calendar;

import com.A.xml.v4.DateTimeOrTimeRangeType;

/**
 * @author ebthomas
 * 
 */
public final class UnmarshallDate {

	/**
	 * Unmarshall Date.
	 */
	private UnmarshallDate() {
		super();
	}

	/**
	 * @param dateTimeOrTimeRangeType
	 *            source Date Time
	 * @return calendar based on DateTime or TimeRange.
	 */
	public static Calendar getCalendar(
			final DateTimeOrTimeRangeType dateTimeOrTimeRangeType) {

		if ((dateTimeOrTimeRangeType != null)
				&& (dateTimeOrTimeRangeType.isSetTime())) {
			return dateTimeOrTimeRangeType.getTime();
		}

		if ((dateTimeOrTimeRangeType != null)
				&& (dateTimeOrTimeRangeType.isSetStartTime())) {
			return dateTimeOrTimeRangeType.getStartTime();
		}

		// Default
		return Calendar.getInstance();
	}

	public static Calendar getCalendarDateTimeStart(
			final DateTimeOrTimeRangeType dateTimeOrTimeRangeType) {

		if (dateTimeOrTimeRangeType != null) {
			Calendar cal = dateTimeOrTimeRangeType.getDate();

			if (cal != null) {

				if (dateTimeOrTimeRangeType.getStartTime() != null) {
					Calendar calStartTime = dateTimeOrTimeRangeType
							.getStartTime();

					cal.set(Calendar.HOUR, calStartTime.get(Calendar.HOUR));
					cal.set(Calendar.MINUTE, calStartTime.get(Calendar.MINUTE));
					cal.set(Calendar.SECOND, calStartTime.get(Calendar.SECOND));
					cal.set(Calendar.MILLISECOND,
							calStartTime.get(Calendar.MILLISECOND));
					cal.set(Calendar.AM_PM, calStartTime.get(Calendar.AM_PM));
				} else if (dateTimeOrTimeRangeType.isSetTime()
						&& dateTimeOrTimeRangeType.getTime() != null) {
					Calendar calTime = dateTimeOrTimeRangeType.getTime();

					cal.set(Calendar.HOUR, calTime.get(Calendar.HOUR));
					cal.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
					cal.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
					cal.set(Calendar.MILLISECOND,
							calTime.get(Calendar.MILLISECOND));
					cal.set(Calendar.AM_PM, calTime.get(Calendar.AM_PM));

				}

			}
			return cal;
		}

		return Calendar.getInstance();
	}

	public static Calendar getCalendarDateTimeEnd(
			final DateTimeOrTimeRangeType dateTimeOrTimeRangeType) {

		if (dateTimeOrTimeRangeType != null) {
			Calendar cal = dateTimeOrTimeRangeType.getDate();

			if (cal != null) {

				if (dateTimeOrTimeRangeType.getEndTime() != null) {
					Calendar calEndTime = dateTimeOrTimeRangeType.getEndTime();

					cal.set(Calendar.HOUR, calEndTime.get(Calendar.HOUR));
					cal.set(Calendar.MINUTE, calEndTime.get(Calendar.MINUTE));
					cal.set(Calendar.SECOND, calEndTime.get(Calendar.SECOND));
					cal.set(Calendar.MILLISECOND,
							calEndTime.get(Calendar.MILLISECOND));
					cal.set(Calendar.AM_PM, calEndTime.get(Calendar.AM_PM));
				} else if (dateTimeOrTimeRangeType.isSetTime()
						&& dateTimeOrTimeRangeType.getTime() != null) {
					Calendar calTime = dateTimeOrTimeRangeType.getTime();

					cal.set(Calendar.HOUR, calTime.get(Calendar.HOUR));
					cal.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
					cal.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
					cal.set(Calendar.MILLISECOND,
							calTime.get(Calendar.MILLISECOND));
					cal.set(Calendar.AM_PM, calTime.get(Calendar.AM_PM));

				}
			}
			return cal;
		}

		return Calendar.getInstance();
	}

}
