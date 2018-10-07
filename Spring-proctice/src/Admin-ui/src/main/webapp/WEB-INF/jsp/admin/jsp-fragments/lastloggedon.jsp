<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  				
    <label>
        <fmt:message key="last.logged.on" />
    </label>
	<div class="summaryValue">
	 	<fmt:formatDate pattern="${dateTimePattern}" value="${user.lastLoginDate}"/>
	</div>
		
         

