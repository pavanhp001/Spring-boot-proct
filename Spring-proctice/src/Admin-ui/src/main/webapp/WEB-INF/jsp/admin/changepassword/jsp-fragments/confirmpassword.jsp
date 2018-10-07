<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   
				
    <c:set var="confirmPasswordErrors"><form:errors path="confirmPassword"/></c:set> 

    <c:choose>			    
        <c:when test="${not empty confirmPasswordErrors}">
            <label class="formError" id="passwordConfirmError">
                <fmt:message key="password.confirm"/><c:out value="${mandatoryField}"/>*
            </label>
        </c:when>			    
        <c:otherwise>      
            <label>
                <fmt:message key="password.confirm"/><c:out value="${mandatoryField}"/>*&nbsp;
            </label>              
        </c:otherwise>			
    </c:choose>   
                     
    <form:password path="confirmPassword" size="20" maxlength="20" id="confirmPassword" /> 
    

    
    
    
