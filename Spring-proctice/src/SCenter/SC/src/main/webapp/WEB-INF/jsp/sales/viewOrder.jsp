<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title>View Order Details</title>
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/script/recommendations.js'/>"></script>


<script type="text/javascript">
$(document).ready(function(){
 	$("#featuresTab").click(function(){
	 	$("#featuresContent").show();
	 	$("#promotionsContent").hide();
	 	$(this).css("background-color", "#585858"); 
	 	$("#promotionsTab").css("background-color", "#C8C8C8");
 	});
 	$("#promotionsTab").click(function(){
	 	$("#promotionsContent").show();
	 	$("#featuresContent").hide();
	 	$(this).css("background-color", "#585858"); 
	 	$("#featuresTab").css("background-color", "#C8C8C8");
 	});
});

</script>


<style type="text/css">
.changePageRow {
	float: left;
	height: 25px;
	margin: -3px 5px 0 503px;
	padding: 0;
}

.coaching {
	color: green;
}

#contentWrapper {
	overflow-y: scroll;
}

/*Tabs Content Class for Dish Item*/
.tabcontentDish {
	background: none repeat scroll 0 0 #FFFFFF;
	border-radius: 0 3px 3px 3px;
	box-shadow: 0 1px 0 rgba(0, 0, 0, 0.1);
	color: #5A5B5D;
	float: left;
	height: 130px;
	margin-top: 0;
	width: 100%;
	z-index: 5;
}

.row {
	display: inline-block;
}

.tabsWrapper fieldset {
	border: none;
}

#action_wrapper {
	position:relative;
	margin-top:-5px;
}
.changePageRow {
    float: right;
    height: 25px;
    margin: -3px 5px 0 0;
    padding: 0;
}
.productBaseInfo {
	display: inline-block;
	float: right;
	font-size: 10px;
	padding: 2px 15px 2px 2px;
}

.pp_form {
	width: auto;
}

.row p {
	padding: 5px;
}

.greentab {
	background: none repeat scroll 0 0 #577D3E;
	top: 0;
}

.product_header {
	width: 100%;
	height: 150px;
}

#content_border { /*position:relative;*/
	
}

#action_wrapper {
	position: relative;
	margin-top: -25px;
}

#tabsWrapper {
	position: absolute;
	width: 300px;
}

.product_header_image {
	width: 100px;
	hieght: 100px;
}

.product_header_info {
	display: inline;
	width: 500px;
}

.outerDiv span.row {
	width: inherit;
	height: 24px;
	display: inline-block;
	background-color: #fff;
	color: #000;
}
.outerDiv span.rowBig {
	width: inherit;
	height: 37px;
	display: inline-block;
	background-color: #fff;
	color: #000;
}

.outerDiv   span.cols {
	height: 24px;
	margin: 1px;
	padding: 2px;
	display: inline-block;
	background-color: #fff;
	color: #000;
}
.outerDiv   span.colsBig {
	height: 37px;
	margin: 1px;
	padding: 2px;
	display: inline-block;
	background-color: #fff;
	color: #000;
}


.outerDiv span.featureGrpHead {
	width: inherit;
	height: 100px;
	display: inline-block;
	background-color: #fff;
	color: #000;
}

.outerDiv span.featureGrp {
	height: 100px;
	margin: 1px;
	padding: 2px;
	display: inline-block;
	background-color: #fff;
	color: #000;
	vertical-align: middle;
}

.outerDiv span.featureGrpScroll {
	height: 100px;
	margin: 1px;
	padding: 2px;
	display: inline-block;
	background-color: #fff;
	color: #000;
	overflow-y: scroll;
	vertical-align: middle;
}

.outerDiv span.header {
	width: inherit;
	height: 24px;
	display: inline-block;
	color: #fff;
}

.outerDiv span.head {
	height: 24px;
	margin: 1px;
	padding: 2px;
	font-weight: bold;
	text-align: center;
	display: inline-block;
	background-color: #ff0000;
	color: #000;
}
 
#featuresTab {
	float:left;
	width: 155px;
	padding: 5px;
	margin-right:3px;
	font-weight:bold;
	font-size:13px;
	border-radius:7px 7px 0 0;
	background-color: #585858;
}

#promotionsTab {
	float:left;
	width: 150px;
	padding: 5px;
	font-weight:bold;
	font-size:13px;
	border-radius:7px 7px 0 0;
	background-color: #C8C8C8;
}


</style>

