/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;
import abc.xyz.pts.bcs.common.exception.AlreadyAcknowledgedException;
import abc.xyz.pts.bcs.common.exception.ApplicationException;
import abc.xyz.pts.bcs.common.referral.workflows.notification.ReferralNotificationService;

/**
 * @author cwalker
 *
 */
@Service("referralStateService")
public class ReferralStateService {
    private static final Logger log = Logger.getLogger(ReferralStateService.class);

    private ReferralService referralService;

    private ReferralNotificationService referralNotificationService;


    /**
     * Save the referral data set
     *
     * @param referral
     * @throws ApplicationException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save
        ( final Referral referral
        , final String oldActionCode
        , final String eventType
        , final Locale locale
        ) throws ApplicationException
    {
        if (log.isDebugEnabled()) {
            log.debug("newActionCode=" + referral.getActionCode() + ", oldActionCode=" + oldActionCode);
        }

        final String oldStatus = referral.getStatus();
        handleStateTransition(referral);
        referralService.updateReferral(referral, oldActionCode, eventType, locale);

        // start Notification Timers when moving from UNQUALIFIED to NEW
        if (ReferralStatusType.NEW.name().equals(referral.getStatus()) && ReferralStatusType.UNQ.name().equals(oldStatus)) {
            referralNotificationService.sendNotification(referral.getTraId());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void close
        ( final Referral referral
        , final String eventType
        , final String closureNote
        , final Locale locale
        , final boolean closeOnSave) throws ApplicationException
   {
        handleStateTransitionToClose(referral);

        referralService.closeReferral(referral, eventType, closureNote, locale, closeOnSave);
    }

    /**
     * This method checks if Referral is already Acknowledged and throws appropriate Exception. If it can
     * be acknowledged it calls the State Machine and then acknowledges the Referral.
     * @param referral
     * @param eventType
     * @param closureNote
     * @param locale
     * @throws ApplicationException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void acknowledge(
            final Referral referral,
            final Locale locale)
            throws ApplicationException
    {
        if (ReferralStatusType.NEW.name().equals(referral.getStatus()) == false) {
            throw new AlreadyAcknowledgedException();
        }

        handleStateTransitionToAcknowledge(referral);
        referralService.acknowledgeReferral(referral, locale);
    }

    private void handleStateTransition(final Referral referral) throws ApplicationException {
        referral.save();
    }

    private void handleStateTransitionToClose(final Referral referral) throws ApplicationException {
        referral.close();
    }

    private void handleStateTransitionToAcknowledge(final Referral referral) throws ApplicationException {
        referral.acknowledge();
    }

    public void setReferralService(final ReferralService referralService) {
        this.referralService = referralService;
    }

    public void setReferralNotificationService(final ReferralNotificationService referralNotificationService) {
        this.referralNotificationService = referralNotificationService;
    }



}
