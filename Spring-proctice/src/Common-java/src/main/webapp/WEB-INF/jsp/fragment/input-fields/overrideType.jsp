<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<label for="commomLabelForOverride"  class="helpinfolabel">
    <fmt:message key="override.type" />
</label>

<c:set var="agentTitle">
    <fmt:message key="override.type.AGENT"/>
</c:set>
<c:set var="govtTitle">
    <fmt:message key="override.type.GOVT"/>
</c:set>
<c:set var="noneTitle">
    <fmt:message key="override.type.NONE"/>
</c:set>

<div class="iconAndCheck" title="${agentTitle}" >
    <img src="/bcs/images/icons/AirOverride24.png" alt="${agentTitle}" />
    <form:checkbox path="overrideTypeAgent" value="AGENT" cssClass="commomLabelForOverride" title="${agentTitle}" />
</div>
<div class="iconAndCheck" title="${govtTitle}">        
    <img src="/bcs/images/icons/GovOverride24.png" alt="${govtTitle}" />
    <form:checkbox path="overrideTypeGovernment" value="GOVT" cssClass="commomLabelForOverride" title="${govtTitle}" />
</div>        
<div class="iconAndCheck" title="${noneTitle}">        
    <img src="/bcs/images/icons/NoOverride24.png" alt="${noneTitle}" />
    <form:checkbox path="overrideTypeNone" value="NONE" cssClass="commomLabelForOverride" title="${noneTitle}" />
</div>