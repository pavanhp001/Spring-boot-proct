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

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.dto.AbstractRequeryableCommand;
import abc.xyz.pts.bcs.common.util.CustomCalendarEditor;

/**
 * Abstract controller for searches and lists etc. Handles user info (such as working airport and role), paging and
 * sorting and jasper reports etc.
 *
 */
public abstract class AbstractDataController extends SimpleFormController {

    public static final String PARAM_SESSION_ID = "sessionId";

    public static final String DISABLED = "disabled";
    public static final String REPORT_DATA_LOCATION = "printableData";
    private static final String REQUERY = "requery";
    private static final String REPORT_DATA = "reportData";
    private static final String PAGES_PARAM = "pages";
    private long maxRetrievableRecords = 5000L;

    private enum PageSelection {
        ALL("all"), CURRENT("current");
        private String value = null;

        PageSelection(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private String reportView;
    private boolean reportRequest = false;

    private String userProfile;

    @Override
    protected void onBindOnNewForm(final HttpServletRequest request, final Object command) throws Exception {

        // remove any left over report command object from previous search screen
        request.getSession().removeAttribute(REPORT_DATA_LOCATION);
    }

    /**
     * @return the maxRetrievableRecords
     */
    protected long getMaxRetrievableRecords() {
        return maxRetrievableRecords;
    }

    /**
     * @param maxRetrievableRecords
     *            the maxRetrievableRecords to set
     */
    public void setMaxRetrievableRecords(final long maxRetrievableRecords) {
        this.maxRetrievableRecords = maxRetrievableRecords;
    }

    /**
     * Get the starting record for a requery
     *
     * @param search
     * @return
     */
    protected BigInteger getStartRecord(final AbstractRequeryableCommand search) {

        return BigInteger
                .valueOf(1 + ((search.getPageNumber().intValue() - 1) * search.getRecordsPerPage().intValue()));

    }

    /**
     * Set page values required for paging/ ordering
     *
     * @param pageValues
     * @param search
     * @return
     */
    protected AbstractRequeryableCommand getPageValues(final AbstractRequeryableCommand search) {
        int startRecordForRequery = 0;
        int recordsPerPage = 0;
        int totalRecordsRetrieved = 0;
        int pageNumber = 0;

        if (search.getStartRecordForRequery() != null) {
            startRecordForRequery = search.getStartRecordForRequery().intValue();
        }
        if (search.getRecordsPerPage() != null) {
            recordsPerPage = search.getRecordsPerPage().intValue();
        }
        if (search.getTotalRecordsRetrieved() != null) {
            totalRecordsRetrieved = search.getTotalRecordsRetrieved().intValue();
        }

        if (search.getPageNumber() != null) {
            pageNumber = search.getPageNumber().intValue();
        }
        search.setRowNumberEnd(BigInteger.valueOf(getEndRowNumber(startRecordForRequery, recordsPerPage,
                totalRecordsRetrieved)));

        search.setPreviousDisabled(getPreviousDisabled(pageNumber));

        search.setNextDisabled(getNextDisabled(totalRecordsRetrieved, search.getRowNumberEnd().intValue()));

        return search;

    }

    /**
     * Returns the last row number to display.
     *
     * @param startRecordForRequery
     * @param recordsPerPage
     * @param totalRecordsRetrieved
     * @return
     */
    private final int getEndRowNumber(final int startRecordForRequery, final int recordsPerPage, final int totalRecordsRetrieved) {

        int lastRecordNumber = startRecordForRequery + recordsPerPage - 1;

        if (lastRecordNumber > totalRecordsRetrieved) {
            return totalRecordsRetrieved;
        } else {
            return lastRecordNumber;
        }
    }

    /**
     * Returns 'disabled' if page number is 1, hence no previous records
     *
     * @param pageNumber
     * @return 'disabled' or empty string
     */
    private final String getPreviousDisabled(final int pageNumber) {
        if (pageNumber == 1) {
            return DISABLED;
        }
        return "";
    }

    /**
     * Returns 'disabled' if totalRecordsRetrieved <= rowNumberEnd, hence no next record
     *
     * @param totalRecordsRetrieved
     *            , rowNumberEnd
     * @return 'disabled' or empty string
     */
    private final String getNextDisabled(final int totalRecordsRetrieved, final int rowNumberEnd) {
        if (totalRecordsRetrieved <= rowNumberEnd) {
            return DISABLED;
        }
        return "";
    }

    @Override
    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) throws Exception {

        super.initBinder(request, binder);

        DateFormat df = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        binder.registerCustomEditor(java.util.Calendar.class, null, new CustomCalendarEditor(df, true));

    }

    /**
     * @return the reportRequest
     */
    protected final boolean isReportRequest() {
        return reportRequest;
    }

