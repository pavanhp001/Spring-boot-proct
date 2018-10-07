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


<script type="text/javascript">
    $(document).ready(function(){
        $.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>'] = {dateFormat: 'dd-mm-yy', firstDay: 0, speed: '', yearRange: '-99:+20'};
        $.datepicker.setDefaults($.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
        $('#startDate').datepicker({showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+20', onSelect: function(date) { $(this).focus();} });
        var disabled = $('#startDate').attr('disabled');
        if (disabled == true) {
            $("#startDate").datepicker('disable');
        } else {
            $("#startDate").datepicker('enable');
        }
    });
</script>

<c:set var="startDateErrors"><form:errors path="startDate"/></c:set>
<c:choose>
    <c:when test="${not empty startDateErrors || not empty startTimeLessThanCurrentErrors}">
        <label for="startDate" class="formError" id="startDateErrors">
           <fmt:message key="date.scan" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label  for="startDate" class="helpinfolabel">
            <fmt:message key="date.scan" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>
<c:set var="disableItem" value="${disableRecurrenceTransField}" />
<div class="calendarControl">
    <form:input path="startDate" maxlength="10" size="10" disabled="${disableItem}"/>
</div>
