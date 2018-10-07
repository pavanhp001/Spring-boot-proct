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
package abc.xyz.pts.bcs.common.referral.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.josso.tc60.agent.jaas.CatalinaSSOUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;
import org.springmodules.validation.commons.DefaultBeanValidator;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.business.lookup.LookupDataService;
import abc.xyz.pts.bcs.common.business.lookup.ReferenceDataService;
import abc.xyz.pts.bcs.common.dto.JSONResponse;
import abc.xyz.pts.bcs.common.dto.JSONTableData;
import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.dto.ReferralLog;
import abc.xyz.pts.bcs.common.enums.QualificationStatusType;
import abc.xyz.pts.bcs.common.enums.ReferralEventType;
import abc.xyz.pts.bcs.common.enums.UserPermissionType;
import abc.xyz.pts.bcs.common.exception.ApplicationException;
import abc.xyz.pts.bcs.common.exception.DBUpdateFailedException;
import abc.xyz.pts.bcs.common.referral.ReferralSearchControllerUtils;
import abc.xyz.pts.bcs.common.referral.ReferralService;
import abc.xyz.pts.bcs.common.referral.ReferralStateService;
import abc.xyz.pts.bcs.common.referral.business.ReferralHitService;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;
import abc.xyz.pts.bcs.common.referral.validator.ReferralCloseValidator;
import abc.xyz.pts.bcs.common.referral.validator.ReferralSearchValidator;
import abc.xyz.pts.bcs.common.referral.web.command.ReferralAddNoteCommand;
import abc.xyz.pts.bcs.common.referral.web.command.ReferralHitCommand;
import abc.xyz.pts.bcs.common.util.CalendarUtils;
import abc.xyz.pts.bcs.common.util.WebConstants;
import abc.xyz.pts.bcs.common.web.controller.ApplicationURL;

/**
 * Encapsulates core referral functionality as used on ReferralSearch (iRisk)
 * and Traveller Data (iDetectDB)
 *
 * @author cwalker
 */
@Controller
public abstract class AbstractReferralController {
    private static final Logger LOG = Logger.getLogger(AbstractReferralController.class);
    public static final String SUCCESS_MESSAGE = "successMessage";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String TABLE_COMMAND = "tableCommand";
    public static final String REFERRAL_SEARCH_COMMAND = "referralSearchCommand";
    public static final String REFERRAL_HIT_COMMAND = "referralHitCommand";
    public static final String REFERRALS = "referrals";
    public static final String PREVIOUS_TABLE_COMMAND = "previousTableCommand";

    public static final String RECOMMENDED_ACTION_CODES = "actionCodes";

    /** action form parameter values. */
    protected static final String ACTION_CLOSE_REFERRAL = "close";
    protected static final String ACTION_ACK_REFERRAL = "acknowledge";
    protected static final String ACTION_SAVE_REFERRAL = "save";
    protected static final String ACTION_ADD_CLEARED_DOC = "addcleareddoc";
    public static final String IRISK_REFERRAL_SEARCH_FORM = "iRiskReferralSearch.form";
    private static final String ADDITIONAL_INSTRUCTION_PREFIX = "export.movement.instruction.";
    protected @Value(value="${export.movement.enabled}") boolean isExportMovementEnabled;
    protected static final String EXPORT_MOVEMENT_ENABLED = "exportMovementEnabled";

    @Autowired
    private DefaultBeanValidator commonsValidator;

    @Autowired
    private ReferralSearchValidator referralSearchValidator;

    @Autowired
    private ReferralCloseValidator referralCloseValidator;

    @Autowired
    private LookupDataService lookupDataService;

    @Autowired
    private ReferenceDataService referenceDataService;

    @Autowired
    private ReferralHitService referralHitService;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private ReferralStateService referralStateService;

