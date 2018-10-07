<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tr class="tabBorder"
    <c:if test="${empty referralHitCommand.expandedReferrals[referral.referralNum]}">
        style="display: none"
    </c:if>
>

<td  colspan="24">
<table class="hits-table" id="hitTable${referral.referralNum}" width="100%" border="0" cellpadding="0" cellspacing="0">
<%-- Headings for hits table --%>
<tr class="hitSummaryHeader" rel="Per<c:out value="${rowCount}" />">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="hitNumHeader"><fmt:message key="hit.number" /></td>    
    <td class="matchHeader"><fmt:message key="referral.match" /></td>       
    <td class="severityHeader"><fmt:message key="referral.severity" /></td>    
    <td><fmt:message key="referral.priority" /></td>
    <td><fmt:message key="hit.type" />  </td>
    <td><fmt:message key="hit.description" /></td>
    <td><fmt:message key="recommended.action" /></td>
    <td><fmt:message key="reason" /></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="topRadio">
        <c:if test="${referral.referralStatus eq 'CLOSED'}">
            <label><fmt:message key="ruled.in" /></label>
        </c:if>
        <c:if test="${referral.referralStatus ne 'CLOSED'}">
            <u:checkRole roleGroup="REFERRAL_WRITE">
                <a href="#" class="ruleAllInButton" name="allCheck1" id="CheckAllP1" onclick="return false;"><fmt:message key="ruled.in" /></a>
            </u:checkRole>
            <u:checkRole roleGroup="REFERRAL_WRITE" not="true">
                <label><fmt:message key="ruled.in" /></label>
            </u:checkRole>
        </c:if>
    </td>
    <td class="topRadio">
        <c:if test="${referral.referralStatus eq 'CLOSED'}">
            <label><fmt:message key="ruled.out" /></label>
        </c:if>
        <c:if test="${referral.referralStatus ne 'CLOSED'}">
            <u:checkRole roleGroup="REFERRAL_WRITE">
                <a href="#" class="ruleAllOutButton" name="allCheck2" id="CheckAllP2" onclick="return false;"><fmt:message key="ruled.out" /></a>
            </u:checkRole>
            <u:checkRole roleGroup="REFERRAL_WRITE" not="true">
                <label><fmt:message key="ruled.out" /></label>
            </u:checkRole>
        </c:if>
    </td>
    <td>&nbsp;</td>
</tr>

