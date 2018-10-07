/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.jmx.impl;

import java.util.Calendar;

import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;
import abc.xyz.pts.bcs.common.jmx.IMessageListenerMXBean;

/**
 * This is Abstract class implements generic methods for JMS messages current status information. This information would
 * be used by JMX
 *
 * @author ryattapu.
 */
public abstract class AbstractMessageListenerMXBean implements IMessageListenerMXBean {
    private long messagesRecievedCount;
    private long rollbackMessagesCount;
    private long minMessageProcessingTime;
    private long maxMessageProcessingTime;
    private long totalProcessingTime;
    private String lastMessageRecievedDate;
    private String rollbackReasion;

    public long calculateAvgMessagesProcessingTime(final long totalProcessingTime, final long totalMessagesCount) {
        return totalProcessingTime / totalMessagesCount;
    }

    public long calculateMaxProcessingTime(final long maxProcessingTime, final long currentProcessingTime) {
        return (currentProcessingTime > maxProcessingTime ? currentProcessingTime : maxProcessingTime);
    }

    public long calculateMinProcessingTime(final long minProcessingTime, final long currentProcessingTime) {
        if (minProcessingTime == 0) {
            return minProcessingTime;
        }
        return (currentProcessingTime < minProcessingTime ? currentProcessingTime : minProcessingTime);
    }

    public long getMessagesRecievedCount() {
        return messagesRecievedCount;
    }

    public void updateMessagesRecievedCount() {
        messagesRecievedCount += 1;
    }

    public long getRollbackMessagesCount() {
        return rollbackMessagesCount;
    }

    public void updateRollbackMessagesCount() {
        rollbackMessagesCount += 1;
    }

    public long getMinMessageProcessingTime() {
        return minMessageProcessingTime;
    }

    public void setMinMessageProcessingTime(final long currentProcessingTime) {
        this.minMessageProcessingTime = calculateMinProcessingTime(getMinMessageProcessingTime(), currentProcessingTime);
    }

    public long getMaxMessageProcessingTime() {
        return maxMessageProcessingTime;
    }

    public void setMaxMessageProcessingTime(final long currentProcessingTime) {
        this.maxMessageProcessingTime = calculateMaxProcessingTime(getMaxMessageProcessingTime(), currentProcessingTime);
    }

    public long getAvgMessageProcessingTime() {
        return getTotalProcessingTime() / getMessagesRecievedCount();
    }

    public long getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(final long totalProcessingTime) {
        this.totalProcessingTime += totalProcessingTime;
    }

    public String getLastMessageRecievedDate() {
        return lastMessageRecievedDate;
    }

    public void setLastMessageRecievedDate(final Calendar cal) {
        this.lastMessageRecievedDate = DateStringUtils.getStringFromDate(cal.getTime(), Constants.TIME_STAMP_FORMAT);
    }

    public String getRollbackReasion() {
        return rollbackReasion;
    }

    public void setRollbackReasion(final String rollbackReasion) {
        this.rollbackReasion = rollbackReasion;
    }

}
