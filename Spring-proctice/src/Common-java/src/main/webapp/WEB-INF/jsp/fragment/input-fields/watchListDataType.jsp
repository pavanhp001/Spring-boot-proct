<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<label for="commomLabelForWatchListData"  class="helpinfolabel">
    <fmt:message key="watch.list.data" />
</label>

<c:set var="personTitle">
    <fmt:message key="person"/>
</c:set>
<c:set var="documentTitle">
    <fmt:message key="document"/>
</c:set>
<c:set var="apRuleTitle">
    <fmt:message key="mail"/>
</c:set>
<c:set var="notifyHitTitle">
    <fmt:message key="notifyhit"/>
</c:set>
<c:set var="noHitTitle">
    <fmt:message key="nohit"/>
</c:set>


<div class="iconAndCheck" title="${personTitle}" >
    <img src="/bcs/images/icons/WLDPerson24.png" alt="${personTitle}" />
    <form:checkbox path="wlDataTypePerson" value="WLD_PERSON" cssClass="commomLabelForWatchListData" title="${personTitle}" />
</div>
<div class="iconAndCheck" title="${documentTitle}">     
    <img src="/bcs/images/icons/WLDDocument24.png" alt="${documentTitle}" />
    <form:checkbox path="wlDataTypeDocument" value="WLD_DOCUMENT" cssClass="commomLabelForWatchListData" title="${documentTitle}" />
</div>      
<div class="iconAndCheck" title="${apRuleTitle}">     
    <img src="/bcs/images/icons/WLDApRule24.png" alt="${apRuleTitle}" />
    <form:checkbox path="wlDataTypeApRule" value="WLD_AP_RULE" cssClass="commomLabelForWatchListData" title="${apRuleTitle}" />
</div>
<div class="iconAndCheck" title="${notifyHitTitle}">     
    <img src="/bcs/images/icons/WLDNotifyHit24.png" alt="${notifyHitTitle}" />
    <form:checkbox path="wlDataTypeNotifyHit" value="WLD_NOTIFY_HIT" cssClass="commomLabelForWatchListData" title="${notifyHitTitle}" />
</div>
<div class="iconAndCheck" title="${noHitTitle}">     
    <img src="/bcs/images/icons/WLDNoHit24.png" alt="${noHitTitle}" />
    <form:checkbox path="wlDataTypeNoHit" value="WLD_NO_HIT" cssClass="commomLabelForWatchListData" title="${noHitTitle}" />
</div>

