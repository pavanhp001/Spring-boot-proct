/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Deepesh.Rathore
 *
 */
public class Command {
	/**
	 * @param object
	 * @param i
	 */
	private Calendar calendar;
	private int number;
	private BigDecimal bigDecimal;
	/**
	 * @return the bigDecimal
	 */
	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	/**
	 * @param bigDecimal the bigDecimal to set
	 */
	public void setBigDecimal(final BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	/**
	 * @return the calendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar the calendar to set
	 */
	public void setCalendar(final Calendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(final int number) {
		this.number = number;
	}

}
