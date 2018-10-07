<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- transitFlag is set outside this fragment --%>

    <%-- Yes! --%>
    <c:if test="${transitFlag == 'Y'}">
        <div title='<fmt:message key="boolean.Y"/>'>
            <img src="/bcs/images/icons/TransitYes24.png" />
        </div>
    </c:if>
    
    <%-- No! --%>
    <c:if test="${transitFlag == 'N'}">
        <div title='<fmt:message key="boolean.N"/>'>
            <img src="/bcs/images/icons/TransitNo24.png" />
        </div>
    </c:if>

