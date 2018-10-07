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

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.HasControls;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.ldap.ParameterizedContextMapperWithControls;
import abc.xyz.pts.bcs.common.ldap.dao.ConfigurableAttributesMapper;
import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;

public abstract class UserAttributesMapper extends ConfigurableAttributesMapper implements
ParameterizedContextMapperWithControls<User>{

	 public enum AttributeName {
	        EMAIL("email"),
	        FAX("faxNumber"),
	        FORENAME("forename"),
	        LASTNAME("lastname"),
	        NAME("name"),
	        LOCATION("location"),
	        MOBILE("mobileNumber"),
	        USERNAME("username"),
	        PASSWORD_CHANGED("passwordChangedDate"),
	        ROLE("role"),
	        LAST_LOGIN("lastLoginDate"),
	        ACCOUNT_LOCKED("accountLocked"),
	        ACCOUNT_DISABLED("accountDisabled"),
	        PASSWORD("password"),
	        MODIFY_TIMESTAMP("modifyTimestamp");

	        private final String value;

	        AttributeName(final String value) {
	            this.value = value;
	        }

	        public String value() {
	            return value;
	        }
	    }
	 
	
		public Attributes mapToAttributes(final User user, final String baseName) {
	        Attributes attrs = new BasicAttributes();
	        Attribute classAttr = new BasicAttribute(LdapConstants.OBJECT_CLASS.getValue(), LdapConstants.INET_ORG_PERSON
	                .getValue());
	        classAttr.add(LdapConstants.ORGANISATIONAL_PERSON.getValue());
	        classAttr.add(LdapConstants.PERSON.getValue());
	        classAttr.add(LdapConstants.TOP.getValue());
	        attrs.put(classAttr);
	        Attribute emailAttribute = new BasicAttribute(mapAttributeName(AttributeName.EMAIL), user.getEmail());
	        attrs.put(emailAttribute);
	        if (StringUtils.isNotEmpty(user.getFaxNumber())) {
	            Attribute faxAttr = new BasicAttribute(mapAttributeName(AttributeName.FAX), user.getFaxNumber());
	            attrs.put(faxAttr);
	        }
	        Attribute forenameAttr = new BasicAttribute(mapAttributeName(AttributeName.FORENAME), user.getForename());
	        attrs.put(forenameAttr);
	        Attribute surnameAttr = new BasicAttribute(mapAttributeName(AttributeName.LASTNAME), user.getLastname());
	        attrs.put(surnameAttr);
	/*
	        Attribute enableAttr = new BasicAttribute(mapAttributeName(AttributeName.ACCOUNT_DISABLED), BLANK);
	        attrs.put(enableAttr);*/

	        String name = user.getForename() + " " + user.getLastname();
	        Attribute nameAttr = new BasicAttribute(mapAttributeName(AttributeName.NAME), name);
	        attrs.put(nameAttr);
	        user.setName(name);
	        if (StringUtils.isNotEmpty(user.getLocation())) {
	            Attribute locAttr = new BasicAttribute(mapAttributeName(AttributeName.LOCATION), user.getLocation());
	            attrs.put(locAttr);
	        }
	        if (StringUtils.isNotEmpty(user.getMobileNumber())) {
	            Attribute mobileAttr = new BasicAttribute(mapAttributeName(AttributeName.MOBILE), user.getMobileNumber());
	            attrs.put(mobileAttr);
	        }
	        Attribute userAttr = new BasicAttribute(mapAttributeName(AttributeName.USERNAME), user.getUsername());
	        attrs.put(userAttr);
	        if (user.getRoles() != null && user.getRoles().length > 0) {
	            Attribute roleAttr = createRoleAttribute(user.getRoles(), baseName);
	            attrs.put(roleAttr);
	        }
	        if (user.getPassword() != null) {
	            Attribute passwordAttr = new BasicAttribute(mapAttributeName(AttributeName.PASSWORD), user.getPassword());
	            attrs.put(passwordAttr);
	        }
	        return attrs;
	    }
		

	    public Attribute createRoleAttribute(final String[] roles, final String baseName) {
	        Attribute attr = null;
	        if (roles != null && roles.length > 0) {
	            for (int i = 0; i < roles.length; i++) {
	                String newRoleWithDn = "cn=" + roles[i].toLowerCase() + "," + baseName;
	                if (attr == null) {
	                    attr = new BasicAttribute(LdapConstants.NS_ROLE_DN.getValue(), newRoleWithDn);
	                } else {
	                    attr.add(newRoleWithDn);
	                }
	            }
	        } else {
	            attr = new BasicAttribute(LdapConstants.NS_ROLE_DN.getValue(), null);
	        }
	        return attr;
	    }
	    
	   
	    public User mapFromContext(final Object ctx) {
	        return mapFromContextWithControls(ctx, null);
	    }

	    protected String resolveAttributeName(final Enum attributeName) {
	        if (attributeName instanceof AttributeName) {
	            return ((AttributeName) attributeName).value();
	        } else {
	            throw new IllegalArgumentException("Only instances of AttributeName are allowed, but it was " + attributeName.getClass().getName());
	        }
	    }
	    
	@SuppressWarnings("unchecked")
	public abstract User mapFromContextWithControls(final Object ctx,
			final HasControls hasControls);

	

}