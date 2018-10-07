/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.business.lookup;

import java.util.Calendar;
import java.util.List;

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
import abc.xyz.pts.bcs.common.enums.WeightingType;
import abc.xyz.pts.bcs.wi.dto.WatchlistWeightings;

public interface LookupDataService {

    String APP_ACTION_CODE_LIST = "appActionCodeList";
    String GENDER_LIST = "genderList";
    String APP_RESULT_TYPE_LIST = "appResultTypeList";
    String HIT_STATUS = "hitStatusList";
    String HIT_TYPE = "hitTypeList";
    String NATIONALITY_LIST = "nationalityList";
    String COUNTRY_LIST = "countryList";
    String REFERRAL_CLOSE_REASON = "referralClosureReasonList";
    String REFERRAL_STATUS = "referralStatusList";
    String SEVERITY_LIST = "severityList";
    String BOOLEAN_SELECT_LIST = "booleanSelectList";
    String ENABLED_SELECT_LIST = "enabledSelectList";
    String TARGET_STATUS_SELECT_LIST = "targetStatusList";
    String CPR_STATUS_LIST = "cprStatusList";
    String CARRIER_LIST = "carrierList";
    String SCAN_STATUS_SELECT_LIST = "scanStatusList";
    String SCHEDLUE_TYPE_SELECT_LIST = "scheduleTypeList";
    String CPR_DATASOURCE_LIST = "dataSourceList";
    String SCAN_TYPE_SELECT_LIST = "scanTypeList";
    String TIME_INTERVAL_SELECT_LIST = "timeIntervalList";
    String PERMISSION_NAME_LIST = "permissionList";
    String ADDITIONAL_INSTRUCTION_LIST = "additionalInstructionList";


    List<NationalityBean> getNationalityList(final String bundleBaseName);

    List<NationalityBean> getNationalityListFromCPR();

    List<FlightStatusBean> getFlightStatusList(final String bundleBaseName);

    List<GenderBean> getGenderList(final String bundleBaseName);

    List<AppResultTypeBean> getAppResultTypeList(final String bundleBaseName);

    WatchlistWeightings getWatchlistWeightings(WeightingType weightingType);

    List<HitTypeBean> getHitTypeList(final String bundleBaseName);

    List<ReferralStatusBean> getReferralStatusList(final String bundleBaseName);

    List<AppActionCodeBean> getAppActionCodeList(final String bundleBaseName);

    @Deprecated // use getSeverityList()
    int[] getSeverityLevels();

    List<SeverityBean> getSeverityList();

    List<HitStatusBean> getHitStatusList(final String bundleBaseName);

    List<ReferralClosureReasonBean> getReferralClosureReasonList(final String bundleBaseName);

    Calendar getDefaultClearedDocumentExpiryDate();

    List<BooleanSelectBean> getBooleanSelectList(final String bundleBaseName);

    List<EnabledSelectBean> getEnabledSelectList(final String bundleBaseName);

    List<CPRStatusBean> getCPRStatusList(final String bundleBaseName);

    List<TargetStatusSelectBean> getTargetStatusList(final String bundleBaseName);

    List<ScanStatusSelectBean> getScanStatusList(final String bundleBaseName);

    List<ScheduleTypeSelectBean> getScheduleTypeList(final String bundleBaseName);

    Calendar getDateRangeDefaultFromDate();

    Calendar getDateRangeDefaultToDate();

    String getStringDateRangeDefaultFromDate();

    String getStringDateRangeDefaultToDate();

    List<CarrierTypeBean> getCarrierTypes();

    Calendar getDateRangeDefaultFromDateForAlertOfficer();

    String getStringDateRangeDefaultFromDateForAlertOfficer();

    List<CPRDataSourceBean> getCPRDataSource();

    List<ScanTypeBean> getScanTypeList(String bundleBaseName);

    List<TimeIntervalBean> getTimeIntervalList(String bundleBaseName);

	List<PermissionNameBean> getPermissionNameList(String bundleBaseName);
	
	List<AdditionalInstructionBean> getAdditionalInstructionList(String bundleBaseName);
}
