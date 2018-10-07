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
package abc.xyz.pts.bcs.common.web.bean;

/**
 * Bean to hold the contents of the screen which is then printed or exported.
 */
public class ScreenContent {

    private Object data;

    /**
     * Gets the screen content data.
     *
     * @return Screen content data.
     */
    public final Object getData() {
        return data;
    }

    /**
     * Sets the screen content data.
     *
     * @param data
     *            The screen content data.
     */
    public final void setData(final Object inData) {
        this.data = inData;
    }
}
