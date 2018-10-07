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
package abc.xyz.pts.bcs.admin.dto;

public class Airport {

    private static final String DELIMITOR = " - ";
    private String iataCode = null;
    private String description = null;
    private String icaoCode = null;

    public Airport() {

    }

    /**
     * @return the iataCode
     */
    public String getIataCode() {
        return iataCode;
    }

    /**
     * @param iataCode
     *            the iataCode to set
     */
    public void setIataCode(final String iataCode) {
        this.iataCode = iataCode;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the description
     */
    public String getCodeDescription() {
        return iataCode + DELIMITOR + description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the icaoCode
     */
    public String getIcaoCode() {
        return icaoCode;
    }

    /**
     * @param icaoCode
     *            the icaoCode to set
     */
    public void setIcaoCode(final String icaoCode) {
        this.icaoCode = icaoCode;
    }
}
