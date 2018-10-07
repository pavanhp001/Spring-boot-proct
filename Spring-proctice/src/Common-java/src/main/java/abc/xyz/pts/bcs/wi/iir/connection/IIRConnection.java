/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
/**
 * @author MMotlib-Siddiqui
 */
package abc.xyz.pts.bcs.wi.iir.connection;

import ssa.ssase.ClieSock;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSessionException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSocketException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSystemException;

public interface IIRConnection {

    public ClieSock getSocket() throws IIRConnectException;

    public String getIirConnectionServerHost();
    public Integer getIirConnectionServerPort();
    public String getIirSystemName();
    public String getIirUserName();
    public String getIirPassword();
    public Integer getIirRuleBaseNumber();
    public String getIirService();
        public String getIirSearchName();
        public void setIirSearchName(String searchName);
    public Integer getIirTimeoutMilliseconds();
    public boolean isPrimary();
    public int setFilter(String filter) throws IIRSearchException;
    public Integer getIirSessionNumber();

    public void connect() throws IIRSessionException, IIRSocketException, IIRSystemException;
    public void disconnect() throws IIRException;

}
