/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.jmx;

/**
 * This interface defined generic methods for JMS messages received status information. This information would be used
 * by JMX
 *
 * @author ryattapu.
 */
public interface IMessageListenerMXBean {

    // messages recieved
    long getMessagesRecievedCount();

    long getRollbackMessagesCount();

    String getRollbackReasion();

    String getLastMessageRecievedDate();

    // messages processing time
    long getMinMessageProcessingTime();

    long getMaxMessageProcessingTime();

    long getAvgMessageProcessingTime();

}
