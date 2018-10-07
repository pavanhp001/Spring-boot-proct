<%--
    Created a basic page for Tiles that sets up the HTML HEAD element correctly, setting up the application resource
    bundle as the default for the whole page, a title for the page and a CSS for the page.

    The required Tiles attributes are:
        page.title.key - the i18n key to use to look up the page title
        page.css.key - the i18n key to use to look up the page css
        page.structure - define the basic structure of the page.
        request.bundle.key - the key to use to look up the resource bundle for i18n that will be used for this request.

    This tile exports page.title.key to request scope as 'pageTitleKey' to allow other pages to make use of it.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:useAttribute name="request.bundle.key" id="bundle"/>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="${bundle}" scope="request"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- set the title key into request scope so that it can be used by other pages -->
        <tiles:useAttribute name="page.title.key" id="pageTitleKey" scope="request"/>
        <title><fmt:message key="${pageTitleKey}"/></title>
        <tiles:useAttribute name="page.css.key" id="cssKey"/>

        <link rel="stylesheet" href="/bcs/<fmt:message key="${cssKey}"/>" type="text/css">
        <link rel="stylesheet" href="/bcs/css/dhtmlwindow.css" type="text/css">
        <link rel="Stylesheet" href="/bcs/scripts/jquery.ui/themes/bcsSmoothness/jquery-ui-1.7.2.custom.css" type="text/css"/>

        <script type="text/javascript" src="/bcs/scripts/jquery-1.3.2.min.js"></script>
        <script type="text/javascript" src="/bcs/scripts/jquery-ui-1.7.2.custom.min.js"></script>
        <script type="text/javascript" src="/bcs/scripts/jquery.ui/datepicker/i18n/ui.datepicker-<c:out value="${pageContext.request.locale.language}"/>.js"></script>

        <script type="text/javascript" src="/bcs/scripts/navigation.js"></script>
        <script type="text/javascript" src="/bcs/scripts/dhtmlwindow.js"></script>
        <script type="text/javascript" src="/bcs/scripts/ContextHelp.js"></script>

        <tiles:useAttribute name="app.name" id="appNameKey" />
        <tiles:useAttribute name="app.version" id="appVersionKey" />
        <!-- Application version -->
        <!-- <c:out value="${appNameKey} - ${appVersionKey}"/> -->

        <!-- Fix for the empty cell problem in IE. Other supported browsers understand CSS2.0 empty-cells:show; -->
        <!--[if lte IE 7]>
            <script type="text/javascript">
                window.onload = function() {
                    tds = document.all.tags("td");
                    for (x = 0; x < tds.length; x++) {
                        if (tds[x].innerHTML == '')
                            tds[x].innerHTML = "&nbsp;";
                    }
                }
            </script>
        <![endif]-->
    </head>
    <body>
        <tiles:insertAttribute name="page.structure"/>
    </body>
</html>

