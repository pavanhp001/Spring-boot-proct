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
package abc.xyz.pts.bcs.common.model;

/**
 * <p>This is a model that represents a function as an object.
 * The concrete implementations can be passed around and treated as function objects for callbacks.
 * This is kind of similar to a closure.
 * </p>
 * @param <Output> the output value the function returns.
 * @author sai.krishnamurthy
 *
 */
public interface Function<Output> {
    Output call();
}
