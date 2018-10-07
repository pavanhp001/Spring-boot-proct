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

   <c:set var="scheduledTimeErrors"><form:errors path="scheduledTime"/></c:set>

   <c:choose>
       <c:when test="${not empty scheduledTimeErrors || not empty scheduledTimeCurrentTimeErrors || not empty startTimeLessThanCurrentErrors}">
           <label class="formError" id="scheduledTimeErrors">
               <fmt:message key="time.of.schedule" /><c:if test="${not empty showStar}">*</c:if>
           </label>
       </c:when>
       <c:otherwise>
           <label class="helpinfolabel">
               <fmt:message key="time.of.schedule" /><c:if test="${not empty showStar}">*</c:if>
           </label>
       </c:otherwise>
   </c:choose>
   
   <form:input onkeypress="return isASCII(event)" path="scheduledTime" maxlength="5" size="5"/>
   

