/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import abc.xyz.pts.bcs.irisk.mvo.wiinterface.TargetSearchErrors;

public class TargetSearchResults implements Serializable {
    private List<TargetItem> targetMatches;
    private List<TargetSearchErrors> errors;
    private String movementDirection;
    public List<TargetItem> getTargetMatches() {
        if (targetMatches == null) {
            targetMatches = new ArrayList<TargetItem>();
        }
        return this.targetMatches;
    }

    public List<TargetSearchErrors> getErrors() {
        if (errors == null) {
            errors = new ArrayList<TargetSearchErrors>();
        }
        return this.errors;
    }

    public String getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(final String movementDirection) {
        this.movementDirection = movementDirection;
    }

}
