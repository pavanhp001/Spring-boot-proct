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

import java.io.ByteArrayOutputStream;

/**
 * Bean used for sending report data as a message.
 *
 * @version: $Id: ReportMessage.java 779 2008-08-08 09:46:09Z wgriffiths $
 */
public class ReportMessage {

    private String subject;
    private ByteArrayOutputStream attachment;
    private String attachmentFileName;
    private String attachmentContentType;
    private String errorText;

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String arg0) {
        this.subject = arg0;
    }

    public ByteArrayOutputStream getAttachment() {
        return attachment;
    }

    public void setAttachment(final ByteArrayOutputStream arg0) {
        this.attachment = arg0;
    }

    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(final String arg0) {
        this.attachmentFileName = arg0;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(final String arg0) {
        this.attachmentContentType = arg0;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(final String arg0) {
        this.errorText = arg0;
    }
}
