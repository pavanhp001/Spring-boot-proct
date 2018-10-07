/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.support.AopUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.BaseCommandController;
import org.springframework.web.servlet.mvc.Controller;

import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroup;
import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroups;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.aspect.AbstractAuditAspect;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;
import abc.xyz.pts.bcs.common.audit.messages.ObjectFactory;
import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.dto.AbstractRequeryableCommand;

/**
 * @author tterry
 *
 */
public class SpringMVCAuditAspect extends AbstractAuditAspect {

    private final static String HTTP_GET_METHOD = "GET";
    private final static String GET_COMMAND_CLASS_METHOD_NAME = "getCommandClass";
    private final static String CSV_EXTENSION = ".csv";
    private final static String PDF_EXTENSION = ".pdf";
    private final static String COMMAND_VIEW_KEY = "command";
    private final static String BINDING_RESULT_KEY = "org.springframework.validation.BindingResult.command";
    private final static String FALSE = "false";

    /**
     *
     */
    public SpringMVCAuditAspect() {

    }

    /**
     * Audits a user action.
     *
     * @param controller
     *            The controller that handled the user action
     * @param request
     *            The raw web request from the user's browser
     * @param response
     *            The servers response
     * @param modelAndView
     *            The model and view
     */
    @SuppressWarnings("unchecked")
    public Object auditUserAction(final ProceedingJoinPoint call, final Controller controller, final HttpServletRequest request,
            final HttpServletResponse response) throws Throwable {
        ModelAndView modelAndView = null;

        try {
            final Date startDate = new Date();
            boolean callError = false;
            Class commandClass = null;
            try {
                final Class controllerTargetClass = AopUtils.getTargetClass(controller);
                if (BaseCommandController.class.isAssignableFrom(controllerTargetClass)) {
                    // we must check the controller type as it could be a proxy or cglib object
                    if (controller instanceof Proxy) {
                        final Method m = BaseCommandController.class.getMethod(GET_COMMAND_CLASS_METHOD_NAME, new Class[0]);
                        commandClass = (Class) Proxy.getInvocationHandler(controller).invoke(controller, m,
                                new Object[0]);
                    } else {
                        commandClass = ((BaseCommandController) controller).getCommandClass();
                    }
                }
                modelAndView = (ModelAndView) call.proceed();
            } catch (final Exception e) {
                log.error(e, e);
                callError = true;
                throw e;
            } finally {
                final Date endDate = new Date();
                final IsCommandAuditableResult result = isCommandAuditable(commandClass, modelAndView, request);

                if (result.isAuditable()) {
                    AuditEvent ae = null;
                    if (result.getCommand() != null) {
                        ae = createAuditEvent(request, result.getCommand(), startDate, endDate, callError, result
                                .getActiveGroup());
                    } else {
                        ae = createAuditEvent(request, commandClass, startDate, endDate, callError);
                        setEventType(request, commandClass, ae, null);
                    }
                    this.audit(ae);
                }
            }
        } catch (final Exception auditException) {
            // there has been a serious system error and the audit could not be performed.
            // we stop the current request by throwing an exception that will redirect to generic
            // error page.
            final HttpSession session = request.getSession();
            session.invalidate();
            throw auditException;
        }

        return modelAndView;
    }

    private class IsCommandAuditableResult {
        private boolean auditable = false;
        private AuditPropertyGroup activeGroup = null;
        private Object command = null;

        public IsCommandAuditableResult(final boolean auditable, final AuditPropertyGroup activeGroup, final Object command) {
            this.auditable = auditable;
            this.activeGroup = activeGroup;
            this.command = command;
        }

        /**
         * @return the result
         */
        public boolean isAuditable() {
            return auditable;
        }

        /**
         * @return the activeGroup
         */
        public AuditPropertyGroup getActiveGroup() {
            return activeGroup;
        }

        /**
         * @return the nullCommand
         */
        public Object getCommand() {
            return command;
        }

    }