    protected void closeReferralProcess
        ( final ReferralHitCommand referralHitCmd
        , final BindingResult errors
        , final HttpSession session
        , final HttpServletRequest request
        , final ModelMap model
        , final Locale locale
        )
    {
        referralHitCmd.setAction("Close");

        final RequestContext ctx = new RequestContext(request);

        try {
            if (referralHitCmd.isCloseOnSave()) {
                referralCloseValidator.validate(referralHitCmd, errors);
            }

            // If close is called due to all hits being ruled out and there are
            // no
            // validation errors
            if (!errors.hasErrors()) {
                if (request.isUserInRole(UserPermissionType.REFERRAL_WRITE.getPermission()))
                {
                    final Referral referral = prepareReferralToSave(referralHitCmd, request.getUserPrincipal().getName());
                    // State transition, if possible
                    referralStateService.close( referral
                                              , ReferralEventType.REFCLOSEDM.name()
                                              , referralHitCmd.getClosureNote()
                                              , locale
                                              , referralHitCmd.isCloseOnSave()
                                              );

                    // Resolve Succes Message
                    final String successMessage = ReferralSearchControllerUtils.getMessage(ctx
                                            , "close.referral.success"
                                            , new String[] { StringUtils.EMPTY + referralHitCmd.getRefId() }
                                            );
                    model.addAttribute(SUCCESS_MESSAGE, successMessage);
                }
                else
                {
                    // Invalid User Permission
                    errors.rejectValue(StringUtils.EMPTY, "error.unauthorised");
                }
            }
        }
        catch (final ApplicationException ae)
        {
            LOG.warn(ae, ae);

            // Any changes made will be rolled back once this method exists
            // (catch-all)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            // Get the RequestContext so we can access the message bundle

            model.addAttribute( ERROR_MESSAGE
                              , ReferralSearchControllerUtils.getMessage(ctx, ae.getErrorCode(), new String[] { StringUtils.EMPTY + referralHitCmd.getRefId() })
                              );
        }
    }

    protected void acknowledgeReferralProcess(
            final ReferralHitCommand referralHitCmd,
            final BindingResult errors,
            final HttpSession session,
            final HttpServletRequest request,
            final ModelMap model,
            final Locale locale) {

        referralHitCmd.setAction("Acknowledge");

        try
        {
            if (request.isUserInRole(UserPermissionType.REFERRAL_INTERVENE.getPermission())) {
                final Referral referral = prepareReferralToSave(referralHitCmd, request.getUserPrincipal().getName());
                referralStateService.acknowledge(referral, locale);
            } else {
                // Invalid User Permission
                errors.rejectValue(StringUtils.EMPTY , "error.unauthorised");
            }
        }
        catch (final ApplicationException ae)
        {
            LOG.warn(ae, ae);

            // Any changes made will be rolled back once this method exists
            // (catch-all)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            // Get the RequestContext so we can access the message bundle
            final RequestContext ctx = new RequestContext(request);

            final String errorMessage = ReferralSearchControllerUtils.getMessage
                    ( ctx
                    , ae.getErrorCode()
                    , new String[] { StringUtils.EMPTY + referralHitCmd.getRefId() }
                    );
            model.addAttribute(ERROR_MESSAGE, errorMessage);
        }

    }

    protected void saveProcess
        ( final ReferralHitCommand referralHitCmd
        , final BindingResult errors
        , final HttpSession session
        , final HttpServletRequest request
        , final ModelMap model
        , final Locale locale
        )
    {
        referralHitCmd.setAction("Save");
        try
        {
            final Referral referral = prepareReferralToSave(referralHitCmd, request.getUserPrincipal().getName());

            String oldActionCode = null;
            final String[] oldRecommendedActionCodes = referralHitCmd.getOldRecommendedAction().split(",");
            if (oldRecommendedActionCodes != null && oldRecommendedActionCodes.length > 0) {
                oldActionCode = oldRecommendedActionCodes[0];
            }

            // State transition, if possible (this method is transactional)
            referralStateService.save(referral, oldActionCode, ReferralEventType.REFQUALM.name(), locale);
        }
        catch (final ApplicationException ae)
        {
            LOG.warn(ae, ae);

            // Any changes made will be rolled back once this method exists
            // (catch-all)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            // Get the RequestContext so we can access the message bundle
            final RequestContext ctx = new RequestContext(request);

            final String errorMessage = ReferralSearchControllerUtils.getMessage
                ( ctx
                , ae.getErrorCode()
                , new String[] { StringUtils.EMPTY + referralHitCmd.getRefId() }
                );
            model.addAttribute( ERROR_MESSAGE, errorMessage);
        }
    }

