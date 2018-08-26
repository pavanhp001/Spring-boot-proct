<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<body>

  <nav id="products">
                	<ul class="productsNav">
	                	<c:choose>
		                	<c:when test="${not empty recIconMap.PP && recIconMap.PP == '#'}">
		                    	<li class="prodcutsBtn prodcutsBtnDis">
		                        		<img src="<%=request.getContextPath()%>/images/images_new/nav/Star_d.png" width="46" height="39" alt="Power Pitch" border="0" />
		                            	<label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.pp" /></label>
		                        </li>
		                      </c:when>
		                      <c:otherwise>
		                      	<li class="prodcutsBtn">
		                    		<a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=PP" >
		                        		<img src="<%=request.getContextPath()%>/images/images_new/nav/Star.png" width="46" height="39" alt="Power Pitch" border="0" />
		                            	<label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.pp" /></label>
		                            </a>
		                        </li>
		                      </c:otherwise>
	                	</c:choose>
                		 <c:if test="${recIconMap.MIXEDBUNDLES != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    		<a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=MIXEDBUNDLES">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/mixed-bundles-icon.png" width="46" height="39" alt="Bundle$" border="0" />
	                            <label id="productLabel" class="productLabel" value="MIXEDBUNDLES" for="MIXEDBUNDLES"><fmt:message key="productContent.MIXEDBUNDLES" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.MIXEDBUNDLES == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/mixed-bundles-icon_d.png" width="46" height="39" alt="Bundle$" border="0" />
	                            <label id="productLabel" class="productLabel" value="MIXEDBUNDLES" for="MIXEDBUNDLES"><fmt:message key="productContent.MIXEDBUNDLES" /></label>
                            </li>
                        </c:if> 
                		
                        <c:if test="${recIconMap.TRIPLE_PLAY != '#'}">
                        	<li class="prodcutsBtn" style="width:72px;">
	                    		<a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=TRIPLE_PLAY" >
		                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Triples.png" width="46" height="39" alt="Triples" border="0" />
		                            <label id="productLabel" class="productLabel" value="TRIPLE_PLAY" for="Triples"><fmt:message key="productContent.triples" /></label> 
	                            </a>
                        	</li>
                        </c:if>
                        
                        <c:if test="${recIconMap.TRIPLE_PLAY == '#'}">
                        	<li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
		                        <img src="<%=request.getContextPath()%>/images/images_new/nav/Triples_d.png" width="46" height="39" alt="Triples" border="0" />
		                        <label id="productLabel" class="productLabel" value="Triples" for="Triples"><fmt:message key="productContent.triples" /></label>
                        	</li>
                        </c:if>

	                  	<c:if test="${recIconMap.DOUBLE_PLAY != '#'}">
	                    	<li class="prodcutsBtn" style="width:72px;">
	                    		<a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=DOUBLE_PLAY"  >
		                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Doubles.png" width="46" height="39" alt="Doubles" border="0" />
		                            <label id="productLabel" class="productLabel" value="DOUBLE_PLAY" for="Doubles"><fmt:message key="productContent.doubles" /></label>
	                            </a>
								<ul>
									<li><a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=DOUBLE_PLAY_VI" id="DOUBLE_PLAY_VI" class="doubleplay_sub">VIDEO-INTERNET</a></li>
									<li><a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=DOUBLE_PLAY_PV" id="DOUBLE_PLAY_PV" class="doubleplay_sub">PHONE-VIDEO</a></li>
									<li><a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=DOUBLE_PLAY_PI" id="DOUBLE_PLAY_PI" class="doubleplay_sub">PHONE-INTERNET</a></li>
								</ul>
	                        </li>
                        </c:if>

                        <c:if test="${recIconMap.DOUBLE_PLAY == '#'}">
                        	<li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Doubles_d.png" width="46" height="39" alt="Doubles" border="0" />
	                            <label id="productLabel" class="productLabel" value="Doubles" for="Doubles"><fmt:message key="productContent.doubles" /></label>
                            </li>
                        </c:if>	
                        
                        <c:if test="${recIconMap.VIDEO != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    	    <a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=VIDEO"  >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/TV.png" width="46" height="39" alt="Video" border="0" />
	                            <label id="productLabel" class="productLabel" value="VIDEO" for="Video"><fmt:message key="productContent.video" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                         <c:if test="${recIconMap.VIDEO == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/TV_d.png" width="46" height="39" alt="Video" border="0" />
	                            <label id="productLabel" class="productLabel" value="Video" for="Video"><fmt:message key="productContent.video" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.INTERNET != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    	    <a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=INTERNET"  >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Internet.png" width="46" height="39" alt="Internet" border="0" />
	                            <label id="productLabel" class="productLabel" value="INTERNET" for="PP"><fmt:message key="productContent.internet" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.INTERNET == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Internet_d.png" width="46" height="39" alt="Internet" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.internet" /></label>
                           </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.PHONE != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    	    <a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=PHONE" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Phone.png" width="46" height="39" alt="Phone" border="0" />
	                            <label id="productLabel" class="productLabel" value="PHONE" for="PP"><fmt:message key="productContent.phone" /></label>
                            </a>
                        </li>
                        </c:if>
                        
                        
                        <c:if test="${recIconMap.PHONE == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Phone_d.png" width="46" height="39" alt="Phone" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.phone" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.HOMESECURITY != '#'}">
                    	<li class="prodcutsBtn" style="width:72px;">
                    	    <a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=HOMESECURITY" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Security.png" width="46" height="39" alt="Security" border="0" />
	                            <label id="productLabel" class="productLabel" value="HOMESECURITY" for="Security"><fmt:message key="productContent.security" /></label>
                            </a>
                        </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.HOMESECURITY == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Security_d.png" width="46" height="39" alt="Security" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="Security"><fmt:message key="productContent.security" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.ASIS_PLAN != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    	    <a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=ASIS_PLAN" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Asis.png" width="46" height="39" alt="Asis" border="0" />
	                            <label id="productLabel" class="productLabel" value="ASIS_PLAN" for="Asis"><fmt:message key="productContent.asis" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.ASIS_PLAN == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Asis_d.png" width="46" height="39" alt="Asis" border="0" />
	                            <label id="productLabel" class="productLabel" value="Asis" for="Asis"><fmt:message key="productContent.asis" /></label>
                            </li>
                        </c:if>
                        
                         <c:if test="${recIconMap.ELECTRICITY != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    	    <a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=ELECTRICITY" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Electric.png" width="46" height="39" alt="Electric" border="0" />
	                            <label id="productLabel" class="productLabel" value="ELECTRICITY" for="Electric"><fmt:message key="productContent.electric" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.ELECTRICITY == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Electric_d.png" width="46" height="39" alt="Electric" border="0" />
	                            <label id="productLabel" class="productLabel" value="Electric" for="Electric"><fmt:message key="productContent.electric" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.NATURALGAS != '#'}">
                    	<li class="prodcutsBtn" style="width:72px;">
                    	    <a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=NATURALGAS" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Gas.png" width="46" height="39" alt="Gas" border="0" />
	                            <label id="productLabel" class="productLabel" value="NATURALGAS" for="Gas"><fmt:message key="productContent.gas" /></label>
                            </a>
                        </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.NATURALGAS == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Gas_d.png" width="46" height="39" alt="Gas" border="0" />
	                            <label id="productLabel" class="productLabel" value="Gas" for="Gas"><fmt:message key="productContent.gas" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.WATER != '#'}">
                    	<li class="prodcutsBtn" style="width:72px;">
                    	    <a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=WATER" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Water.png" width="46" height="39" alt="Water" border="0" />
	                            <label id="productLabel" class="productLabel" value="WATER" for="Water"><fmt:message key="productContent.water" /></label>
                            </a>
                        </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.WATER == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Water_d.png" width="46" height="39" alt="Water" border="0" />
	                            <label id="productLabel" class="productLabel" value="Water" for="Water"><fmt:message key="productContent.water" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.WASTEREMOVAL != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    		<a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=WASTEREMOVAL" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Waste.png" width="46" height="39" alt="Waste" border="0" />
	                            <label id="productLabel" class="productLabel" value="WASTEREMOVAL" for="Waste"><fmt:message key="productContent.waste" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.WASTEREMOVAL == '#'}">
                    	    <li class="prodcutsBtn2 prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Waste_d.png" width="46" height="39" alt="Waste" border="0" />
	                            <label id="productLabel" class="productLabel" value="WASTE" for="Waste"><fmt:message key="productContent.waste" /></label>
                            </li>
                        </c:if>
                        
                         <c:if test="${recIconMap.APPLIANCEPROTECTION != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    		<a href="${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=APPLIANCEPROTECTION" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/ApplianceProtection.png" width="46" height="39" alt="ApplianceProtection" border="0" />
	                            <label id="productLabel" class="productLabel" value="APPLIANCEPROTECTION" for="ApplianceProtection"><fmt:message key="productContent.ApplianceProtection" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.APPLIANCEPROTECTION == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/ApplianceProtection_d.png" width="46" height="39" alt="ApplianceProtection" border="0" />
	                            <label id="productLabel" class="productLabel" value="ApplianceProtection" for="ApplianceProtection"><fmt:message key="productContent.ApplianceProtection" /></label>
                            </li>
                        </c:if>  
                        
                    </ul>	
                  </nav>
  
</body>
</html>