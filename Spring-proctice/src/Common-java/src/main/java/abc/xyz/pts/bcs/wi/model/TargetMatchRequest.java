/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.model;

import java.io.Serializable;
import java.util.Locale;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dto.TargetItem;

/**
 * <p>An immutable model that represents the request sent to a TargetMatcher service.</p>
 * @author sai.krishnamurthy
 */
public class TargetMatchRequest implements Serializable {

    private static final long serialVersionUID = 1637702050693225746L;
    private TableActionCommand tableActionCommand;
    private boolean match;
    private Locale locale;
    private boolean iirEnabled;
    private TargetItem targetItemToBeMatched;
        
    private TargetMatchRequest(){}
    
    public TableActionCommand getTableActionCommand() {
        return tableActionCommand;
    }

    public boolean isMatch() {
        return match;
    }

    public Locale getLocale() {
        return locale;
    }

    public boolean isIirEnabled() {
        return iirEnabled;
    }

    public TargetItem getTargetItemToBeMatched() {
        return targetItemToBeMatched;
    }

    public static class TargetMatchRequestBuilder {
	
	private TargetMatchRequest request = new TargetMatchRequest();
	
	public TargetMatchRequestBuilder tableActionCommand(final TableActionCommand tableActionCommand) {
	    request.tableActionCommand = tableActionCommand;
	    return this;
	}
	
	public TargetMatchRequestBuilder match(final boolean match) {
	    request.match = match;
	    return this;
	}
	
	public TargetMatchRequestBuilder locale(final Locale locale) {
	    request.locale = locale;
	    return this;
	}
	
	public TargetMatchRequestBuilder iirEnabled(final boolean iirEnabled) {
	    request.iirEnabled = iirEnabled;
	    return this;
	}
	
	public TargetMatchRequestBuilder targetItemToBeMatched(final TargetItem targetItemToBeMatched) {
	    request.targetItemToBeMatched = targetItemToBeMatched;
	    return this;
	}
	
	public TargetMatchRequest build() {
	    return this.request;
	}
    }
}
