<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>

<u:checkRole roleGroup="REF_KEY_ICON">
  <a name="refKey" id="refKey"></a>
<%-- As part of QAT-604 the search is disabled --%>
<%--
  <a name="refKey" id="refKey" href="#"
    title="<fmt:message key="reference.key.quick.search"/>"></a>
--%>
</u:checkRole>
<div id="refKeyPopin" title="<fmt:message key='enter.reference.key'/>" class="hideOnLoad">
    <form action="/irisk/iRiskReferenceKeySearch.form" id="refKeyForm" name="refKeyForm" method="POST" >
        <fieldset>
            <legend><fmt:message key="enter.reference.key"/></legend>
            <label for="refKeyInput"><fmt:message key="enter.reference.key"/></label>
            <input type="text" maxlength="19" size="19" id="refKeyInput" name="referenceNo" />

            <button type="submit" id="btnRefKeySubmit">
                <fmt:message key="button.view.alert"/>
            </button>
        </fieldset>
   </form>
</div>
