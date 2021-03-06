<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<c:set var="appActionCodeErrors">
    <form:errors path="appActionCode"/>
</c:set>    

<c:choose>
    <c:when test="${not empty appActionCodeErrors}">
        <label class="formError" id="actionCodeErrors">
            <fmt:message key="app.action.code" /> <c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label>
            <fmt:message key="app.action.code" /> <c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>
    
<c:set var="disableList" value="${disableInputFields}" />
<c:if test="${readonlyInputFields == true}">
    <c:set var="disableList" value="${readonlyInputFields}" />
</c:if>

<form:select path="appActionCode" disabled="${disableList}">
       <form:option value="" label=""/>
       <form:options items="${appActionCodeList}" itemValue="code" itemLabel="description"/>
</form:select>