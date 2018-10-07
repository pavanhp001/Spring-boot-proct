<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<label for="commomLabelForTravelType" class="helpinfolabel">
    <fmt:message key="travel.type" />
</label>

<c:set var="normalTitle">
    <fmt:message key="travel.type.N"/>
</c:set>
<c:set var="unmatchedTitle">
    <fmt:message key="travel.type.U"/>
</c:set>
<c:set var="transferTitle">
    <fmt:message key="travel.type.X"/>
</c:set>
<c:set var="transitTitle">
    <fmt:message key="travel.type.T"/>
</c:set>


<div class="iconAndCheck" title="<fmt:message key="travel.type.N"/>" >
    <img src="/bcs/images/icons/Normal24.png" alt="<fmt:message key="travel.type.N"/>" />
    <form:checkbox path="travelTypeNormal" value="NORMAL" cssClass="commomLabelForTravelType" title="${normalTitle}"/>
</div>
<div class="iconAndCheck" title="<fmt:message key="travel.type.X"/>" >
    <img src="/bcs/images/icons/Transfer24.png" alt="<fmt:message key="travel.type.X"/>" />
    <form:checkbox path="travelTypeTransfer" value="TRANSFER" cssClass="commomLabelForTravelType" title="${transferTitle}"/>
</div>
<div class="iconAndCheck" title="<fmt:message key="travel.type.U"/>" >
    <c:choose>
        <c:when test="${requestContext.locale.language == 'ar'}">
            <img src="/bcs/images/icons/UnmatchedArb24.png" alt="<fmt:message key="travel.type.U"/>" />
        </c:when>
        <c:otherwise>
           <img src="/bcs/images/icons/Unmatched24.png" alt="<fmt:message key="travel.type.U"/>" />
        </c:otherwise>
    </c:choose>    
    <form:checkbox path="travelTypeUnmatched" value="UNMATCHED" cssClass="commomLabelForTravelType" title="${unmatchedTitle}" />
</div>
<div class="iconAndCheck" title="<fmt:message key="travel.type.T"/>" >
    <img src="/bcs/images/icons/Transit24.png" alt="<fmt:message key="travel.type.T"/>" />
    <form:checkbox path="travelTypeTransit" value="TRANSIT" cssClass="commomLabelForTravelType" title="${transitTitle}"/>
</div>
