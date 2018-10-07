<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
	$(document).ready(function(){
	    <c:if test="${!empty mandatoryChangePassword}">
	    	$('#btnHome').hide();
	    </c:if>

		var updatePopin = $('#displayUpdatePopin').val();
		var updateTitle = $('#passwordPopupTitle').val();;
	    if(updatePopin == 'yes'){
	    	$('#displayUpdatePopin').attr('value','');
		       $('#updatePopin').dialog({modal: true, resizeable: false});
		       $( "#updatePopin" ).dialog( "option", "title", updateTitle);
	           $('#btnYes').focus();	 
	                
	    }
	    
	    focusForm();
	});
    
	function focusForm() {
	     $('#oldPassword').focus();
	  }

	function closeDialogBySelector(selector) {
	    closeDialog($(selector));
	}	  

    function gotoHome() {
    	document.location="/home";
    }
</script>

<div id="mainContent">
	<form:form commandName="adminChangePasswordCommand"
		action="/admin/adminChangePassword.form" name="adminChangePassword"
		id="adminChangePassword" method="POST">
		<form:hidden path="displayUpdatePopin" id="displayUpdatePopin" />
		<input type="hidden" value="<fmt:message key="admin.password.user"/>"
			id="passwordPopupTitle" name="passwordPopupTitle" />

		<jsp:include page="/WEB-INF/jsp/fragment/pageErrors.jsp" />
		<jsp:include page="/WEB-INF/jsp/fragment/pageMessages.jsp" />

		<div id="searchFormDiv">
			<div id="searchCriteria">

				<fieldset id="generalSearch">
					<fieldset class="threeCol">
						<div class="fieldsetRow">
							<jsp:include page="/WEB-INF/jsp/admin/changepassword/jsp-fragments/username.jsp" />
						</div>
						<div class="fieldsetRow">
							<jsp:include page="/WEB-INF/jsp/admin/changepassword/jsp-fragments/oldpassword.jsp" />
						</div>
					</fieldset>

					<fieldset class="threeCol">
						<div class="fieldsetRow"></div>
						<div class="fieldsetRow">
							<jsp:include page="/WEB-INF/jsp/admin/changepassword/jsp-fragments/newpassword.jsp" />
						</div>
					</fieldset>

					<fieldset class="threeCol">
						<div class="fieldsetRow"></div>
						<div class="fieldsetRow">
							<jsp:include page="/WEB-INF/jsp/admin/changepassword/jsp-fragments/confirmpassword.jsp" />
						</div>
					</fieldset>
				</fieldset>


				<%-- QAT-951: Password rules just need to appear under the Old Password field,
	                 so we can add them here instead of inside the fieldset --%>
				<fieldset class="oneCol">
					<div class="fieldComment">
						<fmt:message key="password.rules.text" />
					</div>
				</fieldset>
				<fieldset class="oneCol">
					<div class="tableActionsControl">
						<button type="submit" name="saveNewPassword"
							value="saveNewPassword" class="textButton">
							<fmt:message key="button.submit" />
						</button>
						<button id="btnHome" onclick="gotoHome();" class="textButton">
							<fmt:message key="cancel" />
						</button>
					</div>
				</fieldset>


			</div>
		</div>
	</form:form>


</div>
<div id="updatePopin" title="<fmt:message key='update.popin.title'/>"
	class="hideOnLoad">
	<%@ include file="/WEB-INF/jsp/admin/jsp-popin/UpdatePassword.jsp"%>
</div>