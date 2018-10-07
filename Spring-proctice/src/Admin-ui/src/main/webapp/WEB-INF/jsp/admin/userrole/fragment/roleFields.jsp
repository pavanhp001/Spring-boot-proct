<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>		                        

 	<fieldset id="user-fields-column1" class="threeCol">	    
 		<jsp:include page="/WEB-INF/jsp/fragment/input-fields/code.jsp"/>
	</fieldset>

	<fieldset id="user-fields-column2" class="threeCol">
        <jsp:include page="/WEB-INF/jsp/fragment/input-fields/descriptionEnglish.jsp"/>
	</fieldset>

	<fieldset id="user-fields-column3" class="threeCol">
        <jsp:include page="/WEB-INF/jsp/fragment/input-fields/descriptionLang.jsp"/>
	</fieldset>