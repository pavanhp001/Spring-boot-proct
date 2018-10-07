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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="<c:out value='${localeLang}'/>">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="${pageTitleKey}"/></title>
        <!-- set the title key into request scope so that it can be used by other pages -->

        <link rel="stylesheet" href="<bcs:cssPath/>Default-Common.css" type="text/css">
        <link rel="stylesheet" href="<bcs:cssPath/>Default-<c:out value="${localeLang}"/>.css" type="text/css">
            
        <link rel="Stylesheet" href="<bcs:cssPath/>dhtmlwindow.css" type="text/css">
          <link rel="Stylesheet" href="<bcs:rootPath/>/scripts/jquery.ui/themes/bcsSmoothness/jquery-ui-1.8.12.custom.css" type="text/css">
        <!--[if IE]>
            <link rel="stylesheet" href="<bcs:cssPath/>IE.css" type="text/css">
        <![endif]-->

          <script type="text/javascript" src="<bcs:scriptPath/>jquery-1.5.2.min.js"></script>
        <script type="text/javascript" src="<bcs:scriptPath/>jquery-ui-1.8.12.custom.min.js"></script>
        <script type="text/javascript" src="<bcs:scriptPath/>jquery.ui/datepicker/i18n/jquery.ui.datepicker-<c:out value="${requestContext.locale.language}"/>.js"></script>

         <script type="text/javascript" src="<bcs:scriptPath/>navigation.js"></script>
        <script type="text/javascript" src="<bcs:scriptPath/>dhtmlwindow.js"></script>
        <script type="text/javascript" src="<bcs:scriptPath/>jquery.blockUI.js"></script>
        <script type="text/javascript" src="<bcs:scriptPath/>Common.js"></script>

        <c:if test="${not empty jspFileKey}">
            <link rel="stylesheet" href="<bcs:cssPath/>jsp-page/<c:out value="${jspFileKey}"/>.css" type="text/css">
        </c:if>
        
        <%-- Include any page-specific arabic files, if present --%>
        <c:if test="${(not empty arabicPageCssKey) && (localeLang == 'ar')}">
            <link rel="stylesheet" href="<bcs:cssPath/>jsp-page/<c:out value="${arabicPageCssKey}"/>-<c:out value="${localeLang}"/>.css" type="text/css">
        </c:if>

        <!-- Application version -->
        <!-- <c:out value="${appNameKey} - ${appVersionKey}"/> -->
    </head>
<body>
  <script type="text/javascript">
     <!-- Flag javascript as available, to enable the various "hide on load" effects -->
     document.body.className = "js";
  </script>
<div class="header">
     <tiles:insertAttribute name="page.header"/>
     <tiles:insertAttribute name="page.menu"/>

</div>

<h1 class="pageTitle"><fmt:message key="${pageTitleKey}"/></h1>

<tiles:insertAttribute name="page.body"/>

<div class="footer">
<tiles:insertAttribute name="page.footer"/>
</div>
    <form id="reloadPageForm" name="reloadPageForm" method="GET">
    </form>
</body>
<head>
       <meta http-eqiv="Pragma"  content="no-cache">
       <meta http-eqiv="Cache-Control" content="no-cache">
</head>
</html>


