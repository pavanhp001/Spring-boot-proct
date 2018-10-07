/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import abc.xyz.pts.bcs.common.audit.business.SystemAuditor;
import abc.xyz.pts.bcs.common.comparator.PriorityReferralHitComparator;
import abc.xyz.pts.bcs.common.comparator.SeverityReferralHitComparator;
import abc.xyz.pts.bcs.common.dao.impl.FlightSegmentsDAO;
import abc.xyz.pts.bcs.common.dto.FlightSegment;
import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralDetails;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.dto.ReferralLog;
import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.enums.QualificationStatusType;
import abc.xyz.pts.bcs.common.enums.ReferralEventType;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;
import abc.xyz.pts.bcs.common.enums.SortOrderType;
import abc.xyz.pts.bcs.common.exception.ApplicationException;
import abc.xyz.pts.bcs.common.exception.DBUpdateFailedException;
import abc.xyz.pts.bcs.common.exception.DataNoLongerValid;
import abc.xyz.pts.bcs.common.irisk.dao.TravellerDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralHitDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralLogDAO;
import abc.xyz.pts.bcs.common.referral.workflows.notification.ReferralNotificationService;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Response;
import abc.xyz.pts.bcs.wi.dao.PrRecommendedActionDao;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;

/**
 * Encapsulate all referral operations.
 *
 * @author mmotlib-siddiqui
 *
 */
@Service("referralService")
public class ReferralServiceImpl implements ReferralService, ApplicationContextAware {

    private static Logger logger = Logger.getLogger( ReferralService.class );
    private ReferralDAO referralDAO;
    private ReferralLogDAO referralLogDAO;
    private ReferralHitDAO referralHitDAO;
    private PrRecommendedActionDao prRecommendedActionDao;
    private ReferralNotificationService referralNotificationService;
    private Long maxSeverityLevel;
    private Long appSeverityLevel;
    private Long appHitScore;
    private String appRecommendedActionCode;
    private FlightSegmentsDAO flightSegmentsDAO;
    private TravellerDAO travellerDAO;
    private MessageSourceAccessor messageSourceAccessor;

    private SystemAuditor systemAuditor;


    private static final Logger LOG = Logger.getLogger(ReferralSearchControllerUtils.class);
    private static final String CHANGED_ACTION_MESSAGE = "referral.note.changed.recommended.action";

    @Override
    public void setApplicationContext(final ApplicationContext ctx) throws BeansException {
        this.messageSourceAccessor = new MessageSourceAccessor(ctx);
    }

    /**
     * For a referral some values needs recalculating when hits are amended.
     *
     * @param referral
     */
    private void reCalculateValues(final Referral referral)
    {
        // Re-establish the highest priority before saving the referral
        final long priority = getHighestPriority(referral.getId());
        referral.setPriority(priority);

        // Re-establish the highest severity before saving the referral
        final long severity = referralDAO.establishHighestReferralSeverity(referral.getId());
        referral.setSeverityLevel(severity);
    }


    /**
     * Assume the referral has the correct modified userName.
     *
     * @param referral
     * @param oldActionCode
     * @param locale
     */
    @Override
    public void updateReferral
        ( final Referral referral
        , final String oldActionCode
        , final String eventType
        , final Locale locale
        ) throws ApplicationException
    {
        // Update Hits & add logs
        updateAllReferralHits(referral);

        // as a result of changes to hits, referral values need to be update
        reCalculateValues(referral);

        final int rowsUpdate = referralDAO.update(referral);
        if (rowsUpdate != 1) { // we are expected to update a single row
            throw new DataNoLongerValid();
        }

        // Add referral log
        insertReferralLog(referral, oldActionCode, locale, eventType, null);
    }

