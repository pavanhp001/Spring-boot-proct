<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>		                        
 
	
   <c:set var="mobileNumberErrors"><form:errors path="user.mobileNumber"/></c:set>	
    <c:choose>
        <c:when test="${not empty mobileNumberErrors}">
			<label class="formError">
			<fmt:message key="mobile" />
			</label>
		</c:when>
		<c:otherwise>
			<label>
			<fmt:message key="mobile" />
			</label>
		</c:otherwise>
	</c:choose>	
 	
 	<c:choose>			    
		<c:when test="${userScreenName eq 'adminUserUpdateForm'}">
   	        <div class="summaryValue">
 				<c:out value="${adminUserProfileCommand.user.mobileNumber}" />
			</div>
		</c:when>
		<c:otherwise>
			<form:input path="user.mobileNumber" id="mobileNumber" maxlength="15" size="30" onKeyPress="return isASCII(event)"/>
		</c:otherwise>
	</c:choose>	
 	
 	       
	