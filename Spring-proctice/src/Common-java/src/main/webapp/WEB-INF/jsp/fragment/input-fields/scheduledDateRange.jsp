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
    
      jQuery(window).bind("load",function(){
        jQuery.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>'] = {dateFormat: 'dd-mm-yy', firstDay: 0, speed: '', yearRange: '-5:+20'};
        jQuery.datepicker.setDefaults(jQuery.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
      });
    
        $(document).ready(function(){
          $.datepicker.setDefaults($.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
            $('#dateFrom').datepicker({ showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+20', onSelect: function(date) { $(this).focus();} });
            $('#dateTo').datepicker({ showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+20', onSelect: function(date) { $(this).focus();} });
        });
</script>

<c:set var="dateRangeErrors"><form:errors path="dateRange*"/></c:set>
<c:choose>
    <c:when test="${not empty dateRangeErrors || not empty dateRangeCalendarErrors}">
        <label for="commomLabelForDateRange" class="formError" id="dateRangeErrors">
            <fmt:message key="scheduled.date.range" />
        </label>
    </c:when>
    <c:otherwise>
         <label for="commomLabelForDateRange" class="helpinfolabel">            
             <fmt:message key="scheduled.date.range" />
         </label>              
    </c:otherwise>
</c:choose>

<div class="calendarControl">
    <form:input path="dateFrom" maxlength="10" size="10" />
</div>
<div class="calendarControl">
    <form:input path="dateTo" maxlength="10" size="10" />
</div>
