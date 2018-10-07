<%--
THIS IS TILES LAYOUT

    The required Tiles attributes are:
        page.title.key - the i18n key to use to look up the page title
        page.css.key - the i18n key to use to look up the page css
        page.structure - define the basic structure of the page.
        request.bundle.key - the key to use to look up the resource bundle for i18n that will be used for this request.

    This tile exports page.title.key to request scope as 'pageTitleKey' to allow other pages to make use of it.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<tiles:useAttribute name="request.bundle.key" id="bundle"/>
<tiles:useAttribute name="page.title.key" id="pageTitleKey" scope="request"/>
<tiles:useAttribute name="page.css.key" id="jspFileKey"/>
<tiles:useAttribute name="page.css.ar.key" id="arabicPageCssKey" ignore="true"/>

<c:set var="pdfPrintUrl" scope="request"><tiles:insertAttribute name="pdf.print.url" /></c:set>
<c:set var="xlsExportUrl" scope="request"><tiles:insertAttribute name="xls.export.url" /></c:set>
<c:set var="localeLang" scope="request" value="${requestContext.locale.language}"/>
<c:set var="datePattern" scope="request"><fmt:message key="date.pattern"/></c:set>
<c:set var="dateTimePattern" scope="request"><fmt:message key="date.time.pattern"/></c:set>
<c:set var="jqueryDatePattern" scope="request"><fmt:message key="jquery.date.pattern"/></c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="<c:out value='${localeLang}'/>">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        
        <title><fmt:message key="${pageTitleKey}"/></title>

        <%-- Load jQuery UI styles first so we can override them in our own stylesheets --%>
        <link rel="stylesheet" href="<bcs:cssPath/>jquery-ui-1.8.12.custom.css" type="text/css">
        <link rel="stylesheet" href="<bcs:cssPath/>aras.css" type="text/css">
        <link rel="stylesheet" href="<bcs:cssPath/>aras-theme.css" type="text/css">
        <c:if test="${localeLang != 'en'}">
            <link rel="stylesheet" href="<bcs:cssPath/>aras-<c:out value="${localeLang}"/>.css" type="text/css">
        </c:if>
        <!--[if IE 8]>
            <link rel="stylesheet" href="<bcs:cssPath/>ie8.css" type="text/css">
        <![endif]-->
        
        <c:if test="${not empty jspFileKey}">
            <link rel="stylesheet" href="<bcs:cssPath/>jsp-page/<c:out value="${jspFileKey}"/>.css" type="text/css">
        </c:if>
        
        <%-- Include any page-specific Arabic files, if present --%>
        <c:if test="${(not empty arabicPageCssKey) && (localeLang == 'ar')}">
            <link rel="stylesheet" href="<bcs:cssPath/>jsp-page/<c:out value="${arabicPageCssKey}"/>-<c:out value="${localeLang}"/>.css" type="text/css">
        </c:if>

          <script type="text/javascript" src="<bcs:scriptPath/>jquery-1.5.2.min.js"></script>
        <script type="text/javascript" src="<bcs:scriptPath/>jquery-ui-1.8.12.custom.min.js"></script>
        <script type="text/javascript" src="<bcs:scriptPath/>jquery.ui/datepicker/i18n/jquery.ui.datepicker-<c:out value="${requestContext.locale.language}"/>.js"></script>

        <%--   IMPORTANT! THIS IS A QUICK FIX TO OVERRIDE THE LINE 'isRTL: true' IN jquery.ui.datepicker-ar.js FOR THE NEW
               CSS LAYOUT OTHERWISE IT WILL GET SWITCHED BACK TO LTR BY THE STYLESHEET. ONCE ALL APPLICATIONS ARE SWITCHED
               TO THE NEW CSS LAYOUT THIS CAN BE CHANGED IN jquery.ui.datepicker-ar.js AND THE FOLLOWING 3 LINES REMOVED.
               END OF MESSGAGE. --%>
               
        <c:if test="${localeLang == 'ar'}">
            <script type="text/javascript" src="<bcs:scriptPath/>jquery.ui/datepicker/i18n/jquery.ui.datepicker-ar-new.js"></script>
        </c:if>

        <script type="text/javascript" src="<bcs:scriptPath/>jquery.blockUI.js"></script>
        <script type="text/javascript" src="<bcs:scriptPath/>Common.js"></script>
        
        <%-- Use the date patterns from the DD to set vars for use in Common.js --%>        
        <script type="text/javascript">
            {
                Common.setDatePatterns("${datePattern}","${dateTimePattern}","${jqueryDatePattern}");
            }
        </script>
    </head>
    
    <body>
        <div class="header">
            <tiles:insertAttribute name="page.menu"/>
        </div>
        
        <h1 class="pageTitle"><fmt:message key="${pageTitleKey}"/></h1>
        
        <%--Page content --%>
        <tiles:insertAttribute name="page.body"/>

        <div class="footer">
            <tiles:insertAttribute name="page.footer"/>
        </div>
        
    </body>
    <head>
       <meta http-eqiv="Pragma"  content="no-cache">
       <meta http-eqiv="Cache-Control" content="no-cache">
    </head>
</html>


