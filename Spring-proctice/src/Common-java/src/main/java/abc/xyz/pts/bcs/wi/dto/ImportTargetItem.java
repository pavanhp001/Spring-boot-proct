/* **************************************************************************
 *                              - CONFIDENTIAL                           *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Java class for TARGET_ITEM complex type.
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IMPORT_TARGET_ITEM", namespace = "IMPORT_TARGET_ITEM")
public class ImportTargetItem extends TargetItem {

    @XmlAttribute(name = "WATLIF_ID")
    private Long watlifId;

    /**
     * @return the watlifId
     */
    public Long getWatlifId() {
        return watlifId;
    }

    /**
     * @param watlifId the watchlistId to set
     */
    public void setWatlifId(final Long watlifId) {
        this.watlifId = watlifId;
    }

    /**
     *
     * @return
     */
    public boolean containsData() {
        boolean dataPresent = false;

        if (StringUtils.isNotBlank(getForename())) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getLastName())) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getGender())) {
            dataPresent = true;
        } else if (getBirthDate() != null) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getCountryOfBirth())) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getBirthPlace())) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getNationality())) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getDocType())) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getDocNo())) {
            dataPresent = true;
        } else if (getValidUntilDate() != null) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getProtocolNumber())) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getRecommendedAction())) {
            dataPresent = true;
        } else if (StringUtils.isNotBlank(getRescCode())) {
            dataPresent = true;
        }

        return dataPresent;
    }
}
