/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.workflows.notification;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.irisk.dao.TravellerDAO;
import abc.xyz.pts.bcs.common.messaging.JMSSender;
import abc.xyz.pts.bcs.common.util.JaxbHelper;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.AlertActionType;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.NotificationRequest;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.RequestType;


public class ReferralNotificationServiceImpl implements ReferralNotificationService {

    private JMSSender referralNotifcationRequestSender;
    private JMSSender referralAddHitNotifcationRequestSender;
    private TravellerDAO travellerDAO;

    private static Logger logger = Logger.getLogger(ReferralNotificationServiceImpl.class);

    @Override
    public void sendHitAddedNotification(final Long traId) {

        final Long flightSegId = getFlightSegmentId(traId);

        final NotificationRequest response = createNotificationRequest(traId, flightSegId, RequestType.HIT_ADDED);

        final String serializedResponse = JaxbHelper.serialise(response);

        try{
            referralAddHitNotifcationRequestSender.sendMessage(serializedResponse);

            if(logger.isInfoEnabled()){
                logger.info(createLogMessage("Hit added notification sent to workflows \n", response));
            }

        } catch (final JMSException e) {
            logger.error(createLogMessage("Hit added to open referral , notification send request failed!\n ",response), e);
        }
    }

    @Override
    public void sendNotification(final Long traId) {
        final Long flightSegId = getFlightSegmentId(traId);

        final NotificationRequest response = createNotificationRequest(traId, flightSegId, RequestType.START);

        final String serializedResponse = JaxbHelper.serialise(response);

        try{
            referralNotifcationRequestSender.sendMessage(serializedResponse);

            if(logger.isInfoEnabled()){
                logger.info(createLogMessage("Hit added start notification sent to workflows \n", response));
            }
        } catch (final JMSException e) {
            logger.error(createLogMessage("Hit added start notification send request failed!\n ",response), e);
        }
    }

    private Long getFlightSegmentId(final Long traId) {
        final Traveller traveller = travellerDAO.findTravellersById(traId);

        final Long flightSegId = traveller.getFlightSegId();
        return flightSegId;
    }

    private StringBuilder createLogMessage(final String message, final NotificationRequest response) {
        final StringBuilder stringToLog = new StringBuilder();
        stringToLog.append(message);
        stringToLog.append("TraId(").append(response.getTraId()).append(")\n");
        stringToLog.append("FlightSegId(").append(response.getFlightSegId()).append(")\n");
        stringToLog.append("RequestType (").append(response.getRequestType()).append(")\n");
        return stringToLog;
    }

    private NotificationRequest createNotificationRequest(final Long traId, final Long flightSegId, final RequestType requestType) {
        final NotificationRequest    response = new NotificationRequest();
        response.setTraId(traId);
        response.setFlightSegId(flightSegId);
        response.setRequestType(requestType);
        response.setAlertActionType(AlertActionType.NOTIFIED);
        return response;
    }

    public TravellerDAO getTravellerDAO() {
        return travellerDAO;
    }

    public void setTravellerDAO(final TravellerDAO travellerDAO) {
        this.travellerDAO = travellerDAO;
    }

    public JMSSender getReferralNotifcationRequestSender() {
        return referralNotifcationRequestSender;
    }

    public void setReferralNotifcationRequestSender(final JMSSender referralNotifcationRequestSender) {
        this.referralNotifcationRequestSender = referralNotifcationRequestSender;
    }

    public void setReferralAddHitNotifcationRequestSender(final JMSSender referralAddHitNotifcationRequestSender) {
        this.referralAddHitNotifcationRequestSender = referralAddHitNotifcationRequestSender;
    }

}

