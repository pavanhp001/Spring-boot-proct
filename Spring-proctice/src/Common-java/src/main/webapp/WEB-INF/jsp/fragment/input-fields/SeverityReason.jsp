<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

   <c:set var="severityReasonErrors"><form:errors path="severityReason"/></c:set>    
    <c:choose>
        <c:when test="${not empty severityReasonErrors}">
            <label class="formError" id="severityReasonErrors">
                 <fmt:message key="reason" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                <fmt:message key="reason" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableList" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableList" value="${readonlyInputFields}" />
    </c:if>
    
     <form:select path="severityReason" disabled="${disableList}">
            <form:option value="" label=""/>
            <form:options items="${severityReasonList}" itemValue="code" itemLabel="descriptionWithSeverity"/>
     </form:select>