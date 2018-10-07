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
package abc.xyz.pts.bcs.common.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

/**
 * View reports with no data in the various supported formats. Takes a parameter to define which format to render,
 * supports a default value if none otherwise specified. This parameter's value is passed into the Spring Jasper
 * rendering so that JasperReportsMultiFormatView can choose the appropriate view.
 */
public class ViewNoDataReport extends AbstractController {

    private String reportView;
    private String formatParameter;
    private String defaultFormat;
    private String reportDataKey;

    /**
     * Set the name of the report view to be rendered.
     *
     * @param reportView
     *            The name of the report view to be rendered.
     */
    public void setReportView(final String reportView) {
        this.reportView = reportView;
    }

    /**
     * Set the parameter that will be set to define the report format.
     *
     * @param formatParameter
     *            The parameter that will be set to define the report format.
     */
    public void setFormatParameter(final String formatParameter) {
        this.formatParameter = formatParameter;
    }

    /**
     * Set the default format to be rendered.
     *
     * @param defaultFormat
     *            The default format to be rendered.
     */
    public void setDefaultFormat(final String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    /**
     * Set the name of the report data model. Should reflect the set on the report view.
     *
     * @param reportDataKey
     *            the name of the report data model. Should reflect the set on the report view.
     */
    public void setReportDataKey(final String reportDataKey) {
        this.reportDataKey = reportDataKey;
    }

    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        /*
         * Set the format up from the parameter, defaulting a value in if necessary.
         */
        String format = request.getParameter(formatParameter);
        format = format == null ? defaultFormat : format;

        /*
         * Set up the model with the report data and the format.
         */
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY, format);
        model.put(reportDataKey, new JREmptyDataSource());

        return new ModelAndView(reportView, model);
    }
}
