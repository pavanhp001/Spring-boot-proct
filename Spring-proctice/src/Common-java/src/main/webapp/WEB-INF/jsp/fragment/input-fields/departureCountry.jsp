<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 

   <c:set var="departureCountryErrors"><form:errors path="departureCountry"/></c:set>   
    <c:choose>
        <c:when test="${not empty departureCountryErrors || not empty combinationErrors}">
            <label class="formError">
                 <fmt:message key="departure.country" />
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                 <fmt:message key="departure.country" /> 
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
        
     <form:select path="departureCountry" disabled="${disableList}">
            <form:option value="" label=""/>
            <form:options items="${countryList}" itemValue="code" itemLabel="description"/>
     </form:select>

