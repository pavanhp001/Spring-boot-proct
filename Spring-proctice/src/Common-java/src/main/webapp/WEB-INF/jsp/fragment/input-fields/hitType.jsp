<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
<label for="hitTypeList" class="helpinfolabel">
    <fmt:message key="hit.type" />
</label>
<form:select path="hitTypeList" 
    items="${hitTypeList}" 
    itemValue="code" 
    itemLabel="description"
    multiple="multiple"
    size="5" />