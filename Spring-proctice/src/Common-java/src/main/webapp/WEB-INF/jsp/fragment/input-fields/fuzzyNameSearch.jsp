<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="fuzzyNameSearchTitle">
    <fmt:message key="fuzzy.name.search"/>
</c:set>
    <form:checkbox id="fuzzyNameSearch" path="fuzzyNameSearch" value="TRUE" title="${fuzzyNameSearchTitle}"/>
    <label for="fuzzyNameSearch" class="labelNoBlock">
    	<fmt:message key="fuzzy.name.search" />
	</label>