<%-- Now loop through hits for current referral --%>
<c:forEach var="referralHit" items="${referral.referralHits}" varStatus="referralResultRow">

    <c:set var="referralRowCount" value="${referralResultRow.count}" scope="request" />

    <tr class="hitSummary" 
        rel="Per<c:out
        value="${rowCount}" />" 
        id="<c:out value="${referralHit.hitId}"/>"  
        data-targetId="${referralHit.targetWatchlistId}"
        data-refId="${referralHit.referralNum}" 
        >
    
        <input type="hidden" name="referralHitVersions[${referralHit.hitId}]" value="${referralHit.updateVersionNo}" />
        <input type="hidden" name="recommendedAction<c:out value="${referral.referralNum}" />" value="${referral.recommendedAction}" />
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">&nbsp;</td>
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">&nbsp;</td>
        <td class="referralHitData hitNumTd" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <c:out default="" value="${referralHit.hitId}" />
        </td>
        <td class="referralHitData matchTd" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <fmt:formatNumber type="number" maxFractionDigits="0" value="${referralHit.score}" /><fmt:message key="percent" />
        </td>
        <td class="referralHitData severityTd" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <div class="severityDiv severityLevel-${referralHit.severity}"> <c:out default="" value="${referralHit.severity}" /> </div>  
        </td>
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <c:out default="" value="${referralHit.priority}" />
        </td>

        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <fmt:message key="hit.type.${referralHit.hitType}" />
        </td>
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <c:out default="" value="${referralHit.watchListName}" />
        </td>
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <c:out default=" " value="${referralHit.recommendedActionDesc}" />
        </td>
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <c:out default="" value="${referralHit.reasonDesc} " />
        </td>        
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">&nbsp;</td>
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">&nbsp;</td>
        <td class="referralHitData" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">&nbsp;</td>
        <td class="ruleincheckbox" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <c:set var="inValue" value="" />
            <c:if test="${referralHit.qualificationStatus=='IN'}">
                  <c:set var="inValue" value="checked" />
            </c:if>
            <input type="radio" class="rule-radio-in" name="hitRule[${referralHit.hitId}]" data-originalvalue="<c:out value="${inValue}" />" value="IN" <c:out value="${inValue}" />  
                <c:if test="${referral.referralStatus eq 'CLOSED'}">
                    disabled
                </c:if>
                <c:if test="${referral.referralStatus ne 'CLOSED'}">
                    <u:checkRole roleGroup="REFERRAL_WRITE" not="true">
                        disabled
                    </u:checkRole>
                </c:if>
            />
        </td>
        <td class="ruleoutcheckbox" rel="Per<c:out value="${rowCount}" />Rec<c:out value="${referralRowCount}" />">
            <c:set var="outValue" value="" />
            <c:if test="${referralHit.qualificationStatus=='OUT'}">
                  <c:set var="outValue" value="checked" />
            </c:if>
            <input type="radio" class="rule-radio-out" name="hitRule[${referralHit.hitId}]" data-originalvalue="<c:out value="${outValue}" />" value="OUT" <c:out value="${outValue}" /> 
                <c:if test="${referral.referralStatus eq 'CLOSED'}">
                    disabled
                </c:if>
                <c:if test="${referral.referralStatus ne 'CLOSED'}">
                    <u:checkRole roleGroup="REFERRAL_WRITE" not="true">
                        disabled
                    </u:checkRole>
                </c:if>         
            />
        </td>
        <td>
            <c:if test="${referralHit.targetWatchlistId > 0}">
                <u:checkRole roleGroup="CLEARED_DOC_WRITE">
                    <u:checkRole roleGroup="REFERRAL_WRITE">
                        <input type="image" 
                               class="add-cleared-doc-button iconButton" 
                               name="clearDoc${referralHit.targetWatchlistId}" 
                               id="clearDocBtn${referralHit.targetWatchlistId}" 
                               src="/bcs/images/icons/AddClearedDoc24.png"
                               title="<fmt:message key="button.add.cleared.document"/>" 
                         />
                        
                    </u:checkRole>   
                </u:checkRole>
            </c:if>
        </td>
    </tr>
</c:forEach>

<%-- Attrs with a 'data-' prefix are custom attributes. These aren't strictly valid for HTML 4.0 doctypes (but are for XHTML 
     providing you extend the DTD), and so will throw errors in an HTML validator), but all browsers support custom data on DOM 
     nodes (and in point of fact we were inadvertantly getting away with this with our use of the "rel" attr on <tr>/<td> elements 
     in earlier incarnations of the page.) This approach is preferable to loading the page with hidden fields, particularly if such
     data is only being used in JS and not for any kind of form submission. Additionally, the forthcoming HTML5 formalises this 
     providing custom attrs are prefixed with a 'data-' prefix as we have done here.
     
     jQuery.data() allows you to get or set custom attributes on elements (no need for the 'data-' prefix when using the method);
     see CommonReferral.js for examples. --%>
