<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<label for="commomLabelForAppDirection"  class="helpinfolabel">
    <fmt:message key="flight.direction" />
</label>

<c:set var="inboundTitle">
    <fmt:message key="inbound"/>
</c:set>
<c:set var="outboundTitle">
    <fmt:message key="outbound"/>
</c:set>
<c:set var="transitTransferTitle">
    <fmt:message key="ap.transit.transfer"/>
</c:set>

<div class="iconAndCheck" title="${inboundTitle}" >
    <img src="/bcs/images/icons/InboundDirection24.png" alt="${inboundTitle}" />
    <form:checkbox path="directionTypeInbound" value="INBOUND" cssClass="commomLabelForAppDirection" title="${inboundTitle}" />
</div>
<div class="iconAndCheck" title="${outboundTitle}">        
    <img src="/bcs/images/icons/OutboundDirection24.png" alt="${outboundTitle}" />
    <form:checkbox path="directionTypeOutbound" value="OUTBOUND" cssClass="commomLabelForAppDirection" title="${outboundTitle}" />
</div>        
<div class="iconAndCheck" title="${transitTransferTitle}">        
    <img src="/bcs/images/icons/TransitTransferDirection24.png" alt="${transitTransferTitle}" />
    <form:checkbox path="directionTypeTransferTransit" value="TRANSFER_TRANSIT" cssClass="commomLabelForAppDirection" title="${transitTransferTitle}" />
</div>
