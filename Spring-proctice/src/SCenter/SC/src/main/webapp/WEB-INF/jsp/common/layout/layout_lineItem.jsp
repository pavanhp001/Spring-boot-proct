<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
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
<script src="<c:url value='/script/timer.js'/>"></script>

		  
	 

<style>
div#scrollable_content {
	position: absolute;
	top: 0px;
	left: 200px;
	bottom: 0px;
	width: 100%;
	overflow-y: auto;
	margin-left: 200px;
}

div#summary_content {
	position: absolute;
	top: 20px;
	bottom: 0px;
	width: 200px;
	overflow-y: auto;
}

body{
	overflow-y: scroll;
}

#action_wrapper {
    margin-left: -10px;
    margin-top: -50px;
    z-index: 3000;
}

#content_header{
	width :660px;
}

.content_border{
    height :495px;
    width :660px;
}
</style>

</head>

<body onload="updateTimer()">
<div id="wrapper">
    <header id="main_header">
        <div id="SYP_toolbar">
            <div id="SYP_title"><fmt:message key="layout.SYP.title"/></div><div id="AL_title"><fmt:message key="layout.AL"/></div>
            <nav id="main_menu">
	<ul>
		<li><a href="#"><fmt:message key="layout.performance"/></a></li>
		<li><a href="#"><fmt:message key="layout.settings"/></a>
			<ul>
				<li><a href="#">Icon Orientation</a>
                	<ul>
						<li><a href="#">Horizontal</a></li>
						<li><a href="#">Verticle</a></li>
					</ul>
                </li>
				<li><a href="#">Icon Theme</a>
					<ul>
						<li><a href="#">AL</a></li>
						<li><a href="#">Spectrum</a></li>
					</ul>
				</li>
			</ul>
		</li>
		<li><a href="#"><fmt:message key="layout.SYP.help"/></a>
		</li>
        <li>
        	<form class="searchform">
<input class="searchfield" type="text" onblur="if (this.value == '') {this.value = 'Search...';}" onfocus="if (this.value == 'Search...') {this.value = '';}" value="<fmt:message key="layout.search"/>" />
<input class="searchbutton" type="button" value="<fmt:message key="layout.go"/>" />
</form>
        
        </li>
	</ul>
</nav>
        </div>
        <div id="SYP_ALbar"><img src="<%=request.getContextPath()%>/images/img/ALLogoMain.png" width="149" height="20" alt="AL" border="0" />
        </div>
    </header>
    <div id="contentWrapper">
            <aside id="left_aside">
            </aside>
        <section id="main_section">
        	<section id="MiddleContentShell">
            	<tiles:insertAttribute name="product_menu_content" />
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
                    		<span class="cell"><fmt:message key="${title}"/> </span>
                    		<span class="cell" id="startTimeText"></span>
                    	</div>
				</header>
                <div class="content_border">
                
                
				
		<table id="addLiGrid">
			<tr>
			<td>
				<section id="action_wrapper">
				<tiles:insertAttribute name="main_content" />
				</section> 
				</td>
				<td>
				<section id="action_wrapper">
				<tiles:insertAttribute name="summary_content" />
				</section> 
				</td>
			</tr>

		</table>

<footer id="action_footer">
                <span class="row changePageRow">
                	<span class="cell">
                    <input type="submit" value="" class="backArrowBtn"/>
                    </span>
                    <span class="cell">
                    <input type="submit" value="" class="forwardArrowBtn"/>
                    </span>
                </span>
                </footer>
                </div>
         <! --- end content div --->
           </section>
        </section>
        <aside id="main_aside">
        </aside>
    </div>    
    <footer id="main_footer">
    <span class="row">
    <span class="cell terms font8">
   <fmt:message key="layout.footer.message"/></span>
					<span class="cell">
                       <form action="<%=request.getContextPath()%>/salescenter/endCall" method="post">
                        <input type="submit" name="End Call" value="<fmt:message key="layout.endCall"/>" class="css3button" /></input>
                        </form>
                     </span>
                     <span class="cell font9"><fmt:message key="layout.footer.msg2"/></span>
    </span>
    </footer>
                	
</div>
</body>
</html>
