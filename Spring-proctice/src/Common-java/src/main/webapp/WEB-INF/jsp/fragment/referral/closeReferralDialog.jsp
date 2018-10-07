<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%-- Usage:

     This div should be configured in the page JS as a jQuery UI dialog, using
     something along the lines of 
     
                    $('#closeDialog').dialog({...});
--%>

<div id="closeDialog" class="closeDialog" title="<fmt:message key="close.referral"/>" rel="">
    <form:form commandName="referralHitCommand" id="closeReferralForm" method="POST" action="iRiskReferralSearch.form">
        <%-- Need to know which referral this closure refers to; set up in JS when dialog is displayed --%>
        <input type="hidden" name="refId" />
        <input type="hidden" name="action" value="close" />
        <input type="hidden" name="referralVersion" value="${referral.updateVersioNo}" />
        <input type="hidden" name="totalHits" value="${referral.totalHits}" />
        <input type="hidden" name="actionCode" value="" />
        <input type="hidden" name="additionalInstructionCode" value="" />
        <input type="hidden" name="closeOnSave" value="false" />
        
        
        <%--This form may also submit hit data as well, where referral hits are
            ruled in or out. These form fields are added in dynamically by
            javascript when required --%>
        
        <span ><fmt:message key="close.referral.reason"/>:</span>
        <br/> 
        <form:select id="closureReason" path="${closureReason}" name="closureReason" cssStyle=" margin-left: 0px; width: auto;">
            <form:options items="${referralClosureReasonList}" itemValue="code" itemLabel="description"/>
        </form:select>
        <br/>
        <span ><fmt:message key="referral.notes"/>:</span>
        <br/>
        <form:textarea path="${closureNote}" id="closureNote" style="width: 95%;" class="popUpCloseNote" rows="5" name="closureNote"></form:textarea><br/>
        <input type="submit" class="closeConfirm button" id="closeConfirm" value="<fmt:message key="button.confirm"/>" />
        <input type="button" class="closeCancel button" id="closeCancel"  value="<fmt:message key="button.cancel"/>" />
    </form:form>
</div>