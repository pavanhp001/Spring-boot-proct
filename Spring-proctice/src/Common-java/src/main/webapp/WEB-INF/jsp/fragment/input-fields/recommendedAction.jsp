<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

   <c:set var="recommendedActionErrors"><form:errors path="recommendedAction"/></c:set>    
    <c:choose>
        <c:when test="${not empty recommendedActionErrors}">            
        <label class="formError" id="recommendedActionErrors">
                 <fmt:message key="recommended.action" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                <fmt:message key="recommended.action" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableList" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableList" value="${readonlyInputFields}" />
    </c:if>

     <form:select path="recommendedAction" disabled="${disableList}">
            <form:option value="" label=""/>
            <form:options items="${recommendedActionListVisible}" itemValue="code" itemLabel="description"/>
     </form:select>
