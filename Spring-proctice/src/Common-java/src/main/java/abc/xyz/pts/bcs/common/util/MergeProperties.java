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

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;

/**
 * This class is intended to provide a utility to merge properties files.
 */
public class MergeProperties extends MatchingTask {
    private List locales;
    private File outputFile;
    private boolean append;

    public static Properties mergeProperties(final Map<String, Properties> propertiesToMerge) {
        Properties newProperties = new Properties();
        // Map<String, Set<String>> duplicateList = new HashMap<String, Set<String>>();
        Set<String> keys = propertiesToMerge.keySet();
        for (Iterator<String> keyIterator = keys.iterator(); keyIterator.hasNext();) {
            String key = keyIterator.next();
            Properties currentProperties = propertiesToMerge.get(key);
            Set propertyKeySet = currentProperties.keySet();
            for (Iterator propertyKeyIterator = propertyKeySet.iterator(); propertyKeyIterator.hasNext();) {
                String propertyKey = (String) propertyKeyIterator.next();
                String propertyValue = currentProperties.getProperty(propertyKey);
                String oldValue = (String) newProperties.setProperty(propertyKey, propertyValue);
            }
        }

        return newProperties;
    }

    public void execute() throws BuildException {
        super.execute(); // To change body of overridden methods use File | Settings | File Templates.
    }

    public void addLocale(final LocaleConfigurer locale) {
        locales.add(locale.getLocale());
    }

    public void setOutputfile(final File outputFile) {
        this.outputFile = outputFile;
    }

    public void setAppend(final boolean append) {
        this.append = append;
    }

    public class LocaleConfigurer {

        private String language;
        private String country;
        private String variant;

        public void setCountry(final String country) {
            this.country = country;
        }

        public void setLanguage(final String language) {
            this.language = language;
        }

        public void setVariant(final String variant) {
            this.variant = variant;
        }

        private Locale getLocale() {
            return new Locale(language, country, variant);
        }
    }
}
