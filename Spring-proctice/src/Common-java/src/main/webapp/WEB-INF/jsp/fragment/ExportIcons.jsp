<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="menu-buttons">
    <ul id="printMenu">
        <c:if test="${not empty pdfPrintUrl}">
            <li class="menu-button">
                <a href="javascript:printPopin('<fmt:message key="print"/>','<fmt:message key="cancel"/>','<c:out value="${pdfPrintUrl}"/>',${showPrintPopin ? true : false});" name="pdf" title="<fmt:message key="print"/>" class="topnav">
                    <img src="/bcs/images/icons/PDF32.png">
                </a>
            </li>
        </c:if>
        <c:if test="${not empty xlsExportUrl}">
            <li class="menu-button">
                <a href="javascript:printPopin('<fmt:message key="exportcsv"/>','<fmt:message key="cancel"/>','<c:out value="${xlsExportUrl}"/>',${showPrintPopin ? true : false});" name="csv" title="<fmt:message key="exportcsv"/>" class="topnav">
                    <img src="/bcs/images/icons/Excel32.png">
                </a>
            </li>
        </c:if>
    </ul>
</div>