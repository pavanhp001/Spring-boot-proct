<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- toFromError is local variable to OR the to and from errors --%>
<c:set var="toFromError" value="false"/>
<spring:bind path="dateFrom">
    <c:if test="${status.error}">
        <c:set var="toFromError" value="true"/>
    </c:if>
</spring:bind>
<spring:bind path="dateTo">
    <c:if test="${status.error}">
        <c:set var="toFromError" value="true"/>
    </c:if>
</spring:bind>

<c:choose>
    <c:when test="${toFromError}">
        <label class="formError">
            <fmt:message key="daterange"/>
        </label>
    </c:when>
    <c:otherwise>
        <label class="helpinfolabel">
            <fmt:message key="daterange"/>
        </label>
    </c:otherwise>
</c:choose>
<spring:bind path="dateFrom">
    <input type="text" maxlength="10" size="10" name="dateFrom" id="dateFrom" value="<c:out value="${status.value}"/>" border="0">
</spring:bind>
<spring:bind path="dateTo">
    <input type="text" maxlength="10" size="10" name="dateTo" id="dateTo" value="<c:out value="${status.value}"/>">
</spring:bind>
