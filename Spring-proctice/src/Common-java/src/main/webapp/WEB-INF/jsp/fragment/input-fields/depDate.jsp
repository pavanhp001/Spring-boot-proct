<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<script type="text/javascript">
    $(document).ready(function(){
        $.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>'] = {dateFormat: 'dd-mm-yy', firstDay: 0, speed: '', yearRange: '-99:+20'};
        $.datepicker.setDefaults($.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
        $('#departureDate').datepicker({showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+20', onSelect: function(date) { $(this).focus();} });

        var disabled = $('#departureDate').attr('disabled');
        if (disabled == true) {
            $("#departureDate").datepicker('disable');
        } else {
            $("#departureDate").datepicker('enable');
        }
    });
</script>
<c:set var="disableItem" value="${disableRecurrenceTransField}" />
<c:set var="departureDateErrors"><form:errors path="departureDate"/></c:set>
<c:choose>
    <c:when test="${not empty departureDateErrors || not empty scheduledTimeDepDateErrors}">
        <label for="departureDate" class="formError" id="departureDateErrors">
           <fmt:message key="date.of.departure" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label  for="departureDate" class="helpinfolabel">
            <fmt:message key="date.of.departure" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>

<div class="calendarControl">
    <form:input path="departureDate" maxlength="10" size="10" disabled="${disableItem}"/>
</div>
