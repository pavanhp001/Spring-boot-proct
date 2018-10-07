/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2012
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springmodules.validation.commons.DefaultBeanValidator;

import abc.xyz.pts.bcs.admin.web.command.UserRoleDataCommand;
import abc.xyz.pts.bcs.admin.web.command.UserRoleListCommand;

public class AbstractUserRoleController 
{
    protected static final String ACTION_VIEW_USER_ROLE = "viewUserRole";
    protected static final String ACTION_ADD_USER_ROLE = "addUserRole";
    protected static final String ACTION_EDIT_USER_ROLE = "editUserRole";
    
    protected static final String USER_ROLE_LIST_COMMAND = "userRoleListCommand";
    protected static final String USER_ROLE_COMMAND = "userRoleCommand";
    
    protected static final String ADD_USER_ROLE = "addUserRole.view";
    protected static final String VIEW_USER_ROLE = "viewUserRole.view";
    protected static final String LIST_USER_ROLE = "userRoleList.view";
    protected static final String EDIT_USER_ROLE = "editUserRole.view";
    
    protected static final String REDIRECT_USER_ROLE_LIST = "redirect:/userRoleList.form";
    protected static final String REDIRECT_VIEW_USER_ROLE = "redirect:/viewUserRole.form"; 
    protected static final String REDIRECT_ADD_USER_ROLE = "redirect:/addUserRole.form";
    protected static final String REDIRECT_EDIT_USER_ROLE = "redirect:/editUserRole.form";

    @Autowired
    private DefaultBeanValidator commonsValidator;
    
    @ModelAttribute(USER_ROLE_COMMAND)
    public UserRoleDataCommand newUserRoleCommand() {
        return new UserRoleDataCommand();
    }
    
    @ModelAttribute(USER_ROLE_LIST_COMMAND)
    public UserRoleListCommand newUserRoleListCommand() {
        return new UserRoleListCommand();
    }
    
    public void clearSession(final HttpSession session)
    {
        session.removeAttribute(USER_ROLE_COMMAND);
    }
    
    public DefaultBeanValidator getCommonsValidator() {
        return commonsValidator;
    }

   
    
}
