/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.db.dto;

public interface FlightSearch {

    String getDepartureAirport();

    void setDepartureAirport(String value);

    String getArrivalAirport();

    void setArrivalAirport(String workingAirport);

    String getDefaultDateRangeFrom();

    void setDefaultDateRangeFrom(String value);

    String getDefaultDateRangeTo();

    void setDefaultDateRangeTo(String value);

}
