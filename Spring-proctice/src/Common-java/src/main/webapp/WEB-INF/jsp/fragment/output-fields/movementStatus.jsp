<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- movementStatus set outside this fragment, so that the fragment works with
     traveller or travellerSearchRecord beans --%>

<div title='<fmt:message key="movement.status.${movementStatus}"/>'>
    <%-- Cancelled --%>
    <c:if test="${movementStatus == 'C'}">
        <img src="/bcs/images/icons/Cancelled24.png" />
    </c:if>
    <%-- Denied --%>
    <c:if test="${movementStatus == 'D'}">
        <img src="/bcs/images/icons/Denied24.png" />
    </c:if>
    <%-- Expected --%>
    <c:if test="${movementStatus == 'E'}">
        <img src="/bcs/images/icons/Expected24.png" />
    </c:if>
    <%-- No Movement Specified --%>
    <c:if test="${movementStatus == 'N'}">
        <img src="/bcs/images/icons/NoMovement24.png" />
    </c:if>
</div>