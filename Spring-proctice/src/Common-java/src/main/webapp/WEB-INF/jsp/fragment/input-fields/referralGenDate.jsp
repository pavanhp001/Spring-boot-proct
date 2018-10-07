<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 

<script type="text/javascript">
$(document).ready(function(){
    $.datepicker.setDefaults($.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
    $('#referralGenDateFrom').datepicker({showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+0', onSelect: function(date) { $(this).focus();} });
    $('#referralGenDateTo').datepicker({showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+0', onSelect: function(date) { $(this).focus();} });
});
</script> 
    
<c:set var="referralGenDateFromErrors"><form:errors path="referralGenDateFrom"/></c:set>
<c:set var="referralGenDateToErrors"><form:errors path="referralGenDateTo"/></c:set>

<%-- Generic msg for both dates? As per other screens? --%>    
<c:choose>
    <c:when test="${not empty referralGenDateFromErrors || not empty referralGenDateFromErrors}">
        <label for="commomLabelForGenDateRange" class="formError">
             <fmt:message key="generation.date" />
        </label>
    </c:when>
    <c:otherwise>
        <label for="commomLabelForGenDateRange" class="helpinfolabel">
            <fmt:message key="generation.date" />
        </label>
    </c:otherwise>
</c:choose>

<div class="calendarControl">
    <form:input path="referralGenDateFrom" maxlength="10" size="10" cssClass="commomLabelForGenDateRange" />
</div>
<div class="calendarControl">
    <form:input path="referralGenDateTo" maxlength="10" size="10" cssClass="commomLabelForGenDateRange" />
</div>

