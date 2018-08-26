<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title><fmt:message key="login.title" /></title>
<!-- Source file -->
<!-- <script src="https://yui.yahooapis.com/2.8.0r4/build/json/json-min.js"></script> -->
<script language="javascript">window.history.forward(0);</script>
<script src="<c:url value='/script/feedback.js'/>"></script>
<link rel='stylesheet' href="<c:url value='/style/placeorder.css'/>" />
<!-- Style Sheets -->
<!-- link rel="stylesheet" href="<c:url value='/style/html5reset-1.6.1.css'/>" /> -->

<script src="${dtmUrl}"></script>

<script type="text/javascript">
var dataLayer='${dataLayer}';
dataLayer=dataLayer!=''?JSON.parse(dataLayer):dataLayer;
</script>
</head>
<body oncontextmenu="return false"  >

	<!-- **************************************** -->
	<div class="container-fluid">
			<div id="row-fluid" >
				<div>
					
					<tiles:insertAttribute name="main_content" />
				</div>
			</div>
 </div>
		
	 <script type="text/javascript">
     if (typeof(_satellite) !== 'undefined') {
      _satellite.pageBottom();
     }
     
    </script>
	 
</body>

<script type="text/javascript">
 $(document).ready(function(){
	  $('body').on('click', function (e) {
	      $('[data-toggle="popover"]').each(function () {
	          //the 'is' for buttons that trigger popups
	          //the 'has' for icons within a button that triggers a popup
	          if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
	              $(this).popover('hide');
	          }
	      });
showPopover();
 });
});
</script>

</html>
