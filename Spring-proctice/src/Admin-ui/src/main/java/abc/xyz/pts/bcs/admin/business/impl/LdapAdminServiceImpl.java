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
package abc.xyz.pts.bcs.admin.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.MessageSourceAccessor;

import abc.xyz.pts.bcs.admin.business.AdminService;
import abc.xyz.pts.bcs.admin.dao.AirportDao;
import abc.xyz.pts.bcs.admin.dao.PasswordChangeResult;
import abc.xyz.pts.bcs.admin.dao.UserDao;
import abc.xyz.pts.bcs.admin.dto.Airport;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.exception.InvalidAirportException;
import abc.xyz.pts.bcs.admin.exception.UserExistsException;
import abc.xyz.pts.bcs.admin.exception.UserMissingException;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;
import abc.xyz.pts.bcs.common.mail.EmailService;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

public class LdapAdminServiceImpl implements AdminService, ApplicationContextAware {

    private static final String PASSWORD_SUBJECT = "admin.email.password.subject";
    private static final String USERNAME_SUBJECT = "admin.email.username.subject";
    private static final String EMAIL_PASSWORD_TEXT = "admin.email.password.text";
    private static final String EMAIL_USER_NAME_TEXT = "admin.create.user.email.text";

    private UserDao userDao = null;
    private EmailService emailService = null;
    private AirportDao airportDao = null;
    private Locale locale = null;
    private MessageSourceAccessor messageSourceAccessor = null;

    /**
     * @param locale
     *            the locale to set
     */
    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    /**
     * @param emailService
     *            the emailService to set
     */
    public void setEmailService(final EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * @param personDao
     *            the personDao to set
     */
    public void setUserDao(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(final String username) {
        return userDao.getUser(username);
    }

    public void updateUser(final User user) throws UserMissingException, InvalidAirportException {
        String location = user.getLocation();
        if (location != null) {
            location = location.toUpperCase(locale);
            user.setLocation(location);
            if (airportDao != null) {
            	if (!airportDao.exists(location)) {
            		throw new InvalidAirportException(location);
            	}
            }
        }
        user.setPassword("");
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(final String username) throws UserMissingException {
        userDao.deleteUser(username);
    }

    @Override
    public void addUser(final User user) throws UserExistsException, InvalidAirportException {
        String location = user.getLocation();
        if (location != null) {
            location = location.toUpperCase(locale);
            user.setLocation(location);
            if (airportDao != null) {
            	if (!airportDao.exists(location)) {
            		throw new InvalidAirportException(location);
            	}
            }
        }
        user.setPassword(null);
        userDao.addUser(user);
        // now email the user
        String emailText = getMessage(EMAIL_USER_NAME_TEXT, new String[] { "", user.getName(), user.getUsername() });
        String subject = getMessage(USERNAME_SUBJECT, new String[] {});
        emailService.sendMail(emailText, subject, user.getEmail());
        // now email the password
        sendPasswordEmail(user);

    }

    @Override
    public void resetPassword(final String username) {
        User user = getUser(username);
        // String newPassword = createRandomPassword();
        String newPassword = userDao.resetPassword(user.getUsername());
        user.setPassword(newPassword);
        // now email the password
        sendPasswordEmail(user);
    }

    @Override
    public PasswordChangeResult changePassword(final String userName, final String oldPwd, final String newPwd) {
        return userDao.changePassword(userName, oldPwd, newPwd);
    }

    private void sendPasswordEmail(final User user) {
        String emailText = null;
        // Escape $'s in password as is special char in regex replaceAll
        String password = StringUtils.replace(user.getPassword(), "$", "\\$");

        emailText = getMessage(EMAIL_PASSWORD_TEXT, new String[] { "", user.getName(), password });

        String subject = getMessage(PASSWORD_SUBJECT, new String[] {});
        emailService.sendMail(emailText, subject, user.getEmail());
    }

    /**
     * There seems to be a bug with the message source accessor that causes it not to replace the arguments in a message
     * when specific characters exist. So we must do it by hand.
     *
     * @param messageKey
     * @param arguments
     * @return
     */
    private String getMessage(final String messageKey, final String[] arguments) {
        String translated = messageSourceAccessor.getMessage(messageKey, locale);
        for (int i = 0; i < arguments.length; i++) {
            translated = translated.replaceAll("\\{" + i + "\\}", arguments[i]);
        }
        return translated;
    }

    @Override
    public void setApplicationContext(final ApplicationContext context) {
        this.messageSourceAccessor = new MessageSourceAccessor(context);
    }

    /**
     * @param airportDao
     *            the airportDao to set
     */
    public void setAirportDao(final AirportDao airportDao) {
        this.airportDao = airportDao;
    }

    @Override
    public List<Airport> getValidAirports() {
    	if (airportDao!=null) {
    		return airportDao.getAirports();
    	} else {
    		return new ArrayList<Airport>();
    	}
    }

    @Override
    public List<User> getSearchUsers(
            final AdminUserSearchCommand adminUserSearchCommand,
            final TableActionCommand adminSearchTableCommand) {
        return userDao.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
    }

}
