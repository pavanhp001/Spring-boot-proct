<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- toFromError is local variable to OR the to and from errors --%>
<c:set var="toFromTimeError" value="false"/>
<spring:bind path="timeFrom">
    <c:if test="${status.error}">
        <c:set var="toFromTimeError" value="true"/>
    </c:if>
</spring:bind>
<spring:bind path="timeTo">
    <c:if test="${status.error}">
        <c:set var="toFromTimeError" value="true"/>
    </c:if>
</spring:bind>

<c:choose>
    <c:when test="${toFromTimeError}">
        <label class="formError" id="timeRangeError">
            <fmt:message key="time.range" />*
        </label>
    </c:when>
    <c:otherwise>
        <label class="helpinfolabel">
            <fmt:message key="time.range" />
        </label>
    </c:otherwise>
</c:choose>
<spring:bind path="timeFrom">
    <input type="text" maxlength="10" size="10" name="timeFrom" id="timeFrom" value="<c:out value="${status.value}"/>" border="0">
</spring:bind>
<spring:bind path="timeTo">
    <input type="text" maxlength="10" size="10" name="timeTo" id="timeTo" value="<c:out value="${status.value}"/>">
</spring:bind>
