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
import abc.xyz.pts.bcs.common.jmx.IMessageSenderMXBean;

/**
 * This is class implements methods store status of sent JMS messages information. This information would be used by JMX
 *
 * @author ryattapu.
 */
public class MessageSenderMXBean implements IMessageSenderMXBean {
    private long messagesSentCount;
    private String lastMessageSentDate;

    public long getMessagesSentCount() {
        return messagesSentCount;
    }

    public void updateMessagesSentCount() {
        messagesSentCount += 1;
    }

    public String getLastMessageSentDate() {
        return lastMessageSentDate;
    }

    public void setLastMessageSentDate(final Calendar cal) {
        this.lastMessageSentDate = DateStringUtils.getStringFromDate(cal.getTime(), Constants.TIME_STAMP_FORMAT);
    }

}
