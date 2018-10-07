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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.naming.directory.SearchControls;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.ContextExecutor;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.PresentFilter;

import abc.xyz.pts.bcs.admin.dto.Airport;
import abc.xyz.pts.bcs.admin.ldap.support.AbstractLdapCache;
import abc.xyz.pts.bcs.admin.ldap.support.CacheListenerContextExecutor;
import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;
import abc.xyz.pts.bcs.common.ldap.support.OrderingSortControlDirContextProcessor;

public class AirportDao {

    private static final Logger LOGGER = Logger.getLogger(AirportDao.class);
    private static final ConcurrentMap<String, AirportCache> cachedAirports = new ConcurrentHashMap<String, AirportCache>();
    private static final String CACHE_KEY = "KEY";

    private SimpleLdapTemplate ldapTemplate = null;
    private String airportBaseName = null;
    private AirportAttributesMapper airportAttributesMapper = null;
    private SimpleLdapTemplate cacheLdapTemplate = null;
    private AirportComparator airportComparator = new AirportComparator();

    /**
     * @param cacheLdapTemplate
     *            the cacheLdapTemplate to set
     */
    public void setCacheLdapTemplate(final SimpleLdapTemplate cacheLdapTemplate) {
        this.cacheLdapTemplate = cacheLdapTemplate;
    }

    /**
     * @param ldapTemplate
     *            the ldapTemplate to set
     */
    public void setLdapTemplate(final SimpleLdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    private SearchControls createSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes(airportAttributesMapper.getAttributeNamesArray());
        searchControls.setReturningObjFlag(true);
        return searchControls;
    }

    class AirportCache extends AbstractLdapCache<List<Airport>> {

        public AirportCache() {
            super();
        }

        @Override
        protected Callable<List<Airport>> createCallable() {
            return new AirportCallable(this);
        }

    }

    class AirportCallable implements Callable<List<Airport>> {

        private AirportCache listener = null;

        public AirportCallable(final AirportCache listener) {
            this.listener = listener;
        }

        @Override
        public List<Airport> call() throws Exception {
            return getAirportsInternal(listener);
        }
    }

    public List<Airport> getAirports() {

        AirportCache cacheTask = cachedAirports.get(CACHE_KEY);

        if (cacheTask == null) {
            AirportCache newTask = new AirportCache();
            cacheTask = cachedAirports.putIfAbsent(CACHE_KEY, newTask);
            if (cacheTask == null) {
                cacheTask = newTask;
                newTask.run();
            }
        }

        try {
            return cacheTask.get();
        } catch (Exception ex) {
            LOGGER.error("Unable to retrive valid airports from ldap", ex);
            return null;
        }
    }

    private List<Airport> getAirportsInternal(final AirportCache listener) {
        List<Airport> airports = null;
        final AndFilter andFilter = new AndFilter();
        Filter filter;

        filter = new EqualsFilter(LdapConstants.OBJECT_CLASS.getValue(), LdapConstants.ORGANISATION_UNIT.getValue());
        andFilter.and(filter);

        filter = new PresentFilter(LdapConstants.OU.toString());
        andFilter.and(filter);

        // ensure we don't process empty rows
        filter = new PresentFilter(AirportAttributesMapper.AttributeName.DESCRIPTION.toString());
        andFilter.and(filter);

        OrderingSortControlDirContextProcessor sortProcessor = new OrderingSortControlDirContextProcessor(
                airportAttributesMapper.mapAttributeName(AirportAttributesMapper.AttributeName.IATA_CODE), true);

        // airports = ldapTemplate.search(airportBaseName, andFilter.encode(), airportAttributesMapper);

        airports = ldapTemplate.search(airportBaseName, andFilter.encode(), createSearchControls(),
                airportAttributesMapper, sortProcessor);

        // We only need to register the listener once unless an exception occurs
        if (listener.isListenerRegistrationRequired()) {
            ContextExecutor contextExecutor = new CacheListenerContextExecutor(airportBaseName, listener);
            // add the listener
            cacheLdapTemplate.getLdapOperations().executeReadOnly(contextExecutor);
        }
        return airports;
    }

    public boolean exists(final String airport) {
        List<Airport> airports = this.getAirports();
        boolean result = false;
        Airport targetAirport = new Airport();
        targetAirport.setIataCode(airport);
        int targetIndex = Collections.binarySearch(airports, targetAirport, airportComparator);
        if (targetIndex >= 0) {
            result = true;
        }
        return result;
    }

    class AirportComparator implements Comparator<Airport> {

        @Override
        public int compare(final Airport o1, final Airport o2) {
            return o1.getIataCode().compareTo(o2.getIataCode());
        }

    }

    /**
     * @param airportBaseName
     *            the airportBaseName to set
     */
    public void setAirportBaseName(final String airportBaseName) {
        this.airportBaseName = airportBaseName;
    }

    /**
     * @param airportAttributesMapper
     *            the airportAttributesMapper to set
     */
    public void setAirportAttributesMapper(final AirportAttributesMapper airportAttributesMapper) {
        this.airportAttributesMapper = airportAttributesMapper;
    }
}
