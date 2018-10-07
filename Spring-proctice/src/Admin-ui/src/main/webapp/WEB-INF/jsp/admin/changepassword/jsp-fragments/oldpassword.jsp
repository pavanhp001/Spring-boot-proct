<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>				

    <c:set var="oldPasswordErrors"><form:errors path="oldPassword"/></c:set>  
    <c:choose>			    
        <c:when test="${not empty oldPasswordErrors}">
            <label class="formError" id="oldPasswordError">
                <fmt:message key="password.old"/><c:out value="${mandatoryField}"/>*
            </label>
        </c:when>
        <c:otherwise>      
            <label >
                <fmt:message key="password.old"/><c:out value="${mandatoryField}"/>*&nbsp;
            </label>              
        </c:otherwise>			
    </c:choose>
    
    <form:password path="oldPassword" size="20" maxlength="20" id="oldPassword" />

