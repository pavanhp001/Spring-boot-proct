/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import abc.xyz.pts.bcs.common.dao.support.query.Field;


/**
 *  FuzzyTravellerQueryArg arg object for simple WHERE clauses (e.g. table1.field1 = ? )
 */
public class FuzzyTravellerQueryArg extends QueryArg {


    /**
     * Constructor.
     * @param searchField Search field name
     * @param table Table to prepend field name
     * @param value Criteria value
     */
    public FuzzyTravellerQueryArg(final Field firstName, 
    		final Field lastName, 
    		final Object firstNameValue,
    		final Object lastNameValue
    		) {    	
   
    	 if (firstNameValue != null && !"".equals(firstNameValue) && lastNameValue != null && !"".equals(lastNameValue)){    		 
    		 addToWhereClause(" CONTAINS(FULL_NAME,'NDATA(FORENAME," + firstNameValue + ") AND NDATA(LAST_NAME," + lastNameValue + ")',1)>0");   
    	 }else if (firstNameValue != null && !"".equals(firstNameValue)){    
    		 addToWhereClause(" CONTAINS(FULL_NAME,'NDATA(FORENAME," + firstNameValue + ")',1)>0");
    	 }else if (lastNameValue != null && !"".equals(lastNameValue)){    
    		 addToWhereClause(" CONTAINS(FULL_NAME,'NDATA(LAST_NAME," + lastNameValue + ")',1)>0");	 
    	 }
    }



}
