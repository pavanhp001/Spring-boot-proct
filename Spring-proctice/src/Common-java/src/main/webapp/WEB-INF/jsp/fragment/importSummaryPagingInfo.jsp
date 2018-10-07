<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="ARHead">
        <h2 class="ARHead2">
            <span class="minorText">
                <c:choose>
                    <c:when test="${tableCommand.rowCountEstimated eq true}">
                         <fmt:message key="imported.files.string">
                              <fmt:param value="${tableCommand.startIndex}"/>
                              <fmt:param value="${tableCommand.pageNumber * tableCommand.pageSize}"/>
                        </fmt:message>
                         
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="imported.files.string">
                              <fmt:param value="${tableCommand.startIndex}"/>
                              <c:choose>
                                <c:when test="${ (tableCommand.endIndex - 1 > tableCommand.totalResults) || tableCommand.printAll}">
                                    <fmt:param value="${tableCommand.totalResults}"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:param value="${tableCommand.endIndex - 1}"/>
                                </c:otherwise>
                             </c:choose>
                        </fmt:message>
                    </c:otherwise>
                </c:choose>
            </span>
       </h2>
</div>
