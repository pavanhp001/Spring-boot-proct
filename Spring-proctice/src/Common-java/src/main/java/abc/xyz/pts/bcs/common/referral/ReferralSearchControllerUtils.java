/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.support.RequestContext;

import abc.xyz.pts.bcs.common.dto.JSONTableData;
import abc.xyz.pts.bcs.common.dto.ReferralLog;
import abc.xyz.pts.bcs.common.enums.ReferralHitsType;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;
import abc.xyz.pts.bcs.common.util.CalendarUtils;

public final class ReferralSearchControllerUtils {
    private static final Logger log = Logger.getLogger(ReferralSearchControllerUtils.class);

    /**
     *
     * This utility method takes a ReferralHitTargetRec and a
     * ReferralTravellerRec (which may be null in the case of an AP Hit) and
     * populates a new JSONTableData object with the necessary data and headers
     * (from the Data Dictionary) ready to send back to the UI
     *
     * @param referralHitTargetRec
     * @param referralTravellerRec
     * @param ctx
     * @return
     */    public static JSONTableData getJSONHitTable(final ReferralHitTargetRec referralHitTargetRec,
            final ReferralTravellerRec referralTravellerRec, final RequestContext ctx) {
        return getJSONHitTable(referralHitTargetRec, referralTravellerRec, ctx, null);
    }

    /**
     *
     * This utility method takes a ReferralHitTargetRec and a
     * ReferralTravellerRec (which may be null in the case of an AP Hit) and
     * populates a new JSONTableData object with the necessary data and headers
     * (from the Data Dictionary) ready to send back to the UI
     * It also allows the setting of an id field which relates to the data
     *
     * @param referralHitTargetRec
     * @param referralTravellerRec
     * @param ctx
     * @param id
     * @return
     */
    public static JSONTableData getJSONHitTable(final ReferralHitTargetRec referralHitTargetRec,
            final ReferralTravellerRec referralTravellerRec, final RequestContext ctx, final Long id) {
        final JSONTableData tableData = new JSONTableData();
        tableData.setId(id);
        final List<String> tableHeaders = new ArrayList<String>();
        final List<List<String>> tableRows = new ArrayList<List<String>>();

        if (referralHitTargetRec != null) {
            if (ReferralHitsType.WL.name().equals(referralHitTargetRec.getHitType())) {
                // We are dealing with a watch list hit
                tableHeaders.add(StringUtils.EMPTY);
                tableHeaders.add(getMessage(ctx, "hit.table.heading.NAME"));
                tableHeaders.add(getMessage(ctx, "hit.table.heading.GENDER"));
                tableHeaders.add(getMessage(ctx, "hit.table.heading.DATE.OF.BIRTH"));
                tableHeaders.add(getMessage(ctx, "hit.table.heading.COUNTRY.OF.BIRTH"));
                tableHeaders.add(getMessage(ctx, "hit.table.heading.PLACE.OF.BIRTH"));
                tableHeaders.add(getMessage(ctx, "hit.table.heading.NATIONALITY"));
                tableHeaders.add(getMessage(ctx, "hit.table.heading.DOCUMENT.NUMBER"));
                tableHeaders.add(getMessage(ctx, "hit.table.heading.OWNERS.REF"));

                final List<String> targetRow = new ArrayList<String>();
                targetRow.add(getMessage(ctx, "hit.table.title.TARGET"));
                targetRow.add(StringUtils.defaultString(referralHitTargetRec.getWlFullname()));
                targetRow.add(StringUtils.defaultString(referralHitTargetRec.getWlGender()));
                targetRow.add(CalendarUtils.calToString(referralHitTargetRec.getWlBirthDate(),getMessage(ctx, "date.pattern")));
                targetRow.add(StringUtils.defaultString(referralHitTargetRec.getCountryOfBirth()));
                targetRow.add(StringUtils.defaultString(referralHitTargetRec.getWlBirthPlace()));
                targetRow.add(StringUtils.defaultString(referralHitTargetRec.getWlNationality()));
                targetRow.add(StringUtils.defaultString(referralHitTargetRec.getDocData()));
                targetRow.add(StringUtils.defaultString(referralHitTargetRec.getWlProtocolNumber()));

                if (referralTravellerRec != null) {
                    final List<String> travellerRow = new ArrayList<String>(11); // should help performance a bit

                    travellerRow.add(getMessage(ctx, "hit.table.title.TRAVELLER"));
                    travellerRow.add(StringUtils.defaultString(referralTravellerRec.getFullname()));
                    travellerRow.add(StringUtils.defaultString(referralTravellerRec.getGender()));
                    travellerRow.add(CalendarUtils.calToString(referralTravellerRec.getBirthDate(),getMessage(ctx, "date.pattern")));
                    travellerRow.add(StringUtils.defaultString(referralTravellerRec.getCountryOfBirth()));
                    travellerRow.add(StringUtils.defaultString(referralTravellerRec.getBirthPlace()));
                    travellerRow.add(StringUtils.defaultString(referralTravellerRec.getNationality()));
                    travellerRow.add(StringUtils.defaultString(referralTravellerRec.getDocData()));

                    travellerRow.add(StringUtils.EMPTY);

                    tableRows.add(travellerRow);
                }

                tableRows.add(targetRow);
            } else if (ReferralHitsType.APP.name().equals(referralHitTargetRec.getHitType())) {
                // We are dealing with an AP hit
                tableHeaders.add(getMessage(ctx, "hit.table.heading.APP.REASON"));

                final List<String> reasonRow = new ArrayList<String>();
                final String appHitReason = referralHitTargetRec.getAppHitReason();
                if (appHitReason != null) {
                    final String appHitReasonDescription = getMessage(ctx, "app.hit.reason." + appHitReason);
                    if (appHitReasonDescription != null) {
                        reasonRow.add(appHitReasonDescription);
                    }

                }
                tableRows.add(reasonRow);
            }
        }

        tableData.setTableHeaders(tableHeaders);
        tableData.setTableRows(tableRows);

        return tableData;
    }

