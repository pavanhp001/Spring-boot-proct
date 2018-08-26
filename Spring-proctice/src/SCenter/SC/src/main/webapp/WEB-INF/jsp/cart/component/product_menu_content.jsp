<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<body>
  <nav id="products">
                	<ul class="productsNav">
                    	<li class="prodcutsBtn1">
                    		<a href="<%=request.getContextPath()%>/salescenter/recommendations" >
                        		<img src="<%=request.getContextPath()%>/images/images_new/nav/Star.png" width="50" height="42" alt="Power Pitch" border="0" />
                            	<label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.pp" /></label>
                            </a>
                        </li>
                        <c:if test="${recIconMap.MIXEDBUNDLES != '#'}">
                    	    <li class="prodcutsBtn" style="width:72px;">
                    		<a href="${flowExecutionUrl}&_eventId=recommendationsBySyntheticBundlesEvent&CategoryName=MIXEDBUNDLES" onclick="sendCategory('MIXEDBUNDLES')">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/mixed-bundles-icon.png" width="46" height="39" alt="Security" border="0" />
	                            <label id="productLabel" class="productLabel" value="MIXEDBUNDLES" for="MIXEDBUNDLES"><fmt:message key="productContent.MIXEDBUNDLES" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.MIXEDBUNDLES == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis" style="width:72px;">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/mixed-bundles-icon_d.png" width="46" height="39" alt="Security" border="0" />
	                            <label id="productLabel" class="productLabel" value="MIXEDBUNDLES" for="MIXEDBUNDLES"><fmt:message key="productContent.MIXEDBUNDLES" /></label>
                            </li>
                        </c:if>
                        <c:if test="${recIconMap.TRIPLE_PLAY != '#'}">
                        	<li class="prodcutsBtn">
	                    		<a href="${recIconMap.TRIPLE_PLAY}" >
		                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Triples.png" width="50" height="42" alt="Triples" border="0" />
		                            <label id="productLabel" class="productLabel" value="Triples" for="Triples"><fmt:message key="productContent.triples" /></label>
	                            </a>
                        	</li>
                        </c:if>
                        
                        <c:if test="${recIconMap.TRIPLE_PLAY == '#'}">
                        	<li class="prodcutsBtn prodcutsBtnDis">
		                        <img src="<%=request.getContextPath()%>/images/images_new/nav/Triples_d.png" width="50" height="42" alt="Triples" border="0" />
		                        <label id="productLabel" class="productLabel" value="Triples" for="Triples"><fmt:message key="productContent.triples" /></label>
                        	</li>
                        </c:if>

	                  	<c:if test="${recIconMap.DOUBLE_PLAY != '#'}">
	                    	<li class="prodcutsBtn">
	                    		<a href="${recIconMap.DOUBLE_PLAY}" >
		                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Doubles.png" width="50" height="42" alt="Doubles" border="0" />
		                            <label id="productLabel" class="productLabel" value="Doubles" for="Doubles"><fmt:message key="productContent.doubles" /></label>
	                            </a>
								<ul>
									<li><a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/DOUBLE_PLAY_VI">VIDEO-INTERNET</a></li>
									<li><a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/DOUBLE_PLAY_PV">PHONE-VIDEO</a></li>
									<li><a href="<%=request.getContextPath()%>/salescenter/recommendationsbyCategory/DOUBLE_PLAY_PI">PHONE-INTERNET</a></li>
								</ul>
	                        </li>
                        </c:if>

                        <c:if test="${recIconMap.DOUBLE_PLAY == '#'}">
                        	<li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Doubles_d.png" width="50" height="42" alt="Doubles" border="0" />
	                            <label id="productLabel" class="productLabel" value="Doubles" for="Doubles"><fmt:message key="productContent.doubles" /></label>
                            </li>
                        </c:if>	
                        
                        <c:if test="${recIconMap.VIDEO != '#'}">
                    	    <li class="prodcutsBtn">
                    	    <a href="${recIconMap.VIDEO}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/TV.png" width="50" height="42" alt="Video" border="0" />
	                            <label id="productLabel" class="productLabel" value="Video" for="Video"><fmt:message key="productContent.video" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                         <c:if test="${recIconMap.VIDEO == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/TV_d.png" width="50" height="42" alt="Video" border="0" />
	                            <label id="productLabel" class="productLabel" value="Video" for="Video"><fmt:message key="productContent.video" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.INTERNET != '#'}">
                    	    <li class="prodcutsBtn">
                    	    <a href="${recIconMap.INTERNET}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Internet.png" width="50" height="42" alt="Internet" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.internet" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.INTERNET == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Internet_d.png" width="50" height="42" alt="Internet" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.internet" /></label>
                           </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.PHONE != '#'}">
                    	    <li class="prodcutsBtn">
                    	    <a href="${recIconMap.PHONE}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Phone.png" width="50" height="42" alt="Phone" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.phone" /></label>
                            </a>
                        </li>
                        </c:if>
                        
                        
                        <c:if test="${recIconMap.PHONE == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Phone_d.png" width="50" height="42" alt="Phone" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="PP"><fmt:message key="productContent.phone" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.HOMESECURITY != '#'}">
                    	<li class="prodcutsBtn">
                    	    <a href="${recIconMap.HOMESECURITY}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Security.png" width="50" height="42" alt="Security" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="Security"><fmt:message key="productContent.security" /></label>
                            </a>
                        </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.HOMESECURITY == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Security_d.png" width="50" height="42" alt="Security" border="0" />
	                            <label id="productLabel" class="productLabel" value="PP" for="Security"><fmt:message key="productContent.security" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.ASIS_PLAN != '#'}">
                    	    <li class="prodcutsBtn">
                    	    <a href="${recIconMap.ASIS_PLAN}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Asis.png" width="50" height="42" alt="Asis" border="0" />
	                            <label id="productLabel" class="productLabel" value="Asis" for="ASIS"><fmt:message key="productContent.asis" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.ASIS_PLAN == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Asis_d.png" width="50" height="42" alt="Asis" border="0" />
	                            <label id="productLabel" class="productLabel" value="Asis" for="Asis"><fmt:message key="productContent.asis" /></label>
                            </li>
                        </c:if>
                        
                         <c:if test="${recIconMap.ELECTRICITY != '#'}">
                    	    <li class="prodcutsBtn">
                    	    <a href="${recIconMap.ELECTRICITY}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Electric.png" width="50" height="42" alt="Electric" border="0" />
	                            <label id="productLabel" class="productLabel" value="Electric" for="Electric"><fmt:message key="productContent.electric" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.ELECTRICITY == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Electric_d.png" width="50" height="42" alt="Electric" border="0" />
	                            <label id="productLabel" class="productLabel" value="Electric" for="Electric"><fmt:message key="productContent.electric" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.NATURALGAS != '#'}">
                    	<li class="prodcutsBtn">
                    	    <a href="${recIconMap.NATURALGAS}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Gas.png" width="50" height="42" alt="Gas" border="0" />
	                            <label id="productLabel" class="productLabel" value="Gas" for="Gas"><fmt:message key="productContent.gas" /></label>
                            </a>
                        </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.NATURALGAS == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Gas_d.png" width="50" height="42" alt="Gas" border="0" />
	                            <label id="productLabel" class="productLabel" value="Gas" for="Gas"><fmt:message key="productContent.gas" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.WATER != '#'}">
                    	<li class="prodcutsBtn">
                    	    <a href="${recIconMap.WATER}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Water.png" width="50" height="42" alt="Water" border="0" />
	                            <label id="productLabel" class="productLabel" value="Water" for="Water"><fmt:message key="productContent.water" /></label>
                            </a>
                        </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.WATER == '#'}">
                    	    <li class="prodcutsBtn prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Water_d.png" width="50" height="42" alt="Water" border="0" />
	                            <label id="productLabel" class="productLabel" value="Water" for="Water"><fmt:message key="productContent.water" /></label>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.WASTEREMOVAL != '#'}">
                    	    <li class="prodcutsBtn2">
                    		<a href="${recIconMap.WASTEREMOVAL}" >
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Waste.png" width="50" height="42" alt="Waste" border="0" />
	                            <label id="productLabel" class="productLabel" value="WASTE" for="Waste"><fmt:message key="productContent.waste" /></label>
                            </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${recIconMap.WASTEREMOVAL == '#'}">
                    	    <li class="prodcutsBtn2 prodcutsBtnDis">
	                        	<img src="<%=request.getContextPath()%>/images/images_new/nav/Waste_d.png" width="50" height="42" alt="Waste" border="0" />
	                            <label id="productLabel" class="productLabel" value="WASTE" for="Waste"><fmt:message key="productContent.waste" /></label>
                            </li>
                        </c:if>
                        
                    </ul>	
                  </nav>
  
</body>
</html>