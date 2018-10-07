<%--
    Basic tiles page that defines the general header. It is designed used by HeaderBody.jsp.

    The required Tiles attributes are:
        page.menu - the definition of the page menu.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- should have been set by the BasicPage.jsp-->
 <div class="headerImg">
   <div class=" logo"></div>
   <h1><fmt:message key="${pageTitleKey}"/></h1>
 </div>
