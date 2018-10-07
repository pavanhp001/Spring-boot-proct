 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>				

	<c:set var="forenameErrors"><form:errors path="user.forename"/></c:set>	
    <c:choose>
        <c:when test="${not empty forenameErrors}">
            <label class="formError">
                <fmt:message key="forename" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label>
                <fmt:message key="forename" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose> 
    
    <c:choose>			    
		<c:when test="${userScreenName eq 'adminUserUpdateForm'}">
   	        <div class="summaryValue">
 				<c:out value="${adminUserProfileCommand.user.forename}" />
			</div>
		</c:when>
		<c:otherwise>
			<form:input path="user.forename" id="forename" size="35" maxlength="35" disabled="${disableItem}" onKeyPress="return isASCII(event)"/>
		</c:otherwise>
	</c:choose>	