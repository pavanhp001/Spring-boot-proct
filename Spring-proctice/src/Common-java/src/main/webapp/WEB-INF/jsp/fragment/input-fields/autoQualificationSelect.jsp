<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 

   <c:set var="autoQualifyIndErrors"><form:errors path="autoQualifyInd"/></c:set>    
    <c:choose>
        <c:when test="${not empty autoQualifyIndErrors || not empty combinationErrors}">
            <label class="formError">
                 <fmt:message key="automatic.qulaification.enabled" />
            </label>
        </c:when>
        <c:otherwise>
            <label>
                 <fmt:message key="automatic.qulaification.enabled" />
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
    
     <form:select path="autoQualifyInd" 
        items="${booleanSelectList}" 
        itemValue="code" 
        itemLabel="description"/>