    /**
     * Assume the referral has the correct modified userName.
     *
     * @param referral
     * @param locale
     * @return
     */
    @Override
    public void closeReferral
        ( final Referral referral
        , final String eventType
        , final String closureNote
        , final Locale locale
        , final boolean closeOnSave) throws ApplicationException {

        if (closeOnSave)
        {
            // Update Hits & add logs
            updateAllReferralHits(referral);

            // as a result of changes to hits, referral values need to be update
            reCalculateValues(referral);
        }

        final int rowsUpdate = referralDAO.update(referral);
        if (rowsUpdate != 1) { // we are expected to update a single row
            throw new DataNoLongerValid();
        }

        // Add referral log. Nothing to add in comments.
        insertReferralLog(referral, referral.getActionCode(), locale, eventType, closureNote);
    }

    /**
     * Assume the referral has the correct modified userName.
     *
     * @param referral
     * @param oldActionCode
     * @param locale
     * @return
     */
    @Override
    public void acknowledgeReferral(final Referral referral, final Locale locale) {
        final Referral referralInDb = referralDAO.getReferralById(referral.getId());
        referral.setPriority(referralInDb.getPriority());
        referralDAO.update(referral);

        // Add referral log.
        insertReferralLog(referral, referral.getActionCode(), locale, ReferralEventType.REFACK.name(), null);

    }

    private void updateAllReferralHits(final Referral referral) throws ApplicationException
    {
        final Long refId = referral.getId();
        final Map<Long, ReferralHit> currentHitRules = referralDAO.getReferralHitsMap(refId);

        for (final ReferralHit referralHit : referral.getReferralHits()) {

            final ReferralHit existingReferralHit = currentHitRules.get(referralHit.getId());
            if (!referralHit.getQualificationStatus().equals(existingReferralHit.getQualificationStatus())) {

                // ** Update Referral Hit
                final int rowsUpdate = referralDAO.update(referralHit);
                if (rowsUpdate != 1) { // we should be updating on one row
                    throw new DataNoLongerValid();
                }

                // ** Insert new referral Log
                insertReferralLog(refId, referral.getModifiedBy(), referralHit.getId(), referralHit.getHitType(),
                        referralHit.getQualificationStatus());
            }
        }
    }

    private void insertReferralLog(final Long refId, final String userName, final Long hitId, final String hitType, final String newQualStat) {
        // User has changed the status of a hit so we need to log it
        final ReferralLog referralLogRec = new ReferralLog();
        referralLogRec.setRefId(refId);
        referralLogRec.setCreatedBy(userName);
        referralLogRec.setCreatedDatetime(Calendar.getInstance());
        referralLogRec.setHitType(hitType);
        referralLogRec.setHitId(hitId);

        if (newQualStat.equals(QualificationStatusType.IN.name())) {
            referralLogRec.setEventType(ReferralEventType.HITIN.name());
        } else if (newQualStat.equals(QualificationStatusType.OUT.name())) {
            referralLogRec.setEventType(ReferralEventType.HITOUT.name());
        } else {
            // In next story user wont be able to change a hit from
            // ruled in or out to Unknown. For now we are catering to it
            // temporarily against REFQUALM event type
            referralLogRec.setEventType(ReferralEventType.REFQUALM.name());
        }

        referralDAO.insert(referralLogRec);
    }

    /**
     *
     * @param referral
     * @param oldActionCode
     * @param locale
     * @throws DBUpdateFailedException
     */
    private void insertReferralLog(final Referral referral, final String oldActionCode, final Locale locale, final String eventType, final String closureNote) {
        final ReferralLog referralLogRec = new ReferralLog();
        referralLogRec.setRefId(referral.getId());
        referralLogRec.setCreatedBy(referral.getModifiedBy());
        referralLogRec.setCreatedDatetime(Calendar.getInstance());
        referralLogRec.setEventType(eventType);
        referralLogRec.setClosureCode(referral.getClosureCode());

        final String currentActionCode = referral.getActionCode();

        // trap Action Code change
        if (isActionCodeChanged(currentActionCode, oldActionCode)) {
            final String remarks = createNoteForActionCodeChanged(currentActionCode, oldActionCode);
            referralLogRec.setRemarks(remarks);
        } else if (!StringUtils.isEmpty(closureNote)) {
            referralLogRec.setRemarks(closureNote);
        }

        referralDAO.insert(referralLogRec);
    }

