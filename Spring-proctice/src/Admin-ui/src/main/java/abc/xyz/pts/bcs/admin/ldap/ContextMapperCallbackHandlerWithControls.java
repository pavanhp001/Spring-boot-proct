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

import javax.naming.Binding;
import javax.naming.NameClassPair;
import javax.naming.ldap.HasControls;

import org.springframework.ldap.core.ContextMapperCallbackHandler;
import org.springframework.ldap.core.ObjectRetrievalException;

/**
 * An extension of the contextMapperCallbackHandler. This is able to provide the mapper with request controls specific
 * to a search result not those associated with the entire request.
 *
 * @author tterry
 */
public class ContextMapperCallbackHandlerWithControls extends ContextMapperCallbackHandler {

    private ParameterizedContextMapperWithControls<?> mapper = null;

    public ContextMapperCallbackHandlerWithControls(final ParameterizedContextMapperWithControls<?> mapper) {
        super(mapper);
        this.mapper = mapper;
    }

    /**
     * Cast the NameClassPair to a {@link Binding} and pass its object to the ContextMapper.
     *
     * @param nameClassPair
     *            a Binding instance.
     * @return the Object returned from the mapper.
     */
    public Object getObjectFromNameClassPair(final NameClassPair nameClassPair) {
        if (!(nameClassPair instanceof Binding)) {
            throw new IllegalArgumentException("Parameter must be an instance of Binding");
        }

        Binding binding = (Binding) nameClassPair;
        Object object = binding.getObject();
        if (object == null) {
            throw new ObjectRetrievalException("Binding did not contain any object.");
        }
        Object result = null;
        if (nameClassPair instanceof HasControls) {
            result = mapper.mapFromContextWithControls(object, (HasControls) nameClassPair);
        } else {
            result = mapper.mapFromContext(object);
        }
        return result;
    }
}
