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
 * A Parameterized generic interface that represents a Service.
 * The concrete implementations will know what types they need to handle. 
 * This will decouple the sender and receiver and any new service will scale in the future as a concrete implementation rather than changing the API and the existing implementation.
 * </p>
 * @author sai.krishnamurthy
 *
 */
public interface Service<Input,Output> {
    
    /**
     * <p>Abstract method to perform an operation.</p> 
     * @param input parameterized input type.
     * @return parameterized output type.
     */
    Output execute(Input input);
}
