<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>
<%@page import="abc.xyz.pts.bcs.common.util.SortColumnType; "%>

      
<thead>
                        
    <tr class="TopTitleBar">   

        <th>&nbsp;</th>
                                    
        <th title="<fmt:message key="referral.reference.number"/>" >
            <label for="refIdSortBy"><fmt:message key="referral.reference.number.short"/></label>
        </th>
        
        <th title="<fmt:message key="status"/>" >
            <label for="statusSortBy"><fmt:message key="status.short"/></label>
        </th>
                   
        <th title="<fmt:message key="severity"/>" >
            <label for="severitySortBy"><fmt:message key="severity.short"/></label>
        </th>
                                                                   
        <th title="<fmt:message key="referral.priority"/>" >
            <label for="prioritySortBy"><fmt:message key="referral.priority.short"/></label>
        </th>
                
        <th title="<fmt:message key="referral.hits"/>" >
            <label for="hitsSortBy"><fmt:message key="referral.hits.short"/></label>
        </th>
                                    
        <th title="<fmt:message key="referral.generated"/>" >
            <label for="generatedSortBy"><fmt:message key="referral.generated.short"/></label>
        </th>                       
                                  
        <th title="<fmt:message key="movement.status"/>" >
            <label for="mvstStatusSortBy"><fmt:message key="movement.status.short"/></label>
        </th>
                                    
        <th title="<fmt:message key="traveller.type"/>" >
            <label for="travellerTypeSortBy"><fmt:message key="traveller.type.short"/></label>
        </th>
                                                 
        <th title="<fmt:message key="travel.type"/>" >
            <label for="typeOfTravelSortBy"><fmt:message key="travel.type.short"/></label>
        </th>                                               
                     
        <th title="<fmt:message key="traveller.name"/>" >
            <label for="travellerNameSortBy"><fmt:message key="traveller.name.short"/></label>
        </th>
                                                      
        <th title="<fmt:message key="date.of.birth"/>" >
            <label for="dobSortBy"><fmt:message key="date.of.birth.short"/></label>
        </th>                       
                              
        <th title="<fmt:message key="traveller.document.number"/>" >
            <label for="docTypeNumSortBy"><fmt:message key="traveller.document.number.short"/></label>
        </th>
                                              
        <th title="<fmt:message key="traveller.nationality"/>" >
            <label for="nationalitySortBy"><fmt:message key="traveller.nationality.short"/></label>
        </th>
                                                 
        <th title="<fmt:message key="flight.carrier.and.number"/>" >
            <label for="carrierCodeNumSortBy"><fmt:message key="flight.carrier.and.number.short"/></label>
        </th>
         
        <th title="<fmt:message key="airport.departure"/>" >
            <label for="depAptSortBy"><fmt:message key="airport.departure.short"/></label>
        </th>
                                
        <th title="<fmt:message key="airport.arrival"/>" >
            <label for="arrAptSortBy"><fmt:message key="airport.arrival.short"/></label>
        </th>
                
        <th title="<fmt:message key="datetime.departure"/>" >
            <label for="depTimeSortBy"><fmt:message key="datetime.departure.short"/></label>
        </th>
                                 
        <th title="<fmt:message key="datetime.arrival"/>" >
            <label for="arrTimeSortBy"><fmt:message key="datetime.arrival.short"/></label>
        </th>               
         
        <th colspan="4" title="<fmt:message key="recommended.action"/>" >
            <label for="recActionCodeSortBy"><fmt:message key="recommended.action.short"/></label>
        </th> 
        
   </tr>      
                
</thead>
        