    @RequestMapping(value=IRISK_REFERRAL_SEARCH_FORM, params = "selectedHitId", method = RequestMethod.GET)
    public @ResponseBody JSONResponse processGetHitDetail
        ( @RequestParam final Long selectedHitId
        , final HttpServletRequest request
        )
    {
        // Get the RequestContext so we can access the message bundle
        final RequestContext ctx = new RequestContext(request);

        // Create a JSON response object
        final JSONResponse jsonResponse = new JSONResponse();

        // Get the hit target
        final ReferralHitTargetRec referralHitTargetRec = referralHitService.getReferralHitTarget(selectedHitId);

        ReferralTravellerRec referralTravellerRec = null;
        if (referralHitTargetRec != null) {

            // Get the traveller if applicable
            if (referralHitTargetRec.getWatlrId() != null) {
                referralTravellerRec = referralHitService.getReferralHitTraveller(referralHitTargetRec.getWatlrId());
            }

            // Populate a JSONTableData object with the data from the target and traveller
            final JSONTableData tableData = ReferralSearchControllerUtils.getJSONHitTable(referralHitTargetRec,
                    referralTravellerRec, ctx);
            jsonResponse.setData(tableData);

        } else {
            jsonResponse.setError(JSONResponse.NO_DATA_FOUND_ERROR);
        }

        return jsonResponse;
    }

    @RequestMapping(value=IRISK_REFERRAL_SEARCH_FORM, params = "getAllHitsForRefId", method = RequestMethod.GET)
    public @ResponseBody JSONResponse processGetAllHitDetails
        ( @RequestParam final Long getAllHitsForRefId
        , final HttpServletRequest request
        )
    {
        // Get the RequestContext so we can access the message bundle
        final RequestContext ctx = new RequestContext(request);

        // Create a JSON response object
        final JSONResponse jsonResponse = new JSONResponse();

        // Get all the hit target objects for this referral
        final List<ReferralHitTargetRec> referralHitTargetRecs = referralHitService.getAllReferralHitTargets(getAllHitsForRefId);

        if (!referralHitTargetRecs.isEmpty())
        {
            final List<JSONTableData> tableList = new ArrayList<JSONTableData>();
            // If we have some hits then iterate through them...
            for (final ReferralHitTargetRec referralHitTargetRec:referralHitTargetRecs)
            {
                ReferralTravellerRec referralTravellerRec = null;
                if (referralHitTargetRec != null)
                {
                    // Get the traveller if applicable
                    if (referralHitTargetRec.getWatlrId() != null) {
                        referralTravellerRec = referralHitService.getReferralHitTraveller(referralHitTargetRec.getWatlrId());
                    }

                    // Populate a JSONTableData object with the data from the target and traveller
                    final JSONTableData tableData = ReferralSearchControllerUtils.getJSONHitTable(referralHitTargetRec,
                            referralTravellerRec, ctx, referralHitTargetRec.getId());

                    // Add it to the list
                    tableList.add(tableData);
                }
            }

            // Add the list of JSONTableData objects to the JSONResponse
            jsonResponse.setData(tableList);
        }
        else
        {
            // If we don't have any hits then set the JSONResponse error flag
            jsonResponse.setError(JSONResponse.NO_DATA_FOUND_ERROR);
        }
        return jsonResponse;
    }

    /**
     * Process add note from JSON request and return all the referral logs for
     * this referral as a JSONResponse
     *
     * @param noteText
     * @return
     */

    @AuditableEvent(Event.REFERRAL_NOTE)
    @RequestMapping(value=IRISK_REFERRAL_SEARCH_FORM, params = { "noteText", "refId" }, method = RequestMethod.POST)
    public @ResponseBody JSONResponse processAddNoteAsJson(
            @ModelAttribute(REFERRAL_HIT_COMMAND) final ReferralAddNoteCommand referralAddNoteCommand,
            final BindingResult errors,
            final HttpServletRequest request)
    {
        // Create an empty JSONResponse object
        final JSONResponse jsonResponse = new JSONResponse();

        try {
            // Save the note as a new referral log entry
            addReferralLog(referralAddNoteCommand.getRefId(), referralAddNoteCommand.getNoteText(), null, request,
                    ReferralEventType.ADDNOTE.name());
        } catch (final DBUpdateFailedException dbufe) {
            // Set the error flag in JSONResponse if there is a problem
            jsonResponse.setError(JSONResponse.UPDATE_DATA_FAILED_ERROR);
        }

        // Add the referral logs to the JSONResponse
        getReferralLogs(jsonResponse, referralAddNoteCommand.getRefId(), request);

        return jsonResponse;
    }

