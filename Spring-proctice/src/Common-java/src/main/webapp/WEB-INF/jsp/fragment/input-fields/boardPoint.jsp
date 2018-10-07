<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
        
<c:set var="boardPointErrors"><form:errors path="boardPoint"/></c:set>    
<c:choose>
    <c:when test="${not empty boardPointErrors}">
        <label for="boardPoint" class="formError">
            <fmt:message key="board.point" />
        </label>
    </c:when>
    <c:otherwise>
        <label for="boardPoint" class="helpinfolabel">
            <fmt:message key="board.point" />
        </label>
    </c:otherwise>
</c:choose>

<div class="airportControl">
     <form:input path="boardPoint" maxlength="3" size="3" onkeypress="return isASCII(event)" onchange="this.value = this.value.toUpperCase()"/>
    <button type="button" class="iconButton" onClick="window.open('/ets/airportSearch.form?freeText=' + document.getElementById('boardPoint').value +
                    '&destElement=boardPoint',
                    '_blank',
                    'location=no,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=950,height=767');">
        <img name="boardPointSearch" src="/bcs/images/icons/Airport24.png"/>
    </button>
</div>

            
              