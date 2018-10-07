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
        $('#scheduledDate').datepicker({showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+20', onSelect: function(date) { $(this).focus();} });
    });
</script>

<c:set var="scheduledDateErrors"><form:errors path="scheduledDate"/></c:set>    
<c:choose>
    <c:when test="${not empty scheduledDateErrors || not empty scheduledTimeCurrentTimeErrors || not empty scheduledTimeDepDateErrors}">
        <label for="scheduledDate" class="formError" id="scheduledDateErrors">
           <fmt:message key="date.of.schedule" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label  for="scheduledDate" class="helpinfolabel">
            <fmt:message key="date.of.schedule" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>

<div class="calendarControl">
    <form:input path="scheduledDate" maxlength="10" size="10"/>
</div>