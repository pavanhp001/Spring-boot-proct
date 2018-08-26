<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.AL.ui.repository.SessionCache" %>
<html>
<head>
<title> Add LineItem</title>

<link media="all" href="<c:url value='/style/cart/default/screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/cust_screen.css'/>" type="text/css" rel="stylesheet">
<link media="all" href="<c:url value='/style/cart/default/lineItem.css'/>" type="text/css" rel="stylesheet">

<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/script/lineItem.js'/>"></script>

<style>
.navigator {
    background-image: url("<%=request.getContextPath()%>/images/navigators.png");
    background-position: 0 -1px;
    background-repeat: no-repeat;
    display: inline-block;
    height: 15px;
    vertical-align: middle;
    width: 8px;
}
</style>
</head>

<body>
<form method="POST"
		action="<%=request.getContextPath()%>/login_process.html"
		name="lineItem_details" id="lineItem_details" style="font-family: lucida grande,tahoma,verdana,arial,sans-serif; font-size: 10px;">
 	<center>
			 <div style="margin-left: 10px; width: 95%;" id="UIContentTopper" class="UIContentTopper clearfix">
			 <div class="UIContentTopper_text" style="font-size: 14px;">
			 <div style="margin-top: 30px; width: 95%;" id="UIContentTopper"
						class="  clearfix">
                        <input id="status" name="status" type="hidden" style="width: 200px;" />
						<input id="reason" name="reason" type="hidden" style="width: 200px;" />
						<input type="hidden" name="totalCount" id="totalCount" value="${fn:length(lineItems)}" />
						<input type="hidden" name="totalPages" id="totalPages" value="0" />
						<input type="hidden" name="selectedCount" id="selectedCount" value="3" />
						<input type="hidden" name="selectedPage" id="selectedPage" value="1" />
						<input type="hidden" name="schedulerType" id="schedulerType" value="" />
			</div>
			</div>
			</div>
			
					
 			<div style="margin-top: 75px;width:700px;">
 			<div class="pageBox">
 				<h2 class="sectionHeading"><span>Customer Information</span></h2>
					<div class="pageBox">
					
					</div>
					
				<div class="rounded">
				
					<h2 class="sectionHeading"><span>Customer Information</span></h2>
					<div class="pageBox">
					
					</div>
				<fieldset  id="Info" >
						<span class="pHead" style="float:left;" ></span></fieldset>
						<fieldset style="border-radius :6px 6px 6px 6px;margin-bottom: 15px;font-size : 15px;">
						<div class="order-level" style="margin-bottom: 18px; margin-left: 50px;margin-top: 6px">
							<div style="float: left; ">
								<span class="rlabel">First Name :</span>
								<span class="llabel">First Name</span>
								<!--<input id="firstName" type="text" name="firstName">-->
							</div>
							
							<div>
								<span class="rlabel">Last Name :</span>
								<span class="llabel" style="margin-left: 15px" >Last Name</span>
							</div>
							</div>
							<div style="margin-bottom: 15px; margin-left: 50px">
							<div style="float: left;">
								<span class="rlabel">Order Id :</span>
								<span class="llabel" style="margin-left: 21px" >Order Id</span>
							</div>
							<div style="margin-left: 180px">
								<span class="rlabel">Order Status :</span>
								<span class="llabel" >Order Status</span>
							</div>
							
						</div>
						</fieldset>
						</div>
						</div>
 <fieldset style="border-radius :3px 3px 3px 3px;background-color:#000000">
<span>	<span style="float:left;font-family:Arial,Helvetica,sans-serif;font-size:17px;color:#FFFFFF">Add LineItem</span>
		<c:if test="${fn:length(lineItems) > 0}">
								<div id="navigatorBlock" style="float: right;">
									<span id="startCount">1</span> - <span id="endCount">3</span>
									of <span id="totalCount">${fn:length(lineItems)}</span><span class="pipe"> | </span>
								
									<span id="leftNavigator" class="disabledTxt" ><i
										class="navigator"></i> Previous </span><span id="rightNavigator"> Next
										<i class="navigator disRightNavigator"></i>
									</span>
								</div>
							</c:if></span>
		</fieldset></div>
						
							
	<div style="width: 700px;" id="jobListBlock">
	
	    <fieldset style="border-radius :6px 6px 6px 6px;">
	   <center>
		<c:forEach var="li" items="${lineItems}">
		<div  class="main">
		
		<fieldset style="width: 95%; border-radius :6px 6px 6px 6px; border: 1px solid #D3DAE6;border-top: 2px solid #D3DAE6;margin-top: 8px;background-color:#ECEFF5; float :center;">
		<div style="width: 100%;">
		<div style="float: left; width: 10%;"><img src="http://blog.laptopmag.com/wpress/wp-content/uploads/2012/01/ATT-logo-t.jpg" style=" width : 100px"></img></div>
        <div style="float: left; width: 80%; margin-top : 25px;"><span style="font-weight: bold;font-size : 15px;">Partner Name : </span><span style="font-size : 15px;"><c:out value="${li.partnerName}"  /></span><br />
        <span style="font-weight: bold;font-size : 15px;">Partner ExternalId : </span><span style="font-size : 15px;"><c:out value="${li.partnerExternalId}" /></span><br />
        <span style="font-weight: bold;font-size: 15px;">Product Name : </span><span style="font-size: 15px;"><c:out value="${li.productName}" /></span><br /></div>
        <div style="float: left; width: 10%; margin-top : 25px;"><input type="submit" name="addToCart" id="addToCart" value="Add to Cart" style="margin:5px; float: right; padding: 2px 5px 2px 5px;font-size: 12px;  font-weight: bold;line-height:30px; color : #3366FF;" /></div>
        </div>
		</fieldset>
		</div>
 		</c:forEach>
 		 </center>
 		 </fieldset>
	</div>
	</center>
	</form>
</body>
</html>