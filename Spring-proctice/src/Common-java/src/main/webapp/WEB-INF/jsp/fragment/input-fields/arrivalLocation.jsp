<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 

   <c:set var="arrivalLocationErrors"><form:errors path="arrivalLocation"/></c:set>    
    <c:choose>
        <c:when test="${not empty arrivalLocationErrors || not empty combinationErrors}">
            <label class="formError">
                 <fmt:message key="arrival.location" />
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                 <fmt:message key="arrival.location" />
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
    
     <form:input path="arrivalLocation" maxlength="35" size="30" disabled="${disableItem}"
        onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()"/>

