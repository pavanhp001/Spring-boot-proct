<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/main.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/css_new/jquery-ui.css'/>"/>

<!--<script type="text/javascript" src="/js/js_new/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/js/js_new/jquDD5B.js"></script>
<script type="text/javascript" src="/js/js_new/jquery-ui.min.js"></script>


<script src="<c:url value='/script/timer.js'/>"></script>
 -->
<script src="<c:url value='/js/jquDD5B.js'/>"></script>
<script src="<c:url value='/js/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/js/backbuttons.js'/>"></script>

		<meta charset="utf-8">
		<title><fmt:message key="sales.title" /></title>
		<link rel="stylesheet" href="<c:url value='/css/css_new/main.css'/>" />
		<script	src="<c:url value='/js/jquery.js'/>"></script>
		<script type="text/javascript">
		$(document).ready(function(){
		 $(document).bind("dragstart", function() {
		     return false;
		 });
		 $("input:text:visible:first").focus();
		 var wrongUserName = $('#wrongUserName').val();
		 if(wrongUserName == 'true'){
			 $("input:text:visible:first").focus();
			 }
		 var wrongPwd = $('#wrongPwd').val();
		 if(wrongPwd == 'true'){
			 $("input:password:visible:first").focus();
			 }
		});
			var validate = function(){
			$("div.loginerror").html("");
			var userId  = $('input#username').val();
			var pswd = $('input#password').val();
			var stat = true;
			var msg = "";
			if (userId == null || userId == ""){
				$("input:text:visible:first").focus();
				msg = "Please enter a valid UserName";
				$('input#username').val("");
				$('input#password').val("");
				 stat = false;
			} 
			
			if(pswd == null || pswd == ""){
				$("input:password:visible:first").focus();
				if (userId == null || userId == ""){
					$("input:text:visible:first").focus();
				}
				msg = msg+"<br/>"+" Please enter a valid Password";
				$('input#password').val("");
				 stat = false;	
			}
			$('div#missingFields').html(msg);
			return stat;
		}		
		</script>
	</head>
	<body>
		<div class="container">
			<header id="header">
				<div class="headerinfocontainer">
					<div class="headerInformation">
						<div class="headerTextInformation">
							<span class="topRow">							
							</span><br/>
							<span class="bottomRow">							
							</span>
						</div>
						<div class="headerTextInformation2">
							<span class="topRow">							
							</span><br/>
							<span class="bottomRow">							
							</span>
						</div>
					</div>
					<!-- nav id="main_menu">
						<ul>
							<li><a href="#">Performance</a></li>
							<li><a href="#">Settings</a></li>
							<li><a href="#">Help</a></li>
						</ul>						
						<form class="searchform">
							<input class="searchfield" type="text" onblur="if (this.value == '') {this.value = 'Search...';}" onfocus="if (this.value == 'Search...') {this.value = '';}" value="Search..." />
							<input class="searchbutton" type="button" value="" />
						</form>
					</nav-->
					
				</div>
				<div class="headernavcontainer"></div>
			</header>
			<div class="providerlogo">
				<div class="providerlogocont">
					<img src="<%=request.getContextPath()%>/images/images_new/AL_logo.png" alt="AL" />
				</div>
			</div>
			<div class="concertlogo">
				<div class="concertlogocont">
					<img src="<%=request.getContextPath()%>/images/images_new/concert_logo.png" />
				</div>
			</div>
			<article id="mainsection">
				<aside id="left_aside">
				</aside>
				<section id="maincontent">
					<section id="logindialog">
						<form method="post" action="<%=request.getContextPath()%>/salescenter/login_process" name="loginProcess" style="height:265px;" id="login" class="loginWidget" autocomplete="off">
							<section class="logindialogcont">
								<div class="logindialogcontent">
									<div class="logintitle">Log in to Concert!</div>
								    <%if( request.getAttribute("isSessionTimeOut")!= null){
								    	Boolean isSessionTimeOut =(Boolean)request.getAttribute("isSessionTimeOut");
								    	if(isSessionTimeOut){ %>
								    		<div class="loginerror">Your session has timed out.  Please log in again.</div>
								    	<%}
								    }%>
								    <input type="hidden" id="wrongUserName" value="${wrongUserName}">
								    <input type="hidden" id="wrongPwd" value="${wrongPwd}">
								    
								    
								    <c:if test="${wrongUserName == true}">
								    	<div class="loginerror" style="color:red" > UserName is Invalid</div>
								    </c:if>
								    <c:if test="${wrongPwd == true}">
								    	<div class="loginerror" style="color:red"> Password is Invalid</div>
								    </c:if>
								    <c:if test="${permissionDenied == true}">
								    	<div class="loginerror" style="color:red"> Permissions Denied.   Contact IT Help Desk </div>
								    </c:if>							       
				     				<div id="missingFields" class="loginerror"></div>
									<div class="loginfields"><input type="text" id="username" name="chatForm:j_username" value="${userName }" class="loginuser" placeholder="User Name" /></div>
									<div class="loginfields"><input type="password" id="password" name="chatForm:j_password" class="loginpwd" placeholder="Password" /></div>
									<div class="loginbutton"><input type="submit" id="submit" value="" onclick="return validate();"/></div>
								</div>
							</section>
						</form>
						<c:if test="${wrongUserName == true || wrongPwd == true}">
									    	<div class="loginerror" style="color:red;margin-top:300px;" >There seems to be a problem
							verifying your credentials.  Please double check
							the information you entered and try again. If you continue to experience
							problems, please click <a href="https://acsecmgmt05.AL.com/QPMUser" >here</a> to reset your password or contact the IT helpdesk
							for assistance.</div>
					    </c:if>
					</section><!-- logindialog -->
				</section>
				<aside id="main_aside">
				</aside>
			</article>
			<footer id="main_footer">
				<div class="footer_center">
					<div class="cell terms font9">
						<fmt:message key="layout.footer.message" />
					</div>
					<div class="footer_content">
						<div class="copyright">
							Copyright &copy; 2013 AL Inc. All Rights Reserved. ${buildVersion}
						</div>
						<div class="endcall">&nbsp;</div>
						<div class="ambMsg">&nbsp;</div>
						<div class="poweredBy">
							<div class="poweredByImage"></div>
						</div>
					</div>
				</div>
			</footer>
		</div>
	</body>
</html>
