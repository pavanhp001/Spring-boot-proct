<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
<label class="helpinfolabel">
    <fmt:message key="enabled.indicator" />
</label>

    <c:set var="disableItem" value="${disableInputFields}" />

    
    <c:if test="${readonlyInputFields == true}">
        <c:set var="disableItem" value="${readonlyInputFields}" />
    </c:if>
    
<form:checkbox path="enabledInd" disabled="${disableItem}" value="Y"/>