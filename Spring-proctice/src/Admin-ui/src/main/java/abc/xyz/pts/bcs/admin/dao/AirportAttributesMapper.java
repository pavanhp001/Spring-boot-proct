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

import javax.naming.directory.Attributes;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;

import abc.xyz.pts.bcs.admin.dto.Airport;
import abc.xyz.pts.bcs.common.ldap.dao.ConfigurableAttributesMapper;

public class AirportAttributesMapper extends ConfigurableAttributesMapper implements
        ParameterizedContextMapper<Airport> {

    private static final Logger LOG = Logger.getLogger(AirportAttributesMapper.class);

    public enum AttributeName {
        IATA_CODE("iataCode"), ICAO_CODE("icaoCode"), DESCRIPTION("description");
        private final String value;

        AttributeName(final String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    @Override
    public Airport mapFromContext(final Object ctx) {
        Airport result = new Airport();
        DirContextAdapter adapter = (DirContextAdapter) ctx;
        Attributes attrs = adapter.getAttributes();
        result.setIataCode(getStringAttribute(attrs, AttributeName.IATA_CODE));
        result.setIcaoCode(getStringAttribute(attrs, AttributeName.ICAO_CODE));
        result.setDescription(getStringAttribute(attrs, AttributeName.DESCRIPTION));

        return result;
    }

    protected String resolveAttributeName(final Enum attributeName) {
        if (attributeName instanceof AttributeName) {
            return ((AttributeName) attributeName).value();
        } else {
            throw new IllegalArgumentException("Only instances of AttributeName are allowed");
        }
    }

}
