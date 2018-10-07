 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="selectedTimeIntervalErrors">
    <form:errors path="selectedTimeInterval" />
</c:set>

<c:choose>
    <c:when test="${not empty timeIntervalErrors}">
        <label class="formError"> <fmt:message key="time.interval" />
        </label>
    </c:when>
    <c:otherwise>
        <label> <fmt:message key="time.interval" />
        </label>
    </c:otherwise>
</c:choose>

<form:select path="selectedTimeInterval" items="${timeIntervalList}"
    itemValue="code" itemLabel="description" multiple="multiple" size="7" />
