<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${tableCommand.totalResults == 0}">
        <div id="searchResults">
            <label class="noMatch"><fmt:message key="no.matches.found" />
            <c:if test="${cprScreenName eq 'ruleSearchForm'}">
                <jsp:include page="/WEB-INF/jsp/fragment/ExportIcons.jsp" />
            </c:if>
            </label>
        </div>
    </c:when> 
    <c:when test="${tableCommand.errorNumber != 0}">
        <jsp:include page="/WEB-INF/jsp/fragment/tableCommandError.jsp"/>
    </c:when>   
</c:choose>
