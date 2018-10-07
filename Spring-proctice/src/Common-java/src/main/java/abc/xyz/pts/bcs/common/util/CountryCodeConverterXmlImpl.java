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
package abc.xyz.pts.bcs.common.util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;

/**
 * Implementation class for CountryCodeConverter interface which takes an XML representation of all country codes and
 * scans it to find the matching codes.
 *
 * @version $Id: CountryCodeConverterXmlImpl.java 605 2008-07-16 16:41:49Z wgriffiths $
 */
public class CountryCodeConverterXmlImpl implements CountryCodeConverter {

    private static final Logger LOG = Logger.getLogger(CountryCodeConverterXmlImpl.class);

    private static final String CONFIG_IO_ERROR = "Unable to load the configuration file for the SDI-NSIS country code converter utility";

    private static final String CONFIG_PARSE_ERROR = "The configuration file for the SDI-NSIS country code converter utility is not a valid format";

    private Document countryCodesDocument;

    /**
     * Constructs a country code converter object using the specified XML country code mapping file.
     *
     * @param countryCodeMapXml
     *            Country code mapping file.
     */
    public CountryCodeConverterXmlImpl(final Resource countryCodeMapXml) {
        super();

        try {
            // Load the XML from the specified resource
            SAXReader reader = new SAXReader();
            countryCodesDocument = reader.read(countryCodeMapXml.getInputStream());
        } catch (DocumentException e) {
            LOG.error(CONFIG_PARSE_ERROR, e);
        } catch (IOException e) {
            LOG.error(CONFIG_IO_ERROR, e);
        }
    }

    public int convert(final String isoCode) {

        int iStatCode = UNKNOWN_COUNTRY_CODE;

        if (countryCodesDocument != null && isoCode != null) {

            String xpathStr = "//countries/country[@iso-code = '" + isoCode + "']";

            Node node = countryCodesDocument.selectSingleNode(xpathStr);

            if (node != null) {
                iStatCode = Integer.parseInt(node.valueOf("@istat-code"));
            }
        }

        return iStatCode;
    }

    public String convert(final int istatCode) {

        String isoCode = null;

        if (countryCodesDocument != null && istatCode != UNKNOWN_COUNTRY_CODE) {

            String xpathStr = "//countries/country[@istat-code = '" + istatCode + "']";

            Node node = countryCodesDocument.selectSingleNode(xpathStr);

            if (node != null) {
                isoCode = node.valueOf("@iso-code");
            }
        }

        return isoCode;
    }
}
