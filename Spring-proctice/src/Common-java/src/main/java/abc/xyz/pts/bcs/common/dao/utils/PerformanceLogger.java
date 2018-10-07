/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;

/**
 * Basic performance logging helper. Initially developed for QAT-1259 (Audit Search
 * performance) and QAT-1327 (Traveller Search performance). The *proper* way to
 * do perf logging would be aspects, and we need some tech debt to address
 * logging generally.
 *
 * However, for the purposes of the above stories, the following code should
 * suffice.
 */
public class PerformanceLogger {

    private static Logger logger = Logger.getLogger(PerformanceLogger.class);

    /** Start time of operation. */
    private long startTime;
    /** Source of the performance logging operation. */
    private final String className;


    public PerformanceLogger(final Object obj) {
        className = obj.getClass().getName();
    }

    /**
     * Start a logging operation. Note for an initial cut we assume logging ops
     * are not nested, since we're running statically. We will likely have to change
     * this in future (unless we move to aspects)
     *
     * @param obj The calling object
     */
    public void start() {
        if (startTime > 0) {
            throw new IllegalStateException("Currently logging data!");
        }

        startTime = System.currentTimeMillis();
    }

    /**
     * Stop a logging operation, and output builder details and an accompanying
     * message
     *
     * @param builder The builder used for the DB query
     * @param message The message to output as part of the logging
     */
    public void stop (final QueryBuilder builder, final String message) {
        stop(message);
        if (logger.isInfoEnabled()) {
            logger.info(className + " - " + builder);
        }
    }

    /**
     * Stop a logging operation and output an accompanying message.
     *
     * @param message The message to output as part of the logging
     */
    public void stop(final String message) {
        final long endTime = System.currentTimeMillis();

        final long executionTime = endTime - startTime ;
        if(logger.isInfoEnabled()) {
            final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSSS");
            logger.info(className + " - " + message + " took: " + sdf.format(new Date(executionTime)) + " (mm:ss:SSSS)");
        }

        startTime = 0;
    }

    /**
     * Output a perf-related log message
     */
    public void log(final String str) {
        if(logger.isInfoEnabled()) {
            logger.info(className + " - " + str);
        }
    }
}
