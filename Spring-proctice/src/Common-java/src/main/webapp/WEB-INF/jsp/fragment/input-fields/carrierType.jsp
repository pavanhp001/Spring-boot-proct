<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:set var="airTitle">
    <fmt:message key="carrier.type.AIR"/>
</c:set>
<c:set var="generalAviationTitle">
    <fmt:message key="carrier.type.GENA"/>
</c:set>
<c:set var="seaTitle">
    <fmt:message key="carrier.type.SEA"/>
</c:set>
<c:set var="busTitle">
    <fmt:message key="carrier.type.BUS"/>
</c:set>

<ul>
<li>

<c:set var="carrierTypeErrors"><form:errors path="carrierType"/></c:set>    
    <c:choose>
        <c:when test="${not empty carrierTypeErrors}">
            <label  class="formError">
                <fmt:message key="carrier" />
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel">
                <fmt:message key="carrier" />
            </label>
        </c:otherwise>
    </c:choose>
  
</li>
<c:forEach items="${carrierList}" varStatus="carrierStatus" var="carrier">
    <c:if test="${carrier.code eq 'AIR'}">
        <li class="labelAndCheck">
        <input type="hidden" name="carrierTypesSelected" value="AIR"/>
            <form:checkbox path="carrierTypes" value="${carrier.code}" cssClass="commomLabelForCarrierType"/>
            <label>${airTitle}</label>
        </li>
    </c:if>
    <c:if test="${carrier.code eq 'GENA'}">
        <li class="labelAndCheck">
        <input type="hidden" name="carrierTypesSelected" value="GENA"/>
            <form:checkbox path="carrierTypes" value="${carrier.code}" cssClass="commomLabelForCarrierType"/>
            <label>${generalAviationTitle}</label>
        </li>
    </c:if>
    <c:if test="${carrier.code eq 'SEA'}">
        <li class="labelAndCheck">
        <input type="hidden" name="carrierTypesSelected" value="SEA"/>        
            <form:checkbox path="carrierTypes" value="${carrier.code}" cssClass="commomLabelForCarrierType"/>
            <label>${seaTitle}</label>
        </li>
    </c:if>    
    <c:if test="${carrier.code eq 'BUS'}">
        <li class="labelAndCheck">        
        <input type="hidden" name="carrierTypesSelected" value="BUS"/>
            <form:checkbox path="carrierTypes" value="${carrier.code}" cssClass="commomLabelForCarrierType"/>
            <label>${busTitle}</label>
        </li>
    </c:if>
</c:forEach>
</ul>