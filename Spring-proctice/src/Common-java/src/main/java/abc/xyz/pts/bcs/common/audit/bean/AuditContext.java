/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.audit.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abc.xyz.pts.bcs.common.audit.business.Event;

public class AuditContext {
    private static final String AUDIT_MESSAGE_RETRY_THRESHOLD = "audit.message.retry.threshold";

    private static final EventParameterMap eventParameterMap = new EventParameterMap();
    private static final EventStatusMap eventStatusMap = new EventStatusMap();
    private static final JmsXDeliverCount jmsXDeliverCount = new JmsXDeliverCount();


    private static final class EventParameterMap extends ThreadLocal<Map<Event, List<AuditParameter>>> {
        @Override
        protected Map<Event, List<AuditParameter>> initialValue() {
            return new HashMap<Event, List<AuditParameter>>();
        }
    }

    private static final class EventStatusMap extends ThreadLocal<Map<Event, Boolean>> {
        @Override
        protected Map<Event, Boolean> initialValue() {
            return new HashMap<Event, Boolean>();
        }
    }

    private static final class JmsXDeliverCount extends ThreadLocal<Integer> {

        public void setValue(final String count) {
            set(Integer.valueOf(Integer.parseInt(count)));
        }
    }

    public Map<Event, List<AuditParameter>> getEventParameterMap() {
        return eventParameterMap.get();
    }

    private Map<Event, Boolean> getEventStatusMap() {
        return eventStatusMap.get();
    }

    private int getJmsXDeliverCountValue() {
        return jmsXDeliverCount.get().intValue();
    }

    public void setJmsXDeliverCountValue(final String deliverCount) {
        jmsXDeliverCount.setValue(deliverCount);
    }

    public int getMaxMessageRetryCount() {
        return Integer.parseInt(System.getProperty(AUDIT_MESSAGE_RETRY_THRESHOLD));
    }

    public boolean isMessageRetryThresholdReached(){
        boolean thresholdReached = false;
        if(getJmsXDeliverCountValue() == getMaxMessageRetryCount()){
            thresholdReached = true;
        }
        return thresholdReached;
    }

    public boolean isMessageRetryPassedThreshold(){
        boolean thresholdReached = false;
        if(getJmsXDeliverCountValue() > getMaxMessageRetryCount()){
            thresholdReached = true;
        }
        return thresholdReached;
    }

    public  List<AuditParameter> getParametersByEvent(final Event event) {
        return getEventParameterMap().get(event);
    }

    public void put(final Event event, final List<AuditParameter> parameterList){
        getEventParameterMap().put(event, parameterList);
    }

    public boolean isEventStatusSuccess(final Event event) {
        final Boolean status =  getEventStatusMap().get(event);
        return (status != null ? status : true);
    }

    public void setEventStatus(final Event event,  final boolean status) {
        getEventStatusMap().put(event, status);
    }

    public void clear() {
        eventParameterMap.remove();
        eventStatusMap.remove();
        jmsXDeliverCount.remove();
    }

}
