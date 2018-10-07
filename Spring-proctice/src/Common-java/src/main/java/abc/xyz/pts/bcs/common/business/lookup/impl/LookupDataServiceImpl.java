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
package abc.xyz.pts.bcs.common.business.lookup.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;

import abc.xyz.pts.bcs.common.business.lookup.LookupDataService;
import abc.xyz.pts.bcs.common.business.lookup.bean.AdditionalInstructionBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.AppActionCodeBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.AppResultTypeBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.BooleanSelectBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.CPRDataSourceBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.CPRStatusBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.CarrierTypeBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.EnabledSelectBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.FlightStatusBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.GenderBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.HitStatusBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.HitTypeBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.LookupItem;
import abc.xyz.pts.bcs.common.business.lookup.bean.NationalityBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.PermissionNameBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.ReferralClosureReasonBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.ReferralStatusBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.ScanStatusSelectBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.ScanTypeBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.ScheduleTypeSelectBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.SeverityBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.TargetStatusSelectBean;
import abc.xyz.pts.bcs.common.business.lookup.bean.TimeIntervalBean;
import abc.xyz.pts.bcs.common.dao.CPRDataSourceDao;
import abc.xyz.pts.bcs.common.dao.CarrierTypesDao;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.enums.WeightingType;
import abc.xyz.pts.bcs.common.util.CalendarUtils;
import abc.xyz.pts.bcs.common.util.WebConstants;
import abc.xyz.pts.bcs.cpr.dao.CountryDao;
import abc.xyz.pts.bcs.wi.dao.WatchlistWeightingsDao;
import abc.xyz.pts.bcs.wi.dto.WatchlistWeightings;

/**
 * Technical debt alert:
 */
public class LookupDataServiceImpl implements LookupDataService {

    private static final Logger LOG = Logger.getLogger(LookupDataServiceImpl.class);

    private static final String IGNORE_SHORT_SUFFIX = ".short";

    private static final String DATE_RANGE_FROM_DEFAULT_DAYS = "date.range.from.default.days";
    private static final String DATE_RANGE_FROM_DEFAULT_DAYS_FOR_ALERT_OFFICER = "date.range.from.default.days.for.alert.officer";

    private static final String DATE_RANGE_TO_DEFAULT_DAYS = "date.range.to.default.days";
    private static final String NATIONALITY_CODE_PREFIX = "country.code.";
    private static final String FLIGHT_STATUS_PREFIX = "flight.status.";
    private static final String GENDER_PREFIX = "gender.";
    private static final String APP_RESULT_TYPE_PREFIX = "app.result.type.";
    private static final String HIT_TYPE_PREFIX = "hit.type.";
    private static final String REFERRAL_STATUS_PREFIX = "referral.status.";
    private static final String APP_ACTION_CODE_PREFIX = "app.action.code.";
    private static final String HIT_STATUS_CODE_PREFIX = "referral.hit.status.";
    private static final String REFERRAL_CLOSE_REASON_PREFIX = "close.referral.reason.";
    private static final String BOOLEAN_SELECT_PREFIX = "boolean.select.";
    private static final String ENABLED_SELECT_PREFIX = "enabled.select.";
    private static final String TARGET_STATUS_SELECT_PREFIX = "target.status.";
    private static final String CPR_STATUS_PREFIX = "cpr.status.";
    private static final String SCAN_STATUS_SELECT_PREFIX = "scan.status.";
    private static final String SCHEDULE_TYPE_SELECT_PREFIX = "search.schedule.type.";
    private static final String SCHEDULE_SCAN_TYPE_PREFIX = "scheduler.scan.type.";
    private static final String SCHEDULE_TIME_INTERVAL_PREFIX = "scheduler.timeinterval.";
    private static final String PERMISSION_NAME_PREFIX = "permission.";
    private static final String ADDITIONAL_INSTRUCTION_PREFIX = "export.movement.instruction.";

