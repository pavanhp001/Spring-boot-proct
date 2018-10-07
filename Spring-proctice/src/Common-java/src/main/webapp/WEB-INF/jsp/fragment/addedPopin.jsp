<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="popupArea">

    <div class="popupMessage">
        <fmt:message key='add.successful' />
    </div>
    
    <div class="popupButtons">
        <button id="btnYes" onclick="resetForm()">
            <fmt:message key='button.yes' />
        </button>
        <button id="btnNo" onclick="redirectToSearchView()">
            <fmt:message key='button.no'/>
        </button>
    </div>
</div>