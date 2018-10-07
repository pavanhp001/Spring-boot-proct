<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript">
	$(document).ready(function(){
		
		$('#forename').change(function() {
			disableResetPassword();
		});
		
		$('#lastname').change(function() {
			disableResetPassword();
		});
			
		$('#mobileNumber').change(function() {
			disableResetPassword();
		});
		
		$('#roleSelect').change(function() {
			disableResetPassword();
		});
		
		$('#userStatus').change(function() {
			disableResetPassword();
		});
		
		$('#email').change(function() {
			disableResetPassword();
		});
		
		$('#location').change(function() {
			disableResetPassword();
		}); 
		
		$('#username').attr('disabled', true);
		disableAirTextFields();

	   	$('input[type="text"]').filter('[name^=air]').blur(function() {
	    	this.value = this.value.toUpperCase();
	    });
	    $('#location').blur(function() {
	    	this.value = this.value.toUpperCase();
	    });
	    
		$('#btnCancel').click(function() {
			$('#btnCancelSubmit').click();	
		 });


		var updatePopin = $('#displayResetPasswordPopin').val();
		var updateTitle = $('#passwordPopupTitle').val();;
	    if(updatePopin == 'yes'){
	    	$('#displayResetPasswordPopin').attr('value','');
		       $('#updatePopin').dialog({modal: true, resizeable: false});
		       $( "#updatePopin" ).dialog( "option", "title", updateTitle);
	           $('#btnYes').focus();	 
	                
	    }
	    
	 <spring:bind path="adminUserUpdateCommand.updateValidated">
	 	<c:if test="${status.value}">
	 		addUpdateUserConfirmPopin();
	 	</c:if>
	 </spring:bind>
	 
	    focusForm();
	});

 
   function focusForm() {
	     $('#foreName').focus();
	  }


//#########################
//###  Update User  Confirm Popin
//#########################
	function addUpdateUserConfirmPopin(){
            $('#updateUserPopin').dialog({modal: true, resizeable: false});
		$('#btnCancelfocus').focus();
		return false;
	}

	//called from update user confirm popin
	function updateUser(){
		closeDialogBySelector('#updateUserPopin');
		$('#updateconfirmButton').click();
	}
	
	function closeDialogBySelector(selector) {
	    closeDialog($(selector));
	}

	//###############################################################
	//###  Disable Reset Password button when user edits user details
	//###############################################################
	
	function disableResetPassword() {
		if($('#btnResetPassword')){
			$('#btnResetPassword').attr('disabled', true);
		}
	}	

</script>

<c:set var="userScreenName" value="adminUserUpdate" scope="request"/>

<div id="mainContent"> 
		<form:form commandName="adminUserUpdateCommand" id="adminUserUpdate" name="adminUserUpdate" action="adminUserUpdate.form" method="POST">    	
			<input type="hidden" value="<fmt:message key="admin.password.user"/>" id="passwordPopupTitle" name="passwordPopupTitle"  / >
			<input type="hidden" name="displayResetPasswordPopin" id="displayResetPasswordPopin" value="<c:out value="${adminUserUpdateCommand.displayResetPasswordPopin}"/>"/>
			<jsp:include page="/WEB-INF/jsp/fragment/pageErrorsMessages.jsp"/>
	        <c:set var="showStar" value="true" scope="request"/>
	        <jsp:include page="/WEB-INF/jsp/admin/jsp-fragments/UserFields.jsp" />
	    	<input type="hidden" id="user.username" name="user.username" value="<c:out value="${adminUserUpdateCommand.user.username}"/>"/>
	    	<input type="hidden" id="user.modifyTimestamp" name="user.modifyTimestamp" value="<c:out value="${adminUserUpdateCommand.user.modifyTimestamp}"/>"/>

			<div id="updateUserPopin" title="<fmt:message key='update.user'/>" class="hideOnLoad" >
					<%-- problems with fmt tags if jsp:include used--%>
					<%@ include file="/WEB-INF/jsp/admin/jsp-popin/UpdateUserPopin.jsp" %>
			</div>
		</form:form>
		<form:form commandName="adminUserProfileCommand" id="adminUserProfileForm" name="adminUserProfileForm" action="adminUserProfile.cform" method="POST"> 
			<input type="hidden" id="userProfileUsername" name="user.username" value="${user.username}"/>
			 <button class="hideOnLoad" type="submit"  id="btnCancelSubmit" >
                   <fmt:message key="button.cancel" />
              </button>
		</form:form>
</div>
<div id="updatePopin" title="<fmt:message key='update.popin.title'/>" class="hideOnLoad">
    <%@ include file="/WEB-INF/jsp/admin/jsp-popin/ResetPassword.jsp" %>
</div> 






