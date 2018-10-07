<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	

<c:set var="userScreenName" value="adminUserAdd" scope="request"/>
<script type="text/javascript" src="/bcs/scripts/Search.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		disableAirTextFields();
	   	shouldDisplayPopin();
	   	$('input[type="text"]').filter('[name^=air]').blur(function() {
	    	this.value = this.value.toUpperCase();
	    });
	    $('#location').blur(function() {
	    	this.value = this.value.toUpperCase();
	    });
	    focusForm();
	});

   function focusForm() {
	     $('#user.username').focus();
	  }

	function shouldDisplayPopin(){
			<c:if test="${addUserCommand.userAdded}">
                       $('#addUserPopin').dialog({modal: true, resizeable: false});
			</c:if>
	}

	//##################
	//###  popin button
	//##################
	function submitPopin(confirmValue){
            $('#addUserPopin').dialog('close');
		if (confirmValue.id=='btnYes'){ //Yes'){			
			$('#reset').click();
		}else{
			$('#search').click();		
		}
	}
</script>
<div id="mainContent">
		<form:form commandName="addUserCommand" action="/admin/adminUserAdd.form" name="adminUserAdd" id="adminUserAdd" method="POST">  
			<input type="hidden" name="action" id="action"/>
			<!-- Show asterisked mandatory fields -->
	        <c:set var="showStar" value="true" scope="request"/>
	        <jsp:include page="/WEB-INF/jsp/fragment/pageErrors.jsp"/>
	        <jsp:include page="/WEB-INF/jsp/admin/jsp-fragments/UserFields.jsp" />
	        <button  type="submit" class="hideOnLoad"  id="search" name="search" value="search"><fmt:message key="button.search" /></button>	        
	    </form:form>
</div>

	<div id="addUserPopin" title="<fmt:message key='admin.add.user'/>" class="hideOnLoad">
		<%-- problems with fmt tags if jsp:include used--%>
		<%@ include file="/WEB-INF/jsp/admin/jsp-popin/AddUserPopin.jsp" %>
	</div>



