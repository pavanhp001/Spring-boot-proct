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
/**
 *
 */
package abc.xyz.pts.bcs.common.audit.aspect;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;
import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

@Aspect
public class AuditAspect extends AbstractAuditAspect {

    private static final Logger LOGGER = Logger.getLogger(AuditAspect.class);

    @Around("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..)) && @annotation(event) && args(command,anotherCommand,bindingResult,..) && this(controller)")
    public Object auditUserAction(final ProceedingJoinPoint call, final AuditableEvent event, final Object command, final Object anotherCommand, final BindingResult bindingResult, final Object controller) throws Throwable {
        return auditUserAction(call, event, null, command, bindingResult, controller);
    }

    @Around("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..)) && @annotation(event) && args(command,bindingResult,..) && this(controller)")
    public Object auditUserAction(final ProceedingJoinPoint call, final AuditableEvent event, final Object command, final BindingResult bindingResult, final Object controller) throws Throwable {
        return auditUserAction(call, event, null, command, bindingResult, controller);
    }

    @Around("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..)) && @annotation(event) && args(command,anotherCommand,tableCommand,bindingResult,..) && this(controller)")
    public Object auditUserAction(final ProceedingJoinPoint call, final AuditableEvent event, final Object command, final Object anotherCommand, final TableActionCommand tableCommand, final BindingResult bindingResult, final Object controller) throws Throwable {
        return auditUserAction(call, event, tableCommand, command, bindingResult, controller);
    }

    @Around("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..)) && @annotation(event) && args(tableCommand,command,bindingResult,..) && this(controller)")
    public Object auditUserAction(final ProceedingJoinPoint call, final AuditableEvent event, final TableActionCommand tableCommand, final Object command, final BindingResult bindingResult, final Object controller) throws Throwable {

        final HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        if(LOGGER.isTraceEnabled()) {
           LOGGER.trace("UI command: " + command);
           LOGGER.trace("UI controller: " + controller);
           LOGGER.trace("UI Binding result: " + bindingResult);
           LOGGER.trace("UI Event: " + event);
        }

        Object result = null;
        boolean callError = false;

        final long startTime = System.currentTimeMillis();
        try {
            try {
                result = call.proceed();
            } catch (final Exception e) {
                log.error(e, e);
                callError = true;
            } finally {
                final long endTime = System.currentTimeMillis();
                final Class<?> clazz = command.getClass();

                // If the AuditableCommand annotation is present and
                // there are no errors in the binding result then continue.
                if(isAuditable(clazz, bindingResult)) {
                    final Event eventType = event.value();
                    final AuditEvent ae = createBasicAuditEvent(request, clazz, eventType, startTime, endTime, callError);

                    if (isMenuOrPrintEvent(eventType) ) {
                        createGetParameters(clazz, eventType, ae);
                    } else {
                        createAdditionalParameters(command, ae);
                    }

                    if(LOGGER.isTraceEnabled()) {
                        LOGGER.trace("UI Audit Event: " + ReflectionToStringBuilder.toString(ae));
                    }

                    audit(ae);
                }

            }
        } catch (final Exception e) {
            // there has been a serious system error and the audit could not be performed.
            // we stop the current request by throwing an exception that will redirect to generic
            // error page.
            request.getSession().invalidate();
            throw e;
        }

        return result;
    }

    @Around("execution(boolean abc.xyz.pts.bcs..business.impl.*.*(..)) && @annotation(event) && args(dto)")
    public Object auditBusinessAction(final ProceedingJoinPoint call, final AuditableEvent event, final Object dto) throws Throwable {

        final HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Business Event: " + event);
            LOGGER.trace("Business DTO: " + dto);
        }

        boolean callError = false;
        Boolean result = null;
        Object callResult = null;
        final long startTime = System.currentTimeMillis();

        try {
            callResult = call.proceed();
            result = (Boolean)callResult;
            callError = callResult == null || !result.booleanValue();
        } catch (final Exception e) {
            callError = true;
            throw e;
        } finally {
            final long endTime = System.currentTimeMillis();
            final Class<?> clazz = dto.getClass();
            if(clazz.isAnnotationPresent(AuditableCommand.class)) {
                final AuditEvent ae = createBasicAuditEvent(request, clazz, event.value(), startTime, endTime, callError);
                createAdditionalParameters(dto, ae, null);
                if(LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Business Audit event: " + ReflectionToStringBuilder.toString(ae));
                }
                audit(ae);
            }
        }

        return callResult;
    }

    private boolean isMenuOrPrintEvent(final Event event){
        boolean isMenuOrPrintEvent = false;
        switch(event) {
            case MENU_SELECTION:
            case PRINT:
            case EXPORT_TO_CSV:
                isMenuOrPrintEvent = true;
                break;
            default:
        }
        return isMenuOrPrintEvent;
    }

    private void createGetParameters(final Class<?> clazz, final Event event, final AuditEvent ae) {
        final AuditableCommand annotation = clazz.getAnnotation(AuditableCommand.class);

        final List<AuditEventParameter> list = ae.getParameters();
        final AuditEventParameter nameParam = new AuditEventParameter();

        switch(event) {
            case MENU_SELECTION:
                nameParam.setName(Parameter.MENU_NAME.value());
                break;
            case PRINT:
            case EXPORT_TO_CSV:
                nameParam.setName(Parameter.SCREEN_NAME.value());
                break;
            default:
                return;
        }

        nameParam.setValue(annotation.value().value());
        list.add(nameParam);

        final AuditEventParameter appParam = new AuditEventParameter();
        appParam.setName(Parameter.APPLICATION_NAME.value());
        appParam.setValue(getApplicationName());
        list.add(appParam);
    }

    /**
     * If the AuditableCommand annotation is present, then this class is auditable.
     * @param commandClass
     * @param bindingResult -     if there are any errors in the binding result,
     *                             then return false indicating this class is not auditable
     * @return
     */
    protected boolean isAuditable(final Class<?> commandClass, final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return false;
        }
        return commandClass.isAnnotationPresent(AuditableCommand.class);
    }

    protected AuditEvent createBasicAuditEvent(final HttpServletRequest request, final Class<?> clazz, final Event event, final long startTime, final Long endTime, final boolean callError) {
        final AuditEvent ae = new AuditEvent();
        ae.setName(event.value());
        String userName = UNAUTH_USER;
        final Principal userPrincipal = request.getUserPrincipal();

        if (userPrincipal != null) {
            final UserDetails userDetails = (UserDetails)request.getAttribute(Constants.USER_PROFILE_KEY);
            if (userDetails != null) {
                ae.setEmployeeName(userDetails.getEmployeeName());
                userName = userDetails.getName();
            } else {
                userName = userPrincipal.getName();
            }
        }

        ae.setUserId(userName);
        ae.setApplicationName(this.getApplicationName());
        ae.setCreatedDate(new Date());

        final long timeTaken = endTime - startTime;

        ae.setResponseTime(timeTaken);

        if (callError) {
            ae.setResponseStatus(AbstractAuditAspect.RESPONSE_STATUS_FAILURE);
        } else {
            ae.setResponseStatus(AbstractAuditAspect.RESPONSE_STATUS_SUCCESS);
        }
        return ae;
    }

}
