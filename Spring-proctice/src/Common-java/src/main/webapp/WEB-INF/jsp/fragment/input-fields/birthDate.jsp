<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
<script type="text/javascript">
$(document).ready(function(){
    $.datepicker.setDefaults($.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);
    $('#birthDate').datepicker({showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, yearRange: '-110:+0', onSelect: function(date) { $(this).focus();} });
});
</script>     
   <c:set var="birthDateErrors"><form:errors path="birthDate"/></c:set>    
    <c:choose>
        <c:when test="${not empty birthDateErrors}">
            <label class="formError" id="birthDateErrors">
                 <fmt:message key="date.of.birth" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                 <fmt:message key="date.of.birth" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>

    <div class="ARcalIcon1">
         <form:input path="birthDate" maxlength="10" size="10" disabled="${disableInputFields}"/>
    </div>
