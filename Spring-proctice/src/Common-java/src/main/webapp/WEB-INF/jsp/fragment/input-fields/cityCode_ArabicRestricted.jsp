<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
    <c:set var="cityCodeErrors"><form:errors path="cityCode"/></c:set>    
    <c:choose>
        <c:when test="${not empty cityCodeErrors}">
            <label class="formError">
                <fmt:message key="city.code" />
            </label>
        </c:when>
        <c:otherwise>
            <label class="helpinfolabel" >
                <fmt:message key="city.code" />
             </label>
        </c:otherwise>
    </c:choose>
    
    <form:input path="cityCode" size="14" onchange="this.value = this.value.toUpperCase()"/>
            
              