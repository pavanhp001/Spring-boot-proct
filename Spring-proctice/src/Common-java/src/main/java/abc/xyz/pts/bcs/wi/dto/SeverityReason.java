/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dto;

import java.io.Serializable;

public class SeverityReason implements Serializable {

    private static final long serialVersionUID = 5509308067126253061L;
    private String description;
    private String descriptionLang;

    private String code;
    private Long severityLevel;

    public Long getSeverityLevel() {
        return severityLevel;
    }
    public void setSeverityLevel(final Long severityLevel) {
        this.severityLevel = severityLevel;
    }
    public String getDescription() {
        return description;
    }
    public String getDescriptionWithSeverity() {
        return description+ " ("+severityLevel+")";
    }
    public String getDescriptionLang() {
        return descriptionLang;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    public void setDescriptionLang(final String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }
    public String getCode() {
        return code;
    }
    public void setCode(final String code) {
        this.code = code;
    }

}
