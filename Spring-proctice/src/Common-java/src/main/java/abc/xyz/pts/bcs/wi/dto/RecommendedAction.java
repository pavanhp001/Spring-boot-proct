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

public class RecommendedAction implements Serializable {


    //private static final long serialVersionUID = -5319393227367829908L;

    private String code;
    private String description;
    private String descriptionLang;
    private String autoQualifyInd = "N"; // default to NO
    private String appActionCode;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getAppActionCode() {
        return appActionCode;
    }

    public void setAppActionCode(final String appActionCode) {
        this.appActionCode = appActionCode;
    }

    public String getDescriptionLang() {
        return descriptionLang;
    }

    public void setDescriptionLang(final String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }

    public String getAutoQualifyInd() {
        return autoQualifyInd;
    }

    public void setAutoQualifyInd(final String autoQualifyInd) {
        this.autoQualifyInd = autoQualifyInd;
    }
}
