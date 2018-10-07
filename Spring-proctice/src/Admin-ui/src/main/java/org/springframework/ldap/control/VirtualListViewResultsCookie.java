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
package org.springframework.ldap.control;

/**
 * Wrapper class for the cookie returned when using the {@link VirtualListViewControl}.
 *
 * @author Ulrik Sandberg
 */
public class VirtualListViewResultsCookie {

    private byte[] cookie;

    private int contentCount;

    private int targetPosition;

    /**
     * Constructor.
     *
     * @param cookie the cookie returned by a VirtualListViewResponseControl.
     * @param targetPosition
     * @param contentCount
     */
    public VirtualListViewResultsCookie(final byte[] cookie, final int targetPosition, final int contentCount) {
        this.cookie = cookie;
        this.targetPosition = targetPosition;
        this.contentCount = contentCount;
    }

    /**
     * Get the cookie.
     *
     * @return the cookie.
     */
    public byte[] getCookie() {
        return cookie;
    }

    public int getContentCount() {
        return contentCount;
    }

    public int getTargetPosition() {
        return targetPosition;
    }
}
