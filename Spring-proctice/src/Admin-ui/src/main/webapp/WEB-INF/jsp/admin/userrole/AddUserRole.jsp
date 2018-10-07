<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u"%>

<script type="text/javascript" src="/bcs/scripts/Util.js"></script>
<script type="text/javascript" src="/bcs/scripts/jsp-page/UserRole.js"></script>

<div id="mainContent">
    <form:form commandName="userRoleCommand" id="addUserRoleForm" name="addUserRoleForm" action="/admin/addUserRole.form" method="POST">
        <form:hidden path="userRoleAdded" id="userRoleAdded"/>
        <jsp:include page="/WEB-INF/jsp/admin/userrole/fragment/roleDetail.jsp" />
    </form:form>
</div>

<div id="addUserRolePopin" title="<fmt:message key='admin.user.role.add'/>" class="hideOnLoad">
    <%@ include file="/WEB-INF/jsp/fragment/dialog/add.jsp" %>
</div>
