<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- overrideType set outside this fragment, so that the fragment works with
     traveller or travellerSearchRecord beans --%>

    <%-- Airline --%>
    <c:if test="${overrideType == 'AGENT' || overrideType == 'A'}">
        <div title='<fmt:message key="override.type.AGENT"/>'>
            <img src="/bcs/images/icons/AirOverride24.png" />
        </div>
    </c:if>
    <%-- Government --%>
    <c:if test="${overrideType == 'GOVT' || overrideType == 'G'}">
        <div title='<fmt:message key="override.type.GOVT"/>'>
            <img src="/bcs/images/icons/GovOverride24.png" />
        </div>
    </c:if>
    <%-- Otherwise, none --%>
    <c:if test="${overrideType == ''}">
        <div>
            <img src="/bcs/images/icons/Blank24.png" />
        </div>
    </c:if>
</div>