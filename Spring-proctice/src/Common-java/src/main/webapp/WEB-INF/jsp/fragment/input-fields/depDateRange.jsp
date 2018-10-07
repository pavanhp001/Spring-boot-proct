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
          $.datepicker.setDefaults($.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
            $('#departureDateRangeFrom').datepicker({ showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+1'
                , onSelect: function(date) { $(this).focus();} 
                 , onClose: function(dateText, inst) { postProcessAirportFields(); }
            });
            $('#departureDateRangeTo').datepicker({ showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+1'
                    , onSelect: function(date) { $(this).focus();} 
                , onClose: function(dateText, inst) { postProcessAirportFields(); }
            });
        });
</script>

<c:set var="departureDateRangeErrors"><form:errors path="departureDateRange*"/></c:set>    
<c:choose>
    <c:when test="${not empty departureDateRangeErrors}">
        <label for="commomLabelForDepartureDateRange" class="formError" id="departureDateRangeErrors">
            <fmt:message key="daterange.departure" />
        </label>
    </c:when>
    <c:otherwise>
         <label for="commomLabelForDepartureDateRange" class="helpinfolabel">            
             <fmt:message key="daterange.departure" />
         </label>              
    </c:otherwise>
</c:choose>


<div class="calendarControl">
   <form:input path="departureDateRangeFrom" maxlength="10" size="10" cssClass="commomLabelForDepartureDateRange"/>
</div>
<div class="calendarControl">
   <form:input path="departureDateRangeTo" maxlength="10" size="10" cssClass="commomLabelForDepartureDateRange"/>
</div>
          
              