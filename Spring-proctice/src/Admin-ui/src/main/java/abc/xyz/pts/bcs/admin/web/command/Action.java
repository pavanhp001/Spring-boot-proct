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
package abc.xyz.pts.bcs.admin.web.command;

public enum Action {

    ADD("add"), VIEW("view"), DELETE("delete"), RESET_PASSWORD("resetPassword"), VALIDATE_UPDATE("validateUpdate"), UPDATE(
            "update");

    private final String value;

    Action(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
