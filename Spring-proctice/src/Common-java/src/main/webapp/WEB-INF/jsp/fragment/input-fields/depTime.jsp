
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

   <c:set var="depTimeErrors"><form:errors path="depTime"/></c:set>

   <c:choose>
       <c:when test="${not empty depTimeErrors}">
           <label class="formError" id="depTimeErrors">
               <fmt:message key="time.of.departure" /><c:if test="${not empty showStar}">*</c:if>
           </label>
       </c:when>
       <c:otherwise>
           <label class="helpinfolabel">
               <fmt:message key="time.of.departure" /><c:if test="${not empty showStar}">*</c:if>
           </label>
       </c:otherwise>
   </c:choose>

   <form:input onkeypress="return isASCII(event)" path="depTime" maxlength="5" size="5"/>


