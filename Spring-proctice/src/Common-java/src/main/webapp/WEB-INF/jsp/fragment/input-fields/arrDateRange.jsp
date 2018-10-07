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
            $('#arrivalDateRangeFrom').datepicker({ showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+1'
                , onSelect: function(date) { $(this).focus();}
                 , onClose: function(dateText, inst) { postProcessAirportFields(); }
             });
            $('#arrivalDateRangeTo').datepicker({ showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+1'
                , onSelect: function(date) { $(this).focus();} 
                , onClose: function(dateText, inst) { postProcessAirportFields(); }
            });
        });
</script>
                                    
<c:set var="arrivalDateRangeErrors"><form:errors path="arrivalDateRange*"/></c:set>    
<c:choose>
   <c:when test="${not empty arrivalDateRangeErrors}">
        <label for="commomLabelForArrivalDateRange" class="formError" id="arrivalDateRangeErrors">
            <fmt:message key="daterange.arrival" />
        </label>
    </c:when>
    <c:otherwise>
        <label for="commomLabelForArrivalDateRange" class="helpinfolabel">            
            <fmt:message key="daterange.arrival" />        
        </label>               
    </c:otherwise>
</c:choose>


<div class="calendarControl">
   <form:input path="arrivalDateRangeFrom" maxlength="10" size="10" cssClass="commomLabelForArrivalDateRange"/>
</div>
<div class="calendarControl">
   <form:input path="arrivalDateRangeTo" maxlength="10" size="10" cssClass="commomLabelForArrivalDateRange"/>
</div>    
 <div id="dateRangeWarning">
     <fmt:message key="arr.dep.daterange.is.same" />
 </div>    