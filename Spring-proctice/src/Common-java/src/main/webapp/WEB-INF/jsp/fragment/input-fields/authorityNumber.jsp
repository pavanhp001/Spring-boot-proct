<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 

   <c:set var="authorityNumberErrors"><form:errors path="authorityNumber"/></c:set>    
    <c:choose>
        <c:when test="${not empty authorityNumberErrors || not empty combinationErrors}">
            <label class="formError">
                 <fmt:message key="override.authority" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                 <fmt:message key="override.authority" /> <c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
    
     <form:input path="authorityNumber" disabled="${disableItem}" onchange="this.value = this.value.toUpperCase()" size="3" maxlength="3" />

