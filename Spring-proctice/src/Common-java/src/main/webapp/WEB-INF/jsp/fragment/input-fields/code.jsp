<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 

   <c:set var="codeErrors"><form:errors path="code"/></c:set>    
    <c:choose>
        <c:when test="${not empty codeErrors || not empty combinationErrors}">
            <label class="formError" id="codeErrors">
                 <fmt:message key="code" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label>
                 <fmt:message key="code" /> <c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields || readonlyPrimaryField}">
        <c:set var="disableItem" value="true" />
    </c:if>
    
     <form:input path="code" maxlength="10" size="10" disabled="${disableItem}"
         onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()"/>

