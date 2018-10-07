/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2012
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.ldap.exception;

import abc.xyz.pts.bcs.common.exception.ApplicationException;
/**
 * This is used when a User Role already exists.
 * 
 * @author Mohammed.Motlib-Siddiqui
 *
 */
public final class RoleExistsException extends ApplicationException 
{
    private static final long serialVersionUID = -5652594107270540577L;
    
    public RoleExistsException(final Exception e)
    {
        super(e, "role.already.exists");
    }
}
