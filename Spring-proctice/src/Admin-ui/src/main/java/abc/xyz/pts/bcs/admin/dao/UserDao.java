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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.control.VirtualListViewResultsCookie;
import org.springframework.ldap.core.CollectingNameClassPairCallbackHandler;
import org.springframework.ldap.core.ContextExecutor;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.NotPresentFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.security.ldap.ppolicy.PasswordPolicyControl;
import org.springframework.security.ldap.ppolicy.PasswordPolicyResponseControl;

import abc.xyz.pts.bcs.admin.business.UserStatus;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.exception.UserExistsException;
import abc.xyz.pts.bcs.admin.exception.UserMissingException;
import abc.xyz.pts.bcs.admin.ldap.ContextMapperCallbackHandlerWithControls;
import abc.xyz.pts.bcs.admin.ldap.control.PasswordModifyControl;
import abc.xyz.pts.bcs.admin.ldap.control.PasswordModifyControl.PasswordModifyResponse;
import abc.xyz.pts.bcs.admin.ldap.support.AccountUsabilityDirContextProcessor;
import abc.xyz.pts.bcs.admin.ldap.support.UserSearchAggregateDirContentProcessor;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;
import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

public class UserDao {

    private static final Logger logger = Logger.getLogger(UserDao.class);

    private static final String BLANK = "";
    private static final String TRUE = "true";
    protected SimpleLdapTemplate ldapTemplate;
    private SimpleLdapTemplate pagingLdapTemplate;
    private SimpleLdapTemplate readOnlyLdapTemplate;
    protected String roleAbsoluteBaseName;
    protected String usersAbsoluteBaseName;
    protected UserAttributesMapper userAttributesMapper;

    /**
     * @param usersAbsoluteBaseName
     *            the usersAbsoluteBaseName to set
     */
    public void setUsersAbsoluteBaseName(final String usersAbsoluteBaseName) {
        this.usersAbsoluteBaseName = usersAbsoluteBaseName;
    }

    /**
     * @param rolesBaseName
     *            the rolesBaseName to set
     */
    public void setRoleAbsoluteBaseName(final String roleAbsoluteBaseName) {
        this.roleAbsoluteBaseName = roleAbsoluteBaseName;
    }

    /**
     * @param ldapTemplate
     *            the ldapTemplate to set
     */
    public void setLdapTemplate(final SimpleLdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
    
    /**
     * @param userAttributesMapper
     *            the userAttributesMapper to set
     */
    public void setUserAttributesMapper(final UserAttributesMapper userAttributesMapper) {
        this.userAttributesMapper = userAttributesMapper;
    }

    public void addUser(final User user) throws UserExistsException {
        try {
            final DistinguishedName dn = new DistinguishedName(usersAbsoluteBaseName);
            dn.append(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.USERNAME), user
                    .getUsername());
            Attributes attributes = userAttributesMapper.mapToAttributes(user, roleAbsoluteBaseName);
            ldapTemplate.bind(dn, null, attributes);
            DistinguishedName absDn = createAbsoluteDNFromUsername(user.getUsername());
            PasswordModifyResponse response = passwordModify(new PasswordModifyControl(absDn.toString()));
            user.setPassword(response.getPassword());
        } catch (NameAlreadyBoundException nabe) {
            throw new UserExistsException(nabe);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("unable to set user password", uee);
        }
    }

