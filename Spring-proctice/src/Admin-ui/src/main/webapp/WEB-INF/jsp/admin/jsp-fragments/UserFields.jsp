<!-- 
/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */

-->

<!--
********************************************************************************************************
VERSION:
$HeadURL: http://casource:7777/svn/irisk/adminUI/trunk/src/main/webapp/WEB-INF/jsp/admin/adminUserSearchForm.jsp $
********************************************************************************************************
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>

<link rel="stylesheet" href="/bcs/css/jsp-page/AdminCommon.css" type="text/css">
<script type="text/javascript" src="/bcs/scripts/Util.js"></script>
<script type="text/javascript">	
	function getSelectedRoles() {
		var selectedStr = '';
		var roleSelect = document.getElementById('roleSelect');
		if (roleSelect) {

			for (var i = 0; i < roleSelect.length; i++) {
				var option = roleSelect[i];
				if (option.selected) {
					if (selectedStr.length == 0) {
						selectedStr = option.value;	
					} else {
						selectedStr = selectedStr + ',' + option.value;
					}
				}
			}
			document.getElementById('roles').value = selectedStr;
		}
	}

	function roleSelection(){
		getSelectedRoles();
		disableAirTextFields();
	}
	
	function disableAirTextFields(){
		var roles = document.getElementById('roles').value;
		var acceptedRoles = new Array('PPA','HOS','INT','BAM','ADR','VTD');
		var enableAirport = false;
		var selectedRoleCount = 0;
		for(var i = 0; i < acceptedRoles.length; i++){
			if(roles.indexOf(acceptedRoles[i]) > -1){
				enableAirport = true;
				break;
			}
		}
		if($('#isShowingAirport').length){
			var selectedRoles = $('#roleSelect option:selected');
			for(var i = 0; i < selectedRoles.length; i++){
				if(!(jQuery.inArray(selectedRoles[i].value, acceptedRoles)> -1)) {
					selectedRoleCount = selectedRoleCount+1;
				}
			}
			if(enableAirport && selectedRoleCount > 0){
				enableAirport = !enableAirport;
			}
		}

		if(enableAirport){			
			$('#airportDiv').removeAttr('disabled'); 
			$('#airportDiv').show(); 
			$('#location').removeAttr('disabled'); 
			$('#location').show();
            $('#airportLabel').show();
            $('#airportLabelError').show();
		}
		else{
			$('#airportDiv').hide();
			$('#location').attr('disabled', true);
			$('#location').val(''); 
			$('#location').hide();
            $('#airportLabel').hide();
            $('#airportLabelError').hide();
		}
		
		
	}
	
</script>           

<c:set var="mandatoryField" value="*"/> 
<c:if test="${!(empty displayMode)}">
	<c:set var="mandatoryField" value=""/> 
</c:if>
  <div id="searchFormDiv">
   <div id="searchCriteria">
    		<fieldset id="user-fileds-column1" class="threeCol">
                    <div class="fieldsetRow">
                    	<jsp:include page="username.jsp" />
                    </div>
                    <div class="fieldsetRowTall">
                    	<jsp:include page="roles.jsp" />
                    </div>
                    <div class="fieldsetRow">
            			&nbsp;
        			</div>
                </fieldset>
                
                <fieldset id="user-fileds-column2" class="threeCol">
                    <div class="fieldsetRow">
                    	<jsp:include page="forename.jsp" />
                    </div> 
                    <div class="fieldsetRowTall">
                    	<jsp:include page="email.jsp" />
                    </div> 
                    <div class="fieldsetRow" id="airportDiv">
                    	<jsp:include page="airport.jsp" />
                    </div>
                </fieldset>
        		
        		<fieldset id="user-fileds-column3" class="threeCol">
                    <div class="fieldsetRow">
						<jsp:include page="lastname.jsp" />
					</div>
					<div class="fieldsetRowTall">
						<jsp:include page="mobile.jsp" /> 
					</div>
					
					<c:if test="${userScreenName eq 'adminUserUpdateForm' or userScreenName eq 'adminUserUpdate'}">
			            <div class="fieldsetRow">
		                	<jsp:include page="passwordchanged.jsp" />
		                </div>
		                <u:checkRole roleGroup="UPDATE_OWN_PROFILE"> 
			                <div class="fieldsetRow">
				                <jsp:include page="lastloggedon.jsp" />
				            </div>         	               	
                        </u:checkRole>
					</c:if>
					 <u:checkRole roleGroup="UPDATE_OWN_PROFILE">
						<div class="fieldsetRow">
							<jsp:include page="status.jsp" />
						</div>
                    </u:checkRole>
					
                </fieldset>            
	           
             
            <!-- <div> &nbsp; </div> -->
            
            <fieldset class="oneCol">
            <div class="searchControl">
				<c:choose>			    
					<c:when test="${userScreenName eq 'adminUserSearchForm'}">
		    	        <button class="textButton" type="submit" id="btnSearchForUsers">
		                    <fmt:message key="button.search" />
		                </button>
					</c:when>	
					<c:when test="${userScreenName eq 'adminUserUpdateForm' && admRole}">
					   <u:checkRole roleGroup="UPDATE_OWN_PROFILE"> 
			    	        <button class="textButton" type="submit" id="btnUpdateUser">
			                    <fmt:message key="button.modify" />
			                </button>
		                </u:checkRole>
					</c:when>	
					<c:when test="${userScreenName eq 'adminUserAdd'}">
		    	        <button class="textButton" type="submit"  id="btnAdd" name="add">
		                    <fmt:message key="button.add" />
		                </button> 
					</c:when>   
					<c:when test="${userScreenName eq 'adminUserUpdate'}">
		    	        <button class="textButton" type="submit" name="update" id="btnUpdate">
		                    <fmt:message key="button.update" />
		                </button> 
		    	        <button class="textButton" type="submit" name="resetPassword" id="btnResetPassword">
		                    <fmt:message key="button.reset.password" />
		                </button> 
		    	        <button class="textButton" type="submit" name="reset" id="btnClearAll">
		                    <fmt:message key="button.reset.all" />
		                </button>
		                 <button class="textButton" type="button"  id="btnCancel">
		                    <fmt:message key="button.cancel" />
		                </button>
		                <button type="submit" class="hideOnLoad" id="updateconfirmButton" name="updateconfirm" value="updateconfirm">
		                	<fmt:message key='button.ok'/> 
		                </button> 		                
					</c:when>   
				</c:choose> 
				<c:if test="${userScreenName eq 'adminUserUpdateForm'}">
	    	        <button class="textButton" type="button" id="btnClose" name="btnClose">
	                    <fmt:message key="button.close" />
	                </button>
				</c:if>
				<c:if test="${(userScreenName eq 'adminUserUpdateForm') && admRole && (empty user.lastLoginDate)}">
				     <u:checkRole roleGroup="UPDATE_OWN_PROFILE"> 
		              	 <button class="textButton" type="button" id="btnDeleteUser" name="btnDeleteUser" >
	                 		<fmt:message key="button.delete"/>
	             		</button>
             		</u:checkRole>
                </c:if>
				<c:if test="${userScreenName ne 'adminUserUpdateForm' && userScreenName ne 'adminUserUpdate'}">	
					<button class="textButton" type="submit" name="reset" id="reset">
	                    <fmt:message key="button.clear.all" />
	                </button>
				</c:if>
            </div>
            </fieldset>              
    
    </div>  <!-- /searchCriteria -->
  </div>  <!-- /searchFormDiv -->

		