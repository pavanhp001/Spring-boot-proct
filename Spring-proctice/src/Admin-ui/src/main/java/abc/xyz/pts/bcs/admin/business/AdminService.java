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
package abc.xyz.pts.bcs.admin.business;

import java.util.List;

import abc.xyz.pts.bcs.admin.dao.PasswordChangeResult;
import abc.xyz.pts.bcs.admin.dto.Airport;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.exception.InvalidAirportException;
import abc.xyz.pts.bcs.admin.exception.UserExistsException;
import abc.xyz.pts.bcs.admin.exception.UserMissingException;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

public interface AdminService {

    User getUser(String username);

    void updateUser(User user) throws UserMissingException, InvalidAirportException;

    void deleteUser(String username) throws UserMissingException;

    void addUser(User user) throws UserExistsException, InvalidAirportException;

    void resetPassword(String username);

    PasswordChangeResult changePassword(String username, String oldPwd, String newPwd);

    List<Airport> getValidAirports();

    List<User> getSearchUsers(AdminUserSearchCommand adminUserSearchCommand, TableActionCommand adminSearchTableCommand);
}
