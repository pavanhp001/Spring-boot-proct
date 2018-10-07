/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.profiler.model;

import java.util.Collection;

/**
 * <p>A Generic DAO that will be used for the data access through appropriate implementations.</p>
 * @author sai.krishnamurthy
 */
public interface GenericDAO<T> {
    T create(T entity);
    T read(long id);
    Collection<T> readAll();
    T update(T entity);
}
