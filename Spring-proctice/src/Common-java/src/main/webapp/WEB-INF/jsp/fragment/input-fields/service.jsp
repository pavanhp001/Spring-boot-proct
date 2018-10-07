<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    

   <c:set var="serviceErrors"><form:errors path="service*"/></c:set>    
    <c:choose>
        <c:when test="${not empty serviceErrors}">
            <label for="commomLabelForService" class="formError">
                <fmt:message key="service" />
            </label>
        </c:when>
        <c:otherwise>
            <label for="commomLabelForService" class="helpinfolabel">
                <fmt:message key="service" />
            </label>
        </c:otherwise>
    </c:choose>

    <div class="flightInputFields">
     <form:input path="serviceCode" maxlength="3" size="3" onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()" cssClass="commomLabelForService"/>

     <form:input path="serviceNumber" maxlength="5" size="5" onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()" cssClass="commomLabelForService"/>
    </div>        
              