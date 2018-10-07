<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<label>
    <fmt:message key="username"/>
</label>    
<div class="summaryValue">
    <c:out value="${adminChangePasswordCommand.userName}" />
</div> 