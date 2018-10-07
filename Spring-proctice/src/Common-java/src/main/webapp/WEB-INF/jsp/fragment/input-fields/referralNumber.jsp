<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    

<c:set var="referralNumberError"><form:errors path="referralNumber"/></c:set>    
<c:choose>
    <c:when test="${not empty referralNumberError}">
        <label for="referralNumber" class="formError">
            <fmt:message key="referral.number" />
        </label>
    </c:when>
    <c:otherwise>
        <label for="referralNumber" class="helpinfolabel">
            <fmt:message key="referral.number" />
        </label>
    </c:otherwise>
</c:choose>    

<form:input path="referralNumber" maxlength="19" size="19" 
    onkeypress="return isASCII(event)" 
    onchange="this.value = this.value.toUpperCase()"
    /> 

          