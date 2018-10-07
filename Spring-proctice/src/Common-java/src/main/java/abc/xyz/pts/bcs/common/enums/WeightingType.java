/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

public enum WeightingType {
	
	DOCUMENT("D", "DOCUMENT_WEIGHTING"),
	PERSON("P", "PERSON_WEIGHTING");
	
	private final String code ; 
	private final String description;


	private WeightingType(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	
	public static WeightingType fromCode(final String code)
	{
		for(WeightingType weightingType : WeightingType.values())
		{
			if( weightingType.getCode().equals(code))
			{
				return weightingType; 
			}
		}
		throw new IllegalArgumentException("Cannot find WeightingType for code" + code ); 
	}

	public String getCode() {
		return code;
	}


	
}
