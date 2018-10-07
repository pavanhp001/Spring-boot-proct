<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>				
        <label>
			<fmt:message key="last.password.change"/>
		</label>
		<div class="summaryValue">
	 		<fmt:formatDate pattern="${dateTimePattern}" value="${user.passwordChangeDate}"/>
		</div>
			
