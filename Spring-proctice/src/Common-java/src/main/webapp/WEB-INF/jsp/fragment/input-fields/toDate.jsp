<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fieldset>
    <fieldset id="flightSearch-column1" class="inputGroup">
        <form:label cssErrorClass="formError" path="toDate"><fmt:message key="to.date.time"/><c:if test="${showStar eq true}">*</c:if></form:label>
        <form:input path="toDate" size="18" maxlength="14"/>
        <form:input path="toTime" size="10" maxlength="5" onkeypress="return isValidDateTime(event,this.value)" />
    </fieldset>
</fieldset>