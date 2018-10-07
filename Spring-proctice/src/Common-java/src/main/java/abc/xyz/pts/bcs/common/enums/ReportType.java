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
package abc.xyz.pts.bcs.common.enums;

public enum ReportType {

    PDF ("pdf"), EXCEL("xls"), CSV("csv");

    private String formatName;

    private ReportType(final String formatName) {
        this.formatName = formatName;
    }

    public String getFormatName() {
        return formatName;
    }

    public static ReportType fromString(final String value) {
        for(final ReportType type : ReportType.values()){
            if(type.getFormatName().equals(value)){
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value "+value);
    }
}
