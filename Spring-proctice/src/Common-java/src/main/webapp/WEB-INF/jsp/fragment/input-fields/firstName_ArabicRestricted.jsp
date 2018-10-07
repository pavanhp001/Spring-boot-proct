<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
      <c:set var="forenamesErrors"><form:errors path="forenames"/></c:set>    
    <c:choose>
        <c:when test="${not empty forenamesErrors}">
            <label for="forenames" class="formError">
                 <fmt:message key="traveller.name.fore" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label for="forenames" class="helpinfolabel">
                <fmt:message key="traveller.name.fore" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>        
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
    
     <form:input path="forenames" maxlength="80" size="30" disabled="${disableItem}" 
            onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()"/>
 