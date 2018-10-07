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
$HeadURL: http://casource:7777/svn/admin/adminUI/trunk/src/main/webapp/WEB-INF/jsp/admin/UserSearch.jsp $
********************************************************************************************************
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="/bcs/scripts/Search.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	    disableAirTextFields();
	    var deleteButton = document.getElementById('btnDeleteUser');
	    if(deleteButton){
	    	deleteButton.disabled=true;
	    }
   		var viewButton = document.getElementById('btnViewUser');
   		if(viewButton){
   			viewButton.disabled=true;
   		}
	    $('#btnClearAll').click(function() {
	    	clearAll();
	    });
 		$('#btnSearchForUsers').click(function() {
	   		search();
	    });

	   	$('input[type="text"]').filter('[name^=air]').blur(function() {
	    	this.value = this.value.toUpperCase();
	    });
	    $('#location').blur(function() {
	    	this.value = this.value.toUpperCase();
	    });
	    $('#btnViewUser').click(function() {
	    	showDetailForRowFromBtn();
	    });

		var deleteButton = document.getElementById('btnDeleteUser');
	    if(deleteButton){
		     $('#btnDeleteUser').click(function() {
		    	showDeletePopinForRowFromBtn();
		    });
		}
		
	    $('tr.results').click(function() {
	   		var highlight = $(this).attr('class');
	   		var id = this.id;
	   		document.getElementById('btnViewUser').disabled=false;
	   		var deleteButton = document.getElementById('btnDeleteUser');
		    if(deleteButton){
		    	deleteButton.disabled=false;
		    }
	   		document.getElementById('selectedUsername').value=id;
	   		if (highlight=='results2'){
	   			showDetailForRow(id)
	   			return;
	   		}
	   		$('tr.results2').attr('class','results');
   			$(this).attr('class','results rowselected');
	    });
	    $('tr.results').dblclick(function() {

	   		var id = this.id;
	   		showDetailForRow(id)
	    });
 $('#btnAddUser').click(function() {
	    	document.adminUserAddForm.submit();
	    });
	    focusForm();
	});
	
	function changeSortOrder(newOrder) {
		$('#sortBy').attr('value', newOrder);
	    $('#sortButton').click();
	}

   function focusForm() {
	     $('#username').focus();
	  }
  	function focusCancel() {
     	$('#btnCancel').focus();
  	}
//##################
//###  buttons
//##################

	function orderColumn(form, sortColumn, direction){

		reOrderList(form, sortColumn, direction);
	}

//######################
//###  jump to next page
//######################
	function deleteUser(){
		var selectedRow = getHighlightedTableRow('userList');
		document.getElementById('selectedUsername').value=selectedRow.id;
		document.adminUserSearchForm.action.value='delete';
		closeDialogBySelector('#deleteUserPopin');
		$('#btnDeleteUserConfirm').click();
	}
	function closeDialogBySelector(selector) {
	    closeDialog($(selector));
	}
	function showDetailForRow(id){
		document.getElementById('userProfileUsername').value=id;
	   	document.getElementById('selectedUsername').value=id;
	   	document.adminUserProfileForm.submit();
	}

	function showDetailForRowFromBtn(){
		var selectedRow = getHighlightedTableRow('userList');
		showDetailForRow(selectedRow.id);
	}

	function search(){
		var deleteButton = document.getElementById('btnDeleteUser');
	    if(deleteButton){
	    	deleteButton.disabled=true;
	    }
   		var viewButton = document.getElementById('btnViewUser');
   		if(viewButton){
   			viewButton.disabled=true;
   		}
		document.adminUserSearchForm.totalRecordsRetrieved.value=0;
		document.getElementById('userProfileUsername').value='';
		document.getElementById('selectedUsername').value=''
		document.getElementById('action').value='view'
		getSelectedRoles();
		doSearch(document.adminUserSearchForm, 'username', 'ASC');
		return false;
	}

	function showDeletePopinForRowFromBtn(){
            $('#deleteUserPopin').dialog({modal: true, resizeable: false});
		$('#btnCancelfocus').focus();
		return false;
	}

	function highlightFirstRow2Btn(tableName, buttonName1, buttonName2)
	{
	    if (getHighlightedTableRow(tableName) == null){
	      var table = document.getElementById(tableName);
	      if (table != null && table.rows != null) {
	        unhighlightRow(tableName);
	        highlightButton(tableName, table.rows[0], buttonName1);
	        highlightButton(tableName, table.rows[0], buttonName2);
	      }
	    }
	}

	/*
	    Move 'f'orwards or 'b'ackwards from current row
	    based on keyPress in event.
	*/
	function highlightNextRow2Btn(evt, tableName, buttonName1, buttonName2)
	{
	    var nextRow = getNextRow(evt, tableName, buttonName1);
	    if (nextRow != null){
	        unhighlightRow(tableName);
	        highlightButton(tableName, nextRow, buttonName1);
	        highlightButton(tableName, nextRow, buttonName2);
	    }
	}
</script>
<c:set var="userScreenName" value="adminUserSearchForm" scope="request"/>
<c:set var="tableCommand" value="${adminSearchTableCommand}" scope="request"/>

<div id="mainContent"> 
		<form:form commandName="command" id="adminUserSearchForm" name="adminUserSearchForm" action="adminUserSearch.form" method="POST">	
		<input type="hidden" id="selectedUsername" name="selectedUsername" value=""/>
		<input type="hidden" id="action" name="action" value="view"/>	
		<input  type="hidden" id="pageChanged" name="pageChanged" value="false"/>
		<input  type="hidden" id="isShowingAirport" name="isShowingAirport" value="true"/>      
	    
	        <jsp:include page="/WEB-INF/jsp/fragments/searchElements.jsp"/>
	        <jsp:include page="/WEB-INF/jsp/fragment/pageErrors.jsp"/>
	        <jsp:include page="/WEB-INF/jsp/admin/jsp-fragments/UserFields.jsp" />	        
			<button class="hideOnLoad" id="btnDeleteUserConfirm" name="deleteuser" type="submit">
				<fmt:message key="button.delete"/>
			</button>
	        <div id="deleteUserPopin" title="<fmt:message key='delete.user'/>", class="hideOnLoad">
				<%-- problems with fmt tags if jsp:include used--%>
				<%@ include file="/WEB-INF/jsp/admin/jsp-popin/DeleteUserPopin.jsp" %>
			</div>
		</form:form>
</div>

    <c:choose>
		
	    <c:when test="${command.records != null && adminSearchTableCommand.totalResults > 0}">
	        <jsp:include page="/WEB-INF/jsp/admin/usersearch/jsp-fragment/userSearchResult.jsp" />
	    </c:when>
		<c:otherwise>
	        <jsp:include page="/WEB-INF/jsp/fragments/noResults.jsp"/>
	    </c:otherwise>
    </c:choose>
	<form id="adminUserProfileForm" name="adminUserProfileForm" action="adminUserProfile.cform" method="POST">
		<input type="hidden" id="userProfileUsername" name="user.username" value=""/>
	</form>
	
	<form id="adminUserAddForm" name="adminUserAddForm" action="adminUserAdd.form">
	
	</form>