<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    

   <c:set var="birthPlaceErrors"><form:errors path="birthPlace"/></c:set>    
    <c:choose>
        <c:when test="${not empty birthPlaceErrors}">
            <label class="formError" id="birthPlaceErrors">
                <fmt:message key="traveller.place.of.birth" />
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                <fmt:message key="traveller.place.of.birth" />
            </label>
        </c:otherwise>
    </c:choose> 
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
    
     <form:input path="birthPlace" size="36" maxlength="80" disabled="${disableItem}"
             onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()"/>