    private boolean isActionCodeChanged(final String currentActionCode, final String oldActionCode) {
        return !StringUtils.isEmpty(currentActionCode) && !StringUtils.isEmpty(oldActionCode)
                && !currentActionCode.equals(oldActionCode);
    }

    /**
     * This will create a comment to put in the notes section when the action code is changed.
     * As the comment is stored in the database it cannot dynamically changed based on the locale,
     * so will shown as the Arabic version followed by / then the English version.
     *
     * @param currentActionCode
     * @param oldActionCode
     * @return
     */
    private String createNoteForActionCodeChanged(final String currentActionCode, final String oldActionCode) {

        final Locale localeEn = Locale.ENGLISH;
        final Locale localeAr = new Locale("ar");

        final List<RecommendedAction> recommendedActionListEn = prRecommendedActionDao.findAllRecommendedAction(Locale.ENGLISH);
        final List<RecommendedAction> recommendedActionListAr = prRecommendedActionDao.findAllRecommendedAction(new Locale("ar"));

        String oldActionCodeDescriptionEn = "";
        String newActionCodeDescriptionEn = "";
        for (final RecommendedAction action : recommendedActionListEn) {
            if (action.getCode().equalsIgnoreCase(oldActionCode)) {
                oldActionCodeDescriptionEn = action.getDescription();
            }
            if (action.getCode().equalsIgnoreCase(currentActionCode)) {
                newActionCodeDescriptionEn = action.getDescription();
            }
        }

        String oldActionCodeDescriptionAr = "";
        String newActionCodeDescriptionAr = "";
        for (final RecommendedAction action : recommendedActionListAr) {
            if (action.getCode().equalsIgnoreCase(oldActionCode)) {
                oldActionCodeDescriptionAr = action.getDescription();
            }
            if (action.getCode().equalsIgnoreCase(currentActionCode)) {
                newActionCodeDescriptionAr = action.getDescription();
            }
        }

        final String[] argsEn = {oldActionCodeDescriptionEn,newActionCodeDescriptionEn};
        final String[] argsAr = {oldActionCodeDescriptionAr,newActionCodeDescriptionAr};

        final String message = getMessage(CHANGED_ACTION_MESSAGE,argsAr,localeAr) +
                         " / " +
                         getMessage(CHANGED_ACTION_MESSAGE,argsEn,localeEn);

        return message;
    }

    /**
     * Utility method to get a message from the bundle; if we can't extract the
     * string we'll return a formatted string in the same way <fmt:message> does
     * so that the calling method always gets something back, even if it's not
     * what should be displayed.
     *
     * @param code
     * @param args
     * @param locale
     * @return
     */
    private String getMessage(final String code, final Object[] args, final Locale locale) {
        String message;
        try {
            message = messageSourceAccessor.getMessage(code,args,locale);
        } catch (final NoSuchMessageException nsme) {
            message = new StringBuffer("????").append(code).append("????").toString();
            LOG.warn("unable to resolve " + message);
            LOG.warn(nsme);
        }
        return message;
    }

    private long getHighestPriority(final Long refId) {
        final List<ReferralHit> hitList = referralDAO.getReferralHits(refId);
        final long highestPriority = calculateHighestPriority(hitList);
        return highestPriority;
    }

    private long calculateHighestPriority(final List<ReferralHit> hitList) {
        long highestPriority = 0;
        for (final ReferralHit hit : hitList) {
            if (hit.isRuledOut()) {
                continue;
            }

            if (hit.getPriority() != null) {
                final long priority = hit.getPriority().longValue();
                highestPriority = (priority > highestPriority ? priority : highestPriority);
            }
        }
        return highestPriority;
    }




    /**
     * Get all referral logs for the referral.
     *
     * @param referralId
     * @return referralLog
     */
    @Override
    public List<ReferralLog> getReferralLogs(final Long referralId) {
        return referralDAO.getReferralLogs(referralId);
    }

    @Override
    public void insert(final ReferralLog referralLog)  {
        referralDAO.insert(referralLog);
    }

    /**
     * Get Referral by its id.
     *
     */
    @Override
    public Referral getReferralById(final Long referralId) {
        return referralDAO.getReferralById(referralId);
    }


