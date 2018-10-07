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
package abc.xyz.pts.bcs.admin.web.validator;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.AdminUserUpdateCommand;

public class AdminUserUpdateValidator extends AbstractAdminValidator {

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(final Class clazz) {
        return clazz.equals(AdminUserUpdateCommand.class);
    }

    @Override
    protected User getUser(final Object command) {
        return ((AdminUserUpdateCommand) command).getUser();
    }
}
