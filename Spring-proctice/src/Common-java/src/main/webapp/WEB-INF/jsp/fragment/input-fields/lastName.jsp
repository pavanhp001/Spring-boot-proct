<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
    
   <c:set var="lastNameErrors"><form:errors path="lastName"/></c:set>    
    <c:choose>
        <c:when test="${not empty lastNameErrors || not empty combinationErrors}">
            <label class="formError">
            <fmt:message key="traveller.name.last" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
            <fmt:message key="traveller.name.last" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
    
     <form:input path="lastName" maxlength="80" size="30" disabled="${disableItem}"
           onchange="this.value = this.value.toUpperCase()"/>