    private IsCommandAuditableResult isCommandAuditable(final Class commandClass, final ModelAndView modelAndView,
            final HttpServletRequest request) throws Exception {
        // only audit if
        // 1. The command is not null and contains a validation
        // 2. validation has not failed
        // 3. We have a matching group and validation success,
        // 4. We no matching group, validation success and this is not a requery.
        // (Requery for a report request is allowed)
        boolean logAuditEvent = true;
        AuditPropertyGroup activeGroup = null;
        Object command = null;
        if (commandClass != null && commandClass.isAnnotationPresent(AuditableCommand.class)) {

            if ((modelAndView != null) && (modelAndView.getModel() != null)) {
                final Map model = modelAndView.getModel();
                command = model.get(COMMAND_VIEW_KEY);
                final Object bindingResultObj = model.get(BINDING_RESULT_KEY);
                if (bindingResultObj != null && bindingResultObj instanceof BindingResult) {
                    // we only audit valid form submissions
                    logAuditEvent = !((BindingResult) bindingResultObj).hasErrors();
                }
                if (command != null) {
                    if (logAuditEvent) {
                        final Class<?> c = command.getClass();
                        final AuditPropertyGroups groupsAnnotation = c.getAnnotation(AuditPropertyGroups.class);
                        if (groupsAnnotation != null) {
                            activeGroup = this.findActivePropertyGroup(command);

                            if (!request.getMethod().equals(HTTP_GET_METHOD) && activeGroup == null
                                    && !groupsAnnotation.auditWhenNoGroupMatch()) {
                                logAuditEvent = false;
                            }
                        }
                    }
                    if (logAuditEvent && command instanceof AbstractRequeryableCommand) {
                        final AbstractRequeryableCommand requeryableCmd = (AbstractRequeryableCommand) command;
                        final String requery = requeryableCmd.getRequery();
                        if (requery != null && requery.length() != 0 && !requery.equals(FALSE)
                                && !isReportRequest(request) && activeGroup == null) {
                            logAuditEvent = false;
                        }
                    }
                }
            }
        } else {
            logAuditEvent = false;
        }
        final IsCommandAuditableResult result = new IsCommandAuditableResult(logAuditEvent, activeGroup, command);

        return result;
    }

    private boolean isReportRequest(final HttpServletRequest request) {
        boolean result = false;
        if (request.getRequestURI().endsWith(PDF_EXTENSION) || request.getRequestURI().endsWith(CSV_EXTENSION)) {
            result = true;
        }
        return result;
    }

    /**
     * @param commandClass
     * @param ae
     */
    @SuppressWarnings("unchecked")
    private void setEventType(final HttpServletRequest request, final Class commandClass, final AuditEvent ae,
            final AuditPropertyGroup activePropertyGroup) {
        if (commandClass.isAnnotationPresent(AuditableCommand.class)) {
            final AuditableCommand annotation = (AuditableCommand) commandClass.getAnnotation(AuditableCommand.class);

            if (request.getMethod().equals(HTTP_GET_METHOD)) {
                String eventName = Event.MENU_SELECTION.value();
                String paramName = Parameter.MENU_NAME.value();
                if (request.getRequestURI().endsWith(PDF_EXTENSION)) {
                    eventName = Event.PRINT.value();
                    paramName = Parameter.SCREEN_NAME.value();
                } else if (request.getRequestURI().endsWith(CSV_EXTENSION)) {
                    eventName = Event.EXPORT_TO_CSV.value();
                    paramName = Parameter.SCREEN_NAME.value();
                }
                ae.setName(eventName);
                final AuditEventParameter aep = new AuditEventParameter();
                aep.setName(paramName);
                aep.setValue(annotation.value().value());
                ae.getParameters().add(aep);
                final AuditEventParameter aep2 = new AuditEventParameter();
                aep2.setName(Parameter.APPLICATION_NAME.value());
                aep2.setValue(this.getApplicationName());
                ae.getParameters().add(aep2);
            } else {
                if (activePropertyGroup != null) {
                    ae.setName(activePropertyGroup.name().value());
                } else {
                    ae.setName(annotation.value().value());
                }
            }
        }
    }

    protected AuditEvent createAuditEvent(final HttpServletRequest request, final Object command, final Date startDate, final Date endDate,
            final boolean callError, final AuditPropertyGroup activePropertyGroup) throws Exception {
        final AuditEvent ae = createAuditEvent(request, command.getClass(), startDate, endDate, callError);
        setEventType(request, command.getClass(), ae, activePropertyGroup);
        if (!request.getMethod().equals(HTTP_GET_METHOD)) {
            createAdditionalParameters(command, ae, activePropertyGroup);
        }
        return ae;
    }

    @SuppressWarnings("unchecked")
    protected AuditEvent createAuditEvent(final HttpServletRequest request, final Class clazz, final Date startDate, final Date endDate,
            final boolean callError) {
        final AuditEvent ae = new ObjectFactory().createAuditEvent();
        String userName = null;
        final Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            userName = UNAUTH_USER;
        } else {
            final Object userDetailsObj = request.getAttribute(Constants.USER_PROFILE_KEY);
            if (userDetailsObj != null) {
                final UserDetails ud = (UserDetails) userDetailsObj;
                ae.setEmployeeName(ud.getEmployeeName());
                userName = ud.getName();
            } else {
                userName = request.getUserPrincipal().getName();
            }
        }
        ae.setUserId(userName);
        ae.setApplicationName(this.getApplicationName());
        ae.setCreatedDate(new Date());
        if (!request.getMethod().equals(HTTP_GET_METHOD)) {
            final List<AuditEventParameter> params = buildParameters(clazz, request);
            ae.getParameters().addAll(params);
        }
        final long timeTaken = endDate.getTime() - startDate.getTime();

        ae.setResponseTime(timeTaken);
        if (callError) {
            ae.setResponseStatus(AbstractAuditAspect.RESPONSE_STATUS_FAILURE);
        } else {
            ae.setResponseStatus(AbstractAuditAspect.RESPONSE_STATUS_SUCCESS);
        }
        return ae;
    }
}