    private static final ConcurrentMap<String, Future<List<NationalityBean>>> CACHED_NATIONALITIES = new ConcurrentHashMap<String, Future<List<NationalityBean>>>();
    private static final ConcurrentMap<String, Future<List<NationalityBean>>> CACHED_COUNTRIES = new ConcurrentHashMap<String, Future<List<NationalityBean>>>();
    private static final ConcurrentMap<String, Future<List<FlightStatusBean>>> CACHED_FLIGHT_STATUS = new ConcurrentHashMap<String, Future<List<FlightStatusBean>>>();
    private static final ConcurrentMap<String, Future<List<GenderBean>>> CACHED_GENDER = new ConcurrentHashMap<String, Future<List<GenderBean>>>();
    private static final ConcurrentMap<String, Future<List<AppResultTypeBean>>> CACHED_APP_RESULT_TYPE = new ConcurrentHashMap<String, Future<List<AppResultTypeBean>>>();
    private static final ConcurrentMap<String, Future<List<WatchlistWeightings>>> CACHED_WATCHLIST_WEIGHTINGS = new ConcurrentHashMap<String, Future<List<WatchlistWeightings>>>();
    private static final ConcurrentMap<String, Future<List<HitTypeBean>>> CACHED_HIT_TYPES = new ConcurrentHashMap<String, Future<List<HitTypeBean>>>();
    private static final ConcurrentMap<String, Future<List<ReferralStatusBean>>> CACHED_REFERRAL_STATUS = new ConcurrentHashMap<String, Future<List<ReferralStatusBean>>>();
    private static final ConcurrentMap<String, Future<List<AppActionCodeBean>>> CACHED_APP_ACTION_CODES = new ConcurrentHashMap<String, Future<List<AppActionCodeBean>>>();
    private static final ConcurrentMap<String, Future<List<HitStatusBean>>> CACHED_HIT_STATUS_CODES = new ConcurrentHashMap<String, Future<List<HitStatusBean>>>();
    private static final ConcurrentMap<String, Future<List<ReferralClosureReasonBean>>> CACHED_REFERRAL_CLOSURE_REASON_CODES = new ConcurrentHashMap<String, Future<List<ReferralClosureReasonBean>>>();
    private static final ConcurrentMap<String, Future<List<BooleanSelectBean>>> CACHED_BOOLEAN_SELECT = new ConcurrentHashMap<String, Future<List<BooleanSelectBean>>>();
    private static final ConcurrentMap<String, Future<List<EnabledSelectBean>>> CACHED_ENABLED_SELECT = new ConcurrentHashMap<String, Future<List<EnabledSelectBean>>>();
    private static final ConcurrentMap<String, Future<List<TargetStatusSelectBean>>> CACHED_TARGET_STATUS_SELECT = new ConcurrentHashMap<String, Future<List<TargetStatusSelectBean>>>();
    private static final ConcurrentMap<String, Future<List<CPRStatusBean>>> CACHED_CPR_STATUS = new ConcurrentHashMap<String, Future<List<CPRStatusBean>>>();
    private static final ConcurrentMap<String, Future<List<CarrierTypeBean>>> CACHED_CARRIER_TYPES = new ConcurrentHashMap<String, Future<List<CarrierTypeBean>>>();
    private static final ConcurrentMap<String, Future<List<ScanStatusSelectBean>>> CACHED_SCAN_STATUS_SELECT = new ConcurrentHashMap<String, Future<List<ScanStatusSelectBean>>>();
    private static final ConcurrentMap<String, Future<List<ScheduleTypeSelectBean>>> CACHED_SCHEDULE_TYPE_SELECT = new ConcurrentHashMap<String, Future<List<ScheduleTypeSelectBean>>>();
    private static final ConcurrentMap<String, Future<List<CPRDataSourceBean>>> CACHED_DATA_SOURCE = new ConcurrentHashMap<String, Future<List<CPRDataSourceBean>>>();
    private static final ConcurrentMap<String, Future<List<ScanTypeBean>>> CACHED_SCAN_TYPE_SELECT = new ConcurrentHashMap<String, Future<List<ScanTypeBean>>>();
    private static final ConcurrentMap<String, Future<List<TimeIntervalBean>>> CACHED_TIME_INTERVAL_SELECT = new ConcurrentHashMap<String, Future<List<TimeIntervalBean>>>();
    private static final ConcurrentMap<String, Future<List<PermissionNameBean>>> CACHED_PERMISSION_NAME = new ConcurrentHashMap<String, Future<List<PermissionNameBean>>>();
    private static final ConcurrentMap<String, Future<List<AdditionalInstructionBean>>> CACHED_ADDITIONAL_INSTRUCTION_NAME = new ConcurrentHashMap<String, Future<List<AdditionalInstructionBean>>>();

