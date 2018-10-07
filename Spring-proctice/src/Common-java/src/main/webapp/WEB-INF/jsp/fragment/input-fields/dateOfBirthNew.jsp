<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 

<script type="text/javascript">
jQuery(window).bind("load",function(){
    jQuery.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>'] = {dateFormat: 'dd-mm-yy', firstDay: 0, speed: '', yearRange: '-5:+0'};
    jQuery.datepicker.setDefaults(jQuery.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
  });

$(document).ready(function(){
    var datePattern = '<fmt:message key="jquery.date.pattern"/>';
    $.datepicker.setDefaults($.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
    $('#dateOfBirth').datepicker({ showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+0',
            onSelect: function(date) { 
            var theDate = new Date(Date.parse($(this).datepicker('getDate')));
            var dateFormatted = $.datepicker.formatDate(datePattern, theDate);                      
            $("#dateOfBirth").val(dateFormatted);
            $(this).focus();
        }
    });
});
    
    
</script>
     
<c:set var="dateOfBirthErrors"><form:errors path="dateOfBirth"/></c:set>    
<c:choose>
    <c:when test="${not empty dateOfBirthErrors}">
        <label for="dateOfBirth" class="formError" id="dateOfBirthErrors">
           <fmt:message key="date.of.birth" />
        </label>
    </c:when>
    <c:otherwise>
        <label  for="dateOfBirth" class="helpinfolabel">
            <fmt:message key="date.of.birth" />
        </label>
    </c:otherwise>
</c:choose>

<div class="calendarControl">
    <form:input path="dateOfBirth" maxlength="10" size="10" disabled="${disableInputFields}"/>
</div>