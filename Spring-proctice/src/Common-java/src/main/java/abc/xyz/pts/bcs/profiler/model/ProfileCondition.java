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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * An immutable Model representing a Profile condition that the user would
 * create. A <i>Profile condition</i> is a combination of a
 * {@link ProfileParameter}, {@link ProfileComparator} and one or more
 * {@link ProfileParameterValue} values.
 * </p>
 * 
 * @author sai.krishnamurthy
 */
public class ProfileCondition implements Serializable {

    private static final long serialVersionUID = 8544178128411577275L;
    
    private long id;
    private ProfileParameter parameter;

    // Can be default or user selected.
    private ProfileComparator selectedComparator;

    private List<ProfileComparator> allowedComparators;

    // Will only have the values that the user has selected (either enumerated or free text).
    private List<ProfileParameterValue> selectedParameterValues;
    
    // Can be used to employ a pre-fetch strategy to be used for validation.
    private List<ProfileParameterValue> allowedParameterValues;

    private boolean exclude;
    private int weight;

    private ProfileCondition() {
    }

    /*
     * A static member builder class to enforce immutability of the outer
     * object.
     */
    public static class ProfileConditionBuilder {
	private long id;
	private ProfileParameter parameter;
	private ProfileComparator selectedComparator;
	private List<ProfileComparator> allowedComparators = new ArrayList<ProfileComparator>();
	private List<ProfileParameterValue> selectedParameterValues = new ArrayList<ProfileParameterValue>();
	private boolean exclude;
	private int weight;
	private List<ProfileParameterValue> allowedParameterValues;

	public ProfileConditionBuilder id(final long id) {
	    this.id = id;
	    return this;
	}
	
	public ProfileConditionBuilder parameter(final ProfileParameter parameter) {
	    this.parameter = parameter;
	    return this;
	}

	public ProfileConditionBuilder selectedComparator(final ProfileComparator comparator) {
	    this.selectedComparator = comparator;
	    return this;
	}

	public ProfileConditionBuilder addAllowedComparator(final ProfileComparator comparator) {
	    this.allowedComparators.add(comparator);
	    return this;
	}
	
	public ProfileConditionBuilder addAllowedComparators(final List<ProfileComparator> comparators) {
	    this.allowedComparators.addAll(comparators);
	    return this;
	}

	public ProfileConditionBuilder addSelectedParameterValue(final ProfileParameterValue parameterValue) {
	    this.selectedParameterValues.add(parameterValue);
	    return this;
	}
	
	public ProfileConditionBuilder addSelectedParameterValues(final List<ProfileParameterValue> parameterValues) {
	    this.selectedParameterValues.addAll(parameterValues);
	    return this;
	}
	
	public ProfileConditionBuilder addAllowedParameterValue(final ProfileParameterValue parameterValue) {
	    this.allowedParameterValues.add(parameterValue);
	    return this;
	}
	
	public ProfileConditionBuilder addAllowedParameterValues(final List<ProfileParameterValue> parameterValues) {
	    this.allowedParameterValues.addAll(parameterValues);
	    return this;
	}

	public ProfileConditionBuilder include() {
	    this.exclude = false;
	    return this;
	}

	public ProfileConditionBuilder exclude() {
	    this.exclude = true;
	    return this;
	}

	public ProfileConditionBuilder weight(final int weight) {
	    this.weight = weight;
	    return this;
	}

	public ProfileCondition build() {
	    ProfileCondition condition = new ProfileCondition();
	    condition.id = id;
	    condition.allowedComparators = allowedComparators;
	    condition.parameter = parameter;
	    condition.selectedComparator = selectedComparator;
	    condition.selectedParameterValues = selectedParameterValues;
	    condition.exclude = exclude;
	    condition.weight = weight;
	    return condition;
	}
    }

    public ProfileParameter getParameter() {
	return parameter;
    }

    public ProfileComparator getSelectedComparator() {
	return selectedComparator;
    }

    public List<ProfileComparator> getAllowedComparators() {
	return Collections.unmodifiableList(allowedComparators);
    }

    public List<ProfileParameterValue> getSelectedParameterValues() {
	return Collections.unmodifiableList(selectedParameterValues);
    }
    
    public List<ProfileParameterValue> getAllowedParameterValues() {
        return Collections.unmodifiableList(allowedParameterValues);
    }

    public boolean isExclude() {
	return exclude;
    }

    public int getWeight() {
	return weight;
    }

    public long getId() {
        return id;
    }
    
}
