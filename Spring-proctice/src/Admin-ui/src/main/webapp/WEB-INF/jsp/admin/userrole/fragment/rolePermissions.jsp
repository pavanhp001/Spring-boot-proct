<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>		                        


<fieldset id="user-fields-column1" class="threeCol">        
    &nbsp;
</fieldset>

<fieldset id="user-fields-column2" class="threeCol">
<table class="results" summary="results">           
    <thead> 
        <tr>
            <th colspan="0" title='<fmt:message key="name.permission" />'>
                <fmt:message key="name.permission" />
            </th>
            <th colspan="0" title='<fmt:message key="status"/>'>
                <fmt:message key="status" />
            </th>
        </tr>
    </thead>

    <tbody <c:if test="${userRoleCommand.action ne 'viewUserRole'}">class="selectable"</c:if> >
       <c:forEach items="${permissionList}" var="per">  
            <tr class="results">
                <td><fmt:message key ="permission.${per.permission}"/></td>
                <td class="permissionCheckBox">

                    <c:choose>              
                        <c:when test="${userRoleCommand.action ne 'viewUserRole'}">
                            <form:checkbox path="permissionList" value="${per.permission}" />
                        </c:when>               
                        <c:otherwise>
                            <form:checkbox path="permissionList" value="${per.permission}" disabled="true" />
                        </c:otherwise>          
                    </c:choose> 
                  </td>
            </tr>
        </c:forEach>
    </tbody>
</table></fieldset>

<fieldset id="user-fields-column3" class="threeCol">
    &nbsp;
</fieldset>