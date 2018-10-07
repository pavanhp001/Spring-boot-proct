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

import java.io.Serializable;
import java.util.Date;

import abc.xyz.pts.bcs.admin.business.UserStatus;

public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1710804511092158993L;
    private String[] roles;
    private String name;
    private String forename;
    private String lastname;
    private String username;
    private String mobileNumber;
    private String faxNumber;
    private String email;
    private String location;
    private Date passwordChangeDate;
    private Date lastLoginDate;
    private UserStatus userStatus;
    private String password;
    private String modifyTimestamp;

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the userStatus
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus
     *            the userStatus to set
     */
    public void setUserStatus(final UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserStatusAudit() {
        if (this.userStatus != null) {
            return this.userStatus.getValue();
        }
        return null;
    }

    /**
     * @return the lastLoginDate
     */
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * @param lastLoginDate
     *            the lastLoginDate to set
     */
    public void setLastLoginDate(final Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * @return the passwordChangeDate
     */
    public Date getPasswordChangeDate() {
        return passwordChangeDate;
    }

    /**
     * @param passwordChangeDate
     *            the passwordChangeDate to set
     */
    public void setPasswordChangeDate(final Date passwordChangeDate) {
        this.passwordChangeDate = passwordChangeDate;
    }

    /**
     * @return the roles
     */
    public String[] getRoles() {
        return roles;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(final String[] roles) {
        this.roles = roles;
    }

    /**
     * @return the forename
     */
    public String getForename() {
        return forename;
    }

    /**
     * @param forename
     *            the forename to set
     */
    public void setForename(final String forename) {
        this.forename = forename;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber
     *            the mobileNumber to set
     */
    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the faxNumber
     */
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * @param faxNumber
     *            the faxNumber to set
     */
    public void setFaxNumber(final String faxNumber) {
        this.faxNumber = faxNumber;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the modifyTimestamp
     */
    public String getModifyTimestamp() {
        return modifyTimestamp;
    }

    /**
     * @param modifyTimestamp to set
     */
    public void setModifyTimestamp(final String modifyTimestamp) {
        this.modifyTimestamp = modifyTimestamp;
    }

}
