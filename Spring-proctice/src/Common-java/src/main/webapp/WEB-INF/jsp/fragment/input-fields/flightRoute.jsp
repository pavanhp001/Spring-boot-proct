<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
    <c:set var="routeErrors"><form:errors path="route"/></c:set>    
    <c:choose>
        <c:when test="${not empty routeErrors}">
            <label  for="route" class="formError">
                <fmt:message key="flight.route" />
            </label>
        </c:when>
        <c:otherwise>
            <label  for="route" class="helpinfolabel">
                <fmt:message key="flight.route" />
             </label>
        </c:otherwise>
    </c:choose>
    
    <form:input path="route" maxlength="14" size="14" onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()"/>
            
              