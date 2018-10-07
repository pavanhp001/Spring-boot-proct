<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="cityNameErrors"><form:errors path="cityName"/></c:set>    
   <c:choose>
       <c:when test="${not empty cityNameErrors}">
           <label class="formError">
             <fmt:message key="city.name" />
        </label>
    </c:when>
    <c:otherwise>
        <label class="helpinfolabel">
        <fmt:message key="city.name" />
        </label>
    </c:otherwise>
</c:choose>

<form:input path="cityName" onchange="this.value = this.value.toUpperCase()"/>

     