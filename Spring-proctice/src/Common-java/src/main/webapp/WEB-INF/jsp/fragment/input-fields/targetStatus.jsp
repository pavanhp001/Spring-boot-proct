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

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 

<c:set var="targetStatusErrors"><form:errors path="targetStatus"/></c:set>    
    <c:choose>
        <c:when test="${not empty targetStatusErrors}">
            <label class="formError">
                 <fmt:message key="taregt.status" />
            </label>
        </c:when>
        <c:otherwise>
            <label>
                 <fmt:message key="target.status" />
            </label>
        </c:otherwise>
    </c:choose>
    
<c:set var="disableList" value="${disableInputFields}" />
<c:if test="${readonlyInputFields == true}">
    <c:set var="disableList" value="${readonlyInputFields}" />
</c:if>

<form:select path="targetStatus" disabled="${disableList}">
       <form:option value="" label=""/>
       <form:options items="${targetStatusList}" itemValue="code" itemLabel="description"/>
</form:select>