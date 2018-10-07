<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
    <c:set var="isMandatory"><%= request.getParameter("isMandatory") %></c:set>
    <c:set var="genderErrors"><form:errors path="gender"/></c:set>    
    
    <c:choose>
        <c:when test="${not empty genderErrors}">
            <label for="gender" class="formError" id="genderErrors">
                <fmt:message key="traveller.gender" /><c:if test="${isMandatory}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label for="gender" class="helpinfolabel">
                <fmt:message key="traveller.gender" /><c:if test="${isMandatory}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>

    <c:set var="disableList" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableList" value="${readonlyInputFields}" />
    </c:if>
    
     <form:select path="gender" disabled="${disableList}">
            <form:option value="" label=""/>
            <form:options items="${genderList}" itemValue="code" itemLabel="description"/>
     </form:select>