    private WatchlistWeightingsDao watchlistWeightingsDao;

    private CPRDataSourceDao cPRDataSourceDao;

    private CarrierTypesDao carrierTypesDao;

    private CountryDao countryDao;


    private <E extends LookupItem> List<E> getLookupList
        ( final Class<E> clazz
        , final ConcurrentMap<String, Future<List<E>>> cachedItems
        , final String prefix
        )
    {
        return getLookupList(clazz, cachedItems, prefix, false);
    }



    /**
     * Get reference data from the resource bundle.
     *
     * @param clazz
     * @param cachedItems
     * @param prefix
     * @param showKey
     * @return
     */
    private <E extends LookupItem> List<E> getLookupList
        ( final Class<E> clazz
        , final ConcurrentMap<String, Future<List<E>>> cachedItems
        , final String prefix
        , final boolean showKey
        )
    {

        // Uses MEMOIZATION pattern from JCIP
        final Locale locale = LocaleContextHolder.getLocale();
        final String cacheKey = locale.getDisplayName();
        Future<List<E>> cacheTask = cachedItems.get(cacheKey);
        if (cacheTask == null) {
            final Callable<List<E>> callable = new Callable<List<E>>() {
                @Override
                public List<E> call() throws InstantiationException, IllegalAccessException {
                    final ResourceBundle resourceBundle = ResourceBundle.getBundle(WebConstants.BUNDLE_BASE_NAME, locale);
                    final Enumeration<String> enumeration = resourceBundle.getKeys();
                    final List<E> itemList = new ArrayList<E>();
                    while (enumeration.hasMoreElements()) {
                        String key = enumeration.nextElement();
                        key = key.trim();
                        if (key.startsWith(prefix )&& !key.endsWith(IGNORE_SHORT_SUFFIX)) {
                            final String value = resourceBundle.getString(key);
                            if (!StringUtils.isEmpty(value)) {
                                E item = null;
                                item = clazz.newInstance();
                                key = key.substring(key.lastIndexOf('.') + 1);
                                item.setCode(key);
                                if (showKey) {
                                    item.setDescription(value.trim() + " (" + key + ")");
                                } else {
                                    item.setDescription(value.trim());
                                }
                                itemList.add(item);
                            }
                        }
                    }
                    // NEED TO UNCOMMENT THIS!
                    Collections.sort(itemList);
                    return itemList;
                }
            };

            final FutureTask<List<E>> newTask = new FutureTask<List<E>>(callable);
            cacheTask = cachedItems.putIfAbsent(cacheKey, newTask);
            if (cacheTask == null) {
                cacheTask = newTask;
                newTask.run();
            }
        }

        // We should really be throwing these exception
        try {
            return cacheTask.get();
        } catch (final ExecutionException ex) {
            LOG.error("Unable to retrive valid events from database", ex);
            return null;
        } catch (final InterruptedException ex) {
            LOG.error("Unable to retrive valid events from database", ex);
            return null;
        }

    }

    @Override
    public List<NationalityBean> getNationalityList(final String bundleBaseName) {
        return getLookupList(NationalityBean.class, CACHED_NATIONALITIES, NATIONALITY_CODE_PREFIX, true);
    }

    @Override
    public List<FlightStatusBean> getFlightStatusList(final String bundleBaseName) {
        return getLookupList(FlightStatusBean.class, CACHED_FLIGHT_STATUS, FLIGHT_STATUS_PREFIX);
    }