    /**
     * Get Referral by Id an Version number
     */
    @Override
    public Referral getReferralById(final Long referralId, final Long versionNo) throws ApplicationException
    {
        final Referral referral = referralDAO.getReferralById(referralId, versionNo);
        if (referral == null) {
            throw new DataNoLongerValid();
        }

        return referral;
    }

    public void setReferralDAO(final ReferralDAO referralDAO) {
        this.referralDAO = referralDAO;
    }

    public void setPrRecommendedActionDao(final PrRecommendedActionDao prRecommendedActionDao) {
        this.prRecommendedActionDao = prRecommendedActionDao;
    }

   @Override
     public void updateReferralStatus(Referral referral) {
        final List<ReferralHit> referralHitsList = referralDAO.getReferralHits(referral.getId());
        referral.addReferralHits(referralHitsList);

            // Highest Priority
        Collections.sort(referralHitsList, new PriorityReferralHitComparator(SortOrderType.DESCENDING));
        final ReferralHit referralHitsPriority = referralHitsList.get(0);

        // Highest Severity
        Collections.sort(referralHitsList, new SeverityReferralHitComparator(SortOrderType.DESCENDING));
        final ReferralHit referralHitsSeverity = referralHitsList.get(0);

        referral.setActionCode(referralHitsPriority.getActionCode());
        referral.setPriority (
          calculatePriority(Double.valueOf(referralHitsPriority.getHitScore())
        , referralHitsPriority.getSeverityLevel()));
        referral.setSeverityLevel(referralHitsSeverity.getSeverityLevel());

        if(referralDAO.update(referral)  < 1){
            // updateVerisionNo changed, so call itself recursively with the updated values
            referral  = referralDAO.findById(referral.getId());
            updateReferralStatus(referral);
        }

        if (referral.getReferralState().isStatusChanged()) {
            sendNotificationIfRequired(referral);
            switch (ReferralStatusType.valueOf(referral.getStatus())) {
                    case NEW:
                        createReferralLog(referral, ReferralEventType.REFQUALA);
                        break;
                    case CLOSED:
                        createReferralLog(referral, ReferralEventType.REFCLOSEDA);
                        break;
                    default:
                        // NO Action is required.
            }
        } else {
            if (logger.isDebugEnabled()) {
                 logger.debug("Referral Status has not changed refId(" + referral.getId() + ") refStatus( " + referral.getStatus() + ")");
            }
        }

    }

    /**
     * the Referral State Changes to New (and notifications start)    or
     * A 'Hit Added' to Open Referral, notification is sent to the Alerts Officers team
     * @param referral
     */

    private void sendNotificationIfRequired(final Referral referral) {
        if (referral.isOpen()){
            referralNotificationService.sendHitAddedNotification(referral.getTraId());
        } else if(referral.isNew()) {
            referralNotificationService.sendNotification(referral.getTraId());
        }
    }

    @Override
    public void createReferralLog(final Referral referral, final ReferralEventType eventType) {
        final ReferralLog referralLog = new ReferralLog();
        referralLog.setRefId(referral.getId());
        referralLog.setEventType(eventType.name());
        referralLog.setCreatedBy(referral.getCreatedBy());
        referralLogDAO.insert(referralLog);
    }

    @Override
    public Referral addReferralAndLog(final Referral newReferral) {

        // Insert Referral
        newReferral.setId(referralDAO.insert(newReferral));
        createReferralLog(newReferral, ReferralEventType.REFCREATED);
        // Update AT Risk Flag of traveller
        travellerDAO.updateAtRiskFlag(newReferral.getTraId());
        // Update At Risk Flag of the flightSegments
        final Traveller traveller = travellerDAO.findTravellersById(newReferral.getTraId());
        flightSegmentsDAO.updateAtRiskFlag(traveller.getFlightSegId());
        sendNotificationIfRequired(newReferral);
        return newReferral;
    }

    @Override
    public ReferralHit saveReferralHitAndLog(final Traveller traveller, final ReferralHit newReferralHit, final FlightSegment  flightSegment )  {
        final ReferralHit referralHit =  saveReferralHitAndLog(newReferralHit, null);
        systemAuditor.auditAddReferralHit(traveller, newReferralHit , flightSegment);
        return referralHit ;
    }

