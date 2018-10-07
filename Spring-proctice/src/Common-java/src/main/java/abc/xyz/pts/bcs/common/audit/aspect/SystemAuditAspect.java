/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.audit.aspect;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Message;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import abc.xyz.pts.bcs.common.audit.annotation.Auditable;
import abc.xyz.pts.bcs.common.audit.bean.AuditContextHolder;
import abc.xyz.pts.bcs.common.audit.bean.AuditParameter;
import abc.xyz.pts.bcs.common.audit.business.AuditException;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.messages.AuditEvent;
import abc.xyz.pts.bcs.common.audit.messages.AuditEventParameter;
import abc.xyz.pts.bcs.common.enums.AuditUserTypes;

@Aspect
public class SystemAuditAspect extends AbstractAuditAspect {

    private final static Logger logger = Logger.getLogger(AuditAspect.class);
    private static final String JMSX_DELIVERY_COUNT = "JMSXDeliveryCount";

    /**
     * This is a Point Cut method for any methods which are annotated with @Auditable except onMessage method
     * @param call
     * @param annotatedEvent
     * @return
     * @throws Throwable
     */
    @Around("execution(@abc.xyz.pts.bcs.common.audit.annotation.Auditable * *(..)) && !execution(* onMessage(..)) && @annotation(annotatedEvent)")
    public Object auditSystemAction(final ProceedingJoinPoint call, final Auditable annotatedEvent) throws Throwable {
        return auditSystemAction(call, annotatedEvent, null);
    }

    /**
     * This is a Point Cut method for any methods which are annotated with @Auditable and Message is first method argument
     * @param call
     * @param annotatedEvent
     * @param msg
     * @return
     * @throws Throwable
     */
    @Around("execution(@abc.xyz.pts.bcs.common.audit.annotation.Auditable * *(..)) && @annotation(annotatedEvent) && args(msg,..)")
    public Object auditSystemAction(final ProceedingJoinPoint call, final Auditable annotatedEvent, final Message msg) throws Throwable {
        Object result = null;
        boolean targetMethodExceptioned = false;
        final long startTime = System.currentTimeMillis();

        try {
            /**
             * This target class is not MEssage Listener then ignore JmsXDeliverCount
            */
            if(msg != null) {
                AuditContextHolder.setJmsXDeliverCount(msg.getStringProperty(JMSX_DELIVERY_COUNT));
            }
            result = call.proceed();
        } catch (final Exception e) {
            log.error(e, e);
            targetMethodExceptioned = true;
            throw e;
        } finally {
            if (msg == null) {
                generateSystemAudit(startTime, annotatedEvent, targetMethodExceptioned);
            } else {
                /* If the target Object is the MessageListener
                 * Check the retry count reached the Audit Threshold count
                 * If it has, then audit the message as having failed , otherwise ignore.
                */
                if(!targetMethodExceptioned || (AuditContextHolder.isMessageRetryThresholdReached() && targetMethodExceptioned)) {
                    generateSystemAudit(startTime, annotatedEvent, targetMethodExceptioned);
                } else if(AuditContextHolder.isMessageRetryPassedThreshold()){
                    logger.error("Message ID:"+msg.getJMSCorrelationID() +" -  Retry count reached the Audit Retry Audit Threshold count");
                }
            }
            //Clear the audit context
            AuditContextHolder.clear();
        }

        return result;
    }

