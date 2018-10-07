<%-- 

    Place inside you jsp form for paging and ordering functionality

--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <%-- If true, requery called --%>
    <spring:bind path="command.requery"> 
        <input  type="hidden" id="requery" name="requery"/>
    </spring:bind>
    
    <%-- Current page number --%>
    <spring:bind path="command.pageNumber"> 
        <input  type="hidden" id="pageNumber" name="pageNumber" value="<c:out value="${status.value}"/>"/>
    </spring:bind>
    
    <%-- Column to order by --%>
    <spring:bind path="command.orderByColumn"> 
        <input  type="hidden" id="orderByColumn" name="orderByColumn" value="<c:out value="${status.value}"/>"/>
    </spring:bind>
    
    <%-- Ordering type [ASC|DESC] --%>
    <spring:bind path="command.ascDesc"> 
        <input  type="hidden" id="ascDesc" name="ascDesc" value="<c:out value="${status.value}"/>"/>
    </spring:bind>
    
    <%-- Number of records retrieved by initial query --%>
    <spring:bind path="command.totalRecordsRetrieved">
        <input  type="hidden" id="totalRecordsRetrieved" name="totalRecordsRetrieved" value="<c:out value="${status.value}"/>"/>
    </spring:bind>
    
    <%-- Last row number to display --%>
    <spring:bind path="command.rowNumberEnd">
        <input  type="hidden" id="rowNumberEnd" name="rowNumberEnd" value="<c:out value="${status.value}"/>"/>
    </spring:bind>
