<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>
<%@page import="abc.xyz.pts.bcs.common.util.SortColumnType; "%>

<form:form commandName="tableCommand" method="POST" action="${pageContext.request.contextPath}/iRiskReferralSearch.form">

    <button type="submit" class="hideOnLoad" id="sortButton" name="sort" value="sort">Sort Column</button>
    <form:input type="hidden" path="sortBy" />
                    
    <thead>
                
    <c:set var="sortClass" scope="page">
        <c:choose>
            <c:when test="${tableCommand.descending}">class="sorteddesc"</c:when>
            <c:otherwise>class="sortedasc"</c:otherwise>
        </c:choose>
    </c:set>          

    <tr class="TopTitleBar sortable">   

        <th class="unSortableCol">&nbsp;</th>
                
        <c:set var="sortByRefId" value="<%= SortColumnType.REFERRAL_ID.getJspColName() %>" />
        <th  <c:if test="${tableCommand.sortBy == sortByRefId}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByRefId}');" title="<fmt:message key="referral.reference.number"/>">
            <fmt:message key="referral.reference.number.short" />
        </th>                
        
        <c:set var="sortByStatus" value="<%= SortColumnType.REFERRAL_STATUS.getJspColName() %>" />
        <th <c:if test="${tableCommand.sortBy == sortByStatus}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByStatus}');" title="<fmt:message key="status"/>" >
            <fmt:message key="status.short"/>
        </th>
        
        <c:set var="sortBySeverity" value="<%= SortColumnType.REFERRAL_SEVERITY.getJspColName() %>" />                      
        <th <c:if test="${tableCommand.sortBy == sortBySeverity}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortBySeverity}');" title="<fmt:message key="severity"/>" >
            <fmt:message key="severity.short"/>
        </th>
                                                
        <c:set var="sortByPriority" value="<%= SortColumnType.REFERRAL_PRIORITY.getJspColName() %>" />                      
        <th <c:if test="${tableCommand.sortBy == sortByPriority}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByPriority}');" title="<fmt:message key="referral.priority"/>" >
            <fmt:message key="referral.priority.short"/>
        </th>

        <c:set var="sortByHits" value="<%= SortColumnType.REFERRAL_HITS_TOTAL.getJspColName() %>" />                        
        <th <c:if test="${tableCommand.sortBy == sortByHits}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByHits}');" title="<fmt:message key="referral.hits"/>" >
            <fmt:message key="referral.hits.short"/>
        </th>
                 
        <c:set var="sortByGenerated" value="<%= SortColumnType.REFERRAL_GENERATED_ON.getJspColName() %>" />                     
        <th <c:if test="${tableCommand.sortBy == sortByGenerated}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByGenerated}');" title="<fmt:message key="referral.generated"/>" >
            <fmt:message key="referral.generated.short"/>
        </th>                       
                 
        <c:set var="sortByMvtType" value="<%= SortColumnType.REFERRAL_MVT_TYPE.getJspColName() %>" />                       
        <th <c:if test="${tableCommand.sortBy == sortByMvtType}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByMvtType}');" title="<fmt:message key="movement.status"/>" >
            <fmt:message key="movement.status.short"/>
        </th>
                 
        <c:set var="sortByTravellerType" value="<%= SortColumnType.REFERRAL_TRAVELLER_TYPE.getJspColName() %>" />                       
        <th <c:if test="${tableCommand.sortBy == sortByTravellerType}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByTravellerType}');" title="<fmt:message key="traveller.type"/>" >
            <fmt:message key="traveller.type.short"/>
        </th>
                            
        <c:set var="sortByTypeOfTravel" value="<%= SortColumnType.REFERRAL_TYPE_OF_TRAVEL.getJspColName() %>" />                        
        <th <c:if test="${tableCommand.sortBy == sortByTypeOfTravel}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByTypeOfTravel}');" title="<fmt:message key="travel.type"/>" >
            <fmt:message key="travel.type.short"/>
        </th>                                               
        
        <c:set var="sortByTravellerName" value="<%= SortColumnType.SURNAME_FORENAME.getJspColName() %>" />                      
        <th <c:if test="${tableCommand.sortBy == sortByTravellerName}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByTravellerName}');" title="<fmt:message key="traveller.name"/>" >
            <fmt:message key="traveller.name.short"/>
        </th>
                                        
        <c:set var="sortByDob" value="<%= SortColumnType.REFERRAL_DATE_OF_BIRTH.getJspColName() %>" />                      
        <th <c:if test="${tableCommand.sortBy == sortByDob}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByDob}');" title="<fmt:message key="date.of.birth"/>" >
            <fmt:message key="date.of.birth.short"/>
        </th>                       
        
        <c:set var="sortByDocTypeNum" value="<%= SortColumnType.REFERRAL_DOC_TYPE_NUM.getJspColName() %>" />                        
        <th <c:if test="${tableCommand.sortBy == sortByDocTypeNum}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByDocTypeNum}');" title="<fmt:message key="traveller.document.number"/>" >
            <fmt:message key="traveller.document.number.short"/>
        </th>
                                
        <c:set var="sortByNationality" value="<%= SortColumnType.NATIONALITY.getJspColName() %>" />                     
        <th <c:if test="${tableCommand.sortBy == sortByNationality}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByNationality}');" title="<fmt:message key="traveller.nationality"/>" >
            <fmt:message key="traveller.nationality.short"/>
        </th>
                                
        <c:set var="sortByCarrierCodeNum" value="<%= SortColumnType.REFERRAL_CARRIER_CODE_NUM.getJspColName() %>" />                        
        <th <c:if test="${tableCommand.sortBy == sortByCarrierCodeNum}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByCarrierCodeNum}');" title="<fmt:message key="flight.carrier.and.number"/>" >
            <fmt:message key="flight.carrier.and.number.short"/>
        </th>
                        
        <c:set var="sortByDepApt" value="<%= SortColumnType.DEP_AIRPORT_CODE.getJspColName() %>" />                     
        <th <c:if test="${tableCommand.sortBy == sortByDepApt}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByDepApt}');" title="<fmt:message key="airport.departure"/>" >
            <fmt:message key="airport.departure.short"/>
        </th>
                 
        <c:set var="sortByArrApt" value="<%= SortColumnType.ARR_AIRPORT_CODE.getJspColName() %>" />                     
        <th <c:if test="${tableCommand.sortBy == sortByArrApt}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByArrApt}');" title="<fmt:message key="airport.arrival"/>" >
            <fmt:message key="airport.arrival.short"/>
        </th>
                    
        <c:set var="sortByDepTime" value="<%= SortColumnType.REFERRAL_DEPARTURE_TIME.getJspColName() %>" />                     
        <th <c:if test="${tableCommand.sortBy == sortByDepTime}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByDepTime}');" title="<fmt:message key="datetime.departure"/>" >
            <fmt:message key="datetime.departure.short"/>
        </th>
                
        <c:set var="sortByArrTime" value="<%= SortColumnType.REFERRAL_ARRIVAL_TIME.getJspColName() %>" />                       
        <th <c:if test="${tableCommand.sortBy == sortByArrTime}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByArrTime}');" title="<fmt:message key="datetime.arrival"/>" >
            <fmt:message key="datetime.arrival.short"/>
        </th>               
         
        <c:set var="sortByRecActionCode" value="<%= SortColumnType.REFERRAL_REC_ACTION_CODE.getJspColName() %>" />
        <th colspan="4" <c:if test="${tableCommand.sortBy == sortByRecActionCode}">${sortClass}</c:if>
            onclick="changeSortOrder('${sortByRecActionCode}');" title="<fmt:message key="recommended.action"/>" >
            <fmt:message key="recommended.action.short"/>
        </th> 
        
   </tr>      
                
</thead>
        
</form:form>