    @Override
    public ReferralHit saveReferralHitAndLog(final ReferralHit newReferralHit, final String remarks,  final Response response )
    {
        final ReferralHit referralHit = saveReferralHitAndLog(newReferralHit, remarks);
        systemAuditor.auditAddReferralHit(response, newReferralHit  );
        return referralHit  ;
    }


    private ReferralHit saveReferralHitAndLog(final ReferralHit newReferralHit, final String remarks) {
     if (logger.isDebugEnabled())
     {
         logger.debug(newReferralHit.toString());
     }
      referralHitDAO.insert(newReferralHit);
        final ReferralLog referralLog = new ReferralLog();
        referralLog.setRefId(newReferralHit.getRefId());
        referralLog.setHitId(newReferralHit.getId());
        if ( QualificationStatusType.OUT.name().equals(newReferralHit.getQualificationStatus())){
            referralLog.setEventType(ReferralEventType.CLEAREDHIT.name());
        } else if (QualificationStatusType.IN.name().equals(newReferralHit.getQualificationStatus())) {
            referralLog.setEventType(ReferralEventType.HITINA.name());
        }else {
            referralLog.setEventType(ReferralEventType.HITADDED.name());
        }
        referralLog.setHitType(newReferralHit.getHitType());
        referralLog.setCreatedBy(newReferralHit.getCreatedBy());
        referralLog.setRemarks(remarks);
        referralLogDAO.insert(referralLog);

        return newReferralHit;
    }


    /**
     * Get Referral for a given travellerId.
     *
     * @param travellerId
     * @return Referral
     */
    @Override
    public Referral findReferralByTravellerId(final Long travellerId)
    {
        return referralDAO.findByTravellerId(travellerId);
    }


    @Override
    public ReferralDetails findReferralDetailsByTravellerId(final Long traId) {
        return referralDAO.findReferralDetailsByTravellerId(traId);
    }
    public Long getMaxSeverityLevel() {
        return maxSeverityLevel;
    }

    public void setMaxSeverityLevel(final Long maxSeverityLevel) {
        this.maxSeverityLevel = maxSeverityLevel;
    }

    @Override
    public Long getAppSeverityLevel() {
        return appSeverityLevel;
    }

    public void setAppSeverityLevel(final Long appSeverityLevel) {
        this.appSeverityLevel = appSeverityLevel;
    }

    @Override
    public Long getAppHitScore() {
        return appHitScore;
    }

    public void setAppHitScore(final Long appHitScore) {
        this.appHitScore = appHitScore;
    }

    @Override
    public Long calculatePriority(final Double hitScore, final Long severityLevel) {
        return Math.round((hitScore *severityLevel)/maxSeverityLevel.doubleValue());
    }

    @Override
    public Long calculateAppPriority() {
        return Math.round((appHitScore * appSeverityLevel) /maxSeverityLevel.doubleValue());
    }


    @Override
    public String getAppRecommendedActionCode() {
        return appRecommendedActionCode;
    }

    public void setAppRecommendedActionCode(final String appRecommendedActionCode) {
        this.appRecommendedActionCode = appRecommendedActionCode;
    }

    public void setTravellerDAO(final TravellerDAO travellerDAO) {
        this.travellerDAO = travellerDAO;
    }

    public void setFlightSegmentsDAO(final FlightSegmentsDAO flightSegmentsDAO) {
        this.flightSegmentsDAO = flightSegmentsDAO;
    }

    public void setReferralNotificationService(
            final ReferralNotificationService referralNotificationService) {
        this.referralNotificationService = referralNotificationService;
    }

    public void setReferralLogDAO(final ReferralLogDAO referralLogDAO) {
        this.referralLogDAO = referralLogDAO;
    }

    public void setReferralHitDAO(final ReferralHitDAO referralHitDAO) {
        this.referralHitDAO = referralHitDAO;
    }

    public void setSystemAuditor(final SystemAuditor systemAuditor) {
        this.systemAuditor = systemAuditor;
    }

}
