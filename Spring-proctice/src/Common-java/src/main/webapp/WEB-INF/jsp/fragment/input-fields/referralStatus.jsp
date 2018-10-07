<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>                                
 
<label for="referralStatusList" class="helpinfolabel">
    <fmt:message key="referral.status" />
</label>
<form:select path="referralStatusList" 
    items="${referralStatusList}" 
    itemValue="code" 
    itemLabel="description" 
    multiple="multiple"
    size="5" />
