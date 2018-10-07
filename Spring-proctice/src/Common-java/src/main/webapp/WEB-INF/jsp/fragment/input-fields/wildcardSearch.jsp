<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="wildcardSearchTitle">
    <fmt:message key="wildcard.search"/>
</c:set>
    <form:checkbox id="wildCardSearch" path="wildCardSearch" value="TRUE" title="${wildcardSearchTitle}"/>
    <label for="wildcardSearch" class="labelNoBlock">
    	<fmt:message key="wildcard.search" />
	</label>
