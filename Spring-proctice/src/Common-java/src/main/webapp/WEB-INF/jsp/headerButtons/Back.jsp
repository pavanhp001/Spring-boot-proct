<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,abc.xyz.pts.bcs.common.web.userNavigation.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:choose>
  <c:when test="${navHistory == 'true'}">
    <a id="back" href="#" onClick="history.go(-1);" 
        title="<fmt:message key="back"/>" class="topnav"><span class="headerIcons back"></span>
    </a>
  </c:when>
  <c:otherwise>
    <a id="noback" name="noback" title="<fmt:message key="back"/>"><span class="headerIcons noback">noback</span></a>
  </c:otherwise>
</c:choose>

<c:set var="navHistory" value="true" scope="session"/>


