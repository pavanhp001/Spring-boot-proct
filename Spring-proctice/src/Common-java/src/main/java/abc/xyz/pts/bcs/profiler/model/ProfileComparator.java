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
 * <p>
 * An immutable model representing a <code>Profile Comparator</code> For
 * example, <i>=</i> can be a comparator.
 * 
 * </p>
 * 
 * @author sai.krishnamurthy
 */
public class ProfileComparator implements Serializable {

    private static final long serialVersionUID = -987906690295564891L;
    private final long id;
    private final String key;
    private final ProfileComparatorValue value;

    public ProfileComparator(final long id, final String key, final ProfileComparatorValue value) {
	this.id = id;
	this.key = key;
	this.value = value;
    }

    public long getId() {
	return id;
    }

    public String getKey() {
	return key;
    }

    public ProfileComparatorValue getValue() {
	return value;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((key == null) ? 0 : key.hashCode());
	result = prime * result + ((value == null) ? 0 : value.hashCode());
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
	ProfileComparator other = (ProfileComparator) obj;
	if (id != other.id)
	    return false;
	if (key == null) {
	    if (other.key != null)
		return false;
	} else if (!key.equals(other.key))
	    return false;
	if (value != other.value)
	    return false;
	return true;
    }
}
