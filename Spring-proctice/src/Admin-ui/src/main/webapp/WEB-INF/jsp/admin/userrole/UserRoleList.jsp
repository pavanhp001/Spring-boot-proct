<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>

<script type="text/javascript" src="/bcs/scripts/Util.js"></script>
<script type="text/javascript" src="/bcs/scripts/jsp-page/UserRole.js"></script>

<div id="mainContent">&nbsp;</div>
<div id="searchResults">

    <form:form commandName="userRoleListCommand" action="/admin/userRoleList.form" name="userRoleListForm" id="userRoleListForm" method="POST">
        <input type="hidden" name="selectedRowCode" id="selectedRowCode" />
        <input type="hidden" name="action" id="action" value="" />
    
        <table class="results" summary="results">           
            <thead> 
                <tr>
                    <th title='<fmt:message key="code" />'>
                        <fmt:message key="code" />
                    </th>
                    <th title='<fmt:message key="description.english"/>'>
                        <fmt:message key="description.english.short" />
                    </th>
                    <th title='<fmt:message key="description.lang"/>'>
                        <fmt:message key="description.lang.short" />
                    </th>
                </tr>
            </thead>

            <tbody class="selectable" id="roleList" name="roleList">
                <c:forEach items="${userRoleListCommand.userRoleList}" var="rec" varStatus="resultRow">
                    <tr class="results" onDblClick="viewRoleDetail(this.id);" id="${rec.code}">
                        <td><c:out value="${rec.code}" /></td>
                        <td><c:out value="${rec.descriptionInEnglish}" /></td>
                        <td><c:out value="${rec.descriptionInLang}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="searchControl">
            <button class="textButton needSelected" type="button" id="viewUserRoleButton" name="viewUserRoleButton" onclick="viewRoleDetailsButtonPressed();" disabled="disabled">
                <fmt:message key="button.view"/>
            </button>
            <button class="textButton" type="submit" id="btnAdd" name="addUserRole">
                <fmt:message key="button.add" />
            </button> 
        </div>
    </form:form>
    
</div>


