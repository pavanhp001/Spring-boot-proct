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
 
</head>

<body>
<div id="wrapper">
    <header id="main_header">
        <div id="SYP_toolbar">
            <div id="SYP_title">SYP</div><div id="AL_title">AL</div>
            <nav id="main_menu">
	<!--<ul>
		<li><a href="#">Performance</a></li>
		<li><a href="#">Settings</a>
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
		<li><a href="#">Help</a>
		</li>
        <li>
        	<form class="searchform">
<input class="searchfield" type="text" onblur="if (this.value == '') {this.value = 'Search...';}" onfocus="if (this.value == 'Search...') {this.value = '';}" value="Search...">
<input class="searchbutton" type="button" value="Go">
</form>
        
        </li>
	</ul>
--></nav>
        </div>
        <div id="SYP_ALbar"><img src="<%=request.getContextPath()%>/images/img/ALLogoMain.png" width="149" height="20" alt="AL" border="0" />
        </div>
    </header>
    <div id="contentWrapper">
        <aside id="left_aside">
        </aside>
        <section id="main_section">
        	<section id="MiddleContentShell">
            
<form id="login" class="loginWidget">
    <h1 class="loginstyle">Log In</h1>
    <fieldset id="inputs">
        <input id="username" type="text" placeholder="Username" autofocus required>   
        <input id="password" type="password" placeholder="Password" required>
    </fieldset>
    <fieldset id="actions">
        <input type="submit" id="submit" value="Log in">dfgfds
   </fieldset>
</form>            
            </section>
        </section>
        <aside id="main_aside">
        </aside>
    </div>    
    <footer id="main_footer">
    <span class="row">
    <span class="cell terms font7">
    Only users with express authorization from AL, Inc. may use this system. Unauthorized use is strictly prohibited and may expose you to legal liability. Violations will be logged and reported.</span>
					<span class="cell">
                        <form>
                        <input type="submit" name="End Call" value="End Call" class="css3button"></input>
                        </form>
                     </span>
                     <span class="cell font9">powered by AL</span>
                	
</div>
</body>
</html>