<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
    <label for="appResultType" class="helpinfolabel">
        <fmt:message key="status" />
    </label>

    <form:select path="appResultType">
        <form:option value="" label=""/>
        <form:options items="${appResultTypeList}" itemValue="code" itemLabel="description"/>
    </form:select>
