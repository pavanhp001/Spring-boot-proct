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
package abc.xyz.pts.bcs.admin.business;

import java.util.List;

import abc.xyz.pts.bcs.common.exception.ApplicationException;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

public interface UserRoleService {
    
    List<UserRoleData> findAllRoles();
    UserRoleData findRoleByCode(final String code);
    void addRole(final UserRoleData roleData) throws ApplicationException;
    void updateRole(final UserRoleData roleData);

}
