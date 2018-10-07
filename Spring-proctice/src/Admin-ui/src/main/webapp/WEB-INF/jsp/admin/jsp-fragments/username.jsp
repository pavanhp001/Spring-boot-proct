<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>		                        
 

   <c:set var="usernameErrors"><form:errors path="user.username"/></c:set>	
    <c:choose>
        <c:when test="${not empty usernameErrors}">
			<label class="formError">
			<fmt:message key="username" /><c:if test="${not empty showStar}">*</c:if>
			</label>
		</c:when>
		<c:otherwise>
			<label>
			<fmt:message key="username" /><c:if test="${not empty showStar}">*</c:if>
			</label>
		</c:otherwise>
	</c:choose>
	
 	<c:choose>			    
		<c:when test="${userScreenName eq 'adminUserUpdateForm'}">
   	        <div class="summaryValue">
 						<c:out value="${adminUserProfileCommand.user.username}" />
			</div>
		</c:when>
		<c:when test="${userScreenName eq 'adminUserUpdate'}">
   	        <div class="summaryValue">
 						<c:out value="${adminUserUpdateCommand.user.username}" />
			</div>
		</c:when>
		<c:otherwise>
			<form:input path="user.username" id="username" maxlength="35" size="35" onKeyPress="return isASCII(event)"/>
		</c:otherwise>
	</c:choose>	