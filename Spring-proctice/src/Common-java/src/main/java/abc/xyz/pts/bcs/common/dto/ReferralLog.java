/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dto;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author ryattapu
 *
 */

public class ReferralLog implements Serializable
{
    private static final long serialVersionUID = 3645849145213888485L;

    private Long id;
    private Long refId;
    private String eventType;
    private String closureCode;
    private String remarks;
    private String hitType;
    private Long hitId;
    private String notfAirportCode;
    private String notfRecipientCategory;
    private String notfRolesNotified;
    private String notfMediaType;
    private String notfActionStatus;
    private String notfActionErrorMessage;
    private String createdBy;
    private Calendar createdDatetime;

    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public Long getRefId() {
        return refId;
    }
    public void setRefId(final Long refId) {
        this.refId = refId;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }
    public String getClosureCode() {
        return closureCode;
    }
    public void setClosureCode(final String closureCode) {
        this.closureCode = closureCode;
    }
    public String getHitType() {
        return hitType;
    }
    public void setHitType(final String hitType) {
        this.hitType = hitType;
    }
    public Long getHitId() {
        return hitId;
    }
    public void setHitId(final Long hitId) {
        this.hitId = hitId;
    }
    public String getNotfAirportCode() {
        return notfAirportCode;
    }
    public void setNotfAirportCode(final String notfAirportCode) {
        this.notfAirportCode = notfAirportCode;
    }
    public String getNotfRecipientCategory() {
        return notfRecipientCategory;
    }
    public void setNotfRecipientCategory(final String notfRecipientCategory) {
        this.notfRecipientCategory = notfRecipientCategory;
    }
    public String getNotfRolesNotified() {
        return notfRolesNotified;
    }
    public void setNotfRolesNotified(final String notfRolesNotified) {
        this.notfRolesNotified = notfRolesNotified;
    }
    public String getNotfMediaType() {
        return notfMediaType;
    }
    public void setNotfMediaType(final String notfMediaType) {
        this.notfMediaType = notfMediaType;
    }
    public String getNotfActionStatus() {
        return notfActionStatus;
    }
    public void setNotfActionStatus(final String notfActionStatus) {
        this.notfActionStatus = notfActionStatus;
    }
    public String getNotfActionErrorMessage() {
        return notfActionErrorMessage;
    }
    public void setNotfActionErrorMessage(final String notfActionErrorMessage) {
        this.notfActionErrorMessage = notfActionErrorMessage;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }
    public Calendar getCreatedDatetime() {
        return createdDatetime;
    }
    public void setCreatedDatetime(final Calendar createdDatetime) {
        this.createdDatetime = createdDatetime;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }




}
