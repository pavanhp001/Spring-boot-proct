<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>
<%@page import="abc.xyz.pts.bcs.common.util.SortColumnType; "%>
            
<tbody>         <%-- Loop through referrals --%>
    <c:forEach var="referral" items="${referrals}" varStatus="resultRow">       
        <c:set var="rowCount" value="${resultRow.count}" scope="request"/>

            <u:checkRole roleGroup="REFERRAL_UNQUALIFIED_WRITE">
                <input type="hidden" name="enableRuleRadioButtonsAllowed" id="enableRuleRadioButtonsAllowed" value="true" />
            </u:checkRole>
            
            <tr class="referralSummary" onmouseover="CommonReferral.referralSummaryHighlight(this);" onmouseout="CommonReferral.referralSummaryUnhighlight(this);" rel='Per<c:out value="${rowCount}" />' id="<c:out value="${referral.referralNum}"/>" 
                data-travellerid="<c:out value="${referral.traveller.travellerId}"/>" 
                data-flightid="<c:out value="${referral.flight.flightSegmentId}"/>"
                data-status="<c:out value="${referral.referralStatus}"/>">
                            
            <td style="white-space: nowrap;" class="referralSummaryExpand" onClick="CommonReferral.getAllHits(this);" rel='Per<c:out value="${rowCount}" />'><img id="expandIcon" src="/bcs/images/icons/Expand24.png" alt="" /></td>
            
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:out default="" value="${referral.referralNum}" />
            </td> 
            <c:set var="referralStatus" value="${referral.referralStatus}" />               
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                <%@ include file="/WEB-INF/jsp/fragment/output-fields/referralStatus.jsp"%>
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData severityLevel-${referral.referralSeverity}" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:out default="" value="${referral.referralSeverity}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:out default="" value="${referral.highestHitPriority}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:out default="" value="${referral.totalHits}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <fmt:formatDate pattern="${dateTimePattern}" value="${referral.createdDatetime.time}" />
            </td>
            
            <c:set var="movementStatus" value="${referral.traveller.movementStatus}" />               
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />' >          
                 <%@ include file="/WEB-INF/jsp/fragment/output-fields/movementStatus.jsp" %>
            </td>
            <c:set var="travellerType" value="${referral.traveller.travellerType}" />               
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />' > 
                 <%@ include file="/WEB-INF/jsp/fragment/output-fields/travellerType.jsp" %>
            </td>
            <c:set var="travelType" value="${referral.traveller.typeOfTravel}" />
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />' >
                <%@ include file="/WEB-INF/jsp/fragment/output-fields/travelType.jsp" %>
            </td>
            <td class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:if test="${pageContext.request.contextPath == '/irisk'}">
                    <a class="travellerReferralLink">
                 </c:if>
                 <c:out default="" value="${referral.traveller.fullname}" />
                 <c:if test="${pageContext.request.contextPath == '/irisk' }">
                    </a>
                 </c:if>
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <fmt:formatDate pattern="${datePattern}" value="${referral.traveller.birthDate.time}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:out default="" value="${referral.traveller.docData}" />
            </td>
                 <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />' title='<fmt:message key="country.code.${referral.traveller.nationality}"/>' ><c:out default="" value="${referral.traveller.nationality}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:out default="" value="${referral.flight.carrierCode}" /><c:out default="" value="${referral.flight.carrierNumber}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:out default="" value="${referral.flight.depAirportCode}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <c:out default="" value="${referral.flight.appAirportCode}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'>
                 <fmt:formatDate pattern="${dateTimePattern}" value="${referral.flight.depTime.time}" />
            </td>
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />' colspan="2">
                 <fmt:formatDate pattern="${dateTimePattern}" value="${referral.flight.arrTime.time}" />
            </td>
                      
            <td style="white-space: nowrap;" class="referralSummaryData" onClick="CommonReferral.expandTopLevel(this);" rel='Per<c:out value="${rowCount}" />'colspan="3">
                <%-- Overkill here, but will need to refactor too much code to get the
                     textual description out - doing this to support the demo tomorrow
                     (19/05/2011) --%>
                <c:forEach var="recommendedAction" items="${recommendedActionList}">
                     <c:if test="${recommendedAction.code == referral.recommendedAction}" >
                         <c:out default="" value="${recommendedAction.description}" />
                     </c:if>
                </c:forEach>
            </td>                 
        </tr>
        
        <%-- Hits page goes here --%>
        <%@ include file="referralHitsResults.jsp" %>
    </c:forEach> <%-- referral --%>
</tbody>
