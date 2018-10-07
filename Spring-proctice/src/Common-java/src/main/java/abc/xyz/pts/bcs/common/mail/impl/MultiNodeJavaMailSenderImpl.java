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
package abc.xyz.pts.bcs.common.mail.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import abc.xyz.pts.bcs.common.communication.ConcurrentPropertyManagerInterface;

public class MultiNodeJavaMailSenderImpl implements JavaMailSender {

    @Override
    public MimeMessage createMimeMessage(final InputStream inputStream) throws MailException {
        return getJavaMailSenderImpl().createMimeMessage(inputStream);
    }

    @Override
    public void send(final MimeMessage[] mimeMessages) throws MailException {
        getJavaMailSenderImpl().send(mimeMessages);
    }

    @Override
    public void send(final MimeMessagePreparator mimeMessagePreparator) throws MailException {
        getJavaMailSenderImpl().send(mimeMessagePreparator);
    }

    @Override
    public void send(final MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
        getJavaMailSenderImpl().send(mimeMessagePreparators);
    }

    @Override
    public void send(final SimpleMailMessage simpleMailMessage) throws MailException {
        getJavaMailSenderImpl().send(simpleMailMessage);
    }

    @Override
    public void send(final SimpleMailMessage[] simpleMailMessages) throws MailException {
        getJavaMailSenderImpl().send(simpleMailMessages);
    }

    private transient JavaMailSenderImpl javaMailSenderImpl;
    private transient Properties mailProperties;
    private transient ConcurrentPropertyManagerInterface concurrentPropertyManager = null;
    private static final byte ZERO = 0;
    private static Log log = LogFactory.getLog(MultiNodeJavaMailSenderImpl.class);
    private transient List<String> mailExceptionsIgnoreList;

    @Override
    public MimeMessage createMimeMessage() {
        return getJavaMailSenderImpl().createMimeMessage();
    }

    @Override
    public void send(final MimeMessage mimeMessage) {
        do {
            try {
                getJavaMailSenderImpl().send(mimeMessage);
                // mailProperties & javaMailSenderImpl to create a new instance
                // every time mail is sent.
                mailProperties = null;
                javaMailSenderImpl = null;
            } catch (final MailSendException e) {
                if (canIgnoreException(e)) {
                    log
                            .warn(
                                    "Message not sent but no attempt to retry will be made as the exception is in the ignore list ",
                                    e);
                    javaMailSenderImpl = null;
                } else {
                    log.error("Failed to connect to server: " + mailProperties, e);
                    concurrentPropertyManager.passivateProperties(mailProperties);
                    reInitialiseJavaMailSender();
                }
            }
        } while (javaMailSenderImpl != null);
    }

    public void sendSynchronous(final MimeMessage mimeMessage) throws MailException {
        getJavaMailSenderImpl().send(mimeMessage);
    }

    private JavaMailSenderImpl getJavaMailSenderImpl() {
        if (javaMailSenderImpl == null) {
            reInitialiseJavaMailSender();
        }
        return javaMailSenderImpl;
    }

    private void reInitialiseJavaMailSender() {
        if (concurrentPropertyManager == null
                || (concurrentPropertyManager.numberOfActiveProperties() == ZERO && concurrentPropertyManager
                        .numberOfPassivatedProperties() == ZERO)) {
            throw new MailPreparationException("Mail properties are not specified.");
        } else if (concurrentPropertyManager.numberOfActiveProperties() == ZERO) {
            throw new MailPreparationException("No active/live mail server is avaliable. All servers ("
                    + concurrentPropertyManager.numberOfPassivatedProperties() + ") are passivated.");
        } else {
            javaMailSenderImpl = new JavaMailSenderImpl();
            mailProperties = concurrentPropertyManager.getActiveProperties();
            javaMailSenderImpl.setJavaMailProperties(mailProperties);
        }
    }

    public void setConcurrentPropertyManager(final ConcurrentPropertyManagerInterface concurrentPropertyManager) {
        this.concurrentPropertyManager = concurrentPropertyManager;
    }

    public void setMailExceptionsIgnoreList(final List<String> mailExceptionsIgnoreList) {
        this.mailExceptionsIgnoreList = mailExceptionsIgnoreList;
    }

    private boolean canIgnoreException(final Exception e) {
        boolean result = false;
        final String exceptionMessage = e.getMessage();
        if (mailExceptionsIgnoreList != null) {
            for (final String anIgnoreableExceptionMessage : mailExceptionsIgnoreList) {
                if (exceptionMessage.contains(anIgnoreableExceptionMessage)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
