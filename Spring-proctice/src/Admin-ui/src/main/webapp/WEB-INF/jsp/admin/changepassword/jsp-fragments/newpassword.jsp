<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>				
   
    <c:set var="newPasswordErrors"><form:errors path="newPassword"/></c:set>  
    <c:choose>			    
        <c:when test="${not empty newPasswordErrors}">
            <label class="formError" id="newPasswordError" >
	           <fmt:message key="password.new"/><c:out value="${mandatoryField}"/>*
            </label>
        </c:when>			    
        <c:otherwise>      
            <label>
                <fmt:message key="password.new"/><c:out value="${mandatoryField}"/>*&nbsp;
            </label>              
        </c:otherwise>			
    </c:choose>   
    
    <form:password path="newPassword" size="20" maxlength="20" id="newPassword" /> 
    