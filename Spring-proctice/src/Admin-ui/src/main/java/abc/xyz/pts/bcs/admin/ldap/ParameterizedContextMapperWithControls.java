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
package abc.xyz.pts.bcs.admin.ldap;

import javax.naming.ldap.HasControls;

import org.springframework.ldap.core.simple.ParameterizedContextMapper;

/**
 * Mapper interface that allows controls assocaited with a search result to be passed to the mapper.
 *
 * @author tterry
 *
 * @param <T>
 */
public interface ParameterizedContextMapperWithControls<T> extends ParameterizedContextMapper<T> {

    T mapFromContextWithControls(final Object ctx, final HasControls hasControls);
}
