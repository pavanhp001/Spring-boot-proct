<!--
/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */

-->

<!--
********************************************************************************************************
VERSION:
$HeadURL: http://casource:7777/svn/irisk/adminUI/trunk/src/main/webapp/WEB-INF/jsp/admin/UserSearch.jsp $
********************************************************************************************************
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="admRole" value="${adminUserProfileCommand.userInAdminRole}" scope="request"/>

<script type="text/javascript">
	$(document).ready(function(){
	    disableAirTextFields();
	    setFieldsReadOnly();
	    <c:if test="${admRole}">
          if( $('#username').val().length > 0 ) {
		    $('#btnUpdateUser').click(function() {
		    	document.adminUserUpdateForm.submit();
		    });
          }
	    </c:if>
	     focusForm();
	    
	    if ($("#updateSuccessPopin")){	    
	    	 var title = $('#updateSuccessPopin').attr('title');		
	         $('#updateSuccessPopin').dialog({modal: true, resizeable: false});
	         $( "#updateSuccessPopin" ).dialog( "option", "title", title);
			 $('#btnOkey').focus();		
		}   
	    $('#btnClose').click(function() {
	    	var httpMethod = "${pageContext.request.method}";
	    	if(httpMethod == 'GET')
	    		document.location="/home";
	    	else
	    		$('#close').click();
	    });
	    
	    $('#btnDeleteUser').click(function() {
	    	showDeletePopinFromBtn();
	    });

	 });

   	function focusForm() {
		$('#username').focus();
	}
	
	function setFieldsReadOnly(){
		$('input[type="text"][id!="refKey"]').attr("disabled", true);
		$("#roleSelect option").attr("selected","");
		$('select').attr("disabled", true);
	}
	
	function closeDialogToRefresh(){
		closeDialogBySelector('#updateSuccessPopin');
		document.adminUserProfileForm.submit();
	}
	
	  //#########################
	  //###  Delete User  Confirm Popin
	  //########################
		
	    
	function showDeletePopinFromBtn(){
    $('#deleteUserPopin').dialog({modal: true, resizeable: false});
		$('#btnCancelfocus').focus();
		return false;
	}
	function deleteUser(){
		closeDialogBySelector('#deleteUserPopin');
		$('#btnDeleteUserConfirm').click();
	}
	
	function closeDialogBySelector(selector) {
	    closeDialog($(selector));
	}
	
</script>
<c:set var="userScreenName" value="adminUserUpdateForm" scope="request"/>

<div id="mainContent"> 
	        <jsp:include page="/WEB-INF/jsp/fragment/pageErrors.jsp"/>
	        <jsp:include page="/WEB-INF/jsp/admin/jsp-fragments/UserFields.jsp" />
</div>

<form id="adminUserUpdateForm" name="adminUserUpdateForm" action="adminUserUpdate.form" method="POST">
	<input type="hidden" id="username" name="user.username" value="${user.username}"/>
	<input type="hidden" id="action" name="action" value="view"/>
</form>

<form id="adminUserProfileForm" name="adminUserProfileForm" action="adminUserProfile.cform" method="POST">
		<input type="hidden" id="userProfileUsername" name="user.username" value="${user.username}"/>
		<div id="deleteUserPopin" title="<fmt:message key='delete.user'/>" class="hideOnLoad">
				<%-- problems with fmt tags if jsp:include used--%>
				<%@ include file="/WEB-INF/jsp/admin/jsp-popin/DeleteUserPopin.jsp" %>
		</div>
</form>
<form id="adminUserSearchForm" name="adminUserSearchForm" action="adminUserSearch.form" method="POST" >
	<input type="hidden" id="selectedUsername" name="selectedUsername" value="${user.username}"/>
	<button id="btnDeleteUserConfirm" name="deleteuser" type="submit" class="hideOnLoad">
		<fmt:message key="button.delete"/>
	</button>
	<button id="close" name="close" type="submit" class="hideOnLoad">
		<fmt:message key="button.close"/>
	</button>
</form>
<c:if test="${isUserDetailsUpdated == 'true'}">
	<div id="updateSuccessPopin" title="<fmt:message key='admin.update.user'/>" class="hideOnLoad">
	    <%@ include file="/WEB-INF/jsp/admin/jsp-popin/UpdateSuccessPopin.jsp" %>
	</div>
</c:if>

