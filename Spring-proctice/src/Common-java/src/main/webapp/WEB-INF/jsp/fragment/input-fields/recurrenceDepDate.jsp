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

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:set var="recurrenceDepDateErrors"><form:errors path="recurrenceDepDate"/></c:set>

<label class="helpinfolabel"> <fmt:message key="date.departure" />
</label>

<c:set var="previousDay">
    <fmt:message key="schedule.recurrenceDepDate.previousDay" />
</c:set>
<c:set var="sameDay">
    <fmt:message key="schedule.recurrenceDepDate.sameDay" />
</c:set>
<c:set var="nextDay">
    <fmt:message key="schedule.recurrenceDepDate.nextDay" />
</c:set>

<c:set var="disableItem" value="${disableInputFields}" />


<table>
<tr>
<td class="labelAndCheck" title="${previousDay}">
    <form:radiobutton path="recurrenceDepDate" id="previousDay" value="P"
        title="${previousDay}" />
    <label class="radioLabel">${previousDay}</label>
</td>
<td class="labelAndCheck" title="${sameDay}">
    <form:radiobutton path="recurrenceDepDate" id="sameDay" value="S"
        title="${sameDay}" />
    <label class="radioLabel">${sameDay}</label>
</td>
<td class="labelAndCheck" title="${nextDay}">
    <form:radiobutton path="recurrenceDepDate" id="nextDay" value="N"
        title="${nextDay}" />
    <label class="radioLabel">${nextDay}</label>
</td>
</tr></table>

