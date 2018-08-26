<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
 
<link rel="stylesheet" href="<c:url value='/css/main.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/nav.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/classes.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/text.css'/>" />
<link rel="stylesheet" href="<c:url value='/css/formStyles.css'/>" />

<script src="<c:url value='/js/js_new/jquery-1.9.0.min.js'/>"></script>

<script>
$(document).ready(function(){
	$(document).bind("dragstart", function() {
	     return false;
	});
});
</script>
<style>

div#scrollable_content {
  position: absolute;
  top: 50px;
  bottom: 0px;
  width: auto;
  overflow-y:auto;
 
}

</style>

</head>

<body>
	<!-- **************************************** -->
 
		
		
		<div id="wrapper">
   
    <div id="contentWrapper">
   
    <tiles:insertAttribute name="main_content" />
         
    </div> 
		
 
	</div>
</body>
</html>
