<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	
    <c:set var="locationErrors"><form:errors path="user.location"/></c:set>	
    <c:choose>
        <c:when test="${not empty locationErrors}">
            <label class="formError">
                <fmt:message key="airport" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:when>
        <c:otherwise>
            <label>
                <fmt:message key="airport" /><c:if test="${not empty showStar}">*</c:if>
            </label>
        </c:otherwise>
    </c:choose>
   
   <c:choose>			    
		<c:when test="${userScreenName eq 'adminUserUpdateForm'}">
   	        <div class="summaryValue">
 				<c:out value="${adminUserProfileCommand.user.location}" />
 			</div> 
   	    </c:when>
		<c:otherwise>
			<form:select path="user.location" id="location">
					<form:option value="" label=""/>			
		            <form:options items="${validAirports}" itemValue="iataCode" itemLabel="codeDescription"/>
		     </form:select>
		</c:otherwise>
	</c:choose>