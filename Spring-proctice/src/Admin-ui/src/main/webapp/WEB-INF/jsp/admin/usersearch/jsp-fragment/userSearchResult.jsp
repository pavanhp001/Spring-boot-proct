<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>

<div id="searchResults">
 <form:form commandName="adminSearchTableCommand" method="POST" action="adminUserSearch.form">    
        	
    	<table class="results" summary="results">           
        	
        	<caption>
	        	<div class="caption-text">
	        		<jsp:include page="/WEB-INF/jsp/fragment/paginginfoNew.jsp" />
	        	</div>
	        	<div class="caption-icon">
	        		<jsp:include page="/WEB-INF/jsp/fragment/ExportIcons.jsp"/>
	        	</div>
        	</caption>
            <!-- ** Header ** -->
            <thead> 
            	<c:set var="sortClass" scope="page" >
	              <c:choose>
	                    <c:when test="${adminSearchTableCommand.descending}">class="sortedasc"</c:when>
	                    <c:otherwise>class="sorteddesc"</c:otherwise>
	              </c:choose>
	            </c:set>          
                <!-- header -->
                <tr class="sortable">
		                <th id="name" <c:if test="${adminSearchTableCommand.sortBy == 'name'}">${sortClass}</c:if> 
	                        onclick="changeSortOrder('name');" title="<fmt:message key="name"/>" >
	                            <fmt:message key="name"/>
	                    </th>
	                    <th id="username" <c:if test="${adminSearchTableCommand.sortBy == 'username'}">${sortClass}</c:if> 
	                        onclick="changeSortOrder('username');" title="<fmt:message key="username"/>" >
	                            <fmt:message key="username"/>
	                    </th>
                        
                        <th id="status" class="listheadleft" title="<fmt:message key="status"/>">
                                         <fmt:message key="status"/>&nbsp;
                        </th>
                        
                        <th id="email" <c:if test="${adminSearchTableCommand.sortBy == 'email'}">${sortClass}</c:if> 
	                        onclick="changeSortOrder('email');" title="<fmt:message key="email"/>" >
	                            <fmt:message key="email"/>
	                    </th>
	                    <th id="airport" <c:if test="${adminSearchTableCommand.sortBy == 'location'}">${sortClass}</c:if> 
	                        onclick="changeSortOrder('location');" title="<fmt:message key="airport"/>" >
	                            <fmt:message key="airport"/>&nbsp;
	                    </th>
	                    <th id="lastLoginDate" <c:if test="${adminSearchTableCommand.sortBy == 'lastLoginDate'}">${sortClass}</c:if> 
                            onclick="changeSortOrder('lastLoginDate');" title="<fmt:message key="last.logged.on"/>" >
                            <fmt:message key="last.logged.on"/>                              
                        </th>
                </tr>
            
            </thead>

            
            <tbody id="userList" class="selectable">
         
				<c:forEach items="${command.records}"  var="record" varStatus="counter">
		        	<tr class="results" id="<c:out value="${record.username}"/>">
						<td id="${record.username}-name">
							${record.name}
						</td>
						<td id="${record.username}-username">
							${record.username}
						</td>
						<td id="${record.username}-status">
							<fmt:message key="user.status.${record.userStatus.value}"/>
						</td>
						<td id="${record.username}-email">
							${record.email}
						</td>
						<td id="${record.username}-airport">
							${record.location}
						</td>
						<td id="${record.username}-lastLoginDate">
						<fmt:formatDate pattern="${dateTimePattern}" value="${record.lastLoginDate}"/>
                        </td>
					</tr>
				</c:forEach>
                      
            </tbody>

        </table>
        
     <fieldset class="oneCol">
     	<div class="tableActionsControl">
             <button id="btnViewUser" class="textButton" name="btnViewUser" disabled>
                 <fmt:message key="button.view"/>
             </button>
             <u:checkRole roleGroup="READ_ONLY_ADMIN_MENU_ITEMS" not="true">
	             <button id="btnDeleteUser" type="button" class="textButton" name="btnDeleteUser" disabled>
	                 <fmt:message key="button.delete"/>
	             </button>
	             <button id="btnAddUser" class="textButton" name="btnAddUser">
                 <fmt:message key="button.add"/>
                 </button>          
             </u:checkRole>                    	
         </div>
         <div class="prevNextControl">
             <button id="btnPrevious" class="textButton" type="submit"  name="previous" <c:out value="${adminSearchTableCommand.previousDisabled}"/> > 
                 <fmt:message key="button.previous"/>
             </button>
             <button id="btnNext" class="textButton" type="submit" id="next" name="next" <c:out value="${adminSearchTableCommand.nextDisabled}"/> >
                 <fmt:message key="button.next"/>
             </button>
              <button type="submit" class="hideOnLoad" id="sortButton" name="sort" value="sort">Sort Column</button>
              <form:input type="hidden" path="sortBy" />
         </div>
     
    </fieldset>             
</form:form>
</div> <!-- /searchResults -->