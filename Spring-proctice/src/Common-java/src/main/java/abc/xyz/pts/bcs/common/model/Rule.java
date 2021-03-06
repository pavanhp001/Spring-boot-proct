/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.model;

/**
 * <p>Rule that will evaluate to a boolean</p>
 * @author sai.krishnamurthy
 * @param <T>
 */
public interface Rule<T> {
    boolean evaluate(T input); 
}
