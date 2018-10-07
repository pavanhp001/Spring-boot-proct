<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
    function clickPrevious() {
        $('#previous').click();
    }
    
    function clickNext() {
        $('#next').click();
    }
</script>

<div class="menu-buttons">
   <ul id='<c:out value="printMenu${showNumber}"/>'>
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
   
   
   <c:choose>
   <c:when test="${requestContext.locale.language == 'ar'}">
   <c:choose>
   <c:when test="${tableCommand.nextDisabled == 'disabled'}">
   <li class="menu-button">
   <a href="javascript:void(0);" name="next" title="<fmt:message key="next"/>" class="topnav">
   <img src="/bcs/images/icons/NextDsbld32.png">
   </a>
   </li>
   </c:when>
   <c:otherwise>
   <li class="menu-button">
   <a href="javascript:clickNext();" name="next" title="<fmt:message key="next"/>" class="topnav">
   <img src="/bcs/images/icons/Next32.png">
   </a>
   </li>
   </c:otherwise>
   </c:choose>
   
   <c:choose>
   <c:when test="${tableCommand.previousDisabled == 'disabled'}">
   <li class="menu-button">
   <a href="javascript:void(0);" name="prev" title="<fmt:message key="previous"/>" class="topnav">
   <img src="/bcs/images/icons/PreviousDsbld32.png">
   </a>
   </li>
   </c:when>
   <c:otherwise>
   <li class="menu-button">
   <a href="javascript:clickPrevious();" name="prev" title="<fmt:message key="previous"/>" class="topnav">
   <img src="/bcs/images/icons/Previous32.png">
   </a>
   </li>
   </c:otherwise>
   </c:choose>
   </c:when>
   <c:otherwise>
   <c:choose>
   <c:when test="${tableCommand.previousDisabled == 'disabled'}">
   <li class="menu-button">
   <a href="javascript:void(0);" name="prev" title="<fmt:message key="previous"/>" class="topnav">
   <img src="/bcs/images/icons/PreviousDsbld32.png">
   </a>
   </li>
   </c:when>
   <c:otherwise>
   <li class="menu-button">
   <a href="javascript:clickPrevious();" name="prev" title="<fmt:message key="previous"/>" class="topnav">
   <img src="/bcs/images/icons/Previous32.png">
   </a>
   </li>
   </c:otherwise>
   </c:choose>
   
   <c:choose>
   <c:when test="${tableCommand.nextDisabled == 'disabled'}">
   <li class="menu-button">
   <a href="javascript:void(0);" name="next" title="<fmt:message key="next"/>" class="topnav">
   <img src="/bcs/images/icons/NextDsbld32.png">
   </a>
   </li>
   </c:when>
   <c:otherwise>
   <li class="menu-button">
   <a href="javascript:clickNext();" name="next" title="<fmt:message key="next"/>" class="topnav">
   <img src="/bcs/images/icons/Next32.png">
   </a>
   </li>
   </c:otherwise>
   </c:choose>
   </c:otherwise>
   </c:choose>
  </ul>
</div>