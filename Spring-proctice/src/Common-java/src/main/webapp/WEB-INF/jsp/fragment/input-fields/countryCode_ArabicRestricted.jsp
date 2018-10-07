<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
    <c:set var="countryCodeErrors"><form:errors path="countryCode"/></c:set>    
    <c:choose>
        <c:when test="${not empty countryCodeErrors}">
            <label class="formError">
                <fmt:message key="country.code" />
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                <fmt:message key="country.code" />
             </label>
        </c:otherwise>
    </c:choose>
    
    <form:input path="countryCode" maxlength="3" size="14" onchange="this.value = this.value.toUpperCase()"/>
            
              