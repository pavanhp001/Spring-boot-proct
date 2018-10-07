<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="airportNameErrors"><form:errors path="airportName"/></c:set>    
   <c:choose>
       <c:when test="${not empty airportNameErrors}">
           <label class="formError">
             <fmt:message key="airport.name" />
        </label>
    </c:when>
    <c:otherwise>
        <label class="helpinfolabel">
        <fmt:message key="airport.name" />
        </label>
    </c:otherwise>
</c:choose>

<form:input path="airportName" onchange="this.value = this.value.toUpperCase()"/>

