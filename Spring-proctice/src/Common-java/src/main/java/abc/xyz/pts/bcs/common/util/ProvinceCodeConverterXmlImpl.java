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
 * Implementation class for ProvinceCodeConverter interface which takes an XML representation of all province codes and
 * scans it to find the matching codes.
 *
 * @version $Id: ProvinceCodeConverterXmlImpl.java 605 2008-07-16 16:41:49Z wgriffiths $
 */
public class ProvinceCodeConverterXmlImpl implements ProvinceCodeConverter {

    private static final Logger LOG = Logger.getLogger(ProvinceCodeConverterXmlImpl.class);

    private static final String CONFIG_IO_ERROR = "Unable to load the configuration file for the SDI-NSIS province code converter utility";

    private static final String CONFIG_PARSE_ERROR = "The configuration file for the SDI-NSIS province code converter utility is not a valid format";

    private Document provinceCodesDocument;

    /**
     * Constructs a province code converter object using the specified XML province code mapping file.
     *
     * @param provinceCodeMapXml
     *            Province code mapping file.
     */
    public ProvinceCodeConverterXmlImpl(final Resource provinceCodeMapXml) {
        super();

        try {
            // Load the XML from the specified resource
            SAXReader reader = new SAXReader();
            provinceCodesDocument = reader.read(provinceCodeMapXml.getInputStream());
        } catch (DocumentException e) {
            LOG.error(CONFIG_PARSE_ERROR, e);
        } catch (IOException e) {
            LOG.error(CONFIG_IO_ERROR, e);
        }
    }

    /**
     * @see abc.xyz.pts.bcs.common.util.ProvinceCodeConverter#convert(java.lang.String)
     */
    public int convert(final String code) {

        int iStatCode = UNKNOWN_PROVINCE_CODE;

        if (provinceCodesDocument != null && code != null) {

            String xpathStr = "//provinces/province[@code = '" + code + "']";

            Node node = provinceCodesDocument.selectSingleNode(xpathStr);

            if (node != null) {
                iStatCode = Integer.parseInt(node.valueOf("@istat-code"));
            }
        }

        return iStatCode;
    }
}
