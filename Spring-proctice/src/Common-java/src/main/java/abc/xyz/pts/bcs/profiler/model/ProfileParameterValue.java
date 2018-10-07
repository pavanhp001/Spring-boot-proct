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
 * An immutable model representing a <code>Profile Parameter Value</code> There
 * are 2 possible types of values for the parameters.
 * <ul>
 * <li>Enumerated (ie allowed set of values) - eg : For Gender parameter, the
 * predefined set of values are "Male" and "Female"</li>
 * <li>Free format(ie user entered values) - eg : For Credit Card parameter, the
 * users will enter a free text.</li>
 * </ul>
 * </p>
 * 
 * @author sai.krishnamurthy
 */
public class ProfileParameterValue implements Serializable {

    private static final long serialVersionUID = 6642749941975672550L;
    private final long id;
    private final String key;
    private final String value;
    private final boolean enumerated;

    public ProfileParameterValue(final long id, final String key, final String value, final boolean enumerated) {
	this.id = id;
	this.key = key;
	this.value = value;
	this.enumerated = enumerated;
    }

    public long getId() {
	return id;
    }

    public String getKey() {
	return key;
    }

    public String getValue() {
	return value;
    }

    public boolean isEnumerated() {
	return enumerated;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (enumerated ? 1231 : 1237);
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
	ProfileParameterValue other = (ProfileParameterValue) obj;
	if (enumerated != other.enumerated)
	    return false;
	if (id != other.id)
	    return false;
	if (key == null) {
	    if (other.key != null)
		return false;
	} else if (!key.equals(other.key))
	    return false;
	if (value == null) {
	    if (other.value != null)
		return false;
	} else if (!value.equals(other.value))
	    return false;
	return true;
    }
}
