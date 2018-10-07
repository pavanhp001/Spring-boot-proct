/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2011
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.exception.UserMissingException;
import abc.xyz.pts.bcs.admin.web.command.Action;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.util.WebConstants;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * AdminUserSearchController.java
 * Annotated controller for handling Admin user search
 */
@Controller
@RequestMapping("adminUserSearch.form")
public class AdminUserSearchController extends AdminUserCommonController {

    private static final String UPDATE_CONFLICT_MSG = "record.update.conflict";
    private static final String ORDER_BY_ASC = "ASC";
    private static final String DISPLAY_MODE = "displayMode";
    private static final String ADMIN_SEARCH_COMMAND = "command";
    private static final String ADMIN_SEARCH_TABLE_COMMAND = "adminSearchTableCommand";
    private static final String ADMIN_SESSION_SEARCH_COMMAND = "adminSearchCommand";

    private static final String SUCCESS_VIEW = "adminUserSearch.view";
    private static final String HOME_VIEW = "redirect:/";
    public static final String REPORT_DATA_LOCATION = "printableData";
    private static final String USER = "user";
    public static final String DISABLED = "disabled";
    public static final String USERNAME = "username";

    private static final String REPORT_VIEW = "adminUserSearch.report";
    private static final String REPORT_VIEW_RTL = "adminUserSearchRTL.report";
    private static final String REPORT_DATA = "reportData";
    public static final String SHOW_PRINT_POPIN = "showPrintPopin";
    public static final String RESULTS = "results";

    @Autowired
    private PropertyEditorRegistrar customPropertyEditorRegistrar;
    @Autowired
    private DefaultBeanValidator commonsValidator;

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        this.customPropertyEditorRegistrar.registerCustomEditors(binder);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    @ModelAttribute
    public void referenceData(final ModelMap model) {
        super.referenceData(model);
        model.addAttribute(DISPLAY_MODE, true);
    }

    @ModelAttribute(ADMIN_SEARCH_TABLE_COMMAND)
    public TableActionCommand newTableCommand() {
        return new TableActionCommand();
    }

    @ModelAttribute(ADMIN_SEARCH_COMMAND)
    public AdminUserSearchCommand newDefaultCommand() {
        AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        adminUserSearchCommand.setAction(Action.VIEW);
        adminUserSearchCommand.setUser(new User());
        return adminUserSearchCommand;
    }

    @RequestMapping(method = RequestMethod.GET, params = "locale")
    public String changeLocale(final HttpSession session, final ModelMap model) throws Exception {
        AdminUserSearchCommand adminUserSearchCommand = (AdminUserSearchCommand) session.getAttribute(ADMIN_SEARCH_COMMAND);
        TableActionCommand adminSearchTableCommand = (TableActionCommand) session.getAttribute(ADMIN_SEARCH_TABLE_COMMAND);
        adminSearchTableCommand.setPrintAll(false);
        addCommandObjects(adminSearchTableCommand, adminUserSearchCommand, model, session);
        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST, params = "reset")
    public String processReset(final ModelMap model, final SessionStatus status, final HttpSession session) throws Exception {
        removeSessionCommands(session);
        AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        adminUserSearchCommand.setAction(Action.VIEW);
        adminUserSearchCommand.setUser(new User());
        session.setAttribute(ADMIN_SEARCH_COMMAND, adminUserSearchCommand);
        session.setAttribute(ADMIN_SEARCH_TABLE_COMMAND, new TableActionCommand());
        status.setComplete();
        return SUCCESS_VIEW;
    }

    @AuditableEvent(Event.MENU_SELECTION)
    @RequestMapping(method = RequestMethod.GET)
    public String getForm(@ModelAttribute(ADMIN_SEARCH_TABLE_COMMAND) final TableActionCommand adminSearchTableCommand,
            @ModelAttribute(ADMIN_SEARCH_COMMAND) final Object command,
            final BindingResult result,
            final HttpSession session,
            final ModelMap model) throws Exception {
        removeSessionCommands(session);
        AdminUserSearchCommand adminUserSearchCommand = (AdminUserSearchCommand) command;
        addCommandObjects(adminSearchTableCommand, adminUserSearchCommand, model, session);
        return SUCCESS_VIEW;
    }

    @AuditableEvent(Event.SEARCH_USER)
    @RequestMapping(method = RequestMethod.POST)
    public String processSearch(@ModelAttribute(ADMIN_SEARCH_TABLE_COMMAND) final TableActionCommand adminSearchTableCommand
            , @ModelAttribute(ADMIN_SEARCH_COMMAND) final Object command
            , final BindingResult errors
            , final HttpSession session
            , final ModelMap model)
            throws Exception {
        AdminUserSearchCommand adminUserSearchCommand = (AdminUserSearchCommand) command;
        commonsValidator.validate(adminUserSearchCommand, errors);
        if (errors.getAllErrors().isEmpty()) {
            adminUserSearchCommand.setAction(Action.VIEW);
            adminSearchTableCommand.setDefaultTableStatus(USERNAME, ORDER_BY_ASC);
            adminSearchTableCommand.setDescending(true);
            retrieveSearchResults(adminUserSearchCommand, adminSearchTableCommand, model, session);
            session.setAttribute(REPORT_DATA_LOCATION, adminUserSearchCommand.copyProperties(new AdminUserSearchCommand()));
        }
        return SUCCESS_VIEW;

    }

