<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u"%>

<c:set var="readonlyPrimaryField" value="true" scope="request"/>

<script type="text/javascript" src="/bcs/scripts/Util.js"></script>

<div id="mainContent">
    <form:form commandName="userRoleCommand" id="editUserRoleForm" name="editUserRoleForm" action="/admin/editUserRole.form" method="POST">
        <input type="hidden" name="selectedCode" value="${userRoleCommand.code}" />
        <jsp:include page="/WEB-INF/jsp/admin/userrole/fragment/roleDetail.jsp" />
    </form:form>
</div>
