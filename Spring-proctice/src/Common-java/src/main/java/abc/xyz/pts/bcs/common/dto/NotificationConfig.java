/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dto;

import java.util.Calendar;

public class NotificationConfig {

    private Long id ;
    private String aletCode ;
    private String nottCode ;
    private String recipientCategory;
    private String roles;
    private Integer value ;
    private String airportCode;
    private String primaryMediaTypes;
    private String secondaryMediaTypes;
    private String modifiedBy ;
    private Calendar modifiedDatetime ;
    private String createdBy;
    private String createdDatetime;


    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }

    public String getAletCode() {
        return aletCode;
    }
    public void setAletCode(final String aletCode) {
        this.aletCode = aletCode;
    }

    public String getAirportCode() {
        return airportCode;
    }
    public void setAirportCode(final String airportCode) {
        this.airportCode = airportCode;
    }

    public String getNottCode() {
        return nottCode;
    }
    public void setNottCode(final String nottCode) {
        this.nottCode = nottCode;
    }
    public String getModifiedBy() {
        return modifiedBy;
    }
    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public Calendar getModifiedDatetime() {
        return modifiedDatetime;
    }
    public void setModifiedDatetime(final Calendar modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedDatetime() {
        return createdDatetime;
    }
    public void setCreatedDatetime(final String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }
    public String getRoles() {
        return roles;
    }
    public void setRoles(final String roles) {
        this.roles = roles;
    }
    public String getPrimaryMediaTypes() {
        return primaryMediaTypes;
    }
    public void setPrimaryMediaTypes(final String primaryMediaTypes) {
        this.primaryMediaTypes = primaryMediaTypes;
    }
    public String getSecondaryMediaTypes() {
        return secondaryMediaTypes;
    }
    public void setSecondaryMediaTypes(final String secondaryMediaTypes) {
        this.secondaryMediaTypes = secondaryMediaTypes;
    }
    public String getRecipientCategory() {
        return recipientCategory;
    }
    public void setRecipientCategory(final String recipientCategory) {
        this.recipientCategory = recipientCategory;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(final Integer value) {
        this.value = value;
    }
}
