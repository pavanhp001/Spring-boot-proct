/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.profiler.model;

/**
 * <p>
 * Enum that models the possible values. This is strictly to ensure type-safety
 * and to give a flexibility to implement enum-based strategy pattern when the
 * comparators are actually used. These enums are the actual distinct possible
 * comparators that the system can ever support.
 * </p>
 * 
 * @author sai.krishnamurthy
 */
public enum ProfileComparatorValue {

    EQUAL_TO("="), NOT_EQUAL_TO("!="), LESS_THAN_EQUAL_TO("<="), GREATER_THAN_EQUAL_TO(">="), LESS_THAN("<"), GREATER_THAN(">"), BETWEEN("BETWEEN");
    
    private static final String INVALID_TOKEN_ERROR_MESSAGE = "No ProfileComparatorValue found for the token %s";
    private final String token;

    private ProfileComparatorValue(String token) {
	this.token = token;
    }

    public String getToken() {
	return token;
    }

    /**
     * <p>
     * A static look-up method to return {@link ProfileComparatorValue} from a
     * token An {@link IllegalArgumentException} is thrown for an invalid token.
     * </p>
     * 
     * @param token
     * @return {@link ProfileComparatorValue}
     */
    public static ProfileComparatorValue fromToken(final String token) {
	for (ProfileComparatorValue value : ProfileComparatorValue.values()) {
	    if (value.getToken().equals(token)) {
		return value;
	    }
	}
	throw new IllegalArgumentException(String.format(INVALID_TOKEN_ERROR_MESSAGE, token));
    }
}
