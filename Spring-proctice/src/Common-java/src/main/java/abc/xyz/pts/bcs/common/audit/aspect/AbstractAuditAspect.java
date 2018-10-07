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

package abc.xyz.pts.bcs.common.audit.aspect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroup;
import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroups;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCompositeBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperties;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperty;
import abc.xyz.pts.bcs.common.audit.aspect.propertyResolver.ExpressionResolver;
import abc.xyz.pts.bcs.common.audit.business.AuditException;
import abc.xyz.pts.bcs.common.audit.business.AuditMessageSender;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;
import abc.xyz.pts.bcs.common.audit.messages.ObjectFactory;
import abc.xyz.pts.bcs.common.audit.util.PropertyFormatter;

/**
 * @author tterry
 *
 */
public abstract class AbstractAuditAspect {

    public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
    public static final String RESPONSE_STATUS_FAILURE = "FAILURE";
    protected static final String UNAUTH_USER = "Unauthenticated";
    private static final String PROPERTY_GROUP_NONE = "None";
    protected static final Logger log = Logger.getLogger(AbstractAuditAspect.class);
    private String applicationName;
    private AuditMessageSender messageSender;

    /**
     * Generic audit operation for all user action pointcuts.
     *
     * @param userName
     *            Current user name
     * @param ipAddress
     *            IP address of current user
     * @param auditDate
     *            Date and time that the audit was created
     * @param viewName
     *            The screen that was the source of the event
     * @param parameters
     *            Any parameters (name value pairs) that the user has supplied
     * @param errors
     *            Any errors that the action has caused
     */
    protected void audit(final AuditEvent ae) throws AuditException {

        this.getMessageSender().send(ae);
    }

    protected AuditEvent createAuditEvent(final HttpServletRequest request, final Class clazz) {
        final AuditEvent ae = new ObjectFactory().createAuditEvent();
        String userName = null;
        if (request.getUserPrincipal() == null) {
            userName = UNAUTH_USER;
        } else {
            userName = request.getUserPrincipal().getName();
        }
        final String ipAddress = request.getRemoteAddr();
        ae.setUserId(userName);
        ae.setCreatedDate(new Date());
        final List<AuditEventParameter> params = buildParameters(clazz, request);
        ae.getParameters().addAll(params);
        return ae;
    }

    /**
     * Builds a map of user entered parameters.
     *
     * @param command
     * @param request
     * @return
     */
    protected List<AuditEventParameter> buildParameters(final Class clazz, final HttpServletRequest request) {
        final List<AuditEventParameter> results = new ArrayList<AuditEventParameter>();
        final Iterator parameterIt = request.getParameterMap().entrySet().iterator();
        while (parameterIt.hasNext()) {
            final Map.Entry entry = (Map.Entry) parameterIt.next();
            final String parameterName = entry.getKey().toString();
            final String propertyName = resolvePropertyName(parameterName);
            final AuditablePropertyEntry propertyEntry = AuditablePropertyCache.getPropertyEntry(clazz, propertyName);
            if (propertyEntry != null && propertyEntry.isAuditable()) {
                String propertyValue = null;
                try {
                    final String[] propertyValues = (String[]) entry.getValue();
                    propertyValue = propertyValues[0];
                } catch (final ClassCastException cce) {
                    log.info(cce.getMessage());
                    propertyValue = (String) entry.getValue();
                }
                final String resourceKey = propertyEntry.getResourceKey();
                final AuditEventParameter param = new ObjectFactory().createAuditEventParameter();
                param.setName(resourceKey);
                param.setValue(propertyValue);
                results.add(param);
            }
        }
        return results;
    }

    /**
     * Will be overridden by subclasses. For example JSF implementation means that request params dont match bean
     * properties exactly.
     *
     * @param parameterName
     * @return
     */
    protected String resolvePropertyName(final String parameterName) {
        return parameterName;
    }

    private void addEventParameter(final Object value, final String name, final List<AuditEventParameter> params) {
        if (value != null) {
            final Class type = value.getClass();
            if (type.isArray()) {
                final Object[] objArray = (Object[]) value;
                for (final Object obj : objArray) {
                    final AuditEventParameter auditEventParameter = new AuditEventParameter();
                    auditEventParameter.setName(name);
                    final String valueStr = PropertyFormatter.format(obj);
                    auditEventParameter.setValue(valueStr);
                    params.add(auditEventParameter);
                }
            } else {
                final AuditEventParameter auditEventParameter = new AuditEventParameter();
                auditEventParameter.setName(name);
                final String valueStr = PropertyFormatter.format(value);
                auditEventParameter.setValue(valueStr);
                params.add(auditEventParameter);
            }
        }
    }
    /**
     * Audits additional parameters that were not supplied by the user but are related.
     * This method looks for any @AuditPropertyGroup defined in Command object
     * Checks is valid group to audit logs
     * @param command
     * @param ae
     */
    protected void createAdditionalParameters(final Object command, final AuditEvent ae) throws Exception {
        AuditPropertyGroup activeGroup = null;
        boolean logAuditEvent = true;
        final Class<?> commandClass = command.getClass();
        // Check for AuditPropertyGroups annotation
        final AuditPropertyGroups groupsAnnotation = commandClass.getAnnotation(AuditPropertyGroups.class);
        if (groupsAnnotation != null) {
            // find the active audit group using the groupProperty and propertyValue.
            activeGroup = this.findActivePropertyGroup(command);
            // there is no active group, auditWhenNoGroupMatch is false (true && !false) = true therefore logAuditEvent = false (value 1 of ternary operator)
            // there is no active group, auditWhenNoGroupMatch is true (true && !true) = false therefore logAuditEvent = true (value 2 of ternary operator)
            // there is an active group = short circuit evaluation of false therefore logAuditEvent = true (value 2 of ternary operator)
            logAuditEvent = (activeGroup == null && !groupsAnnotation.auditWhenNoGroupMatch()) ? false : true;
        }
        if(logAuditEvent){
            createAdditionalParameters(command, ae, activeGroup);
        }
    }


