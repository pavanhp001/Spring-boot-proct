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
import java.util.Calendar;

/**
 * Bean used to hold the output from the generated report along with the type of output produced.
 *
 * @version: $Id: ReportOutput.java 779 2008-08-08 09:46:09Z wgriffiths $
 */
public class ReportOutput {

    private ByteArrayOutputStream content;
    private String contentType;
    private String fileSuffix;
    private Calendar runTime;
    private Throwable error;

    public ByteArrayOutputStream getContent() {
        return content;
    }

    public void setContent(final ByteArrayOutputStream arg0) {
        this.content = arg0;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String arg0) {
        this.contentType = arg0;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(final String arg0) {
        this.fileSuffix = arg0;
    }

    public Calendar getRunTime() {
        return runTime;
    }

    public void setRunTime(final Calendar arg0) {
        this.runTime = arg0;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(final Throwable arg0) {
        this.error = arg0;
    }
}
