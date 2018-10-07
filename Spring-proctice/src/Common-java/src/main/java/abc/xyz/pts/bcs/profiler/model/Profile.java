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

import abc.xyz.pts.bcs.wi.dto.RecommendedAction;
import abc.xyz.pts.bcs.wi.dto.SeverityReason;

/**
 * <p>
 * 	An immutable model representing the <i>Profile</i> in the system.
 * Note that a disabled Profile can exist even without a single condition.
 * </p>
 * 
 * @author sai.krishnamurthy
 */
public class Profile implements Serializable {

    private static final long serialVersionUID = -5645427296790216805L;

    private long id;
    private String name;
    private String description;
    private List<RecommendedAction> recommendedActions;
    private List<SeverityReason> reasons;
    private List<ProfileCondition> conditions;
    private int threshold;
    private boolean enabled;
    private String createdBy;
    private int version;

    // A Static builder class to support optional objects at the same time achieving immutability. 
    public static class ProfileBuilder {
	private long id;
	private String name;
	private String description;
	private List<RecommendedAction> recommendedActions = new ArrayList<RecommendedAction>();
	private List<SeverityReason> reasons = new ArrayList<SeverityReason>();
	private List<ProfileCondition> conditions = new ArrayList<ProfileCondition>();
	private int threshold;
	private boolean enabled;
	private String createdBy;
	private int version;
	
	public ProfileBuilder id(final long id) {
	    this.id = id;
	    return this;
	}
	
	public ProfileBuilder name(final String name) {
	    this.name = name;
	    return this;
	}
	
	public ProfileBuilder description(final String description) {
	    this.description = description;
	    return this;
	}
	
	public ProfileBuilder addRecommendedAction(final RecommendedAction recommendedAction) {
	    this.recommendedActions.add(recommendedAction);
	    return this;
	}
	
	public ProfileBuilder addRecommendedActions(final List<RecommendedAction> recommendedActions) {
	    this.recommendedActions.addAll(recommendedActions);
	    return this;
	}
	
	public ProfileBuilder addReason(final SeverityReason reason) {
	    this.reasons.add(reason);
	    return this;
	}
	
	public ProfileBuilder addReasons(final List<SeverityReason> reasons) {
	    this.reasons.addAll(reasons);
	    return this;
	}
	
	public ProfileBuilder addCondition(final ProfileCondition condition) {
	    this.conditions.add(condition);
	    return this;
	}
	
	public ProfileBuilder addConditions(final List<ProfileCondition> conditions) {
	    this.conditions.addAll(conditions);
	    return this;
	}
	
	public ProfileBuilder threshold(final int threshold) {
	    this.threshold = threshold;
	    return this;
	}
	
	public ProfileBuilder enable() {
	    this.enabled = true;
	    return this;
	}
	
	public ProfileBuilder disable() {
	    this.enabled = false;
	    return this;
	}
	
	public ProfileBuilder createdBy(final String createdBy) {
	    this.createdBy = createdBy;
	    return this;
	}
	
	public ProfileBuilder version(final int version) {
	    this.version = version;
	    return this;
	}
	
	public Profile build() {
	    Profile profile = new Profile();
	    profile.id = id;
	    profile.name = name;
	    profile.description = description;
	    profile.recommendedActions = recommendedActions;
	    profile.reasons = reasons;
	    profile.conditions = conditions;
	    profile.threshold = threshold;
	    profile.enabled = enabled;
	    profile.createdBy = createdBy;
	    profile.version = version;
	    return profile;
	}
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<RecommendedAction> getRecommendedActions() {
        return Collections.unmodifiableList(recommendedActions);
    }

    public List<SeverityReason> getReasons() {
        return Collections.unmodifiableList(reasons);
    }

    public List<ProfileCondition> getConditions() {
        return Collections.unmodifiableList(conditions);
    }

    public int getThreshold() {
        return threshold;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public int getVersion() {
        return version;
    }
    
    

}