</head>
   <header id="content_header">
	<div class="row">                	
		<span class="cell">
			<svg version="1.1" id="Layer_5" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 		width="29px" height="29px" viewBox="0 0 29 29" enable-background="new 0 0 29 29" xml:space="preserve" class="headphonesSVG">
				<g>
					<path fill="#96C43E" d="M29,25.298c0,2.066-1.677,3.744-3.744,3.744H4.411c-2.067,0-3.745-1.678-3.745-3.744V4.328
						c0-2.068,1.678-3.745,3.745-3.745h20.845C27.323,0.583,29,2.26,29,4.328V25.298z"/>
					<path fill="#FFFFFF" d="M23.861,18.677c0.116,0.435,0.177,0.891,0.177,1.362c0,2.921-2.367,5.289-5.288,5.289
						c-0.291,0-0.576-0.023-0.854-0.068l-0.004-0.002c-0.285,0.446-0.843,0.75-1.482,0.75c-0.932,0-1.686-0.64-1.686-1.428
						s0.754-1.428,1.686-1.428c0.563,0,1.061,0.234,1.367,0.594l0,0c0.208,0.029,0.422,0.044,0.641,0.044
						c2.19,0,3.967-1.542,3.967-3.445c0-0.307-0.046-0.604-0.132-0.887l-0.052,0.015c-0.244,0.015-0.527,0.008-0.855-0.031v0.608
						c0,0-0.059,0.398-0.808,0.375s-0.784-0.234-0.796-0.445c-0.012-0.21,0-5.5,0-5.5s0.047-0.409,0.819-0.397
						c0.655,0.012,0.737,0.246,0.761,0.375c0.023,0.128,0.023,0.784,0.023,0.784l0.374-0.059l0.088-0.058
						c0.189-0.664,0.265-1.24,0.265-1.966c0-4.188-3.395-7.583-7.582-7.583c-4.188,0-7.583,3.395-7.583,7.583
						c0,0.722,0.128,1.363,0.316,2.024l0.374,0.059c0,0,0-0.655,0.023-0.784s0.105-0.363,0.761-0.375
						c0.772-0.012,0.819,0.398,0.819,0.398s0.012,5.289,0,5.5s-0.047,0.422-0.796,0.445s-0.808-0.375-0.808-0.375v-0.608
						c-1.193,0.141-1.802-0.141-2.07-0.339c-0.27-0.199-1.065-0.913-0.738-2.293c0.328-1.381,1.451-1.58,1.674-1.58
						s0.164-0.07,0.164-0.07c-0.416-1.001-0.609-2.02-0.609-3.171c0-4.679,3.793-8.472,8.473-8.472c4.678,0,8.472,3.793,8.472,8.472
						c0,1.107-0.212,2.165-0.599,3.134l-0.046,0.037c0,0-0.059,0.07,0.163,0.07c0.223,0,1.346,0.199,1.674,1.58
						c0.205,0.863-0.029,1.465-0.299,1.845L23.861,18.677z"/>
				</g>
			</svg>
		</span>
		<input type="hidden" id="callStartTime" value="<%=request.getSession().getAttribute("callStartTime") %>" name="callStartTime" />
		<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
		<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
		<input type="hidden" name="orderId" id="orderId" value="${order.externalId}" />
   		<span class="cell">Recommendations</span>
   		<span class="cell">Call Time: <span id="totalTime"></span></span>
	</div>
</header>
                
 
<div class="content_border">

		<section id="product_header">
			<div style="width:800px;">
				<div style="display:inline-block;margin:10px 0;">
					<div style="float:left;padding-left:10px;width:120px;">
						<span class="product_header_image">
							<img src="<%=request.getContextPath()%>/images/img/${details.product.provider.externalId}.gif"/>
						</span>
					</div>
					<div style="float:right;padding-right:10px;width:120px;">
						<span class="product_header_info"> 
							${prices}	
						</span>
					</div>
					<div style="float:right;padding:0 10px;width:520px;height:90px;overflow-y:scroll;">
						<span class="product_header_info">
							${longDescription}	
						</span>
					</div>
				</div>
				
				<div style="display:inline-block;margin:10px 0;height:340px;overflow-y:scroll;">
					<div style="float:left;margin-left:10px;width:258px;border:1px solid #000;border-radius:10px 10px 0 0;background-color:#000;">
						<div style="padding:10px;color:#fff;">Highlights</div>
						<div style="padding:10px 10px 10px 20px;line-height:16px;background-color:#fff;">
							<span class="product_header_info">
									${marketing}			
							</span>
						</div>
					</div>
					<div style="float:left;margin:0 0 0 10px;width:500px;">
						<div id="tabsDiv" style="display:inline-block;">
							<div id="featuresTab">
								<p style="margin-left:33px;">Features</p>
							</div>
							<div id="promotionsTab">
								<p style="margin-left:33px;">Promotions</p>
							</div>
						</div>
						<span class="outerDiv" id="featuresContent">
							  ${features}
						</span>
						<span class="outerDiv" id="promotionsContent" style="display:none;">
							  ${promotions}
						</span>
					</div>					
				</div>
			</div>
		</section>
 
		<section id="action_wrapper">
		<div class="widget_container">
			<section class="tabsWrapper">
			</section>
			        <span class="changePageRow"> 
        	<span class="cell">
            <input class="addtoorderbtn" type="button" id="AddToOrderBtn" value="Add to Order" />
        	</span>      
           	<span class="cell">
               <input type="button" value="" class="backArrowBtn" onclick="backToDiscovery();"/>
             </span>
             <span class="cell">
               <input type="button" value="" class="forwardArrowBtn" onclick="goToClosingcall();"/>
             </span>
      	</span>
 
		</div>
	</section>
	
	<!-- </form> -->		
 
</div>
