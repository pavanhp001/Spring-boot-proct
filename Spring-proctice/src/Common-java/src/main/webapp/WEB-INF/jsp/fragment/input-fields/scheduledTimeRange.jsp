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

<%-- toFromError is local variable to OR the to and from errors --%>
<c:choose>
    <c:when test="${not empty toFromTimeError}">
        <label class="formError" id="timeRangeError" >
            <fmt:message key="scheduled.time.range" />
        </label>
    </c:when>
    <c:otherwise>
        <label class="helpinfolabel">
            <fmt:message key="scheduled.time.range" />
        </label>
    </c:otherwise>
</c:choose>

<form:input onkeypress="return isASCII(event)" path="timeFrom" maxlength="5" size="5" />
<form:input onkeypress="return isASCII(event)" path="timeTo" maxlength="5" size="5" />
