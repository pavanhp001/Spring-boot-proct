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
package abc.xyz.pts.bcs.common.web;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;

import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;

/**
 * Created a Spring jasper reports view for the rendering of RTFs.
 */
public class JasperReportsRtfView extends AbstractJasperReportsSingleFormatView {

    public JasperReportsRtfView() {
        setContentType("text/rtf");
    }

    protected JRExporter createExporter() {
        return new JRRtfExporter();
    }

    protected boolean useWriter() {
        return true;
    }

}
