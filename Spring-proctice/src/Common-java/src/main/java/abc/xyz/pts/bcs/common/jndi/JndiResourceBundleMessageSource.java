/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2009
 * All rights reserved.
 */

package abc.xyz.pts.bcs.common.jndi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.naming.NamingException;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jndi.JndiTemplate;

/**
 *
 * @author simona
 */
public class JndiResourceBundleMessageSource extends ResourceBundleMessageSource {

    private JndiTemplate jndiTemplate;

    /**
     * @return the jndiTemplate
     */
    public JndiTemplate getJndiTemplate() {
        return jndiTemplate;
    }

    /**
     * @param jndiTemplate the jndiTemplate to set
     */
    public void setJndiTemplate(final JndiTemplate jndiTemplate) {
        this.jndiTemplate = jndiTemplate;
    }

    private class JndiResourceBundleControl extends ResourceBundle.Control {

        @Override
        public List<String> getFormats(final String basename) {
            if(basename == null) {
                throw new IllegalArgumentException("basename == null");
            }
            return FORMAT_PROPERTIES;
        }

        @Override
        public synchronized ResourceBundle newBundle(final String baseName,
                final Locale locale,
                final String format,
                final ClassLoader loader,
                final boolean reload)
                throws IllegalAccessException,
                InstantiationException,
                IOException {

            ResourceBundle bundle = null;
            JndiPropertiesWrapper props = null;

            final String bundleName = toBundleName(baseName, locale);
            // Ignore resource name
//            String resourceName = toResourceName(bundleName, "properties");

                try {
                    props = getJndiTemplate().lookup(bundleName, JndiPropertiesWrapper.class);
                } catch (final NamingException ex) {
                    logger.error("Unable to get resource from JNDI: '" + bundleName + "'", ex);
                    return null;
                } catch (final RuntimeException e) {
                    logger.error("Unable to get resource from JNDI: baseName(" + baseName +")" + "bundleName(" +bundleName + ")");
                    throw e;
                }


                bundle = new PropertyResourceBundle(new ByteArrayInputStream(props.getConfigData()));

            return bundle;
        }
    }

    @Override
    protected ResourceBundle doGetBundle(final String basename, final Locale locale) {
        return ResourceBundle.getBundle(basename, locale, new JndiResourceBundleControl());
    }
}
