 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>				

	<c:set var="lastnameErrors"><form:errors path="user.lastname"/></c:set>	
    <c:choose>
        <c:when test="${not empty lastnameErrors}">
            <label class="formError">
                <fmt:message key="lastname" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label>
                <fmt:message key="lastname" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose> 
    
    <c:choose>			    
		<c:when test="${userScreenName eq 'adminUserUpdateForm'}">
   	        <div class="summaryValue">
 				<c:out value="${adminUserProfileCommand.user.lastname}" />
			</div>
		</c:when>
		<c:otherwise>
			<form:input path="user.lastname" id="lastname" size="35" maxlength="35" disabled="${disableItem}" onKeyPress="return isASCII(event)"/>
		</c:otherwise>
	</c:choose>	