    @Override
    public List<GenderBean> getGenderList(final String bundleBaseName) {
        return getLookupList(GenderBean.class, CACHED_GENDER, GENDER_PREFIX);
    }

    @Override
    public List<AppResultTypeBean> getAppResultTypeList(final String bundleBaseName) {
        return getLookupList(AppResultTypeBean.class, CACHED_APP_RESULT_TYPE, APP_RESULT_TYPE_PREFIX);
    }

    @Override
    public List<HitTypeBean> getHitTypeList(final String bundleBaseName) {
        return getLookupList(HitTypeBean.class, CACHED_HIT_TYPES, HIT_TYPE_PREFIX);
    }

    @Override
    public List<ReferralStatusBean> getReferralStatusList(final String bundleBaseName) {
        return getLookupList(ReferralStatusBean.class, CACHED_REFERRAL_STATUS, REFERRAL_STATUS_PREFIX);
    }

    @Override
    public List<AppActionCodeBean> getAppActionCodeList(final String bundleBaseName) {
        return getLookupList(AppActionCodeBean.class, CACHED_APP_ACTION_CODES, APP_ACTION_CODE_PREFIX);
    }

    @Override
    public List<HitStatusBean> getHitStatusList(final String bundleBaseName) {
        return getLookupList(HitStatusBean.class, CACHED_HIT_STATUS_CODES, HIT_STATUS_CODE_PREFIX);
    }

    @Override
    public List<ReferralClosureReasonBean> getReferralClosureReasonList(final String bundleBaseName) {
        return getLookupList(ReferralClosureReasonBean.class, CACHED_REFERRAL_CLOSURE_REASON_CODES, REFERRAL_CLOSE_REASON_PREFIX);
    }

    @Override
    public List<CPRStatusBean> getCPRStatusList(final String bundleBaseName) {
        return getLookupList(CPRStatusBean.class, CACHED_CPR_STATUS, CPR_STATUS_PREFIX);
    }

    @Override
    public List<BooleanSelectBean> getBooleanSelectList(final String bundleBaseName) {
        return getLookupList(BooleanSelectBean.class, CACHED_BOOLEAN_SELECT, BOOLEAN_SELECT_PREFIX);
    }

    @Override
    public List<TargetStatusSelectBean> getTargetStatusList(final String bundleBaseName) {
        return getLookupList(TargetStatusSelectBean.class, CACHED_TARGET_STATUS_SELECT, TARGET_STATUS_SELECT_PREFIX);
    }

    @Override
    public List<EnabledSelectBean> getEnabledSelectList(final String bundleBaseName) {
        return getLookupList(EnabledSelectBean.class, CACHED_ENABLED_SELECT, ENABLED_SELECT_PREFIX);
    }

    @Override
    public List<ScanStatusSelectBean> getScanStatusList(final String bundleBaseName) {
        return getLookupList(ScanStatusSelectBean.class, CACHED_SCAN_STATUS_SELECT, SCAN_STATUS_SELECT_PREFIX);
    }

    @Override
    public List<ScheduleTypeSelectBean> getScheduleTypeList(final String bundleBaseName) {
        return getLookupList(ScheduleTypeSelectBean.class, CACHED_SCHEDULE_TYPE_SELECT, SCHEDULE_TYPE_SELECT_PREFIX);
    }
    
    @Override
    public List<PermissionNameBean> getPermissionNameList(final String bundleBaseName) {
        return getLookupList(PermissionNameBean.class, CACHED_PERMISSION_NAME, PERMISSION_NAME_PREFIX);
    }


    @Override
    public List <AdditionalInstructionBean> getAdditionalInstructionList(final String bundleBaseName) {
        return getLookupList(AdditionalInstructionBean.class, CACHED_ADDITIONAL_INSTRUCTION_NAME, ADDITIONAL_INSTRUCTION_PREFIX);
    }
    
