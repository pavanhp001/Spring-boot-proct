<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    

   <c:set var="flightErrors"><form:errors path="flight*"/></c:set>    
    <c:choose>
        <c:when test="${not empty flightErrors}">
            <label for="commomLabelForFlightNumber" class="formError">
                <fmt:message key="flight.carrier.and.number" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label for="commomLabelForFlightNumber" class="helpinfolabel">
                <fmt:message key="flight.carrier.and.number" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
    <c:set var="disableItem" value="${disableRecurrenceTransField}" />
    <div class="flightInputFields">
     <form:input path="flightCarrierCode" maxlength="3" size="3" 
     onkeyup="checkForMoveFocus('flightCarrierCode', 'flightNumber', 3, event);" onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()"
     cssClass="commomLabelForFlightNumber" disabled="${disableItem}"/>

     <form:input path="flightNumber" maxlength="5" size="5" 
     onkeypress="return isASCII(event)" onchange="this.value = flightLeftpadZeros(this.value.toUpperCase())" cssClass="commomLabelForFlightNumber" disabled="${disableItem}"/>
    </div>