    public void updateUser(final User user) throws UserMissingException {

        // Get user again to compare modified date
        User oldUser = getUser(user.getUsername());
        if (oldUser == null) {
            throw new UserMissingException("Unable to find user");
        }
        /*
         * if (!StringUtils.equals(oldUser.getModifyTimestamp(), user.getModifyTimestamp())) {
         * user.setModifyTimestamp(null);
         * }
         */
        try {
            final DistinguishedName dn = new DistinguishedName(usersAbsoluteBaseName);
            dn.append(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.USERNAME), user
                    .getUsername());

            // modify the simple attributes. We don't use modification items here
            // as its more readable to let the ldap template do the work.
            DirContextOperations ctx = ldapTemplate.lookupContext(dn);
            ctx.setAttributeValue(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.FORENAME),
                    user.getForename());
            ctx.setAttributeValue(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.LASTNAME),
                    user.getLastname());
            String newName = user.getForename() + " " + user.getLastname();
            ctx.setAttributeValue(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.NAME),
                    newName);
            ctx.setAttributeValue(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.MOBILE),
                    user.getMobileNumber());
            ctx.setAttributeValue(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.FAX), user
                    .getFaxNumber());
            ctx.setAttributeValue(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.EMAIL), user
                    .getEmail());
            ctx.setAttributeValue(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.LOCATION),
                    user.getLocation());
            ldapTemplate.modifyAttributes(ctx);
            LdapOperations ldapOps = ldapTemplate.getLdapOperations();

            // add the new roles
            List<ModificationItem> mis = new ArrayList<ModificationItem>();

            UserStatus status = user.getUserStatus();
            Attribute disabledAttr = null;

            // we ignore the locked out status as we cannot set the user to it.
            if (status.equals(UserStatus.DISABLED)) {
                disabledAttr = new BasicAttribute(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.ACCOUNT_DISABLED), TRUE);
            } else if (status.equals(UserStatus.ENABLED)) {
                disabledAttr = new BasicAttribute(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.ACCOUNT_DISABLED), null);
            }

            if (disabledAttr != null) {
                ModificationItem disabledMi = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, disabledAttr);
                mis.add(disabledMi);
            }

            Attribute roleAttr = userAttributesMapper.createRoleAttribute(user.getRoles(), roleAbsoluteBaseName);
            ModificationItem roleMi = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, roleAttr);
            mis.add(roleMi);

            ModificationItem[] miArray = new ModificationItem[mis.size()];
            miArray = mis.toArray(miArray);
            // operational attributes are not returned by the ldaptemplate.lookupContext
            // so we must use modificationItems to set their values.
            ldapOps.modifyAttributes(dn, miArray);
            user.setName(newName);
        } catch (NameNotFoundException e) {
            throw new UserMissingException(e);
        } catch (Exception e) {
            throw new LdapDataAccessException("Unable to update user", e);
        }

    }

    protected SearchControls createSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes(userAttributesMapper.getAttributeNamesArray());
        searchControls.setReturningObjFlag(true);
        return searchControls;
    }

    public User getUser(final String username) {
        User result = null;

        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter(LdapConstants.OBJECT_CLASS.getValue(), LdapConstants.INET_ORG_PERSON.getValue()));
        filter.and(new EqualsFilter(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.USERNAME),
                username));
        AccountUsabilityDirContextProcessor contextProcessor = new AccountUsabilityDirContextProcessor();
        CollectingNameClassPairCallbackHandler handler = new ContextMapperCallbackHandlerWithControls(
                userAttributesMapper);
        readOnlyLdapTemplate.getLdapOperations().search(usersAbsoluteBaseName, filter.encode(), createSearchControls(),
                handler, contextProcessor);
        List users = handler.getList();
        if (!users.isEmpty()) {
            result = (User) users.get(0);

        }
        return result;
    }

    public List<User> getSearchUsers(final AdminUserSearchCommand adminUserSearchCommand, final TableActionCommand adminSearchTableCommand) {
        UserSearchAggregateDirContentProcessor pagedResultsProcessor = null;
        VirtualListViewResultsCookie cookie = new VirtualListViewResultsCookie(null, 0, 0);
        int pageSize = adminSearchTableCommand.getPageSize();
        if (adminSearchTableCommand.getPrintAll()) {
            pageSize = (int) adminSearchTableCommand.getTotalResults();
        }
        pagedResultsProcessor = new UserSearchAggregateDirContentProcessor(userAttributesMapper
                .mapAttributeName(adminSearchTableCommand.getSortBy()), adminSearchTableCommand.isDescending(), pageSize,
                (adminSearchTableCommand.getStartIndex()), 0, cookie);
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter(LdapConstants.OBJECT_CLASS.getValue(), LdapConstants.INET_ORG_PERSON.getValue()));
        // build the search criteria
        User user = adminUserSearchCommand.getUser();
        if (user != null) {
            if (StringUtils.isNotEmpty(user.getForename())) {
                filter.and(new EqualsFilter(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.FORENAME), user.getForename()));
            }
            if (StringUtils.isNotEmpty(user.getLastname())) {
                filter.and(new LikeFilter(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.LASTNAME), user.getLastname()));
            }
            if (StringUtils.isNotEmpty(user.getUsername())) {
                filter.and(new EqualsFilter(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.USERNAME), user.getUsername()));
            }
            if (StringUtils.isNotEmpty(user.getMobileNumber())) {
                filter.and(new EqualsFilter(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.MOBILE), user.getMobileNumber()));
            }
            if (StringUtils.isNotEmpty(user.getFaxNumber())) {
                filter.and(new EqualsFilter(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.FAX), user.getFaxNumber()));
            }
            if (StringUtils.isNotEmpty(user.getEmail())) {
                filter.and(new EqualsFilter(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.EMAIL), user.getEmail()));
            }
            if (StringUtils.isNotEmpty(user.getLocation())) {
                filter.and(new EqualsFilter(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.LOCATION), user.getLocation()));
            }
            if (user.getUserStatus() != null && (!user.getUserStatus().equals(UserStatus.ALL))) {
                if (user.getUserStatus().equals(UserStatus.DISABLED)) {
                    filter.and(new EqualsFilter(userAttributesMapper
                            .mapAttributeName(UserAttributesMapper.AttributeName.ACCOUNT_DISABLED), TRUE));
                } else {
                    OrFilter notDisabledFilter = new OrFilter();
                    // in some circumstances the nsAccountLock attribute is not removed but set to an empty string
                    notDisabledFilter.or(new NotPresentFilter(userAttributesMapper
                            .mapAttributeName(UserAttributesMapper.AttributeName.ACCOUNT_DISABLED)));
                    notDisabledFilter.or(new EqualsFilter(userAttributesMapper
                            .mapAttributeName(UserAttributesMapper.AttributeName.ACCOUNT_DISABLED), BLANK));
                    filter.and(notDisabledFilter);

                }
            }

            filter = getRoles(filter, user);

        }
        CollectingNameClassPairCallbackHandler handler = new ContextMapperCallbackHandlerWithControls(
                userAttributesMapper);
        pagingLdapTemplate.getLdapOperations().search(usersAbsoluteBaseName, filter.encode(), createSearchControls(),
                handler, pagedResultsProcessor);
        // criteria.setSearchResults(handler.getList());
        VirtualListViewResultsCookie cookie2 = pagedResultsProcessor.getCookie();
        adminSearchTableCommand.setTotalResults(cookie2.getContentCount());

        return handler.getList();
    }

    private AndFilter getRoles(final AndFilter filter, final User user) {
        String[] roles = user.getRoles();
        if (roles != null && roles.length > 0) {
            OrFilter orFilter = new OrFilter();
            for (int i = 0; i < roles.length; i++) {
                String role = roles[i];
                orFilter.or(new EqualsFilter(userAttributesMapper
                        .mapAttributeName(UserAttributesMapper.AttributeName.ROLE), "cn=" + role + ","
                        + roleAbsoluteBaseName));
            }
            filter.and(orFilter);
        }

        return filter;
    }

    /**
     * @param pagingLdapTemplate
     *            the pagingLdapTemplate to set
     */
    public void setPagingLdapTemplate(final SimpleLdapTemplate pagingLdapTemplate) {
        this.pagingLdapTemplate = pagingLdapTemplate;
    }

    public void deleteUser(final String username) throws UserMissingException {
        try {
            final DistinguishedName dn = new DistinguishedName(usersAbsoluteBaseName);
            dn.append(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.USERNAME), username);
            ldapTemplate.unbind(dn);
        } catch (NameNotFoundException e) {
            throw new UserMissingException(e);
        }
    }

    public String resetPassword(final String username) {
        PasswordModifyResponse response = null;
        DistinguishedName dn = createAbsoluteDNFromUsername(username);
        try {
            response = passwordModify(new PasswordModifyControl(dn.toString()));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("unable to set user password", uee);
        }
        return response.getPassword();
    }

    public PasswordChangeResult changePassword(final String username, final String oldPwd, final String newPwd) {
        DistinguishedName dn = createAbsoluteDNFromUsername(username);

        LdapContext ctx = null;
        try {
            LdapTemplate template = (LdapTemplate) (ldapTemplate.getLdapOperations());
            ctx = (LdapContext) template.getContextSource().getContext(dn.toString(), oldPwd);

            PasswordModifyControl ctrl = new PasswordModifyControl(dn.toString(), oldPwd, newPwd);

            ctx.setRequestControls(new Control[] { new PasswordPolicyControl() }); // For OpenDS to report password problems
            ctx.extendedOperation(ctrl);

            return PasswordChangeResult.SUCCESS;
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Unable to read result of password modify control");
        } catch (AuthenticationException e) {
            return PasswordChangeResult.INVALID_OLD_PWD;
        } catch (InvalidAttributeValueException e) { // DSEE returns an error code (19) and an explanation string
            String reason = e.getExplanation();

            if (logger.isDebugEnabled()) {
                logger.debug("Password change rejected because: " + reason);
            }

            if (reason.contains("minimum age")) {
                return PasswordChangeResult.TOO_YOUNG;
            } else if (reason.contains("short")) {
                return PasswordChangeResult.TOO_SHORT;
            } else {
                return PasswordChangeResult.NOT_COMPLEX;
            }
        } catch (NamingException e) { // DSEE returns an error code (49), but no explanation - need to view response control

            if (ctx != null) {

                Control[] controls = null;

                try {
                    controls = ctx.getResponseControls();
                } catch (NamingException ex) {
                    logger.warn("Unable to retreive LDAP controls from password change response", ex);
                }

                if (controls == null) {
                    logger.error("Unable to determine password change results");
                    return PasswordChangeResult.ERROR;
                }

                for (Control control : controls) {
                    if (PasswordPolicyControl.OID.equals(control.getID())) {

                        PasswordPolicyResponseControl pp = new PasswordPolicyResponseControl(control.getEncodedValue());

                        if (logger.isDebugEnabled()) {
                            logger.debug("Password changed attempt returned error status: " + pp.getErrorStatus());
                        }

                        switch (pp.getErrorStatus()) {
                            case PASSWORD_TOO_YOUNG:
                                return PasswordChangeResult.TOO_YOUNG;
                            case PASSWORD_TOO_SHORT:
                                return PasswordChangeResult.TOO_SHORT;
                            case INSUFFICIENT_PASSWORD_QUALITY:
                                return PasswordChangeResult.NOT_COMPLEX;
                            case PASSWORD_IN_HISTORY:
                                return PasswordChangeResult.IN_HISTORY;
                            default:
                                return PasswordChangeResult.ERROR;
                        }

                    }

                }
            }

            logger.error("Unexpected error changing password: ", e);

            return PasswordChangeResult.ERROR;

        } finally {
            if (ctx != null) {
                try {
                    ctx.setRequestControls(null);
                } catch (NamingException e) {
                    logger.warn("Unable to reset request controls after password change attempt");
                }
            }
        }

    }

    private PasswordModifyResponse passwordModify(final PasswordModifyControl control) {
        PasswordModifyResponse response = (PasswordModifyResponse) ldapTemplate.getLdapOperations().executeReadOnly(
                new ContextExecutor() {
                    public Object executeWithContext(final DirContext ctx) throws javax.naming.NamingException {
                        LdapContext ldapContext = (LdapContext) ctx;
                        return ldapContext.extendedOperation(control);
                    }
                });
        return response;
    }

    private DistinguishedName createAbsoluteDNFromUsername(final String username) {
        final DistinguishedName dn = new DistinguishedName(usersAbsoluteBaseName);
        dn.append(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.USERNAME), username);
        return dn;
    }

    /**
     * @param readOnlyLdapTemplate
     *            the readOnlyLdapTemplate to set
     */
    public void setReadOnlyLdapTemplate(final SimpleLdapTemplate readOnlyLdapTemplate) {
        this.readOnlyLdapTemplate = readOnlyLdapTemplate;
    }

}
