<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    

<c:set var="arrivalTimeRangeErrors"><form:errors path="arrivalTimeRange*"/></c:set>    

    <c:choose>
        <c:when test="${not empty arrivalTimeRangeErrors}">
            <label for="commomLabelForArrivalTimeRange" class="formError" id="arrivalTimeRangeErrors">
                <fmt:message key="timerange.arrival" />
            </label>
        </c:when>
        <c:otherwise>
            <label for="commomLabelForArrivalTimeRange" class="helpinfolabel">
                <fmt:message key="timerange.arrival" />
            </label>
        </c:otherwise>
    </c:choose>
    
    <form:input onkeypress="return isASCII(event)" path="arrivalTimeRangeFrom" maxlength="5" size="5" cssClass="commomLabelForArrivalTimeRange"/>
    <form:input onkeypress="return isASCII(event)" path="arrivalTimeRangeTo" maxlength="5" size="5" cssClass="commomLabelForArrivalTimeRange"/>
