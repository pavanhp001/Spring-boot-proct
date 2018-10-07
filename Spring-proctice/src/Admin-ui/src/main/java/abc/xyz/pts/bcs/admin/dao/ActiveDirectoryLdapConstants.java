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

public enum ActiveDirectoryLdapConstants 
{
    BUSINESS_CATEGORY("businessCategory"), // used for storing permissions
    CN("cn"),
    DATE_MODIFIED("dateModified"),
    DESCRIPTION("description"),
    DESCRIPTION_LANG("description;lang"),
    GROUP_OF_UNIQUE_NAMES("groupOfUniqueNames"),
    GROUP_OF_URLS("group"),
    INET_ORG_PERSON("organizationalPerson"),
    MEMBER_URL("memberURL"),
    NS_ROLE_DN("nsRoleDn"),
    OBJECT_CLASS("objectClass"),
    ORGANISATION_UNIT("organizationalUnit"),
    ORGANISATIONAL_PERSON("organizationalPerson"),
    OU("ou"),
    PASSWORD("userPassword"),
    PERSON("person"),
    TOP("top")
    ;

    private final String value;

    ActiveDirectoryLdapConstants(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
