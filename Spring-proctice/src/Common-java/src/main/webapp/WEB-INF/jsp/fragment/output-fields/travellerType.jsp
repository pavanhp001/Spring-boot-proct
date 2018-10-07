<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- travellerType set outside this fragment, so that the fragment works with
     traveller or travellerSearchRecord beans --%>

<div title='<fmt:message key="traveller.sub.type.${travellerType}"/>'>
    <%-- Crew (Operating) --%>
    <c:if test="${travellerType == 'C'}">
        <img src="/bcs/images/icons/Crew24.png" />
    </c:if>
    <%-- Crew (Positioning) --%>
    <c:if test="${travellerType == 'X'}">
        <img src="/bcs/images/icons/PosCrew24.png" />
    </c:if>
    <%-- Passenger --%>
    <c:if test="${travellerType == 'P'}">
        <img src="/bcs/images/icons/Passenger24.png" />
    </c:if>
</div>