<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${tableCommand.errorNumber == 1}">
        <label class="noMatch"><fmt:message key="too.many.records.returned" /></label>
    </c:when> 
    <c:when test="${tableCommand.errorNumber < 0}">
        <label class="noMatch"><fmt:message key="errors.there.has.been.an.error" />: <c:out value="${tableCommand.errorNumber}"/></label>
    </c:when>  
</c:choose>