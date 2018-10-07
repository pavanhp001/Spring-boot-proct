<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${referralStatus eq 'OPEN'}">
        <div title="<fmt:message key="traveller.referral.status.open.indicator"/>">
            <img src="/bcs/images/icons/OpenRef24.png" />
        </div>
    </c:when>
    <c:when test="${referralStatus eq 'CLOSED'}">
        <div title="<fmt:message key="traveller.referral.status.closed.indicator"/>">
            <img src="/bcs/images/icons/ClosedRef24.png" />
        </div>
    </c:when>
    <c:when test="${referralStatus eq 'NEW'}">
        <div title="<fmt:message key="traveller.referral.status.new.indicator"/>">
            <img src="/bcs/images/icons/NewRef24.png" />
        </div>
    </c:when>
    <c:when test="${referralStatus eq 'UNQ'}">
        <div title="<fmt:message key="traveller.referral.status.unqualified.indicator"/>">
            <img src="/bcs/images/icons/UnqualRef24.png" />
        </div>
    </c:when>
    <c:otherwise>
        <div title="<fmt:message key="referral.indicator.no.traveller.referral"/>">
            <img src="/bcs/images/icons/Blank24.png" /> 
        </div>
    </c:otherwise>
</c:choose>