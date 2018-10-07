<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="dialogText">
    <fmt:message key='delete.confirmation'/>
</div>

<fieldset class="oneCol">
	<div class="searchControl">
		<button id="btnPopDeleteUser" class="textButton" onclick="deleteUser();">
        	<fmt:message key='button.ok'/>
    	</button>
    	<button id="btnCancel" class="textButton" onclick="closeDialogBySelector('#deleteUserPopin');">
        	<fmt:message key='cancel' />
    	</button>    
	</div>
</fieldset>

	