    /**
     * Audits additional parameters that were not supplied by the user but are related.
     *
     * @param command
     * @param ae
     */
    protected void createAdditionalParameters(final Object command, final AuditEvent ae, final AuditPropertyGroup activePropertyGroup) {
        // we find all the properties that have be marked 'useBeanValue' that
        // haven't been provided by the user
        final Class<?> commandClass = command.getClass();
        final AuditableBeanPropertyCache propertyCache = AuditableBeanPropertyCache.getInstance();

        final List<Field> fields = propertyCache.getClassFields(commandClass);

        for (final Field field : fields) {
            try {
                if (field.isAnnotationPresent(AuditableExpressionBeanProperties.class)) {
                    final AuditableExpressionBeanProperties propAnnotation = field
                            .getAnnotation(AuditableExpressionBeanProperties.class);
                    if (!propAnnotation.ignoreEvent().equals(ae.getName())) {
                        for (int i = 0; i < propAnnotation.properties().length; i++) {
                            final AuditableExpressionBeanProperty auditableBeanProperty = propAnnotation.properties()[i];
                            final String elExpression = auditableBeanProperty.expression();

                            final Object propertyValue = ExpressionResolver.resolve(command, elExpression);
                            addEventParameter(propertyValue, auditableBeanProperty.key().value(), ae.getParameters());
                        }
                    }
                } else if (field.isAnnotationPresent(AuditableBeanProperty.class)) {
                    final AuditableBeanProperty propAnnotation = field.getAnnotation(AuditableBeanProperty.class);
                    if ((propAnnotation.group().equals(PROPERTY_GROUP_NONE))
                            || (activePropertyGroup != null && activePropertyGroup.name().value().equals(
                                    propAnnotation.group()))) {
                        // ignore auditable property if current event is in
                        // ignore event list
                        if (!ignoreProperty(ae.getName(), propAnnotation)) {
                            final Object value = ExpressionResolver.callPropertyGetter(field.getName(), command);
                            addEventParameter(value, propAnnotation.key().value(), ae.getParameters());
                        }
                    }
                } else if (field.isAnnotationPresent(AuditableCompositeBeanProperty.class)) {
                    // if this is an auditable composite property we recurse
                    // inside it, auditing as we go.
                    final AuditableCompositeBeanProperty propAnnotation = field
                            .getAnnotation(AuditableCompositeBeanProperty.class);
                    final String nestedPropertyName = propAnnotation.value();
                    Object nestedProperty = null;
                    if (nestedPropertyName.length() == 0) {
                        nestedProperty = ExpressionResolver.callPropertyGetter(field.getName(), command);
                    } else {
                        final Object compositeProperty = ExpressionResolver.callPropertyGetter(field.getName(), command);
                        nestedProperty = ExpressionResolver.callPropertyGetter(nestedPropertyName, compositeProperty);
                    }

                    if (nestedProperty != null) {
                        createAdditionalParameters(nestedProperty, ae, activePropertyGroup);
                    }
                }
            } catch (final Exception e) {
                log.error("Unable to audit property '" + commandClass.getName() + "." + field.getName()
                        + "' for event '" + ae.getName() + "'", e);
            }
        }
    }

    private boolean ignoreProperty(final String eventName, final AuditableBeanProperty propAnnotation) {
        boolean result = false;
        final Event[] ignoreEvents = propAnnotation.ignoreEvent();
        for (final Event event : ignoreEvents) {
            if (event.value().equals(eventName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     *
     * @param obj - this is the command being examined for groups.
     * @return
     * @throws Exception
     */
    protected AuditPropertyGroup findActivePropertyGroup(final Object obj) throws Exception {
        AuditPropertyGroup result = null;
        final Class objClass = obj.getClass();
        if (objClass.isAnnotationPresent(AuditPropertyGroups.class)) {
            final AuditPropertyGroups annotation = (AuditPropertyGroups) objClass.getAnnotation(AuditPropertyGroups.class);
            // AuditPropertyGroups has an array of AuditPropertyGroup
            // Loop through all AuditPropertyGroup values
            for (int i = 0; i < annotation.value().length; i++) {
                final AuditPropertyGroup apg = annotation.value()[i];
                // The property group will have a groupProperty eg action.  This is converted into
                // a getter call - eg getAction().  This is called and the return value from this is
                // compared to the anotation propertyValue.  If they match, then the group is considered
                // active.
                final Object groupPropertyValue = ExpressionResolver.callPropertyGetter(apg.groupProperty(), obj);
                if (groupPropertyValue != null && StringUtils.contains(groupPropertyValue.toString(), apg.propertyValue())) {
                    result = apg;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @param applicationName
     *            the applicationName to set
     */
    public void setApplicationName(final String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * @return the auditMessageSender
     */
    public AuditMessageSender getMessageSender() {
        return messageSender;
    }

    /**
     * @param auditMessageSender
     *            the auditMessageSender to set
     */
    public void setMessageSender(final AuditMessageSender auditMessageSender) {
        this.messageSender = auditMessageSender;
    }
}
