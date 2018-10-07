<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="dialogText">
    <c:choose>
        <c:when test="${command.updateSuccess}">
            <fmt:message key="update.success"/>
        </c:when>
        <c:otherwise>
            <fmt:message key="update.failed"/>
        </c:otherwise>
    </c:choose>
</div>
<br>
<fieldset class="oneCol">
    <div class="searchButtons">
        <button id="btnOkey" class="textButton" onclick="closeDialogBySelector('#updatePopin');">
            <fmt:message key='button.ok'/>
        </button>
    </div>
</fieldset>
