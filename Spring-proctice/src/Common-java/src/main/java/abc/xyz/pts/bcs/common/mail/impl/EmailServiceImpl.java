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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.MimeMessageHelper;

import abc.xyz.pts.bcs.common.communication.impl.ConcurrentPropertyManager;
import abc.xyz.pts.bcs.common.mail.EmailService;

public class EmailServiceImpl implements EmailService {

    private static Log log = LogFactory.getLog(EmailService.class);
    private String emailFromField;
    private String emailReplyToField;
    private String encoding = "UTF-8";
    private ConcurrentPropertyManager concurrentPropertyManager;
    private ExecutorService executorService = null;
    private transient List<String> mailExceptionsIgnoreList;

    /**
     * @param mailExceptionsIgnoreList
     *            the mailExceptionsIgnoreList to set
     */
    public void setMailExceptionsIgnoreList(final List<String> mailExceptionsIgnoreList) {
        this.mailExceptionsIgnoreList = mailExceptionsIgnoreList;
    }

    @Override
    public void sendAttachmentMail(final String emailTo, final String subject, final String fileName,
            final String contentType, final byte[] attachmentBytes) {

        final Runnable command = new Runnable() {
            @Override
            public void run() {
                // we must create a new mail sender object each time as it is
                // not thread safe.
                final MultiNodeJavaMailSenderImpl javaMailSender = new MultiNodeJavaMailSenderImpl();
                javaMailSender.setConcurrentPropertyManager(concurrentPropertyManager);
                javaMailSender.setMailExceptionsIgnoreList(mailExceptionsIgnoreList);
                MimeMessage emailMsg = javaMailSender.createMimeMessage();
                emailMsg = buildAttachMailMessage(emailMsg, emailTo, subject, fileName, contentType, attachmentBytes);
                if (emailMsg != null) {
                    try {
                        javaMailSender.send(emailMsg);
                        log.debug("Email sent to: " + emailTo);
                    } catch (final MailException e) {
                        final StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("An error occurred whilst sending emails.");
                        stringBuilder.append("Attachment File name: ").append(fileName);
                        stringBuilder.append(" Email Address: ").append(emailTo);
                        log.error(stringBuilder, e);
                    }
                } else {
                    log.error("No mail send, failed to construct attachment mail message.");
                }
            }
        };
        executorService.execute(command);

    }

    private MimeMessage buildAttachMailMessage(final MimeMessage mimeMessage, final String emailTo, final String subject,
            final String fileName, final String contentType, final byte[] attachmentBytes) {
        MimeMessage mimeMessageToReturn = null;
        try {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, getEncoding());
            helper.setTo(emailTo);
            helper.setFrom(getEmailFromField());
            helper.setReplyTo(getEmailReplyToField());
            helper.setSubject(subject);
            helper.addAttachment(fileName, new ByteArrayResource(attachmentBytes), contentType);
            mimeMessageToReturn = helper.getMimeMessage();
        } catch (final MessagingException e) {
            log.error("Failed to build eMail message.", e);
        }
        return mimeMessageToReturn;
    }

    private String getEmailFromField() {
        return emailFromField;
    }

    public void setEmailFromField(final String emailFromField) {
        this.emailFromField = emailFromField;
    }

    private String getEmailReplyToField() {
        return emailReplyToField;
    }

    public void setEmailReplyToField(final String emailReplyToField) {
        this.emailReplyToField = emailReplyToField;
    }

    @Override
    public void sendMail(final String body, final String subject, final String toAddress) {

        final Runnable command = new Runnable() {
            @Override
            public void run() {
                // we must create a new mail sender object each time as it is not thread safe.
                final MultiNodeJavaMailSenderImpl javaMailSender = new MultiNodeJavaMailSenderImpl();
                javaMailSender.setConcurrentPropertyManager(concurrentPropertyManager);
                javaMailSender.setMailExceptionsIgnoreList(mailExceptionsIgnoreList);
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                mimeMessage = buildMailMessage(mimeMessage, body, subject, toAddress);
                if (mimeMessage != null) {
                    try {
                        javaMailSender.send(mimeMessage);
                        log.debug("Email sent to: " + toAddress);
                    } catch (final MailException e) {
                        final StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("An error occurred whilst sending emails.");
                        stringBuilder.append("Message: ").append(body);
                        stringBuilder.append(" Email Address: ").append(toAddress);
                        log.error(stringBuilder, e);
                    }
                } else {
                    log.error("No mail send, failed to construct mail message.");
                }
            }
        };
        executorService.execute(command);
    }

    /**
     * Try to send email, throw exception on failure
     * @param body Email body
     * @param subject Email subject
     * @param toAddress Email address
     * @throws MailException
     */
    @Override
    public void sendMailSynchronous(final String body, final String subject, final String toAddress) throws MailException {
        final MultiNodeJavaMailSenderImpl javaMailSender = new MultiNodeJavaMailSenderImpl();
        javaMailSender.setConcurrentPropertyManager(concurrentPropertyManager);
        javaMailSender.setMailExceptionsIgnoreList(mailExceptionsIgnoreList);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage = buildMailMessage(mimeMessage, body, subject, toAddress);
        if (mimeMessage != null) {

                javaMailSender.send(mimeMessage);

        } else {
            throw new MailPreparationException("No mail send, failed to construct mail message.");
        }
    }

    private MimeMessage buildMailMessage(final MimeMessage mimeMessage, final String body, final String subject,
            final String toAddress) {
        MimeMessage mimeMessageToReturn = null;
        try {
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, getEncoding());
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.setFrom(getEmailFromField());
            mimeMessageHelper.setReplyTo(getEmailReplyToField());
            mimeMessageHelper.setTo(toAddress);
            mimeMessageToReturn = mimeMessageHelper.getMimeMessage();
        } catch (final MessagingException e) {
            log.error("Failed to build eMail message.", e);
        }

        return mimeMessageToReturn;

    }

    private String getEncoding() {
        return encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    /**
     * @param concurrentPropertyManager
     *            the concurrentPropertyManager to set
     */
    public void setConcurrentPropertyManager(final ConcurrentPropertyManager concurrentPropertyManager) {
        this.concurrentPropertyManager = concurrentPropertyManager;
    }

    /**
     * @param threadPoolSize
     *            the threadPoolSize to set Do not call this after spring configuration.
     */
    public void setThreadPoolSize(final int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }
}
