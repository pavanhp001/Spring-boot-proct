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
package abc.xyz.pts.bcs.common.communication.impl;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import abc.xyz.pts.bcs.common.communication.ConcurrentPropertyManagerInterface;

public class ConcurrentPropertyManager implements ConcurrentPropertyManagerInterface {

    private long passivationPeriod;
    private ConcurrentLinkedQueue<Properties> activePropertiesQueue = new ConcurrentLinkedQueue<Properties>();
    private ConcurrentLinkedQueue<Properties> passivatePropertiesQueue = new ConcurrentLinkedQueue<Properties>();

    public synchronized Properties getActiveProperties() {
        Properties properties = activePropertiesQueue.poll();
        if (properties != null) {
            activePropertiesQueue.offer(properties);
        }
        return properties;
    }

    public int numberOfActiveProperties() {
        return activePropertiesQueue.size();
    }

    public int numberOfPassivatedProperties() {
        return passivatePropertiesQueue.size();
    }

    public synchronized void passivateProperties(final Properties propertiesToPassivate) {
        if (activePropertiesQueue.remove(propertiesToPassivate)) {
            passivatePropertiesQueue.offer(propertiesToPassivate);
        }
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(new Runnable() {
            public void run() {
                Properties properties = passivatePropertiesQueue.poll();
                if (properties != null) {
                    activePropertiesQueue.offer(properties);
                }
            }
        }, getPassivationPeriod(), TimeUnit.SECONDS);
    }

    private long getPassivationPeriod() {
        return passivationPeriod;
    }

    public void setPassivationPeriod(final long passivationPeriod) {
        this.passivationPeriod = passivationPeriod;
    }

    public ConcurrentPropertyManager(final List<Properties> propertiesList) {
        for (Properties properties : propertiesList) {
            activePropertiesQueue.offer(properties);
        }
    }

}
