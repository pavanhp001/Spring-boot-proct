<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
                            
<c:set var="arrivalAirportErrors"><form:errors path="arrivalAirport"/></c:set>    
<c:choose>
    <c:when test="${not empty arrivalAirportErrors || not empty depArrAptErrors}">
        <label for="arrivalAirport" class="formError">
            <fmt:message key="airport.arrival" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label for="arrivalAirport" class="helpinfolabel">
            <fmt:message key="airport.arrival" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>
<c:set var="disableItem" value="${disableRecurrenceTransField}" />
<div class="airportControl">
    <form:input path="arrivalAirport" maxlength="4" size="4" onkeypress="return Common.isAlphabetic(event)" onchange="this.value = this.value.toUpperCase()" disabled="${disableItem}"/>
    <c:choose>
        <c:when test="${disableRecurrenceTransField eq true}">
            <button type="button" class="iconButton" disabled="disabled">
                <img name="arrivalAirportSearch" src="/bcs/images/icons/Airport24.png"/>
            </button>
            
        </c:when>
        <c:otherwise>
            <button type="button" class="iconButton" id="btnArrAirport" onClick="window.open('/ets/airportSearch.form?freeText=' + document.getElementById('arrivalAirport').value +
                                '&destElement=arrivalAirport',
                                '_blank',
                                'location=no,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=950,height=767');">
                <img name="arrivalAirportSearch" src="/bcs/images/icons/Airport24.png"/>
            </button>
        </c:otherwise>
    </c:choose>
</div>