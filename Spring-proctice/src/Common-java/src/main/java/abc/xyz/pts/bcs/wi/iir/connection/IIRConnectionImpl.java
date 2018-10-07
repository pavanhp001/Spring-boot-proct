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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import ssa.ssase.ClieSock;
import ssa.ssautil.SSAException;
import ssa.ssautil.SSASocketException;
import abc.xyz.pts.bcs.wi.iir.IIRConstants;
import abc.xyz.pts.bcs.wi.iir.IIRUtil;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSessionException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSocketException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSystemException;


public final class IIRConnectionImpl implements IIRConnection
{
    private String iirConnectionServerHost; // Primary / Secondary
    private Integer iirConnectionServerPort; // Primary / Secondary

    private String iirSystemName;
    private String iirUserName;
    private String iirPassword;
    private Integer iirRuleBaseNumber;
    private String iirService;
    private Integer iirSessionNumber;
    private String iirSystemOptions;
    private String iirSearchName;
    private Integer iirTimeoutMilliseconds;
    private ClieSock clieSock;
    private boolean isPrimary;
    private String filter;

    private static final Logger log = Logger.getLogger(IIRConnectionImpl.class);

    private void setEncoding(final ClieSock cs) throws IIRSocketException
    {
        try
        {
            // Byte Encoding on the Rulebase Server
            clieSock.ids_set_encoding(IIRConstants.ENCODING_UTF8);
        }
        catch (SSAException e)
        {
            throw new IIRSocketException("ids_set_encoding", e, IIRUtil.getIIRErrorMsg(cs));
        }
    }


    public void connect() throws IIRSessionException, IIRSocketException, IIRSystemException
    {
        try
        {

            // Connect to the Search Server
            clieSock = new ClieSock(iirConnectionServerHost, iirConnectionServerPort, iirTimeoutMilliseconds);

            // character encoding
            setEncoding(clieSock);

            //
            systemOpen(clieSock);
        }
        catch (SSAException e)
        {
            throw new IIRSocketException("new ClieSock", e, IIRUtil.getIIRErrorMsg(clieSock));
        }
    }

    private String getRulebaseName()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("odb");
        buf.append(":");
        buf.append(iirRuleBaseNumber);
        buf.append(":");
        buf.append(iirUserName);
        buf.append("/");
        buf.append(iirPassword);
        buf.append("@");
        buf.append(iirService);

        return buf.toString();
    }

    public void systemOpen(final ClieSock cs) throws IIRSystemException
    {
        try
        {
            cs.ids_system_open
                ( getRulebaseName() // IN: Rulebase name
                , iirSystemName // IN: System name
                , IIRConstants.VERBOSITY_OFF // IN: Verbosity
                , iirSystemOptions // IN: Options
                );
        }
        catch (SSAException e)
        {
            throw new IIRSystemException("ids_system_open", e, IIRUtil.getIIRErrorMsg(cs));
        }
    }

    private void systemClose(final ClieSock cs) throws IIRSystemException
    {
        try
        {
            cs.ids_system_close();
        }
        catch (SSAException e)
        {
            throw new IIRSystemException("ids_system_close", e, IIRUtil.getIIRErrorMsg(cs));
        }
    }

    public void disconnect() throws IIRException
    {
        // release connection to pool

        try
        {
            try {
            systemClose(clieSock);
            } catch(IIRSystemException e) {
                log.warn("Unable to close system when disconnecting IIR connection", e);
            }
            clieSock.ids_disconnect();
        }
        catch (SSASocketException e)
        {
            throw new IIRSystemException("ids_disconnect", e, IIRUtil.getIIRErrorMsg(clieSock));
        }
    }

    // ****
    public ClieSock getSocket() throws IIRConnectException
    {
        return this.clieSock;
    }


    public String getIirConnectionServerHost()
    {
        return iirConnectionServerHost;
    }

    public void setIirConnectionServerHost(final String iirConnectionServerHost)
    {
        this.iirConnectionServerHost = iirConnectionServerHost;
    }

    public Integer getIirConnectionServerPort()
    {
        return iirConnectionServerPort;
    }

    public void setIirConnectionServerPort(final Integer iirConnectionServerPort)
    {
        this.iirConnectionServerPort = iirConnectionServerPort;
    }

    public String getIirSystemName()
    {
        return iirSystemName;
    }

    public void setIirSystemName(final String iirSystemName)
    {
        this.iirSystemName = iirSystemName;
    }

    public String getIirUserName()
    {
        return iirUserName;
    }

    public void setIirUserName(final String iirUserName)
    {
        this.iirUserName = iirUserName;
    }

    public String getIirPassword()
    {
        return iirPassword;
    }

    public void setIirPassword(final String iirPassword)
    {
        this.iirPassword = iirPassword;
    }

    public Integer getIirRuleBaseNumber()
    {
        return iirRuleBaseNumber;
    }

    public void setIirRuleBaseNumber(final Integer iirRuleBaseNumber)
    {
        this.iirRuleBaseNumber = iirRuleBaseNumber;
    }

    public String getIirService()
    {
        return iirService;
    }

    public void setIirService(final String iirService)
    {
        this.iirService = iirService;
    }

    public Integer getIirTimeoutMilliseconds()
    {
        return iirTimeoutMilliseconds;
    }

    public void setIirTimeoutMilliseconds(final Integer iirTimeoutMilliseconds)
    {
        this.iirTimeoutMilliseconds = iirTimeoutMilliseconds;
    }

    public void setIirSessionNumber(final Integer iirSessionNumber)
    {
        this.iirSessionNumber = iirSessionNumber;
    }

    public Integer getIirSessionNumber()
    {
        return iirSessionNumber;
    }

    public boolean isPrimary()
    {
        return isPrimary;
    }

    public void setPrimary(final boolean isPrimary)
    {
        this.isPrimary = isPrimary;
    }

    public String getIirSystemOptions()
    {
        return iirSystemOptions;
    }

    public void setIirSystemOptions(final String iirSystemOptions)
    {
        this.iirSystemOptions = iirSystemOptions;
    }


    /**
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    @Override
    public int setFilter(final String filter) throws IIRSearchException {
        try {
            if(!StringUtils.equals(this.filter, filter)) {
                if(log.isDebugEnabled()) {
                    log.debug("Filter has changed, sending to IIR:" + filter);
                }
                int status = clieSock.ids_search_filter(iirSearchName, filter);
                this.filter = filter;
                return status;
            }

            if(log.isDebugEnabled()) {
                log.debug("Filter was cached: " + filter);
            }
        } catch (SSAException e)
        {
            throw new IIRSearchException("ids_search_filter", e, IIRUtil.getIIRErrorMsg(clieSock));
        }

        return 0;
    }

    /**
     * @return the iirSearchName
     */
    @Override
    public String getIirSearchName() {
        return iirSearchName;
    }

    /**
     * @param iirSearchName the iirSearchName to set
     */
    @Override
    public void setIirSearchName(final String iirSearchName) {
        this.iirSearchName = iirSearchName;
    }


}
