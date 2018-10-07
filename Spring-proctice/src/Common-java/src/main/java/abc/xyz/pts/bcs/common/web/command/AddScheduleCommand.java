/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.command;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.dto.ScheduleRecord;


/**
 * @author Thiruvengadam.S
 *
 */
@AuditableCommand(Event.ADD_SCHEDULE)
public class AddScheduleCommand extends AbstractScheduleCommand {

    /**
     *
     */
    private static final long serialVersionUID = 5236446698663537038L;

    private String displayAddPoppin = "no";
    private String flightId;
    private String errors;
    private String depDateFromFlightSearch;
    private String scheduledDateForRescan;
    private String bookingRefNos;

    public String getDepDateFromFlightSearch() {
        return depDateFromFlightSearch;
    }

    public void setDepDateFromFlightSearch(final String depDateFromFlightSearch) {
        this.depDateFromFlightSearch = depDateFromFlightSearch;
    }

    public String getDisplayAddPoppin() {
        return displayAddPoppin;
    }

    public void setDisplayAddPoppin(final String displayAddPoppin) {
        this.displayAddPoppin = displayAddPoppin;
    }



    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(final String flightId) {
        this.flightId = flightId;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(final String errors) {
        this.errors = errors;
    }

    /**
     * @return the scheduledDateForRescan
     */
    public String getScheduledDateForRescan() {
        return scheduledDateForRescan;
    }

    /**
     * @param scheduledDateForRescan the scheduledDateForRescan to set
     */
    public void setScheduledDateForRescan(final String scheduledDateForRescan) {
        this.scheduledDateForRescan = scheduledDateForRescan;
    }

    /**
     * @return the bookingRefNos
     */
    public String getBookingRefNos() {
        return bookingRefNos;
    }

    /**
     * @param bookingRefNos the bookingRefNos to set
     */
    public void setBookingRefNos(final String bookingRefNos) {
        this.bookingRefNos = bookingRefNos;
    }

    /**
     *
     */
    @Override
    public ScheduleRecord copyTo() {
        final ScheduleRecord record = super.copyTo();
        record.setBookingRefNoList(getBookingRefNos());

        return record;
    }
}
