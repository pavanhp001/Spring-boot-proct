<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--Dedicated form for saving a referral's state, including hit changes,
    acknowledgements etc.
    
    Form values are filled in via client-side JS, and additionally, if hits are
    being ruled in or out, the JS also makes sure the the relevant input radio 
    button elements are copied into this form prior to submission. --%>
<%--${pageContext.request.contextPath} --%>
<form:form commandName="referralHitCommand" cssClass="refForm" id="saveReferralForm" method="POST" action="iRiskReferralSearch.form">
    <%-- No id for refId, as it's used in multiple forms --%>
    <input type="hidden" name="refId" id="refId" value="" />
    <input type="hidden" name="refHitId" id="refHitId" value="" />
    <input type="hidden" name="targetId" id="targetId" value="" />
    <input type="hidden" id="action" name="action" value="" />
    <input type="hidden" id="oldRecommendedAction" name="oldRecommendedAction" value="" />
    <%-- <input type="hidden" name="addNote" id="addNote" value="addNote" />
    <input type="hidden" name="addedNote" id="addedNote" value="addedNote" /> --%>
    <input type="hidden" id="referralVersion" name="referralVersion" value="" />
    <input type="hidden" id="totalHits" name="totalHits" value="" />
    <input type="hidden" id="actionCode" name="actionCode" value="" />
    <input type="hidden" id="additionalInstructionCode" name="additionalInstructionCode" value="" />
 </form:form>