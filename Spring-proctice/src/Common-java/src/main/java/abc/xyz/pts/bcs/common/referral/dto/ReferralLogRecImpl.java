/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dto;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Sudheendra.Singh
 *
 */
@Deprecated // try to use ReferralLog instead
public class ReferralLogRecImpl implements Serializable, ReferralLogRec {

    private static final long serialVersionUID = -106662620578835912L;

    private Long id;
    private Long refId;
    private String createdBy;
    private Calendar createdDatetime;
    private String notfAirportCode;
    private String notfRolesNotified;
    private String notfActionStatus;
    private String notfMediaType;
    private String remarks;
    private String eventType;
    private String hitType;
    private Long hitId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getRefId() {
        return refId;
    }

    public void setRefId(final Long refId) {
        this.refId = refId;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Calendar getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(final Calendar createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    @Override
    public String getNotfAirportCode() {
        return notfAirportCode;
    }

    public void setNotfAirportCode(final String notfAirportCode) {
        this.notfAirportCode = notfAirportCode;
    }

    @Override
    public String getNotfRolesNotified() {
        return notfRolesNotified;
    }

    public void setNotfRolesNotified(final String notfRolesNotified) {
        this.notfRolesNotified = notfRolesNotified;
    }

    @Override
    public String getNotfActionStatus() {
        return notfActionStatus;
    }

    public void setNotfActionStatus(final String notfActionStatus) {
        this.notfActionStatus = notfActionStatus;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String getHitType() {
        return hitType;
    }

    public void setHitType(final String hitType) {
        this.hitType = hitType;
    }

    @Override
    public Long getHitId() {
        return hitId;
    }

    public void setHitId(final Long hitId) {
        this.hitId = hitId;
    }

    @Override
    public String getNotfMediaType() {
        return notfMediaType;
    }

    public void setNotfMediaType(final String notfMediaType) {
        this.notfMediaType = notfMediaType;
    }

    @Override
    public String toString() {
        return "ReferralLogRecImpl [createdBy=" + createdBy
                + ", createdDatetime=" + createdDatetime.getTime() + ", eventType="
                + eventType + ", hitId=" + hitId + ", hitType=" + hitType
                + ", id=" + id + ", notes=" + remarks + ", notfActionStatus="
                + notfActionStatus + ", notfAirportCode=" + notfAirportCode
                + ", notfRolesNotified=" + notfRolesNotified + ", refId="
                + refId + "]";
    }
}