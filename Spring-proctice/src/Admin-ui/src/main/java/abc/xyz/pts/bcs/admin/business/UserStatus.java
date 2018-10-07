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

public enum UserStatus {

    ENABLED(1), DISABLED(2), ALL(3);

    private final int value;

    UserStatus(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserStatus getInstance(final int value) {
        switch (value) {
            case 1:
                return UserStatus.ENABLED;
            case 2:
                return UserStatus.DISABLED;
            case 3:
            default:
                return UserStatus.ALL;
        }
    }
}
