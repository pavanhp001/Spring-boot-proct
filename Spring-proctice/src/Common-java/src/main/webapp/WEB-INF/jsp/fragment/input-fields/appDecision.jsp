<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<label for="commomLabelForAppDecision" class="helpinfolabel">
    <fmt:message key="decision" />
</label>
                    
<c:set var="allowTitle">
    <fmt:message key="allow"/>
</c:set>
<c:set var="denyTitle">
    <fmt:message key="deny"/>
</c:set>
<c:set var="undecidedTitle">
    <fmt:message key="undecided"/>
</c:set>

<div class="iconAndCheck" title="${allowTitle}" >
    <img src="/bcs/images/icons/Allow24.png" alt="${allowTitle}" />
    <form:checkbox path="decisionTypeAllow" value="ALLOW" title="${allowTitle}" />
</div>
<div class="iconAndCheck" title="${denyTitle}">        
    <img src="/bcs/images/icons/Deny24.png" alt="${denyTitle}" />
    <form:checkbox path="decisionTypeDeny" value="DENY"  title="${denyTitle}" />
</div>        
<div class="iconAndCheck" title="${undecidedTitle}">        
    <img src="/bcs/images/icons/Undecided24.png" alt="${undecidedTitle}" />
    <form:checkbox path="decisionTypeUndecided" value="UNDECIDED" title="${undecidedTitle}" />
</div>
