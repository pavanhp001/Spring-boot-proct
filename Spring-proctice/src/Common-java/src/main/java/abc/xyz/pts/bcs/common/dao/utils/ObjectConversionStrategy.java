/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.utils;

/**
 * <p>The conversion strategy to convert from one type to the other.</p>
 * @author sai.krishnamurthy
 * @param <T> the target type or the type to be converted to.
 */
public interface ObjectConversionStrategy<T> {

    /**
     * <p>The type of Object to accept. This is not a generic type because the accept should not be specific to a type and the implementations should use any strategy to work out the accepted type
     * like instanceof for example.</p>
     * @param object the type of {@link Object} to accept.
     * @return boolean to indicate whether this type is accepted or not.
     */
    public boolean accept(Object object);

    /**
     * <p>Returns the converted or the target object</p>
     * @return target object.
     */
    public T getTarget();

}

