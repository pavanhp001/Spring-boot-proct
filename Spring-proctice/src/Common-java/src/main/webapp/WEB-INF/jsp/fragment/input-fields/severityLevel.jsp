<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="severityErrors">
    <form:errors path="severityLevel"/>
</c:set>  

<c:choose>
    <c:when test="${not empty severityErrors}">
        <label class="formError" id="severityErrors">
            <fmt:message key="severity" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label>
            <fmt:message key="severity" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>

<form:select path="severityLevel" disabled="${readonlyInputFields}">
    <form:option value="" label=""/>
    <form:options items="${severityList}" />
</form:select>
