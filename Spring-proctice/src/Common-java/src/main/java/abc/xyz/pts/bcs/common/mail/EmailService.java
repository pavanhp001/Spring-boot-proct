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
package abc.xyz.pts.bcs.common.mail;

import org.springframework.mail.MailException;

public interface EmailService {

    void sendMail(String body, String subject, String toAddress);

    void sendMailSynchronous(String body, String subject, String toAddress) throws MailException;

    void sendAttachmentMail(String emailTo, String subject, String fileName, String contentType, byte[] attachmentBytes);
}
