<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- directionCode is set outside this fragment, so that the fragment works with
     search results or details pages --%>

    <%-- Allow --%>
    <c:if test="${appDecisionCode == 'A'}">
        <div title='<fmt:message key="allow"/>'>
            <img src="/bcs/images/icons/Allow24.png" />
        </div>
    </c:if>
    
    <%-- Deny --%>
    <c:if test="${appDecisionCode == 'D'}">
        <div title='<fmt:message key="deny"/>'>
            <img src="/bcs/images/icons/Deny24.png" />
        </div>
    </c:if>
    
    <%-- Undecided --%>
    <c:if test="${appDecisionCode == 'U'}">
        <div title='<fmt:message key="undecided"/>'>
            <img src="/bcs/images/icons/Undecided24.png" />
        </div>
    </c:if>
