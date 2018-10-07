<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- movementStatus set outside this fragment, so that the fragment works with
     traveller or travellerSearchRecord beans --%>
     
<div title='<fmt:message key="travel.type.${travelType}"/>'>     
    <%-- Normal --%>
    <c:if test="${travelType == 'N'}">
        <img src="/bcs/images/icons/Normal24.png" />
    </c:if>
    <%-- Transit --%>
    <c:if test="${travelType == 'T'}">
        <img src="/bcs/images/icons/Transit24.png" />
    </c:if>
    <%-- Unmatched --%>
    <c:if test="${travelType == 'U'}">
        <c:choose>
            <c:when test="${requestContext.locale.language == 'ar'}">
                <img src="/bcs/images/icons/UnmatchedArb24.png" />
            </c:when>
            <c:otherwise>
                <img src="/bcs/images/icons/Unmatched24.png" />
            </c:otherwise>
        </c:choose>    
    </c:if>
    <%-- Transfer --%>
    <c:if test="${travelType == 'X'}">
        <img src="/bcs/images/icons/Transfer24.png" />
    </c:if>
</div>