    @RequestMapping(method = RequestMethod.POST, params = "close")
    public String processClose(final HttpSession session, final ModelMap model) throws Exception {
        AdminUserSearchCommand adminUserSearchCommand = (AdminUserSearchCommand) session.getAttribute(ADMIN_SESSION_SEARCH_COMMAND);
        TableActionCommand tableActionCommand = (TableActionCommand) session.getAttribute(ADMIN_SEARCH_TABLE_COMMAND);

        if (adminUserSearchCommand != null && CollectionUtils.isNotEmpty(adminUserSearchCommand.getRecords())) {
            retrieveSearchResults(adminUserSearchCommand, tableActionCommand, model, session);
            return SUCCESS_VIEW;
        }
        return HOME_VIEW;
    }

    @AuditableEvent(Event.DELETE_USER)
    @RequestMapping(method = RequestMethod.POST, params = "deleteuser")
    public String processDeleteUser(@ModelAttribute(ADMIN_SEARCH_TABLE_COMMAND) final TableActionCommand adminSearchTableCommand
            , @ModelAttribute(ADMIN_SEARCH_COMMAND) final Object command
            , final BindingResult errors
            , final HttpSession session
            , final ModelMap model) throws Exception {

        AdminUserSearchCommand adminUserSearchCommand = (AdminUserSearchCommand) command;
        adminUserSearchCommand.setAction(Action.DELETE);

        try {
            getAdminService().deleteUser(adminUserSearchCommand.getSelectedUsername());
            adminUserSearchCommand.setRecords(null);
            addCommandObjects(adminSearchTableCommand, adminUserSearchCommand, model, session);
        } catch (UserMissingException e) {
            errors.reject(UPDATE_CONFLICT_MSG);
        }

        return SUCCESS_VIEW;

    }

    protected void addCommandObjects(final TableActionCommand adminSearchTableCommand, final AdminUserSearchCommand adminUserSearchCommand, final ModelMap model, final HttpSession session) {
        model.addAttribute(ADMIN_SEARCH_COMMAND, adminUserSearchCommand);
        model.addAttribute(ADMIN_SEARCH_TABLE_COMMAND, adminSearchTableCommand);
        model.addAttribute(USER, adminUserSearchCommand.getUser());
        model.addAttribute(SHOW_PRINT_POPIN, adminSearchTableCommand.showPrintPopin());
        session.setAttribute(ADMIN_SEARCH_TABLE_COMMAND, adminSearchTableCommand);
        session.setAttribute(ADMIN_SEARCH_COMMAND, adminUserSearchCommand);
        session.setAttribute(ADMIN_SESSION_SEARCH_COMMAND, adminUserSearchCommand);
    }

    protected void retrieveSearchResults(final AdminUserSearchCommand adminUserSearchCommand, final TableActionCommand adminSearchTableCommand, final ModelMap model, final HttpSession session)
            throws Exception {
        List<User> results = null;
        results = getAdminService().getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        // Set current Sort By to Previous Sort By value
        adminSearchTableCommand.setPreviousSortBy();
        adminUserSearchCommand.setRecords(results);
        adminUserSearchCommand.setTotalRecordsRetrieved(BigInteger.valueOf(results.size()));
        // check for next page availability
        setPageAvailableValue(adminSearchTableCommand);
        addCommandObjects(adminSearchTableCommand, adminUserSearchCommand, model, session);
    }

    protected void retrieveScreenData(final ModelMap model,
            final TableActionCommand adminSearchTableCommand, final HttpSession session) throws Exception {
        AdminUserSearchCommand adminUserSearchCommand = (AdminUserSearchCommand) session.getAttribute(ADMIN_SEARCH_COMMAND);
        retrieveSearchResults(adminUserSearchCommand, adminSearchTableCommand, model, session);
    }

