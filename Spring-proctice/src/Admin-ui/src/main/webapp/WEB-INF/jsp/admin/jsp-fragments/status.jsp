<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>					
	<c:if test="${empty hideStatus}">

		   <c:set var="userStatusErrors"><form:errors path="user.userStatus"/></c:set>	
		    <c:choose>
		        <c:when test="${not empty userStatusErrors}">
					<label class="formError">
					<fmt:message key="status" /><c:if test="${not empty showStar}">*</c:if>
					</label>
				</c:when>
				<c:otherwise>
					<label>
					<fmt:message key="status" /><c:if test="${not empty showStar}">*</c:if>
					</label>
				</c:otherwise>
			</c:choose>					
				<c:set var="disabledStatus" value=""/>
				<c:if test="${('3' eq user.userStatus.value) and !(empty editMode)}" >
					<c:set var="disabledStatus" value="disabled"/>
					<input type="hidden" id="user.userStatus" name="user.userStatus" value="<c:out value="${user.userStatus}"/>"/>
 						</c:if>
				
				<c:choose>			    
					<c:when test="${userScreenName eq 'adminUserUpdateForm'}">
			   	        <div class="summaryValue">
			 						<c:if test="${ '1' eq user.userStatus.value}" >		
										<fmt:message key="user.status.Enabled"/>									
									</c:if>	
									<c:if test="${ '2' eq user.userStatus.value}" >		
										<fmt:message key="user.status.Disabled"/>									
									</c:if>
			 			</div>
					</c:when>
					<c:otherwise>
						<select name="user.userStatus" id="userStatus" ${disabledStatus}>	
							<c:set var="selected" value="" scope="request"/>
							<c:if test="${ '3' eq user.userStatus.value}" >		
								<c:set var="selected" value="selected" scope="request"/>									
							</c:if>	
							<option value="<c:out value="3"/>" ${selected}><fmt:message key="user.status.All"/></option>
							
							<c:set var="selected" value="" scope="request"/>
							<c:if test="${ '1' eq user.userStatus.value}" >		
								<c:set var="selected" value="selected" scope="request"/>									
							</c:if>	
							<option value="<c:out value="1"/>" ${selected}><fmt:message key="user.status.Enabled"/></option>
							
							<c:set var="selected" value="" scope="request"/>			
							<c:if test="${ '2' eq user.userStatus.value}" >		
								<c:set var="selected" value="selected" scope="request"/>									
							</c:if>	
							<option value="<c:out value="2"/>" ${selected}><fmt:message key="user.status.Disabled"/></option>								
						</select>
					</c:otherwise>
				</c:choose>
				
				
	</c:if>
	<c:if test="${!(empty hideStatus)}">
		&nbsp;
	</c:if>