    @Override
    public WatchlistWeightings getWatchlistWeightings(final WeightingType weightingType){

        // Uses MEMOIZATION pattern from JCIP
        final Locale locale = LocaleContextHolder.getLocale();
        final String cacheKey = locale.getDisplayName();
        Future<List<WatchlistWeightings>> cacheTask = CACHED_WATCHLIST_WEIGHTINGS.get(cacheKey);
        if (cacheTask == null) {
            final Callable<List<WatchlistWeightings>> callable = new Callable<List<WatchlistWeightings>>() {
                @Override
                public List<WatchlistWeightings> call() {
                    return watchlistWeightingsDao.getWatchlistWeightingsFromDataSource();
                }
            };

            final FutureTask<List<WatchlistWeightings>> newTask = new FutureTask<List<WatchlistWeightings>>(callable);
            cacheTask = CACHED_WATCHLIST_WEIGHTINGS.putIfAbsent(cacheKey, newTask);
            if (cacheTask == null) {
                cacheTask = newTask;
                newTask.run();
            }
        }

        try {
            final List<WatchlistWeightings> unfilteredWatchlistWeightings = cacheTask.get();
            for(final WatchlistWeightings watchlistWeighting : unfilteredWatchlistWeightings)
            {
                if ( WeightingType.fromCode(watchlistWeighting.getWeightType()).equals(weightingType))
                {
                    return watchlistWeighting;
                }
            }

        } catch (final ExecutionException ex) {
            LOG.error("Unable to retrive Watch List Weighting from database", ex);
            return null;
        } catch (final InterruptedException ex) {
            LOG.error("Unable to retrive Watch List Weighting from database", ex);
            return null;
        }

       return null ;
    }

    public void setWatchlistWeightingsDao(final WatchlistWeightingsDao watchlistWeightingsDao) {
        this.watchlistWeightingsDao = watchlistWeightingsDao;
    }

    @Override
    public List<CarrierTypeBean> getCarrierTypes(){

        final Locale locale = LocaleContextHolder.getLocale();
        final String cacheKey = locale.getDisplayName();
        Future<List<CarrierTypeBean>> cacheTask = CACHED_CARRIER_TYPES.get(cacheKey);
        if (cacheTask == null) {
            final Callable<List<CarrierTypeBean>> callable = new Callable<List<CarrierTypeBean>>() {
                @Override
                public List<CarrierTypeBean> call() {
                    return carrierTypesDao.getCarrierTypes();
                }
            };

            final FutureTask<List<CarrierTypeBean>> newTask = new FutureTask<List<CarrierTypeBean>>(callable);
            cacheTask = CACHED_CARRIER_TYPES.putIfAbsent(cacheKey, newTask);
            if (cacheTask == null) {
                cacheTask = newTask;
                newTask.run();
            }
        }

        try {
            return cacheTask.get();
        } catch (final ExecutionException ex) {
            LOG.error("Unable to retrive valid Carrier Types from database", ex);
            return null;
        } catch (final InterruptedException ex) {
            LOG.error("Unable to retrive valid Carrier Types from database", ex);
            return null;
        }

    }

    public void setCarrierTypesDao(final CarrierTypesDao carrierTypesDao) {
        this.carrierTypesDao = carrierTypesDao;
    }


    public void setCountryDao(final CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public void setcPRDataSourceDao(final CPRDataSourceDao cPRDataSourceDao) {
          this.cPRDataSourceDao = cPRDataSourceDao;
      }

    @Override
    @Deprecated // use getSeverityList()
    public int[] getSeverityLevels(){
        final int maxSeverityLevel = Integer.parseInt(System.getProperty("hit.max.serverity.level"));
        final int[] severityLevels = new int[maxSeverityLevel];
        for (int counter=1; counter <= maxSeverityLevel; counter++) {
            severityLevels[counter-1] = counter;
        }
        return severityLevels;
    }

    /**
     * Get list of possible severity levels
     */
    @Override
    public List<SeverityBean> getSeverityList()
    {
        final int maxSeverityLevel = Integer.parseInt(System.getProperty("hit.max.serverity.level"));

        final List<SeverityBean> severityList = new ArrayList<SeverityBean>(maxSeverityLevel);
        for (int counter=1; counter <= maxSeverityLevel; counter++)
        {
            severityList.add(new SeverityBean(String.valueOf(counter)));
        }
        return severityList;
    }



    @Override
    public Calendar getDefaultClearedDocumentExpiryDate() {

        final String clearedDocumentsValidityPeriodProperty = System.getProperty("default.cleared.documents.validity.period");

        if(clearedDocumentsValidityPeriodProperty != null){
            final int defaultClearedDocumentsValidityPeriod = Integer.parseInt(clearedDocumentsValidityPeriodProperty);

            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, defaultClearedDocumentsValidityPeriod);

            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal;
        } else {
            throw new RuntimeException("Property - default.cleared.documents.validity.period - not defined");
        }
    }



