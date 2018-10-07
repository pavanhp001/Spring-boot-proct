<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty infoMessages}">
    <div class="pageMessages">
       <c:forEach var="message" items="${infoMessages}">
           <div id="pageMessage" class="pageMessage">
               <c:if test='${message ne null && "null" ne message}'>
                    <fmt:message key="${message}" />
               </c:if>
           </div>
       </c:forEach>
    </div>              
</c:if> 