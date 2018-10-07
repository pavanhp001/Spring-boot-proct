<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

   <c:set var="countryOfBirthErrors"><form:errors path="countryOfBirth"/></c:set>    
    <c:choose>
        <c:when test="${not empty countryOfBirthErrors}">
            <label class="formError">
                 <fmt:message key="traveller.country.of.birth" />
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
            <fmt:message key="traveller.country.of.birth" />
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableList" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableList" value="${readonlyInputFields}" />
    </c:if>
    
     <form:select path="countryOfBirth" disabled="${disableList}">
            <form:option value="" label=""/>
            <form:options items="${nationalityList}" itemValue="code" itemLabel="description"/>
     </form:select>
