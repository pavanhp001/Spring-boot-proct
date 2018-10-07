<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<c:set var="rolesErrors"><form:errors path="user.roles"/></c:set>	
	
				<form:hidden path="user.roles" id="roles" />
				<c:choose>
			        <c:when test="${not empty rolesErrors}">
			            <label class="formError">
			                <fmt:message key="role" /><c:if test="${not empty showStar}">*</c:if>
			            </label>
			        </c:when>
			        <c:otherwise>
			            <label>
			                <fmt:message key="role" /><c:if test="${not empty showStar}">*</c:if>
			            </label>
			        </c:otherwise>
			    </c:choose> 
	
			<c:choose>			    
				<c:when test="${userScreenName eq 'adminUserUpdateForm'}">
		   	        <div class="summaryValue">
                        <c:forEach var="userRole" items="${adminUserProfileCommand.user.roles}" varStatus="loop">
                            <c:if test="${userRole ne 'PWDRESET'}">
                                <c:out value="${roleMap[userRole]}" default="${userRole}"/><br>
                            </c:if>
                        </c:forEach>
		 			</div>
				</c:when>
				<c:otherwise>
					<select id="roleSelect" multiple size="4" onchange="roleSelection()">
						<c:forEach var="role" items="${roleMap}" varStatus="loop">
							
							<c:set var="hasRole" value="false"/>
							<c:forEach var="userRole" items="${user.roles}">
								<c:if test="${userRole == role.key}">
									<c:set var="hasRole" value="true"/>
								</c:if>
							</c:forEach>
							<c:set var="selected" value=""/>
							<c:if test="${hasRole}">
								<c:set var="selected" value="selected"/>
							</c:if>
						      <c:if test="${hasRole || (!hasRole && empty hideUnallocatedRoles)}">
									<option value="${role.key}" ${selected}>
										<c:out value="${role.value}" default="${role.key}"/>	
									</option>
						      </c:if>
						</c:forEach>
					</select>
				</c:otherwise>
		</c:choose>	

