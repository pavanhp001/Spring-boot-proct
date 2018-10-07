<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
    $(document).ready(function(){
        $.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>'] = {dateFormat: 'dd-mm-yy', firstDay: 0, speed: '', yearRange: '-99:+20'};
        $.datepicker.setDefaults($.datepicker.regional[Common.getLocale()]);
        $('#validUntil').datepicker({showOn: 'button', buttonImageOnly: false, buttonImage: '/bcs/images/icons/Calendar24.png', gotoCurrent:true, changeMonth: true, changeYear: true, onSelect: function(date) { $(this).focus();} });
    });
</script>   

<c:set var="validUntilErrors"><form:errors path="validUntil"/></c:set>    
<c:choose>
    <c:when test="${not empty validUntilErrors}">
        <label class="formError" id="validUntilErrors">
            <fmt:message key="valid.until" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:when>
    <c:otherwise>
        <label class="helpinfolabel">
            <fmt:message key="valid.until" /><c:if test="${not empty showStar}">*</c:if>
        </label>
    </c:otherwise>
</c:choose>

<c:set var="disableItem" value="${disableInputFields}" />
<c:if test="${readonlyInputFields == true}">
    <c:set var="disableItem" value="${readonlyInputFields}" />
</c:if>
<div class="ARcalIcon1">
  <form:input path="validUntil" maxlength="10" size="10" disabled="${disableItem}"/>
</div>