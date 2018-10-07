<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
        
<c:set var="departureAirportErrors"><form:errors path="departureAirport"/></c:set>    
<c:choose>
    <c:when test="${not empty departureAirportErrors || not empty depArrAptErrors}">
        <label for="departureAirport" class="formError">
            <fmt:message key="airport.departure" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label for="departureAirport" class="helpinfolabel">
            <fmt:message key="airport.departure" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>
<c:set var="disableItem" value="${disableRecurrenceTransField}" />


<div class="airportControl">
    <form:input path="departureAirport" maxlength="4" size="4" onkeypress="return Common.isAlphabetic(event)" onchange="this.value = this.value.toUpperCase()" disabled="${disableItem}"/>
    <c:choose>
        <c:when test="${disableRecurrenceTransField eq true}">
            <button type="button" class="iconButton" disabled="disabled">
                <img name="departureAirportSearch" src="/bcs/images/icons/Airport24.png"/>
            </button>
            
        </c:when>
        <c:otherwise>
            <button type="button" class="iconButton" id="btnDepAirport" onClick="window.open('/ets/airportSearch.form?freeText=' + document.getElementById('departureAirport').value +
                            '&destElement=departureAirport',
                            '_blank',
                            'location=no,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=950,height=767');">
                <img name="departureAirportSearch" src="/bcs/images/icons/Airport24.png"/>
            </button>
        </c:otherwise>
    </c:choose>
</div>

