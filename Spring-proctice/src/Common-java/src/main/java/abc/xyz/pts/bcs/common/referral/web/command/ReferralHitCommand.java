/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2011
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.web.command;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroup;
import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroups;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;

/**
 * @author andy taylor
 */

@AuditableCommand(Event.REFERRAL_SAVE)
@AuditPropertyGroups(auditWhenNoGroupMatch = false, value = {
    @AuditPropertyGroup(name = Event.REFERRAL_CLOSE, groupProperty = "action", propertyValue = "Close"),
    @AuditPropertyGroup(name = Event.REFERRAL_ACKNOWLEDGE, groupProperty = "action", propertyValue = "Acknowledge"),
    @AuditPropertyGroup(name = Event.REFERRAL_SAVE, groupProperty = "action", propertyValue = "Save") })
public class ReferralHitCommand implements Serializable {

    private static final long serialVersionUID = 3291908881390006837L;

    @AuditableBeanProperty(key = Parameter.REFERRAL_NUMBER)
    private Long refId;

    private List<ReferralHitTargetRec> targetList;
    private String actionCode;
    private Map<Long, String> hitRule;  // (Hit Id, RI/RO) every hit (ri or ro) - unknown will not appear in this
    private String oldRecommendedAction;
    private String closureReason;
    private String closureNote;
    private boolean closeOnSave;
    private String addedNote;
    private Long referralVersion;
    private Map<Long, Long> referralHitVersions;
    private String action;
    private Long totalHits;
    private Map<Long, Long> expandedReferrals;
    private String additionalInstructionCode;
    private String additionalInstructionDesc;

    private Long refHitId;   // optionally capture the referralHitId
    private Long targetId;

    public List<ReferralHitTargetRec> getTargetList() {
        return targetList;
    }

    public void setTargetList(final List<ReferralHitTargetRec> targetList) {
        this.targetList = targetList;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(final Long refId) {
        this.refId = refId;
    }


    public String getOldRecommendedAction() {
        return oldRecommendedAction;
    }

    public void setOldRecommendedAction(final String oldRecommendedAction) {
        this.oldRecommendedAction = oldRecommendedAction;
    }

    public Map<Long, String> getHitRule() {
        return hitRule;
    }

    public void setHitRule(final Map<Long, String> hitRule) {
        this.hitRule = hitRule;
    }

    public boolean hasAnyRuledInHit() {
        if (hitRule != null)
        {
            for (final Long hitId : hitRule.keySet())
            {
                if (Constants.HIT_IN.equalsIgnoreCase(hitRule.get(hitId))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getClosureReason() {
        return closureReason;
    }

    public void setClosureReason(final String closureReason) {
        this.closureReason = closureReason;
    }

    public boolean isCloseOnSave() {
        return closeOnSave;
    }

    public void setCloseOnSave(final boolean closeOnSave) {
        this.closeOnSave = closeOnSave;
    }

    public String getClosureNote() {
        return closureNote;
    }

    public void setClosureNote(final String closureNote) {
        this.closureNote = closureNote;
    }

    public String getAddedNote() {
        return addedNote;
    }

    public void setAddedNote(final String addedNote) {
        this.addedNote = addedNote;
    }

    public Map<Long, Long> getReferralHitVersions() {
        return referralHitVersions;
    }

    public void setReferralHitVersions(final Map<Long, Long> referralHitVersions) {
        this.referralHitVersions = referralHitVersions;
    }

    public Long getReferralVersion()
    {
        return referralVersion;
    }

    public void setReferralVersion(final Long referralVersion)
    {
        this.referralVersion = referralVersion;
    }

    public String getActionCode()
    {
        return actionCode;
    }

    public void setActionCode(final String actionCode)
    {
        this.actionCode = actionCode;
    }


    public String getAction()
    {
        return action;
    }

    public void setAction(final String action)
    {
        this.action = action;
    }

    public Long getTotalHits()
    {
        return totalHits;
    }

    public void setTotalHits(final Long totalHits)
    {
        this.totalHits = totalHits;
    }

    public Map<Long, Long> getExpandedReferrals() {
        return expandedReferrals;
    }

    public void setExpandedReferrals(final Map<Long,Long> expandedReferrals) {
        this.expandedReferrals = expandedReferrals;
    }

    public Long getRefHitId()
    {
        return refHitId;
    }

    public void setRefHitId(final Long refHitId)
    {
        this.refHitId = refHitId;
    }

    public Long getTargetId()
    {
        return targetId;
    }

    public void setTargetId(final Long targetId)
    {
        this.targetId = targetId;
    }

    public String getAdditionalInstructionCode() {
        return additionalInstructionCode;
    }

    public void setAdditionalInstructionCode(final String additionalInstructionCode) {
        this.additionalInstructionCode = additionalInstructionCode;
    }

    public String getAdditionalInstructionDesc() {
        return additionalInstructionDesc;
    }

    public void setAdditionalInstructionDesc(final String additionalInstructionDesc) {
        this.additionalInstructionDesc = additionalInstructionDesc;
    }


    @Override
    public String toString() {
        return "ReferralHitCommand [refId=" + refId + ", targetList=" + targetList + ", actionCode=" + actionCode
                + ", hitRule=" + hitRule + ", oldRecommendedAction=" + oldRecommendedAction
                + ", closureReason=" + closureReason + ", closureNote=" + closureNote + ", closeOnSave=" + closeOnSave
                + ", addedNote=" + addedNote + ", referralVersion=" + referralVersion
                + ", referralHitVersions=" + referralHitVersions + ", action=" + action + "]";
    }

}
