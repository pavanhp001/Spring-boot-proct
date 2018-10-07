<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${riskAssessmentStatus eq 'NOTSTARTED'}">
        <div title="<fmt:message key="traveller.risk.assessment.not.started.status.indicator"/>">
            <c:choose>
                <c:when test="${requestContext.locale.language == 'ar'}">
                    <img src="/bcs/images/icons/RANotStartedArb24.png" />
                </c:when>
                <c:otherwise>
                    <img src="/bcs/images/icons/RANotStarted24.png" />
                </c:otherwise>
            </c:choose>    
        </div>
    </c:when>
    <c:when test="${riskAssessmentStatus eq 'INPROGRESS'}">
        <div title="<fmt:message key="traveller.risk.assessment.in.progress.status.indicator"/>">
            <img src="/bcs/images/icons/RAInProgress24.png" />
        </div>
    </c:when>      
    <c:when test="${riskAssessmentStatus eq 'FAILED'}">
        <div title="<fmt:message key="traveller.risk.assessment.failed.status.indicator"/>">
            <img src="/bcs/images/icons/RAIncomplete24.png" />
        </div>
    </c:when>
    <c:otherwise>
        <div title="<fmt:message key="traveller.risk.assessment.completed.status.indicator"/>">
            <img src="/bcs/images/icons/RAComplete24.png" /> 
        </div>
    </c:otherwise>
</c:choose>
