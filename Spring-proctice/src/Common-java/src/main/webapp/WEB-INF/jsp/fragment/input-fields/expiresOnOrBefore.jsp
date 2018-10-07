<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

    <c:set var="validUntilErrors"><form:errors path="validUntil"/></c:set>    
    <c:choose>
        <c:when test="${not empty validUntilErrors}">
            <label class="formError" id="validUntilErrors">
                <fmt:message key="expires.on.or.before" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                <fmt:message key="expires.on.or.before" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
    
    <div class="ARcalIcon1">
         <form:input path="validUntil" maxlength="10" size="10" disabled="${disableItem}"/>
       </div>