<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache,java.util.Calendar,java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title>Recoverable Exception</title>

 
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.datepicker.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/jquery.ui.all.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/warmTransfer.css'/>"/>
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.core.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.widget.js'/>"></script>
<script src="<c:url value='/js/jquery.ui.datepicker.js'/>"></script>
<script src="<c:url value='/script/html_save.js'/>"></script>
 <script >
 function submitForm(){
	$('form#recoverable').submit();
 }
 $(document).ready(function(){
	 jQuery(window).load(function () {
	      //To save the html on page load
	      savePageHtml(true, "");
	}); 
 });
 </script>

<style type="text/css">

.bottombuttons {
    left: 0;
    position: absolute;
    top: 610px;
    width: 100%;
    z-index: 999999;
}
.pc_fldset_subcont_txtflds {
  margin-left: 354px;
}
	.retry {
		-moz-box-shadow:inset 0px 1px 0px 0px #ffffff;
		-webkit-box-shadow:inset 0px 1px 0px 0px #ffffff;
		box-shadow:inset 0px 1px 0px 0px #ffffff;
		background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #ededed), color-stop(1, #dfdfdf) );
		background:-moz-linear-gradient( center top, #ededed 5%, #dfdfdf 100% );
		filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ededed', endColorstr='#dfdfdf');
		background-color:#ededed;
		-moz-border-radius:15px;
		-webkit-border-radius:15px;
		border-radius:15px;
		border:2px solid #dcdcdc;
		display:inline-block;
		color:#777777;
		font-family:arial;
		font-size:13px;
		font-weight:bold;
		padding:4px 25px;
		text-decoration:none;
		text-shadow:-2px 1px 4px #ffffff;
	}
	.retry:hover {
		background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #dfdfdf), color-stop(1, #ededed) );
		background:-moz-linear-gradient( center top, #dfdfdf 5%, #ededed 100% );
		filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#dfdfdf', endColorstr='#ededed');
		background-color:#dfdfdf;
	}
	.retry:active {
		position:relative;
		top:1px;
	}
	.pc_pdetails {
	  display: inline-block;
	  font-family: Arial,Helvetica,sans-serif,Verdana;
	  line-height: 18px;
	  width: 100%;
	}
	.pc_pdetails_info {
	  display: inline-block;
	  line-height: 18px;
	  width: 800px;
	}
	.pc_pdetails .label {
	  float: left;
	  font-size: 14px;
	  font-weight: bold;
	  margin-left: 5px;
	  width: 172px;
	}
	.pc_pdetails .value {
	  color: #000000;
	  float: left;
	  font-size: 14px;
	  font-weight: bold;
	  width: 600px;
	}
	.pc_pdetails_cont .pc_frameblk {
	  height: 455px;
	}
/* This imageless css button was generated by CSSButtonGenerator.com */
</style>
<%
Calendar cal=Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
%>
</head>


					<section id="contentfullcont">
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
								<span class="cell pageTitle">Recoverable Error Page</span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="recoverable" name="recoverable" action="<%=request.getContextPath()%>" method="post" autocomplete="off">
							<input type="hidden" id="submitSt" name="submitSt"/>
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
							<input type="hidden" id="pageTitle" name="pageTitle" value="New Services">
							<div class="pc_pdetails">
								<div class="pc_pdetails_info">
									<div class="label">Order Number :</div><div class="value"><%=request.getSession().getAttribute("orderId") %></div>
									<div class="label">Customer Number :</div><div class="value"><%=request.getSession().getAttribute("customerID") %></div>
									<div class="label">GUID :</div><div class="value"><%=request.getSession().getAttribute("GUID") %></div>
									<div class="label">Error On :</div><div class="value">${pageTitle}</div>
									<div class="label">Error Time :</div><div class="value"><%= sdf.format(cal.getTime()) %></div>
								</div>
							</div>
							<div class="pc_pdetails_cont">
									<!-- Left Block -->
									<div class="pc_frameblk">
										<div class="pc_frameblk_title"></div>
										<div class="pc_frameblk_cont">
										<fieldset class="pc_fldset">
										<legend>Error</legend>
											<div class="pc_fldset_cont">
												<div class="pc_fldset_data_item_cont">
													<div class="label_desc">
														<label>${message}</label>
													</div>
												</div>
											<!-- <div class="pc_fldset_subcont_txtflds">
												<span class="value"><a href="#" class="retry" onclick="submitForm();">Retry</a></span>
												</div> -->	
											</div>
										</fieldset>
										<!-- <div class="pc_frameblk_right_btns">
										</div> -->
										<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
										<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
									</div>
									
								</div>
									<div class="bottombuttons">
											<div class="rightbtns">
												<input type="button" id="forward" name="" value="Forward >" onclick ="submitForm();"/>
											</div>
									</div>
							</form>
						</section><!--  Content -->
						
					</section><!-- Content Full Cont -->
