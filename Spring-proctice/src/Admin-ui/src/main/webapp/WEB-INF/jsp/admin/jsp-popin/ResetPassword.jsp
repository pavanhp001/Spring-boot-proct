<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="dialogText">
     <fmt:message key="password.reset.confirm"/>
</div>
<fieldset class="oneCol">
    <div class="searchControl">
		<button id="btnOkey" class="textButton" onclick="closeDialogBySelector('#updatePopin');" >
         	<fmt:message key='button.ok'/>
     	</button>
	</div>
</fieldset>

