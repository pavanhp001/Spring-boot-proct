<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${tableCommand.rowCountEstimated eq true}">
        <fmt:message key="search.results.string">
            <fmt:param value="${tableCommand.startIndex}" />
            <fmt:param value="${tableCommand.pageNumber * tableCommand.pageSize}" />
        </fmt:message>

    </c:when>
    <c:otherwise>
        <fmt:message key="search.results.string">
            <fmt:param value="${tableCommand.startIndex}" />
            <c:choose>
                <c:when
                    test="${ (tableCommand.endIndex - 1 > tableCommand.totalResults) || tableCommand.printAll}">
                    <fmt:param value="${tableCommand.totalResults}" />
                </c:when>
                <c:otherwise>
                    <fmt:param value="${tableCommand.endIndex - 1}" />
                </c:otherwise>
            </c:choose>
        </fmt:message>
    </c:otherwise>
</c:choose>

<c:if test="${hideExactCount ne true}" >
<span id="idOfString" style="display: none;">
	<fmt:message key="pagination.of" />
</span>
<span id="idManyString" style="display: none;">
   <fmt:message key="pagination.row.count.more.than">
   <fmt:param value="${tableCommand.configuredRowCount}" /> 
   </fmt:message>
</span>
<span id="idExactCount"></span>



<script>
    function getTotalResultsCount(exactCountURL) {
    	var configCount = <c:out value="${tableCommand.configuredRowCount}"></c:out>;        
    	$.ajax({
            type : "post",
            url : exactCountURL,
            success : function(msg) {            	
                document.getElementById("idOfString").style.display = "inline";
                if(msg > configCount){
                	document.getElementById("idManyString").style.display = "inline";                	
                }else{
                	document.getElementById("idExactCount").innerHTML = msg;
                }
                
            }
        });

    }
</script>
</c:if>