    /**
     * Get all referral logs for this referral and return them as a JSONResponse
     *
     * @param referralId
     * @param request
     * @return
     */
    @RequestMapping(value=IRISK_REFERRAL_SEARCH_FORM, params = "referralId", method = RequestMethod.GET)
    public @ResponseBody JSONResponse processGetReferralLogAsJson
        ( @RequestParam final Long referralId
        , final HttpServletRequest request
        )
    {
        // Get the referral logs as a JSONResponse
        final JSONResponse jsonResponse = new JSONResponse();
        getReferralLogs(jsonResponse, referralId, request);

        return jsonResponse;
    }



    private Referral prepareReferralToSave
        ( final ReferralHitCommand referralHitCmd
        , final String userName
        ) throws ApplicationException
    {
        if (null == referralHitCmd) {
            throw new IllegalArgumentException("No valid referral hit command ");
        }

     // It's not possible to get all the attributes we need from the JSP page
        final Referral referral = referralService.getReferralById(referralHitCmd.getRefId(), referralHitCmd.getReferralVersion());
        referral.setActionCode(referralHitCmd.getActionCode());
        referral.setModifiedBy(userName);
        referral.setClosureCode(referralHitCmd.getClosureReason());
        referral.setTotalHits(referralHitCmd.getTotalHits());       // so we can make an informed decision in Referral
        referral.setAdditionalInstructionCode(referralHitCmd.getAdditionalInstructionCode());
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(WebConstants.BUNDLE_BASE_NAME, new Locale("AR"));
        if (referralHitCmd.getAdditionalInstructionCode() != null && !referralHitCmd.getAdditionalInstructionCode().equals("")) {
                final String value = resourceBundle.getString(ADDITIONAL_INSTRUCTION_PREFIX + referralHitCmd.getAdditionalInstructionCode());
                referral.setAdditionalInstructionDesc(value);
        }
        if (referralHitCmd.getHitRule() != null)
        {
            for (final Long hitId : referralHitCmd.getHitRule().keySet())
            {
                final ReferralHit hit = new ReferralHit();
                hit.setId(hitId);

                String hitRule = null;
                if (null != referralHitCmd.getHitRule()) {
                    hitRule = referralHitCmd.getHitRule().get(hitId);
                }

                hit.setQualificationStatus(extractQualificationStatus(hitRule));
                hit.setModifiedBy(userName);
                hit.setUpdateVersionNo(referralHitCmd.getReferralHitVersions().get(hitId));
                referral.getReferralHits().add(hit);
            }
        }
        return referral;
    }
    private String extractQualificationStatus(final String hitRule) {
        String qualificationStatus = QualificationStatusType.UNKNOWN.name();
        if (hitRule != null && hitRule.equals("IN")) {
            qualificationStatus = QualificationStatusType.IN.name();
        } else if (hitRule != null && hitRule.equals("OUT")) {
            qualificationStatus = QualificationStatusType.OUT.name();
        }
        return qualificationStatus;
    }


    /**
     * Add a referral log record.
     *
     * @param referralId
     * @param noteText
     * @param reasonCode
     * @param request
     * @param eventType
     */
    private void addReferralLog
        ( final Long referralId
        , final String noteText
        , final String reasonCode
        , final HttpServletRequest request
        , final String eventType
        ) throws DBUpdateFailedException
    {
        final Principal userPrincipal = request.getUserPrincipal();
        String userName = StringUtils.EMPTY;
        if (userPrincipal instanceof CatalinaSSOUser) {
            final CatalinaSSOUser user = (CatalinaSSOUser) userPrincipal;
            userName = user.getName();
        }
        // Add entry in Referral Log table
        final ReferralLog referralLog = new ReferralLog();
        referralLog.setCreatedBy(userName);
        referralLog.setCreatedDatetime(Calendar.getInstance());
        referralLog.setRefId(referralId);
        referralLog.setEventType(eventType);
        referralLog.setRemarks(noteText);
        if (ReferralEventType.REFCLOSEDM.name().equals(eventType)) {
            referralLog.setClosureCode(reasonCode);
        }
        referralService.insert(referralLog);
    }

