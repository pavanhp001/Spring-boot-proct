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
package abc.xyz.pts.bcs.admin.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.Control;
import javax.naming.ldap.HasControls;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;

import abc.xyz.pts.bcs.admin.business.UserStatus;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.ldap.control.AccountUsabilityControl;
import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;

public class LdapUserAttributesMapper extends  UserAttributesMapper {

    private static final String BLANK = "";
    private static final Logger LOG = Logger.getLogger(LdapUserAttributesMapper.class);

    private static final DateTimeFormatter DATETIME_FORMATTER = new DateTimeFormatterBuilder().append(ISODateTimeFormat.basicDate().getParser())
            .appendOptional(
                    new DateTimeFormatterBuilder().append(null, new DateTimeParser[] { ISODateTimeFormat.basicTime().getParser(),
                    										ISODateTimeFormat.basicTimeNoMillis().getParser()})
			                                                .toParser())
			                                                .toFormatter()                                                                                 
			                                                .withZone(DateTimeZone.UTC);

   
    @Override
    @SuppressWarnings("rawtypes")
    public User mapFromContextWithControls(final Object ctx, final HasControls hasControls) {
        User user = new User();
        try {
            DirContextAdapter adapter = (DirContextAdapter) ctx;
            Attributes attrs = adapter.getAttributes();

            user.setEmail(getStringAttribute(attrs, AttributeName.EMAIL));
            user.setFaxNumber(getStringAttribute(attrs, AttributeName.FAX));
            user.setForename(getStringAttribute(attrs, AttributeName.FORENAME));
            user.setLastname(getStringAttribute(attrs, AttributeName.LASTNAME));
            user.setName(getStringAttribute(attrs, AttributeName.NAME));
            user.setLocation(getStringAttribute(attrs, AttributeName.LOCATION));
            user.setMobileNumber(getStringAttribute(attrs, AttributeName.MOBILE));
            user.setUsername(getStringAttribute(attrs, AttributeName.USERNAME));
            user.setModifyTimestamp(getStringAttribute(attrs, AttributeName.MODIFY_TIMESTAMP));

            Attribute pwdChangedAttribute = attrs.get(attributeNames.get(AttributeName.PASSWORD_CHANGED.value()));

            if (pwdChangedAttribute != null) {
                String dateStr = (String) pwdChangedAttribute.get();
                Date pwdChangedDate = DATETIME_FORMATTER.parseDateTime(dateStr).toDate();
                user.setPasswordChangeDate(pwdChangedDate);
            }

            Attribute lastLoginAttr = attrs.get(attributeNames.get(AttributeName.LAST_LOGIN.value()));
            if (lastLoginAttr != null) {
                String dateStr = (String) lastLoginAttr.get();
                Date lastLoginDate = DATETIME_FORMATTER.parseDateTime(dateStr).toDate();
                user.setLastLoginDate(lastLoginDate);
            }

            boolean accountLocked = false;
            if (hasControls != null) {
                for (Control ctrl : hasControls.getControls()) {
                    if (ctrl.getID().equals(AccountUsabilityControl.ID)) {
                        AccountUsabilityControl accountUsabilityControl = new AccountUsabilityControl(ctrl
                                .getEncodedValue());
                        accountLocked = accountUsabilityControl.isInactive();
                    }
                }
            }
            Attribute accountDisabledAttr = attrs.get(attributeNames.get(AttributeName.ACCOUNT_DISABLED.value()));
            String accountEnableDisableAttr = (accountDisabledAttr != null ? accountDisabledAttr.get().toString() : BLANK);
            if (Boolean.valueOf(accountEnableDisableAttr)) {
                user.setUserStatus(UserStatus.DISABLED);
            } else if (accountLocked) {
                // We are setting Locked Out status to Enable default as Admin Application is
                // not dealing Locked Out status presently
                // If any time Amin application decide to handle Locked Out, use the following line
                // user.setUserStatus(UserStatus.LOCKED_OUT);
                user.setUserStatus(UserStatus.ENABLED);
            } else {
                user.setUserStatus(UserStatus.ENABLED);
            }

            Attribute roleAttribute = attrs.get(attributeNames.get(AttributeName.ROLE.value()));
            if (roleAttribute != null) {
                List<String> roles = new ArrayList<String>();
                NamingEnumeration rolesEnum = roleAttribute.getAll();
                while (rolesEnum.hasMoreElements()) {
                    String roleStr = (String) rolesEnum.next();
                    DistinguishedName roleDn = new DistinguishedName(roleStr);
                    String roleName = roleDn.getValue(LdapConstants.CN.getValue()).toUpperCase();
                    roles.add(roleName);
                }
                user.setRoles(roles.toArray(new String[roles.size()]));
            }

        } catch (NamingException ne) {
            LOG.error("Unable to map role attribute", ne);
        }
        return user;
    }
}
