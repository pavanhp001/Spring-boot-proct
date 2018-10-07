<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="dialogText">
    <fmt:message key="user.details.added.confirm"/>
</div>

<fieldset class="oneCol">
	<div class="searchControl">
       <button id="btnYes" class="textButton" onclick="submitPopin(this);">
        	<fmt:message key='button.yes'/>
    	</button>
    	<button id="btnNo" class="textButton" onclick="submitPopin(this);">
        	<fmt:message key='button.no' />
    	</button>    
	</div>
</fieldset>