    @Override
    public List<NationalityBean> getNationalityListFromCPR(){

        // Uses MEMOIZATION pattern from JCIP
        final Locale locale = LocaleContextHolder.getLocale();
        final String cacheKey = locale.getDisplayName();
        Future<List<NationalityBean>> cacheTask = CACHED_COUNTRIES.get(cacheKey);
        if (cacheTask == null) {
            final Callable<List<NationalityBean>> callable = new Callable<List<NationalityBean>>() {
                @Override
                public List<NationalityBean> call() {
                    return countryDao.getCountries();
                }
            };

            final FutureTask<List<NationalityBean>> newTask = new FutureTask<List<NationalityBean>>(callable);
            cacheTask = CACHED_COUNTRIES.putIfAbsent(cacheKey, newTask);
            if (cacheTask == null) {
                cacheTask = newTask;
                newTask.run();
            }
        }

        try {
            return cacheTask.get();
        } catch (final ExecutionException ex) {
            LOG.error("Unable to retrive countries list from database", ex);
            return null;
        } catch (final InterruptedException ex) {
            LOG.error("Unable to retrive countries list from database", ex);
            return null;
        }

    }


    private Calendar addDaysToCurrentDate(final String noOfDays) {
        Calendar date = null;
           if(NumberUtils.isNumber(noOfDays)){
               final int intNoOfDays = Integer.valueOf(noOfDays);
               date = Calendar.getInstance();
               date.set(Calendar.HOUR, 0);
               date.set(Calendar.MINUTE, 0);
               date.add(Calendar.DATE, intNoOfDays);
           }
        return date;
    }

    @Override
    public Calendar getDateRangeDefaultFromDate(){
           final String dateRangeFromDefaultDays = System.getProperty(DATE_RANGE_FROM_DEFAULT_DAYS);
           return addDaysToCurrentDate(dateRangeFromDefaultDays);
    }


    @Override
    public Calendar getDateRangeDefaultToDate(){
           final String dateRangeToDefaultDays = System.getProperty(DATE_RANGE_TO_DEFAULT_DAYS);
           return addDaysToCurrentDate(dateRangeToDefaultDays);
    }

    @Override
    public String getStringDateRangeDefaultFromDate(){
        final Calendar fromDate = getDateRangeDefaultFromDate();
        return  ( fromDate != null )
                ? CalendarUtils.getCalendarAsString
                        ( fromDate
                        , Constants.SIMPLE_DATE_FORMAT
                        )
                : null;

    }


    @Override
    public String getStringDateRangeDefaultToDate(){
        final Calendar toDate = getDateRangeDefaultToDate();
        return  ( toDate != null )
                ? CalendarUtils.getCalendarAsString
                        ( toDate
                        , Constants.SIMPLE_DATE_FORMAT
                        )
                : null;
    }

    @Override
    public Calendar getDateRangeDefaultFromDateForAlertOfficer(){
           final String dateRangeFromDefaultDays = System.getProperty(DATE_RANGE_FROM_DEFAULT_DAYS_FOR_ALERT_OFFICER);
           return addDaysToCurrentDate(dateRangeFromDefaultDays);
    }

