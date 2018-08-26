package com.A.vm.util.converter.marshall;

import java.util.Calendar;

import com.A.xml.v4.DateTimeOrTimeRangeType;

/**
 * @author ebthomas
 * 
 */
public final class MarshallDate {
	/**
	 * static access only.
	 */
	private MarshallDate() {
		super();
	}

	/**
	 * @param calendar
	 *            source
	 * @return destination
	 */

	public static DateTimeOrTimeRangeType getCalendar(
			final Calendar startDate) {

		return getCalendar(startDate, startDate);

	}

	public static DateTimeOrTimeRangeType getCalendar(final Calendar startDate,
			final Calendar endDate) {

		DateTimeOrTimeRangeType dtr = DateTimeOrTimeRangeType.Factory
				.newInstance();

		if (startDate != null) {

			Calendar adjustedEndDate = endDate;

		 
			

			try {
				dtr.setDate(startDate);
				dtr.setTime(startDate);
				dtr.setEndTime(adjustedEndDate);
				dtr.setStartTime(startDate);
				
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dtr;

		 
	}
}
