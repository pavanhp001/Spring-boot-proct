<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="popupArea">

    <div class="popupMessage">
        <fmt:message key='delete.confirmation' />
    </div>

    <div class="popupButtons">
        <button id="btnDeleteCodePopin" >
            <fmt:message key='button.ok'/>
        </button>
        <button id="btnCancelDelete" >
            <fmt:message key='cancel' />
        </button>
    </div>

</div>


