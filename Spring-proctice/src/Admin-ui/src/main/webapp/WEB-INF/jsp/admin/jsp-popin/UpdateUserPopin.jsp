<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="dialogText">
    <fmt:message key='update.confirmation'/>
</div>

<fieldset class="oneCol">
    <div class="searchControl">
     	<button id="btnUpdateUser" class="textButton" onclick="updateUser();">
      		<fmt:message key='button.ok'/>
  		</button>
  		<button id="btnCancel" class="textButton" onclick="closeDialogBySelector('#updateUserPopin');">
      		<fmt:message key='cancel' />
     	</button>    
	</div>
</fieldset>
