/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.audit.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;

public class AuditContextHolder implements Serializable {

    /**
     * The threadlocal variable storing the Audit Parameters.
     */
    private static ThreadLocal<AuditContext> auditContext = new ThreadLocal<AuditContext>() {
          @Override
        public AuditContext initialValue() {
              return new AuditContext();
            }
    };

    public static void addParameter(final Parameter name, final String value) {
        if(name == null){
            throw new RuntimeException("Invalid audit paramater - parameter cannot be null");
        }
        final AuditParameter auditParameter = new AuditParameter();
        auditParameter.setName(name);
        auditParameter.setValue(value);

        getAnnotatedEventParameterList().add(auditParameter);
    }


    public static void addParameterToDefaultEvent(final Parameter name, final String...values) {
        if(name == null){
            throw new RuntimeException("Invalid audit paramater - parameter cannot be null");
        }
        final AuditParameter auditParameter = new AuditParameter();
        auditParameter.setName(name);
        auditParameter.setMultipleValues(Arrays.asList(values));

        getAnnotatedEventParameterList().add(auditParameter);
    }

    public static void addParameterToAnnotatedEvent(final Parameter name, final List<String> values) {
        if(name == null){
            throw new RuntimeException("Invalid audit paramater - parameter cannot be null");
        }
        final AuditParameter auditParameter = new AuditParameter();
        auditParameter.setName(name);
        auditParameter.setMultipleValues(values);

        getAnnotatedEventParameterList().add(auditParameter);
    }

    public static void addParameter(final Event event, final Parameter name, final String value) {

        if(event == null){
            throw new RuntimeException("Invalid audit event - event cannot be null");
        }
        if(name == null){
            throw new RuntimeException("Invalid audit paramater - parameter cannot be null");
        }

        final AuditParameter auditParameter = new AuditParameter();
        auditParameter.setName(name);
        auditParameter.setValue(value);

        final List<AuditParameter> parameterList = getParamaterList(event);

        parameterList.add(auditParameter);
    }

    public static void addParameter(final Event event, final Parameter name, final String...values) {

        if(event == null){
            throw new RuntimeException("Invalid audit event - event cannot be null");
        }
        if(name == null){
            throw new RuntimeException("Invalid audit paramater - parameter cannot be null");
        }

        final AuditParameter auditParameter = new AuditParameter();
        auditParameter.setName(name);
        auditParameter.setMultipleValues(Arrays.asList(values));
        final List<AuditParameter> parameterList = getParamaterList(event);
        parameterList.add(auditParameter);
    }

    public static void addParameter(final Event event, final Parameter name, final List<String> values) {
        if(event == null){
            throw new RuntimeException("Invalid audit event - event cannot be null");
        }

        if(name == null){
            throw new RuntimeException("Invalid audit paramater - parameter cannot be null");
        }
        final AuditParameter auditParameter = new AuditParameter();
        auditParameter.setName(name);
        auditParameter.setMultipleValues(values);

        final List<AuditParameter> parameterList = getParamaterList(event);
        parameterList.add(auditParameter);
    }

    public static void clear(){
        auditContext.get().clear();
        auditContext.remove();
    }

    public static Map<Event, List<AuditParameter>> getParametersMap(){
        return auditContext.get().getEventParameterMap();
    }

    public static void setJmsXDeliverCount(final String jmsXDeliverCount) {
        auditContext.get().setJmsXDeliverCountValue(jmsXDeliverCount);
    }

    public static boolean isMessageRetryThresholdReached(){
        return auditContext.get().isMessageRetryThresholdReached();
    }

    public static boolean isMessageRetryPassedThreshold(){
        return auditContext.get().isMessageRetryPassedThreshold();
    }

    public static boolean isEventSuccess(final Event event) {
        return auditContext.get().isEventStatusSuccess(event);
    }

    public static void setEventStatusSuccess(final boolean eventStatus) {
        auditContext.get().setEventStatus(Event.ANNOTATED_EVENT, eventStatus);
    }

    public static void setEventStatusSuccess(final Event event, final boolean eventStatus) {
        auditContext.get().setEventStatus(event, eventStatus);
    }

    private static List<AuditParameter> getParamaterList(final Event event) {
        List<AuditParameter> parameterList = auditContext.get().getParametersByEvent(event);
        if(parameterList == null){
            parameterList = new ArrayList<AuditParameter>();
            auditContext.get().put(event, parameterList);
        }
        return parameterList;
    }

    private static List<AuditParameter> getAnnotatedEventParameterList() {
        return getParamaterList(Event.ANNOTATED_EVENT);
    }
}