<tr class="referralLog" rel="Per<c:out value="${rowCount}" />" id="<c:out value="${referral.referralNum}" />" data-version="<c:out value="${referral.updateVersioNo}" />"  data-totalhits="<c:out value="${referral.totalHits}" />" data-oldaction="<c:out value="${referral.recommendedAction}" />" >

    <td colspan="18" />
        <table class="referralLogTable">
            <tr>
                <td class="referralHitData logSummary" />
                    <fmt:formatDate type="both" pattern="${dateTimePattern}" value="${referral.latestReferralLog.createdDatetime.time}"/>&nbsp;-&nbsp;
                    <fmt:message key="referral.log.event.${referral.latestReferralLog.eventType}"/>&nbsp;-&nbsp;
                    <c:out value="${referral.latestReferralLog.createdBy}" />
                </td>
                <td class="Ctrls">
                    <c:set var="fieldDisabled" value="" />
                    <c:if test="${referral.referralStatus eq 'CLOSED'}">
                        <c:set var="fieldDisabled" value="disabled" />
                    </c:if>
                    <u:checkRole roleGroup="REFERRAL_WRITE" not="true">
                        <c:set var="fieldDisabled" value="disabled" />
                    </u:checkRole>
            
                    <!-- SAVE -->
                    <button type="button" class="save-button referralButton" name="save" <c:out value="${fieldDisabled}" /> >
                        <div title="<fmt:message key="button.save" />">
                            <img src="/bcs/images/icons/Save32<c:out value="${fieldDisabled}" />.png" />
                        </div>
                    </button>
            
                    <!-- RESET -->
                    <button type="button" class="reset-button referralButton" name="reset" <c:out value="${fieldDisabled}" /> >
                        <div title="<fmt:message key="button.reset.all" />">
                            <img src="/bcs/images/icons/Refresh32<c:out value="${fieldDisabled}" />.png"/>
                        </div>
                    </button>        
            
                    <!-- CLOSE --> 
                    <button type="button" class="close-button referralButton" name="close" <c:out value="${fieldDisabled}" /> >
                        <div title="<fmt:message key="button.close" />">
                            <img src="/bcs/images/icons/Close32<c:out value="${fieldDisabled}" />.png"/>
                        </div>
                    </button>        
            
                    <!-- ADDNOTE -->
                    <%-- Always enabled as part of QAT-397 unless user doesn't have REFERRAL_WRITE perms --%>
                    <c:set var="fieldDisabled" value="" />
                    <u:checkRole roleGroup="REFERRAL_WRITE" not="true">
                        <c:set var="fieldDisabled" value="disabled" />
                    </u:checkRole>
                    
                    <button type="button" class="addnote-button referralButton" name="note" <c:out value="${fieldDisabled}" /> >
                        <div title="<fmt:message key="button.add.note" />">
                            <img src="/bcs/images/icons/AddNote32<c:out value="${fieldDisabled}" />.png"/>
                        </div>
                    </button>        
                    
                    <!-- OPEN (formally known as ACKNOWLEDGE) -->
                    <c:set var="fieldDisabled" value="" />
                    <u:checkRole roleGroup="REFERRAL_INTERVENE" not="true">
                        <c:set var="fieldDisabled" value="disabled" />
                    </u:checkRole>
                    <c:if test="${referral.referralStatus ne 'NEW'}">
                        <c:set var="fieldDisabled" value="disabled" />
                    </c:if>
                        
                    <button type="button" class="acknowledge-button referralButton" name="acknowledge" id="acknowledge" <c:out value="${fieldDisabled}" /> >
                        <div title="<fmt:message key="referral.status.OPEN" />">
                            <img src="/bcs/images/icons/Open32<c:out value="${fieldDisabled}" />.png"/>
                        </div>
                    </button>        
                    
                    <!--  ACTION CODE SELECT DROPDOWN -->
                    <select name="actionCode" class="recommendedActions" data-originalvalue="<c:out value="${referral.recommendedAction}" />" 
                    <c:if test="${referral.referralStatus eq 'CLOSED'}">
                        disabled
                    </c:if>
                    >
                        <c:forEach var="recommendedAction" items="${recommendedActionListVisible}">
                            <option value="${recommendedAction.code}"
                                <c:if test="${recommendedAction.code == referral.recommendedAction}"> selected </c:if>
                            >${recommendedAction.description}</option>
                        </c:forEach>
                    </select>
                    
                    <c:if test="${exportMovementEnabled == true}">
                    <select name="additionalInstructionCode" class="additionalInstruction" data-originalvalue="<c:out value="${referral.additionalInstructionCode}" />" 
                    <c:if test="${referral.referralStatus eq 'CLOSED'}">
                        disabled
                    </c:if>
                    >
                        <option value=""></option>
                        <c:forEach var="additionalInstructionCode" items="${additionalInstructionList}">
                            <option value="${additionalInstructionCode.code}"   
                            <c:if test="${additionalInstructionCode.code == referral.additionalInstructionCode}"> selected </c:if>                             
                            >${additionalInstructionCode.description}</option>
                        </c:forEach>
                    </select>
                    </c:if> 
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</td>
</tr>