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
package abc.xyz.pts.bcs.common.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.dao.session.DbSessionParameterProvider;

/**
 * This POJO to store user information into session
 *
 * @author Ryattapu
 * @version: $Id$
 */
public class UserDetails implements Serializable, DbSessionParameterProvider {

    private static final long serialVersionUID = 1L;
    private String sessionId;
    private String name;
    private String[] roles;
    private String description;
    private Locale locale;
    private String airport;
    private String ipAddress;
    private String emailAddress;
    private String employeeName;
    private String entity;
    private String office;
    private Date lastLogin;

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName
     *            the employeeName to set
     */
    public void setEmployeeName(final String employeeName) {
        this.employeeName = employeeName;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String[] getRoles() {
        return roles;
    }

    public String getRolesAsString(){
        return StringUtils.join(roles, ",");
    }

    public List<String> getRolesAsList() {
        return Arrays.asList(roles);
    }

    public void setRoles(final String[] roles) {
        this.roles = roles.clone();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    @Override
    public String getAirport() {
        return airport;
    }

    public void setAirport(final String airport) {
        this.airport = airport;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(final Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(final String entity) {
        this.entity = entity;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(final String office) {
        this.office = office;
    }

    @Override
    public String toString() {
        final StringBuilder valueToReturn = new StringBuilder();
        valueToReturn.append("[").append("(sessionId: ").append(sessionId).append("), ");
        valueToReturn.append("(name: ").append(name).append("), ");
        valueToReturn.append("(description: ").append(description).append("), ");
        valueToReturn.append("(locale: ").append(locale).append("), ");
        valueToReturn.append("(airport: ").append(airport).append("), ");
        valueToReturn.append("(ipAddress: ").append(ipAddress).append("), ");
        valueToReturn.append("(emailAddress: ").append(emailAddress).append("), ");
        valueToReturn.append("(entity: ").append(entity).append("), ");
        valueToReturn.append("(office: ").append(office).append("), ");
        valueToReturn.append("(roles: ");
        if (roles != null) {
            for (final String role : roles) {
                valueToReturn.append("{").append(role).append("} ");
            }
        }
        valueToReturn.append(")");
        return valueToReturn.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((airport == null) ? 0 : airport.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + ((employeeName == null) ? 0 : employeeName.hashCode());
        result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
        result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + Arrays.hashCode(roles);
        result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        result = prime * result + ((office == null) ? 0 : office.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDetails other = (UserDetails) obj;
        if (airport == null) {
            if (other.airport != null) {
                return false;
            }
        } else if (!airport.equals(other.airport)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (emailAddress == null) {
            if (other.emailAddress != null) {
                return false;
            }
        } else if (!emailAddress.equals(other.emailAddress)) {
            return false;
        }
        if (employeeName == null) {
            if (other.employeeName != null) {
                return false;
            }
        } else if (!employeeName.equals(other.employeeName)) {
            return false;
        }
        if (ipAddress == null) {
            if (other.ipAddress != null) {
                return false;
            }
        } else if (!ipAddress.equals(other.ipAddress)) {
            return false;
        }
        if (lastLogin == null) {
            if (other.lastLogin != null) {
                return false;
            }
        } else if (!lastLogin.equals(other.lastLogin)) {
            return false;
        }
        if (locale == null) {
            if (other.locale != null) {
                return false;
            }
        } else if (!locale.equals(other.locale)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (!Arrays.equals(roles, other.roles)) {
            return false;
        }
        if (sessionId == null) {
            if (other.sessionId != null) {
                return false;
            }
        } else if (!sessionId.equals(other.sessionId)) {
            return false;
        }
        if (entity == null) {
            if (other.entity != null) {
                return false;
            }
        } else if (!entity.equals(other.entity)) {
            return false;
        }
        if (office == null) {
            if (other.office != null) {
                return false;
            }
        } else if (!office.equals(other.office)) {
            return false;
        }
        return true;
    }
}
