<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
    <c:set var="airportCodeErrors"><form:errors path="airportCode"/></c:set>    
    <c:choose>
        <c:when test="${not empty airportCodeErrors}">
            <label class="formError">
                <fmt:message key="airport.code" />
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                <fmt:message key="airport.code" />
             </label>
        </c:otherwise>
    </c:choose>
    
    <form:input path="airportCode" maxlength="4" size="14" onchange="this.value = this.value.toUpperCase()"/>
