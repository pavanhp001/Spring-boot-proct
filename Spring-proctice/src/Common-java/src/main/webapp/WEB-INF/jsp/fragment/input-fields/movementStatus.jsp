<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Grab input parameters -->
<c:set var="showNoMovement" value="<%= request.getParameter(\"movementStatusShowNoMovement\") %>" />

<label  for="commomLabelForMovementStatus" class="helpinfolabel">
    <fmt:message key="movement.status" />
</label>

<c:set var="expectedTitle">
    <fmt:message key="movement.status.E"/>
</c:set>
<c:set var="cancelledTitle">
    <fmt:message key="movement.status.C"/>
</c:set>
<c:set var="deniedTitle">
    <fmt:message key="movement.status.D"/>
</c:set>
<c:set var="noMovementTitle">
    <fmt:message key="movement.status.N"/>
</c:set>

<div class="iconAndCheck" title="${expectedTitle}" >
        <img src="/bcs/images/icons/Expected24.png" alt="${expectedTitle}" />
        <form:checkbox path="movementStatusExpected" value="EXPECTED" cssClass="commomLabelForMovementStatus" title="${expectedTitle}"/>
</div>
<div class="iconAndCheck" title="${cancelledTitle}">        
        <img src="/bcs/images/icons/Cancelled24.png" alt="${cancelledTitle}" />
        <form:checkbox path="movementStatusCancelled" value="CANCELLED" cssClass="commomLabelForMovementStatus" title="${cancelledTitle}"/>
</div>        
<div class="iconAndCheck"  title="${deniedTitle}">
        <img src="/bcs/images/icons/Denied24.png" alt="${deniedTitle}" />
        <form:checkbox path="movementStatusDenied" value="DENIED" cssClass="commomLabelForMovementStatus" title="${deniedTitle}" />
</div>

<c:if test="${showNoMovement}">
    <div class="iconAndCheck"  title="${noMovementTitle}">
        <img src="/bcs/images/icons/NoMovement24.png" alt="${noMovementTitle}" />
        <form:checkbox path="movementStatusNoMovement" value="N" cssClass="commomLabelForMovementStatus" title="${noMovementTitle}" />
    </div>
</c:if>

