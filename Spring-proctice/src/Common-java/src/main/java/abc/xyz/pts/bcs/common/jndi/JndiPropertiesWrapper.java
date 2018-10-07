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

package abc.xyz.pts.bcs.common.jndi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

public class JndiPropertiesWrapper implements Serializable {

    private static final long serialVersionUID = 1L;
    private long modifiedTimestamp;
    private byte[] configData;

    public JndiPropertiesWrapper() {
    }

    public JndiPropertiesWrapper(final long modifiedTimestamp, final byte[] configData) {
        this.modifiedTimestamp = modifiedTimestamp;
        this.configData = (byte[])configData.clone();
    }

    public void setModifiedTimestamp(final long modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public void setConfigData(final byte[] configData) {
        this.configData = (byte[])configData.clone();
    }

    public byte[] getConfigData() {
        return configData;
    }

    public InputStream getConfigDataAsInputStream() {
        return new ByteArrayInputStream(configData);
    }

    public long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public String toString() {
        return "Date: " + modifiedTimestamp + ", data: " + new String(configData);
    }

}
