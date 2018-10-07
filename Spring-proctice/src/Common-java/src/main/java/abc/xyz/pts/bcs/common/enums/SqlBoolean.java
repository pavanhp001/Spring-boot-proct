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
package abc.xyz.pts.bcs.common.enums;

public enum SqlBoolean {

    YES("Y"), TRUE("T"), FALSE("F"), NO("N");
    private String code;
    private SqlBoolean(final String codeValue) {
        code = codeValue;
    }
    public String getCode() {
        return code;
    }
    public void setCode(final String code) {
        this.code = code;
    }
    public static boolean getBoolean(final String booleanCode) {
        boolean result = false;
        if (booleanCode != null) {
            if ("Y".equals(booleanCode) || "T".equals(booleanCode)) {
                result = true;
            } else if ("F".equals(booleanCode) || "N".equals(booleanCode)) {
                result = false;
            } else {
                result =Boolean.valueOf(booleanCode);
            }
        }
        return result;
    }
    public boolean getBoolean() {
        return getBoolean(getCode());
    }
}