    @Override
    public String getStringDateRangeDefaultFromDateForAlertOfficer(){
        final Calendar fromDate = getDateRangeDefaultFromDateForAlertOfficer();
        return  ( fromDate != null )
                ? CalendarUtils.getCalendarAsString
                        ( fromDate
                        , Constants.SIMPLE_DATE_FORMAT
                        )
                : null;

    }
    @Override
       public List<CPRDataSourceBean> getCPRDataSource(){

            final Locale locale = LocaleContextHolder.getLocale();
            final String cacheKey = locale.getDisplayName();
            Future<List<CPRDataSourceBean>> cacheTask = CACHED_DATA_SOURCE.get(cacheKey);
            if (cacheTask == null) {
                final Callable<List<CPRDataSourceBean>> callable = new Callable<List<CPRDataSourceBean>>() {
                    @Override
                    public List<CPRDataSourceBean> call() {
                        return cPRDataSourceDao.getCPRDataSource();
                    }
                };

                final FutureTask<List<CPRDataSourceBean>> newTask = new FutureTask<List<CPRDataSourceBean>>(callable);
                cacheTask = CACHED_DATA_SOURCE.putIfAbsent(cacheKey, newTask);
                if (cacheTask == null) {
                    cacheTask = newTask;
                    newTask.run();
                }
            }

            try {
                return cacheTask.get();
            } catch (final ExecutionException ex) {
                LOG.error("Unable to retrive valid Data Source from database", ex);
                return null;
            } catch (final InterruptedException ex) {
                LOG.error("Unable to retrive valid Data Source from database", ex);
                return null;
            }

        }

    @Override
    public List<ScanTypeBean> getScanTypeList(final String bundleBaseName) {
        return getLookupList(ScanTypeBean.class, CACHED_SCAN_TYPE_SELECT, SCHEDULE_SCAN_TYPE_PREFIX);
    }

    /*
     * This code is duplicated to accommodate the 0.5 (T-0.5) as part of the Key.
     */
    @Override
    public List<TimeIntervalBean> getTimeIntervalList(final String bundleBaseName) {
        final Locale locale = LocaleContextHolder.getLocale();
        final String cacheKey = locale.getDisplayName();
        Future<List<TimeIntervalBean>> cacheTask = CACHED_TIME_INTERVAL_SELECT.get(cacheKey);
        if (cacheTask == null) {
            final Callable<List<TimeIntervalBean>> callable = new Callable<List<TimeIntervalBean>>() {
                @Override
                public List<TimeIntervalBean> call() throws InstantiationException, IllegalAccessException {
                    final ResourceBundle resourceBundle =
                            ResourceBundle.getBundle(WebConstants.BUNDLE_BASE_NAME, locale);
                    final Enumeration<String> enumeration = resourceBundle.getKeys();
                    final List<TimeIntervalBean> itemList = new ArrayList<TimeIntervalBean>();
                    while (enumeration.hasMoreElements()) {
                        String key = enumeration.nextElement();
                        key = key.trim();
                        if (key.startsWith(SCHEDULE_TIME_INTERVAL_PREFIX ) && !key.endsWith(IGNORE_SHORT_SUFFIX)) {
                            final String value = resourceBundle.getString(key);
                            if (!StringUtils.isEmpty(value)) {
                                TimeIntervalBean item = null;
                                item = TimeIntervalBean.class.newInstance();
                                key = key.replaceAll(SCHEDULE_TIME_INTERVAL_PREFIX, "");
                                item.setCode(key);
                                item.setDescription(value.trim());

                                itemList.add(item);
                            }
                        }
                    }
                    Collections.sort(itemList);
                    return itemList;
                }
            };

            final FutureTask<List<TimeIntervalBean>> newTask = new FutureTask<List<TimeIntervalBean>>(callable);
            cacheTask = CACHED_TIME_INTERVAL_SELECT.putIfAbsent(cacheKey, newTask);
            if (cacheTask == null) {
                cacheTask = newTask;
                newTask.run();
            }
        }

        // We should really be throwing these exception
        try {
            return cacheTask.get();
        } catch (final ExecutionException ex) {
            LOG.error("Unable to retrive valid events from database", ex);
            return null;
        } catch (final InterruptedException ex) {
            LOG.error("Unable to retrive valid events from database", ex);
            return null;
        }
    }
}

