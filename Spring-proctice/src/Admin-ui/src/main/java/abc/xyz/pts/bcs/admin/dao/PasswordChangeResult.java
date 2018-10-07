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
package abc.xyz.pts.bcs.admin.dao;

public enum PasswordChangeResult {
    SUCCESS, INVALID_OLD_PWD, TOO_YOUNG, TOO_SHORT, NOT_COMPLEX, IN_HISTORY, CHG_REJECTED, ERROR
}