    /**
     *
     * This utility method takes a ReferralLogRec and populates a new
     * JSONTableData object with the necessary data and headers (from the Data
     * Dictionary) ready to send back to the UI
     *
     * @param referralHitTargetRec
     * @param referralTravellerRec
     * @param ctx
     * @return
     */
    public static JSONTableData getJSONLogTable(final List<ReferralLog> logRecs, final RequestContext ctx) {
        final JSONTableData tableData = new JSONTableData();
        final List<String> tableHeaders = new ArrayList<String>();
        final List<List<String>> tableRows = new ArrayList<List<String>>();

        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.DATE.TIME"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.EVENT.TYPE"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.HIT.TYPE"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.HIT.NUMBER"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.PERFORMED.BY"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.AIRPORT"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.RECIPIENTS.NOTIFIED"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.NOTIFICATION.MEDIA"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.NOTIFICATION.STATUS"));
        tableHeaders.add(getMessage(ctx, "ref.log.table.heading.NOTES"));

        for (final ReferralLog referralLogRec : logRecs) {
            final List<String> logRow = new ArrayList<String>();

            logRow.add(CalendarUtils.calToString(referralLogRec.getCreatedDatetime(),getMessage(ctx, "date.time.pattern")));
            logRow.add(getMessage(ctx, "referral.log.event.", referralLogRec.getEventType()));
            logRow.add(StringUtils.defaultString(referralLogRec.getHitType()));

            // Due to some autoboxing weirdness, need null on the LHS of the
            // expression; 'referralLogRec.getHitId() != null' throws a
            // NullPointerException
            // if getHitId() returns a null, as it attempts to autobox the null
            // return
            // val and call longValue() on it (!)
            if (null != referralLogRec.getHitId()) {
                // Hit ID can be null, if we've just updated the referral and
                // not a hit
                logRow.add(referralLogRec.getHitId().toString());
            } else {
                logRow.add("");
            }
            logRow.add(StringUtils.defaultString(referralLogRec.getCreatedBy()));
            logRow.add(StringUtils.defaultString(referralLogRec.getNotfAirportCode()));
            logRow.add(StringUtils.defaultString(referralLogRec.getNotfRolesNotified()));
            logRow.add(StringUtils.defaultString(referralLogRec.getNotfMediaType()));
            logRow.add(StringUtils.defaultString(referralLogRec.getNotfActionStatus()));
            logRow.add(StringUtils.defaultString(referralLogRec.getRemarks()));

            tableRows.add(logRow);
        }

        tableData.setTableHeaders(tableHeaders);
        tableData.setTableRows(tableRows);

        return tableData;
    }

    /**
     * Utility method to get a message from the bundle; if we can't extract the
     * string we'll return a formatted string in the same way <fmt:message> does
     * so that the calling method always gets something back, even if it's not
     * what should be displayed.
     *
     * @param str
     *            the message key to retrieve
     * @return Successfully retrieved message, or a failsafe version
     */
    private static String getMessage(final RequestContext ctx, final String str) {
        String message;
        try {
            message = ctx.getMessage(str);
        } catch (final NoSuchMessageException nsme) {
            message = "????" + str + "????";
            log.warn("unable to resolve " + message);
            log.warn(nsme);
        }

        return message;
    }

    private static String getMessage(final RequestContext ctx, final String msgPrefix, final String msg) {
        final String messageKey = msgPrefix + msg;
        return getMessage(ctx, messageKey);

    }

    /**
     * There seems to be a bug with the message source accessor that causes it
     * not to replace the arguments in a message when specific characters exist.
     * So we must do it by hand.
     *
     * @param messageKey
     * @param arguments
     * @return
     */
    public static String getMessage(final RequestContext ctx, final String messageKey, final String[] arguments) {
        String translated = getMessage(ctx, messageKey);
        for (int i = 0; i < arguments.length; i++) {
            translated = translated.replaceAll("\\{" + i + "\\}", arguments[i]);
        }
        return translated;
    }

}
