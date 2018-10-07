<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="validationsErrors" value="false"/>

<spring:hasBindErrors name="command">
    <c:set var="validationsErrors" value="true"/>
</spring:hasBindErrors>
<c:if test='${validationsErrors ne "true"}'> <!-- Only show if no validation errors -->
    <div id="searchResults">
        <c:if test="${command.totalRecordsRetrieved == 0}">
            <label class="noMatch">
               <fmt:message key="no.matches.found" />
               <c:if test="${userScreenName eq 'adminUserSearchForm'}">
                <jsp:include page="/WEB-INF/jsp/fragment/ExportIcons.jsp" />
               </c:if>
            </label>
        </c:if>
        <c:if test="${command.totalRecordsRetrieved < 0}">
            <label class="noMatch">
                <fmt:message key="errors.there.has.been.an.error" />: 
                <c:out value="${command.totalRecordsRetrieved}" />
            </label>
        </c:if>
    </div>
</c:if>
