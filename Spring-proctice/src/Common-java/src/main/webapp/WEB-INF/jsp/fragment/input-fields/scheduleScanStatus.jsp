<!--
/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2012
 * All rights reserved.
 */
-->

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<c:set var="scheduleScanStatusErrors">
    <form:errors path="scheduleScanStatus"/>
</c:set>    

<c:choose>
    <c:when test="${not empty scheduleScanStatusErrors || not empty combinationErrors}">
        <label class="formError">
            <fmt:message key="scan.status" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label class="helpinfolabel">
            <fmt:message key="scan.status" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>    

<c:set var="disableList" value="${disableInputFields}" />
<c:if test="${readonlyInputFields == true}">
    <c:set var="disableList" value="${readonlyInputFields}" />
</c:if>
    
<form:select path="scheduleScanStatus" disabled="${disableList}">
    <form:option value="" label=""/>
    <form:options items="${scanStatusList}" itemValue="code" itemLabel="description"/> 
</form:select>

