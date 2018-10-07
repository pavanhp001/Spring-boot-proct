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
package abc.xyz.pts.bcs.common.ldap.dao;

import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.log4j.Logger;

public abstract class ConfigurableAttributesMapper {
    private static final Logger LOG = Logger.getLogger(ConfigurableAttributesMapper.class);

    protected Map<String, String> attributeNames = null;

    public String[] getAttributeNamesArray() {
        return this.attributeNames.values().toArray(new String[this.attributeNames.size()]);
    }

    public String mapAttributeName(final Enum name) {
        return attributeNames.get(resolveAttributeName(name));
    }

    public String mapAttributeName(final String name) {
        return attributeNames.get(name);
    }

    protected abstract String resolveAttributeName(final Enum attributeName);

    protected String getStringAttribute(final Attributes attrs, final Enum attributeName) {
        String result = null;
        try {
            String enumName = resolveAttributeName(attributeName);
            Attribute attr = attrs.get(attributeNames.get(enumName));
            if (attr != null) {
                result = (String) attr.get();
            }
        } catch (NamingException ne) {
            LOG.error("Unable to map attribute: " + attributeName, ne);
        }
        return result;
    }

    /**
     * @return the attributeNames
     */
    public Map<String, String> getAttributeNames() {
        return attributeNames;
    }

    /**
     * @param attributeNames
     *            the attributeNames to set
     */
    public void setAttributeNames(final Map<String, String> attributeNames) {
        this.attributeNames = attributeNames;
    }
}
