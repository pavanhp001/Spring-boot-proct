<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>			
   <c:set var="emailErrors"><form:errors path="user.email"/></c:set>	
    <c:choose>
        <c:when test="${not empty emailErrors}">
            <label class="formError">
                <fmt:message key="email" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label >
                <fmt:message key="email" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose> 
    
 	<c:choose>			    
		<c:when test="${userScreenName eq 'adminUserUpdateForm'}">
   	        <div class="summaryValue">
 				<c:out value="${adminUserProfileCommand.user.email}" />
			</div> 
   	    </c:when>
		<c:otherwise>
			<form:input path="user.email" id="email" maxlength="50" size="35" onKeyPress="return isASCII(event)"/>
		</c:otherwise>
	</c:choose>