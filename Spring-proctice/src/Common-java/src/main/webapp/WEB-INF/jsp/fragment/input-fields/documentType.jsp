<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<c:set var="documentTypeErrors">
    <form:errors path="documentType"/>
</c:set>    

<c:choose>
    <c:when test="${not empty documentTypeErrors || not empty combinationErrors}">
        <label class="formError">
            <fmt:message key="traveller.document.type" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label class="helpinfolabel">
            <fmt:message key="traveller.document.type" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>    

<c:set var="disableList" value="${disableInputFields}" />
<c:if test="${readonlyInputFields == true}">
    <c:set var="disableList" value="${readonlyInputFields}" />
</c:if>

<form:select path="documentType" disabled="${disableList}">
    <form:option value="" label=""/>
    <c:forEach var="dt" items="${documentTypeList}">
        <form:option value="${dt}" >
            <fmt:message key="document.type.${dt}"/>
        </form:option>
    </c:forEach>     
</form:select>

