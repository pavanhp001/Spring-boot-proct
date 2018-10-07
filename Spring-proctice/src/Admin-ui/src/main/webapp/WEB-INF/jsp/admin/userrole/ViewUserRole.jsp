<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u"%>

<c:set var="disableInputFields" value="true" scope="request"/>

<script type="text/javascript" src="/bcs/scripts/jsp-page/UserRole.js"></script>

<div id="mainContent">
    <form:form commandName="userRoleCommand" id="viewUserRoleForm" name="viewUserRoleForm" action="/admin/viewUserRole.form" method="POST">
        <form:hidden path="updateSuccess" id="updateSuccess"/>
        <jsp:include page="/WEB-INF/jsp/admin/userrole/fragment/roleDetail.jsp" />
    </form:form>
</div>

<div id="updatePopin" title="<fmt:message key='update.popin.title'/>" class="hideOnLoad">
    <c:set var="command" value="${userRoleCommand}" scope="request"/>
    <%@ include file="/WEB-INF/jsp/fragment/dialog/update.jsp" %>
</div>