/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.profiler.model;

import java.io.Serializable;

/**
 * <p>An immutable model representing a <code>Profile Parameter</code>
 * For example,
 *    <i>Payment Method</i> can be a parameter.
 *    All the parameters will be keyed by a token which can then mapped using locale specific translation bundles. 
 * </p>
 * @author sai.krishnamurthy
 */
public class ProfileParameter implements Serializable {

    private static final long serialVersionUID = 6642749941975672130L;
    private final long id;
    private final String key;
    
    public ProfileParameter(final long id, final String key) {
	this.id = id;
	this.key = key;
    }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((key == null) ? 0 : key.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ProfileParameter other = (ProfileParameter) obj;
	if (id != other.id)
	    return false;
	if (key == null) {
	    if (other.key != null)
		return false;
	} else if (!key.equals(other.key))
	    return false;
	return true;
    }
    
    
    
    
    
    
    
    
    
    
    
}