    /**
     * Controllers extending this class must implement this method to give access to reporting functionality. Any data
     * retrieval within the controller should be handled by this method.
     *
     * @param request
     * @param command
     * @throws Exception
     */
    protected abstract void retrieveScreenData(final HttpServletRequest request, final Object command) throws Exception;

    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        if (isReportRequest()) {
            Object asc = getReportData(request.getSession());
            if (asc instanceof AbstractRequeryableCommand) {
                String selectedPages = request.getParameter(PAGES_PARAM);
                // we clone the bean to avoid setting properties on the existing bean
                // if we don't the current page will change the next time we page or sort
                AbstractRequeryableCommand clonedBean = (AbstractRequeryableCommand) BeanUtils.cloneBean(asc);
                if (selectedPages == null || selectedPages.equals(PageSelection.CURRENT.getValue())) {
                    // Some controllers are not resetting the page size correctly after ALL pages report request being
                    // called
                    // Here i am adding check if report request for Current page and page size is MaxRetrievableRecords
                    // resetting the page size to default
                    if (clonedBean.getRecordsPerPage().longValue() == getMaxRetrievableRecords()) {
                        clonedBean.setRowNumberEnd(Constants.DEFAULT_RECORDS_PER_PAGE);
                        clonedBean.setRecordsPerPage(Constants.DEFAULT_RECORDS_PER_PAGE);
                        asc = clonedBean;
                    }

                } else if (selectedPages.equals(PageSelection.ALL.getValue())) {
                    // override the report command properties to retrieve all pages
                    clonedBean.setPageNumber(BigInteger.valueOf(1L));
                    // the max retrievable records is set to 5000 by default, but it can be overridden by the subclass's
                    // spring config if required.
                    clonedBean.setRecordsPerPage(BigInteger.valueOf(getMaxRetrievableRecords()));
                    clonedBean.setRowNumberEnd(BigInteger.valueOf(getMaxRetrievableRecords()));
                    clonedBean.setStartRecordForRequery(BigInteger.valueOf(1L));
                    clonedBean.setRequery("true");
                    asc = clonedBean;
                }
            }
            if (asc != null) {
                retrieveScreenData(request, asc);
            }
            return buildJasperMav(request, asc);
        } else {
            return super.handleRequestInternal(request, response);
        }
    }

    protected ModelAndView buildJasperMav(final HttpServletRequest request, final Object reportData) {
        // Get the format from the request URI
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY, format);
        ArrayList<Object> screenPrintData = new ArrayList<Object>();
        screenPrintData.add(reportData);
        model.put(REPORT_DATA, new JRBeanCollectionDataSource(screenPrintData));
        return new ModelAndView(reportView, model);
    }

    /**
     * Set the UserDetails from the request.
     *
     * Check if the submit was from create or from paging buttons. Do not validate if this is a paging operation
     */
    @Override
    protected void onBind(final HttpServletRequest request, final Object command) throws Exception {
        if (request.getParameter(REQUERY) != null && Boolean.valueOf(request.getParameter(REQUERY))) {
            setValidateOnBinding(false);
        } else {
            setValidateOnBinding(true);
        }
        super.onBind(request, command);
    }

    protected void onBindAndValidate(final HttpServletRequest request, final Object command, final BindException errors)
            throws Exception {

        // Remove REPORT_DATA_LOCATION attribute if validation fails to stop reporting on last successful query
        if (errors.hasErrors()) {
            request.getSession().removeAttribute(REPORT_DATA_LOCATION);
        }

        // Check for Requery errors in AbstractRequeryableCommand
        boolean error = false;
        if (errors.getFieldErrorCount("requery") > 0) {
            error = true;
        }
        if (error || errors.getFieldErrorCount("pageNumber") > 0) {
            error = true;
        }
        if (error || errors.getFieldErrorCount("orderByColumn") > 0) {
            error = true;
        }
        if (error || errors.getFieldErrorCount("ascDesc") > 0) {
            error = true;
        }
        if (error || errors.getFieldErrorCount("totalRecordsRetrieved") > 0) {
            error = true;
        }
        if (error || errors.getFieldErrorCount("rowNumberEnd") > 0) {
            error = true;
        }
        if (error) {
            throw new Exception("Hidden fields have been updated during submission");
        }
    }

    /* Takes the given UserDetails object, and appends the given string to the session id
     * Used to avoid conflicts between the web session using the db session context, and the stats/report jobs
     */
    public UserDetails amendSessionId(final UserDetails details,  final String addition) {
            StringBuffer buffer = new StringBuffer(details.getSessionId());
            buffer.append(addition);
            details.setSessionId(buffer.toString());
            if(logger.isDebugEnabled()) {
               logger.debug("New Session ID for stats/reporting is: " + details.getSessionId());
            }
            return details;
    }

    /**
     * @return the reportView
     */
    public final String getReportView() {
        return reportView;
    }

    /**
     * @param reportView
     *            the reportView to set
     */
    public final void setReportView(final String reportView) {
        this.reportView = reportView;
        reportRequest = true;
    }

    protected final void setReportData(final HttpSession session, final Object obj) {
        session.setAttribute(REPORT_DATA_LOCATION, obj);
    }

    protected final Object getReportData(final HttpSession session) {
        return session.getAttribute(REPORT_DATA_LOCATION);
    }

    protected UserDetails getSessionUserDetails(final HttpServletRequest request) {
        return (UserDetails) request.getAttribute(Constants.USER_PROFILE_KEY);
    }

    /**
     * Set the correct key to store for user profile.
     *
     * @param pUserProfile
     *            the key to use for the flight search user profile.
     */
    public final void setUserProfile(final String pUserProfile) {
        this.userProfile = pUserProfile;
    }

    public final String getUserProfile() {
        return userProfile;
    }
}