    /**
     * Get the referral logs for a given referralId - common code for controller
     * methods. This method updates a given JSONResponse object. (We can't
     * create a new one as it may already contain error information).
     *
     * @param jsonResponse
     * @param referralId
     * @param request
     */
    private void getReferralLogs(final JSONResponse jsonResponse, final Long referralId, final HttpServletRequest request) {

        // Get the RequestContext so we can access the message bundle
        final RequestContext ctx = new RequestContext(request);

        // Get the referral logs
        final List<ReferralLog> logRecs = referralService.getReferralLogs(referralId);

        if (logRecs != null) {
            // Populate a JSONTableData object with the data from the target and
            // traveller
            final JSONTableData tableData = ReferralSearchControllerUtils.getJSONLogTable(logRecs, ctx);
            jsonResponse.setData(tableData);
        } else {
            jsonResponse.setError(JSONResponse.NO_DATA_FOUND_ERROR);
        }
    }

    @RequestMapping(value = IRISK_REFERRAL_SEARCH_FORM, method = RequestMethod.POST, params = "action="+ ACTION_ADD_CLEARED_DOC)
    public String processAddClearedDoc
        ( @ModelAttribute(REFERRAL_HIT_COMMAND) final ReferralHitCommand referralHitCommand
        , final BindingResult errors
        , final HttpSession session
        , final HttpServletRequest request
        , final ModelMap model
        , final Locale locale
        )
    {
        // ****
        // ** NOTE: attribute name maps to the ClearedDocumentsCommand object.
        // ****

        // prepare to redirect
        final StringBuilder url = new StringBuilder();
        url.append(ApplicationURL.getViewTarget(request));
        url.append("?");
        url.append("targetId=" + referralHitCommand.getTargetId());
        url.append("&");
        url.append("action=saveReferralClearedDoc");

        // provide the traveller data
        final ReferralHit refHit = referralHitService.findById(referralHitCommand.getRefHitId());
        final ReferralTravellerRec traveller = referralHitService.getReferralHitTraveller(refHit.getWatlrId());

        url.append("&");
        url.append("forenames=" + traveller.getForename());
        url.append("&");
        url.append("lastName=" + traveller.getLastName());
        url.append("&");
        url.append("gender=" + traveller.getGender());
        url.append("&");
        url.append("nationality=" + traveller.getNationality());
        url.append("&");
        url.append("documentNumber=" + traveller.getDocNo());
        url.append("&");
        url.append("documentType=" + traveller.getDocType());
        url.append("&");
        url.append("birthDate=" + CalendarUtils.toStringDDMMYYYY(traveller.getBirthDate()));
        url.append("&");
        url.append("expiresDate=" + CalendarUtils.toStringDDMMYYYY(lookupDataService.getDefaultClearedDocumentExpiryDate()));

        return "redirect:" + url.toString();
    }


    public void setCommonsValidator(final DefaultBeanValidator commonsValidator) {
        this.commonsValidator = commonsValidator;
    }

    public DefaultBeanValidator getCommonsValidator() {
        return commonsValidator;
    }

    public void setReferralSearchValidator(final ReferralSearchValidator referralSearchValidator) {
        this.referralSearchValidator = referralSearchValidator;
    }

    public ReferralSearchValidator getReferralSearchValidator() {
        return referralSearchValidator;
    }

    public void setReferralCloseValidator(final ReferralCloseValidator referralCloseValidator) {
        this.referralCloseValidator = referralCloseValidator;
    }

    public ReferralCloseValidator getReferralCloseValidator() {
        return referralCloseValidator;
    }

    public void setReferralService(final ReferralService referralService) {
        this.referralService = referralService;
    }

    public ReferralService getReferralService() {
        return referralService;
    }

    public void setReferralStateService(final ReferralStateService referralStateService) {
        this.referralStateService = referralStateService;
    }

    public ReferralStateService getReferralStateService() {
        return referralStateService;
    }

    public void setReferralHitService(final ReferralHitService referralHitService) {
        this.referralHitService = referralHitService;
    }

    public ReferralHitService getReferralHitService() {
        return referralHitService;
    }

    public ReferenceDataService getReferenceDataService() {
        return referenceDataService;
    }

    public void setReferenceDataService(final ReferenceDataService referenceDataService) {
        this.referenceDataService = referenceDataService;
    }

    public LookupDataService getLookupDataService() {
        return lookupDataService;
    }

    public void setLookupDataService(final LookupDataService lookupDataService) {
        this.lookupDataService = lookupDataService;
    }

}
