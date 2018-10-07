<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="toolTipText" value="" /> 

<div onMouseOver="openDiv('assessmentPanel_<c:out value="${rowNum.index}"/>');"
        onMouseOut="closeDiv('assessmentPanel_<c:out value="${rowNum.index}"/>');"
        class="alertStatusImg">    
        
    <c:choose>
        <c:when test="${currectFlight.atRisk}">
            <div title="<fmt:message key="referral.indicator.referral"/>">
                <img src="/bcs/images/icons/Referrals24.png" />
            </div>
        </c:when>
        <c:otherwise>
            <div title="<fmt:message key="referral.indicator.no.referral"/>">
                <img src="/bcs/images/icons/Blank24.png" />
            </div> 
        </c:otherwise>
    </c:choose>
                         
</div>

   