    private void generateSystemAudit(final long startTime, final Auditable annotatedEvent, final boolean targetMethodExceptioned) throws Exception {
        final long endTime = System.currentTimeMillis();
        try{
            boolean isEventFailed = false;
            final Map<Event, List<AuditParameter>> eventParametersMap = AuditContextHolder.getParametersMap();

            if(eventParametersMap.isEmpty() && annotatedEvent.value() != Event.NONE){
                //eventParametersMap empty means the event failed before any thing was recorded in the audit context
                createSystemAudit(startTime, annotatedEvent.value(), true, endTime, null);
            }
            if(targetMethodExceptioned) {//In case of failure there is only one event to be audited, the event annotated on the target method
                createSystemAudit(startTime, annotatedEvent.value(),targetMethodExceptioned, endTime, eventParametersMap.get(Event.ANNOTATED_EVENT));
            } else { //In case of success there can be multiple system events to be audited
                for (final Entry<Event, List<AuditParameter>> auditEventEntry : eventParametersMap.entrySet()) {
                    final Event auditEvent = auditEventEntry.getKey();
                    if(shouldIgnoreEventOnSuccess(annotatedEvent, auditEvent)){
                        //if event key == Event.ANNOTATED_EVENT - it means we are auditing the annotated event,
                        //otherwise its the event key itself that is to be audited
                        Event eventType = null;
                        if(Event.ANNOTATED_EVENT == auditEvent){
                            eventType =  annotatedEvent.value();

                            //Even if there was no exception thrown from the target method, the event still could have failed
                            isEventFailed = !isEventSuccess(Event.ANNOTATED_EVENT, targetMethodExceptioned);
                        }else{
                            eventType =  auditEvent;
                            //Even if there was no exception thrown from the target method, the event still could have failed
                            isEventFailed = !isEventSuccess(eventType, targetMethodExceptioned);
                        }
                        createSystemAudit(startTime, eventType, isEventFailed, endTime, auditEventEntry.getValue());
                    }
                }
            }
        } catch(final Exception e) {
            // there has been a serious system error and the audit could not be performed.
            // we stop the current request by throwing an exception that will redirect to generic
            // error page.
            throw e;

        } finally {
            //Clear the audit context
            AuditContextHolder.clear();
        }

    }

    private boolean isEventSuccess(final Event event, final boolean targetMethodExceptioned){
            //If the target method did not throw exception check the status of the event
            return (!targetMethodExceptioned ? AuditContextHolder.isEventSuccess(event) : targetMethodExceptioned);
    }

    private void createSystemAudit(final long startTime, final Event event, final boolean isEventFailed, final long endTime, final List<AuditParameter> parameterList) throws AuditException {
        final AuditEvent ae = createBasicAuditEvent( event, startTime, endTime, isEventFailed);
        if(parameterList != null){
            addParameters(parameterList, ae);
        }
        audit(ae);
    }

    private boolean shouldIgnoreEventOnSuccess(final Auditable annotatedEvent, final Event auditEvent) {
        return !(annotatedEvent.ignoreEventOnSuccess() && Event.ANNOTATED_EVENT == auditEvent);
    }

    private void addParameters(final List<AuditParameter> parameterList, final AuditEvent ae) {
        final List<AuditEventParameter> parameters = ae.getParameters();

        for (final AuditParameter auditParameter : parameterList) {
            final AuditEventParameter auditEventParameter = new AuditEventParameter();
            auditEventParameter.setName(auditParameter.getName().value());

            final String paramValue = auditParameter.getValue();
            if(paramValue != null){
                auditEventParameter.setValue(paramValue);
            } else if( auditParameter.getMultipleValues() != null){
                final List<String> multipleValues = auditParameter.getMultipleValues();
                auditEventParameter.setValue(StringUtils.join(multipleValues.toArray(), ","));
            }
            parameters.add(auditEventParameter);
        }
    }

    protected AuditEvent createBasicAuditEvent( final Event event, final long startTime, final Long endTime, final boolean isEventFailed) {
        final AuditEvent ae = new AuditEvent();

        ae.setName(event.value());
        ae.setUserId(AuditUserTypes.SYSTEM_USER.value());
        ae.setEmployeeName(AuditUserTypes.SYSTEM_USER.value());
        ae.setApplicationName(this.getApplicationName());
        ae.setCreatedDate(new Date());
        ae.getResponseStatus();
        final long timeTaken = endTime - startTime;
        ae.setResponseTime(timeTaken);

        if (isEventFailed) {
            ae.setResponseStatus(AbstractAuditAspect.RESPONSE_STATUS_FAILURE);
        } else {
            ae.setResponseStatus(AbstractAuditAspect.RESPONSE_STATUS_SUCCESS);
        }
        return ae;
    }

}
