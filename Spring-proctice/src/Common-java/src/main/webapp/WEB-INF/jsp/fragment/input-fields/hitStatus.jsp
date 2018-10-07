<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
<label for="hitStatus" class="helpinfolabel">
    <fmt:message key="hit.status" />
</label>
<form:select path="hitStatus" 
    items="${hitStatusList}" 
    itemValue="code" 
    itemLabel="description"/>