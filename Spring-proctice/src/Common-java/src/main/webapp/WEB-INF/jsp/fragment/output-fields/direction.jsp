<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- directionCode is set outside this fragment, so that the fragment works with
     search results or details pages --%>

    <%-- Inbound --%>
    <c:if test="${directionCode == 'I'}">
        <div title='<fmt:message key="inbound"/>'>
            <img src="/bcs/images/icons/InboundDirection24.png" />
        </div>
    </c:if>
    
    <%-- Outbound --%>
    <c:if test="${directionCode == 'O'}">
        <div title='<fmt:message key="outbound"/>'>
            <img src="/bcs/images/icons/OutboundDirection24.png" />
        </div>
    </c:if>
    
    <%-- Transit/Transfer --%>
    <c:if test="${directionCode == 'T'}">
        <div title='<fmt:message key="ap.transit.transfer"/>'>
            <img src="/bcs/images/icons/TransitTransferDirection24.png" />
        </div>
    </c:if>
