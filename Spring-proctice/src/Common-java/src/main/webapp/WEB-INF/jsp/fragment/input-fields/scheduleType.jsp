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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:set var="scheduleImmediate">
    <fmt:message key="schedule.type.immediate"/>
</c:set>
<c:set var="scheduleScheduled">
    <fmt:message key="schedule.type.scheduled"/>
</c:set>
<c:set var="scheduleRecurrence">
    <fmt:message key="schedule.type.recurrence"/>
</c:set>
<c:set var="disableItem" value="${disableInputFields}" />

  <label class="helpinfolabel">
            <fmt:message key="schedule.type"/>
  </label>

<ul>

    <li class="labelAndCheck" title="${scheduleImmediate}" >
        <form:radiobutton path="scheduledType" id="immediate" value="I"  title="${scheduleImmediate}" disabled="${disableItem}"/>
        <label class="radioLabel" >${scheduleImmediate}</label>
    </li>
    <li class="labelAndCheck" title="${scheduleScheduled}" >
        <form:radiobutton path="scheduledType" id="scheduled" value="S"  title="${scheduleScheduled}" disabled="${disableItem}"/>
        <label class="radioLabel" >${scheduleScheduled}</label>
    </li>
    <li class="labelAndCheck" title="${scheduleRecurrence}" >
        <form:radiobutton path="scheduledType" id="recurrence" value="R"  title="${scheduleRecurrence}" disabled="${disableItem}"/>
        <label class="radioLabel" >${scheduleRecurrence}</label>
    </li>

</ul>
