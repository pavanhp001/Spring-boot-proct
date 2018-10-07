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


<label class="helpinfolabel">
    <fmt:message key="rescan.type" />
</label>

<c:set var="disableList" value="${disableInputFields}" />
<c:if test="${readonlyInputFields == true}">
    <c:set var="disableList" value="${readonlyInputFields}" />
</c:if>

<form:select path="scanType" disabled="${disableList}" onchange="onRescanOptionChange();">
    <form:options items="${scanTypeList}" itemValue="code" itemLabel="description"/>
</form:select>

