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
 

   <c:set var="fileNameErrors"><form:errors path="fileName"/></c:set>    
    <c:choose>
        <c:when test="${not empty fileNameErrors}">
            <label class="formError" id="fileNameErrors">
                 <fmt:message key="file.name" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label>
                 <fmt:message key="file.name" /> <c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
    
    <c:set var="disableItem" value="${disableInputFields}" />
    <c:if test="${readonlyInputFields || readonlyPrimaryField}">
        <c:set var="disableItem" value="true" />
    </c:if>
    <div id="importFile_div">
         <form:input path="fileName" maxlength="80" size="40" type="file" disabled="${disableItem}"
             onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()"/>
    </div>