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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fieldset>
    <fieldset id="flightSearch-column1" class="inputGroup">
        <form:label cssErrorClass="formError" path="fromDate"><fmt:message key="from.date.time"/><c:if test="${showStar eq true}">*</c:if></form:label>
        <form:input path="fromDate" size="18" maxlength="14" />                  
        <form:input path="fromTime" size="10" maxlength="5" onkeypress="return isValidDateTime(event,this.value)" />
    </fieldset>
</fieldset>