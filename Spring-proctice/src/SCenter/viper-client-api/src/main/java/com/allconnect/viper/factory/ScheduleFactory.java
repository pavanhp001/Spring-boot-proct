package com.A.V.factory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import com.A.V.domain.DayOfWeek;
import com.A.xml.v4.DayAndTimeType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.SchedulingInfoType;
import com.A.xml.v4.SchedulingInfoType.ActualSchedule;
import com.A.xml.v4.TimeSlots;
import com.A.xml.v4.WishScheduleCollectionType;
import com.A.xml.v4.WishScheduleType;

public enum ScheduleFactory {

	INSTANCE;

	private ObjectFactory oFactory = new ObjectFactory();

	public SchedulingInfoType create() {

		SchedulingInfoType schedule = oFactory.createSchedulingInfoType();

		return schedule;
	}

	public void updateInfo(SchedulingInfoType schedule,
			Boolean billingInstallments, Boolean earlierAppDate,
			String residenceType, String comment, final String installationFee) {
		schedule.setBillingInstallments(billingInstallments);
		schedule.setEarlierAppointmentDate(earlierAppDate);
		schedule.setResidenceType(residenceType);
		schedule.setAppointmentComment(comment);
		schedule.setInstallationFee(new BigDecimal(installationFee));

	}

	public void createAsSoonAsPossible(SchedulingInfoType schedule,
			final boolean isASAP) {
		schedule.getWishScheduleCollection()
				.setScheduleAsSoonAsPossible(isASAP);

	}

	public void createTimeSlots(SchedulingInfoType schedule,
			final String startHour, final String endHour) {

		WishScheduleType wst = oFactory.createWishScheduleType();
		WishScheduleCollectionType cType = oFactory
				.createWishScheduleCollectionType();
		schedule.setWishScheduleCollection(cType);

		schedule.getWishScheduleCollection().getWishSchedule().add(wst);

		TimeSlots timeslots = oFactory.createTimeSlots();
		timeslots.setEndHour(endHour);
		timeslots.setStartHour(startHour);

		wst.setTimeSlot(startHour + "-" + endHour);

	}

	public void createActualAppointment(SchedulingInfoType schedule,
			final boolean isASAP, final Date date, String startHour,
			String endHour) {

		XMLGregorianCalendar xmlDate = null;

		ActualSchedule as = oFactory.createSchedulingInfoTypeActualSchedule();

		schedule.setActualSchedule(as);
		as.setActualAppointmentDate(xmlDate);
		as.setActualAppointmentStartHour(startHour);
		as.setActualAppointmentEndHour(endHour);

	}

	// TODO: DISCONNECT
	// TODO: DESIRED
	// TODO: SCHEDULED

	public void createScheduleAppointment(SchedulingInfoType schedule,
			final boolean isASAP, final Date date, String startHour,
			String endHour) {

		// XMLGregorianCalendar xmlDate = null;
		// XMLGregorianCalendar xmlDate = null;
		// XMLGregorianCalendar xmlDate = null;
		// XMLGregorianCalendar xmlDate = null;
		//
		//
		// DateTimeOrTimeRangeType dt =
		// oFactory.createDateTimeOrTimeRangeType();
		// dt.setDate(xmlDate);
		// dt.setTime(value);
		// dt.setStartTime(value);
		// dt.setEndTime(value);

	}

	public SchedulingInfoType createDesiredAvailability(
			SchedulingInfoType schedule, final Map<String, String> map,
			final boolean isASAP, final Date date, String startHour,
			String endHour) {

		DayAndTimeType dt = oFactory.createDayAndTimeType();
		WishScheduleType wst = oFactory.createWishScheduleType();
		wst.setScheduleByDayAndTime(dt);

		WishScheduleCollectionType cType = oFactory
				.createWishScheduleCollectionType();
		schedule.setWishScheduleCollection(cType);

		schedule.getWishScheduleCollection().getWishSchedule().add(wst);
		dt.setAnyDayOfTheWeek(map.get(DayOfWeek.any.name()));
		dt.setSunday(map.get(DayOfWeek.sun.name()));
		dt.setMonday(map.get(DayOfWeek.mon.name()));
		dt.setTuesday(map.get(DayOfWeek.tues.name()));
		dt.setWednesday(map.get(DayOfWeek.wed.name()));
		dt.setThursday(map.get(DayOfWeek.thurs.name()));
		dt.setFriday(map.get(DayOfWeek.fri.name()));
		dt.setSaturday(map.get(DayOfWeek.sat.name()));

		return schedule;

	}

}
