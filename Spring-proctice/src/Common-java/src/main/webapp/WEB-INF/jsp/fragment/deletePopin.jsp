<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- TODO: refactor to bring in line with other dialogs --%>
<div class="popupArea">

    <div class="dialogText">
        <fmt:message key='delete.confirmation' />
    </div>
    <br />
    <fieldset class="oneCol">
        <div class="searchControl">
            <button id="btnDeletePopin" class="textButton">
                <fmt:message key='button.ok'/>
            </button>
            <button id="btnCancelDelete" class="textButton"">
                <fmt:message key='cancel' />
            </button>
        </div>
    </fieldset>

</div>


