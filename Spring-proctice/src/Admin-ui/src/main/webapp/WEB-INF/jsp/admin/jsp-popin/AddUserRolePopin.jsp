<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="dialogText">
    <fmt:message key="user.role.details.added.confirm"/>
</div>

<fieldset class="oneCol">
	<div class="searchControl">
       <button id="btnYes" class="textButton" onclick="resetForm();">
        	<fmt:message key='button.yes'/>
    	</button>
    	<button id="btnNo" class="textButton" onclick="redirectToSearchView();">
        	<fmt:message key='button.no' />
    	</button>    
	</div>
</fieldset>
