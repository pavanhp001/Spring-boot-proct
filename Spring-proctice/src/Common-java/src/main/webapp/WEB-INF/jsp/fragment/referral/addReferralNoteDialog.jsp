<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- Usage:

     This div should be configured in the page JS as a jQuery UI dialog, using
     something along the lines of

                    $('#addNoteDialog').dialog({...});

    Note that due to the requirement to auto-refresh the logs list after adding a
    new note, the add note functionality is implemented with Ajax; consquently
    there is not need for form params etc here.
--%>

<div id="addNoteDialog" class="addNoteDialog" title="<fmt:message key="referral.add.note"/>">
    <%-- <form:form commandName="referralAddNoteCommand" id="addNoteForm" method="POST" action="/irisk/iRiskReferralSearch.form"> --%>
        <%-- Need to know which referral this closure refers to; set up in JS when dialog is displayed --%>
        <input type="hidden" name="refId" />
        <span ><fmt:message key="referral.notes"/></span><br/>
        <textarea style="width: 95%;" class="popUpAddNote" rows="5" id="noteText" onkeyup="limitText(this, 200);" onmouseout="limitText(this, 200);" onblur="limitText(this, 200);"></textarea><br/>
        <button type="submit" class="save-notes-button button" id="addNoteSave" name="addNoteSave" >
           <fmt:message key="button.save"/>
        </button>
        <button type="button" class="addNoteCancel button" id="closeCancel" name="closeCancel" >
           <fmt:message key="button.cancel"/>
        </button>
     <%-- </form:form> --%>
</div>