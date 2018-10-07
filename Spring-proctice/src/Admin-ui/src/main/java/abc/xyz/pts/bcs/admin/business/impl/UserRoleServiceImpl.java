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
package abc.xyz.pts.bcs.admin.business.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import abc.xyz.pts.bcs.admin.business.UserRoleService;
import abc.xyz.pts.bcs.common.exception.ApplicationException;
import abc.xyz.pts.bcs.common.ldap.dao.UserRoleDao;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

@Service("userRoleService")
public final class UserRoleServiceImpl implements UserRoleService
{
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRoleData> findAllRoles() {
        return userRoleDao.findAllRoles();
    }

    @Override
    public void addRole(final UserRoleData roleData) throws ApplicationException {
        userRoleDao.add(roleData);
    }
    
    @Override
    public UserRoleData findRoleByCode(final String code) {
        if (StringUtils.isBlank(code)) {
           return null;
        }
        return userRoleDao.findByRoleCode(code);
    }
    
    
    public void setUserRoleDao(final UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public void updateRole(final UserRoleData roleData) {
        if (roleData == null) {
            return;
         }
        userRoleDao.update(roleData);
    }
}
