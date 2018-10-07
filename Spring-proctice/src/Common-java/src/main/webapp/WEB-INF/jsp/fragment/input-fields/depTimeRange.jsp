<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    

   <c:set var="departureTimeRangeErrors"><form:errors path="departureTimeRange*"/></c:set>    
   <c:choose>
       <c:when test="${not empty departureTimeRangeErrors}">
           <label for="commomLabelForDepartureTimeRange" class="formError" id="departureTimeRangeErrors">
               <fmt:message key="timerange.departure" />
           </label>
       </c:when>
       <c:otherwise>
           <label for="commomLabelForDepartureTimeRange" class="helpinfolabel">
               <fmt:message key="timerange.departure" />
           </label>
       </c:otherwise>
   </c:choose>
   
   <form:input onkeypress="return isASCII(event)" path="departureTimeRangeFrom" maxlength="5" size="5" cssClass="commomLabelForDepartureTimeRange"/>
   <form:input onkeypress="return isASCII(event)" path="departureTimeRangeTo" maxlength="5" size="5" cssClass="commomLabelForDepartureTimeRange"/>
