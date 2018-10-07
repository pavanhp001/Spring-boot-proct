<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>

<a id="help" href="#" onClick="window.open('/help/<fmt:message key="help.${pageTitleKey}.page"/>', '_blank', 
										   'location=no,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=950,height=767');" 
    title="<fmt:message key="help"/>" class="topnav">
</a>