    @RequestMapping(method = RequestMethod.POST, params = "sort")
    public String processSort(@ModelAttribute(ADMIN_SEARCH_TABLE_COMMAND) final TableActionCommand adminUserTableCommand, final BindingResult errors,
            final HttpSession session, final ModelMap model) throws Exception {
        // Get Previous sortBy value from session
        TableActionCommand oldTableCommand = (TableActionCommand) session.getAttribute(ADMIN_SEARCH_TABLE_COMMAND);
        adminUserTableCommand.calculateSortDirection(oldTableCommand);
        adminUserTableCommand.setPageNumber(oldTableCommand.getPageNumber());
        retrieveScreenData(model, adminUserTableCommand, session);
        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST, params = "next")
    public String processNextPage(final HttpSession session, final ModelMap model) throws Exception {
        // Call next page method to add the Page Number by One
        TableActionCommand adminSearchTableCommand = (TableActionCommand) session.getAttribute(ADMIN_SEARCH_TABLE_COMMAND);
        adminSearchTableCommand.nextPage();
        retrieveScreenData(model, adminSearchTableCommand, session);
        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST, params = "previous")
    public String processPreviousPage(final HttpSession session, final ModelMap model) throws Exception {
        // Call previous page method to minus the Page Number by One
        TableActionCommand adminSearchTableCommand = (TableActionCommand) session.getAttribute(ADMIN_SEARCH_TABLE_COMMAND);
        adminSearchTableCommand.previousPage();
        retrieveScreenData(model, adminSearchTableCommand, session);
        return SUCCESS_VIEW;
    }

    private void removeSessionCommands(final HttpSession session) {
        session.removeAttribute(ADMIN_SEARCH_COMMAND);
        session.removeAttribute(ADMIN_SEARCH_TABLE_COMMAND);
    }

    private void setPageAvailableValue(final TableActionCommand tableCommand) {
        int pageNumber = tableCommand.getPageNumber();
        int pageSize = tableCommand.getPageSize();
        if (tableCommand.totalResults > (pageNumber * pageSize)) {
            tableCommand.setPagesAvailable(true);
        } else {
            tableCommand.setPagesAvailable(false);
        }
    }

    @AuditableEvent(Event.EXPORT_TO_CSV)
    @RequestMapping(method = RequestMethod.GET, params = "viewreport=xls")
    public String processSearchFileCsv(@ModelAttribute(ADMIN_SEARCH_TABLE_COMMAND) final TableActionCommand adminUserTableCommand,
            @ModelAttribute(ADMIN_SEARCH_COMMAND) final AdminUserSearchCommand command,
            final BindingResult bindingResult,
            final HttpSession session,
            final ModelMap model,
            final Locale locale,
            @RequestParam(value = "pages", required = false) final String pagesToPrint) throws Exception {
        if (!bindingResult.hasErrors()) {
            setupJasperModel("xls", model, session, pagesToPrint);
        }

        if ("ar".equals(locale.getLanguage())) {
            return REPORT_VIEW_RTL;
        } else {
            return REPORT_VIEW;
        }
    }

    @AuditableEvent(Event.PRINT)
    @RequestMapping(method = RequestMethod.GET, params = "viewreport=pdf")
    public String processSearchFilePrint(@ModelAttribute(ADMIN_SEARCH_TABLE_COMMAND) final TableActionCommand adminUserTableCommand,
            @ModelAttribute(ADMIN_SEARCH_COMMAND) final AdminUserSearchCommand command,
            final BindingResult bindingResult,
            final HttpSession session,
            final ModelMap model,
            final Locale locale,
            @RequestParam(value = "pages", required = false) final String pagesToPrint) throws Exception {
        if (!bindingResult.hasErrors()) {
            setupJasperModel("pdf", model, session, pagesToPrint);
        }

        if ("ar".equals(locale.getLanguage())) {
            return REPORT_VIEW_RTL;
        } else {
            return REPORT_VIEW;
        }
    }

    private void setupJasperModel(final String format, final ModelMap model, final HttpSession session, final String pagesTobePrint) throws Exception {
        AdminUserSearchCommand adminUserSearchCommand = (AdminUserSearchCommand) session.getAttribute(ADMIN_SEARCH_COMMAND);
        TableActionCommand adminSearchTableCommand = (TableActionCommand) session.getAttribute(ADMIN_SEARCH_TABLE_COMMAND);
        ArrayList<Object> screenPrintData = new ArrayList<Object>();
        AdminUserSearchCommand adminUserSearchCommandForPrint = new AdminUserSearchCommand();

        if (adminUserSearchCommand != null && adminSearchTableCommand != null) {
            adminSearchTableCommand.setPrintAll(false);
            if (WebConstants.PRINT_ALL_PAGES.equals(pagesTobePrint)) {
                adminSearchTableCommand.setPrintAll(true);
                List<User> results = getAdminService().getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
                adminUserSearchCommandForPrint.setRecords(results);
            }
            else {
                adminUserSearchCommandForPrint.setRecords(adminUserSearchCommand.getRecords());
            }
            adminUserSearchCommandForPrint.setUser(adminUserSearchCommand.getUser());
        }

        if (!"pdf".equals(format)) {
            model.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        screenPrintData.add(adminUserSearchCommandForPrint);
        model.put(REPORT_DATA, new JRBeanCollectionDataSource(screenPrintData));
        model.put(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY, format);
    }

}
