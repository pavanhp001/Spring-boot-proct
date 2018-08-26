<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.AL.controller.AuthAMBController"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<title><fmt:message key="greeting.header"/></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/smooth-jquery-ui.css'/>" />
<script src="<c:url value='/js/jquery-1.10.2.js'/>"></script>
<script src="<c:url value='/js/jquery-ui-1.11.4.js'/>"></script>
<style>
table
{
border-collapse:collapse;
}
/* table, td, th
{
border:1px solid #d3d3d3;
width: 50%;
} */
#customerTable, #customerTable th, #customerTable td{
	border:1px solid #d3d3d3;
	width: 50%;
}
#customerTable td {
  padding: 2px;
  text-align: left;
  cursor: pointer;
}
.custLoaderImg{
height: 200px;
width: 200px; 
}
.unSelectedClass{
	background-color: #fff;
}
.selectedClass{
	background-color: #99CC33;
}
.custom-combobox {
    position: relative;
    display: inline-block;
  }
 .custom-combobox-toggle {
	background: #fff none repeat scroll 0 0;
    border: 0 solid;
    bottom: 0;
    height: 22px;
    margin-left: -41px;
    padding: 0;
    position: absolute;
    top: 4px;
}
.custom-combobox-input {
    background: #fff none repeat scroll 0 0;
    margin: 0;
    padding: 6px 10px;
    -webkit-padding-before: 8px;
  }
  .ui-widget-content{
  z-index : 9980 !important;
  
  }
  
/*   .pagecontainer{
	text-align:left;
	background-color:white;
	padding-left:60px;

} */
.pagecontainer {
  background-color: #e5e0d9;
  border: 1px solid #616264;
  display: table-cell;
  height: auto;
  text-align: center;
  vertical-align: middle;
  width: 997px;
}
.mduinfoformcontent {
  background-color: #ffffff;
  border: 1px solid #616264;
  border-radius: 10px;
  font-size: 12px;
  height: 229px;
  margin: 10px 0 10px 191px;
  padding: 14px 20px;
  width: 588px;
}

.statefield, .mduPropfield {
	font-size:13px;
	margin-bottom:15px;
}

.custom-combobox{
	height:32px;
}
.mduPropfield > span{
	display:block;
	-margin-bottom:5px;
	width:497px;
	text-align: left;
}

.statefield > span{
	display:block;
	margin-bottom:5px;
	text-align: left;
	width:300px;

}



.ui-button-icon-only .ui-icon{
  background-image:url("../images/select-dropdown.png");
  margin-left:14px;
}

.ui-icon{
	height:32px;
	width:37px;
}

.custom-combobox-toggle{
margin-left:-55px;
}

.mduPropfield .custom-combobox-input.mduPropfield.mduProText.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input{
	width:475px;
	border:1px solid;
}

.statefield .custom-combobox-input.statefield.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input{
	border: 1px solid;
	width:280px;
}
.ui-autocomplete{
	max-height: 150px;
    overflow-y: auto;  
    overflow-x: hidden;
 
}

.ui-icon-triangle-1-s{
background-position:0px -5px;
}

.ui-button-icon-only .ui-icon, .ui-button-text-icon-primary .ui-icon, .ui-button-text-icon-secondary .ui-icon, .ui-button-text-icons .ui-icon, .ui-button-icons-only .ui-icon{
	margin-top:-15px !important
}

.lableMdu{
	font-size: 16px;
}

.widget_container {
  background-color: #e5e0d9;
  float: left;
  height: 272px;
  width: 999px;
}

#stateList ~ span a{
	-webkit-margin-start: 249px;
}
</style>
<script src="<c:url value='/script/html_save.js'/>"></script>
<script src="<c:url value='/js/blockUI.js'/>"></script>
<script>
var isMDUSelect = true;
var currentRowId = null;
function selectRowIdByOnclickingCustomer(rowId){
	try{
		currentRowId = rowId;
	}catch(err){alert(err);}
}
window.onload = hideCustomerList;
var isRR = '${RapidResponsecustomer}';
	if(isRR=="yes"){
		<c:forEach var="cust" items="${customerVoList}">
			currentRowId = "${cust.id}";
		</c:forEach>
	}
function hideCustomerList(){
	if(isRR=="yes"){
		$(".greetingsForm").css("display","none");
		$("div#refreshBtn").css("display","none");
		$("div#customerTableDiv").css("display","none");
	}
}
function srollToNextRow(){
	try{
		var tableObject = document.getElementById("customerTable");
		if(document.getElementById(currentRowId)!=null){
			var currentIndex = document.getElementById(currentRowId).rowIndex;
			
			if(currentIndex+1!=tableObject.rows.length){
				
				for(var i=0;i<tableObject.rows.length;i++){
					
					if((tableObject.rows[i]).id==currentRowId){
						
						tableObject.rows[i].className = 'customerSelect unSelectedClass';
						
						i++;	
						
						currentRowId = (tableObject.rows[i]).id;
						tableObject.rows[i].className = 'customerSelect selectedClass';	
						$("#customerId").val(currentRowId);
					}
					else
						tableObject.rows[i].className = 'customerSelect unSelectedClass';	
				}
	
			}
		}
	}catch(err){alert(err);}
}


function srollToPreviousRow(){
	try{
		var tableObject = document.getElementById("customerTable");
		
		if(document.getElementById(currentRowId)!=null){
			var currentIndex = document.getElementById(currentRowId).rowIndex;
			
			if(currentIndex!=0){
				
				for(var i=0;i<tableObject.rows.length;i++){
					
					if((tableObject.rows[i]).id==currentRowId){
						
						currentRowId = (tableObject.rows[i-1]).id;
						tableObject.rows[i-1].className = 'customerSelect selectedClass';
						
						tableObject.rows[i].className = 'customerSelect unSelectedClass';	
						$("#customerId").val(currentRowId);
					}
					else
						tableObject.rows[i].className = 'customerSelect unSelectedClass';	
				}
			}
		}
	}catch(err){alert(err);}
}

$(document).ready(function(){
	
	try{
		var ctiMessage = '${ctiMessage}';
		if(ctiMessage != undefined && ctiMessage != ""){
			<%
			Logger logger = Logger.getLogger(AuthAMBController.class);
			try{
				if(request.getSession().getAttribute("ctiMessage") != null && request.getSession().getAttribute("ctiMessage") != "" && request.getSession().getAttribute("ctiMessage") != "{}"){
					String UCID_VALUE = null;
					JSONObject obj = (JSONObject)request.getSession().getAttribute("ctiMessage");
					if(request.getSession().getAttribute("ucid") != null && request.getSession().getAttribute("ucid") != ""){
						UCID_VALUE = (String)request.getSession().getAttribute("ucid");
						if(UCID_VALUE != null && UCID_VALUE !=""){
							if(UCID_VALUE.equalsIgnoreCase((String)obj.get("callId"))){
								logger.info("duplicate UCID ="+UCID_VALUE);
								request.getSession().setAttribute("isSameUCID",true);
							}else{
								request.getSession().setAttribute("ucid",(String)obj.get("callId"));
								request.getSession().setAttribute("isSameUCID",false);
							}
						}
					}else{
						request.getSession().setAttribute("ucid",(String)obj.get("callId"));
						request.getSession().setAttribute("isSameUCID",false);
					}
				}
			}catch(Exception e){
				logger.info("error occured in postFuseData ="+e.getMessage());
			}
			%>
		}
		var isSameUCID =<%=request.getSession().getAttribute("isSameUCID")%>;
		var enable_fuse_analytics = '${enable_fuse_analytics}';
		var fuseUrl = '${fuseUrl}';
		if(ctiMessage != undefined && ctiMessage != "" 
				&& enable_fuse_analytics == "true" 
				&& fuseUrl != undefined && fuseUrl != ""){
			
			if(ctiMessage != undefined && ctiMessage != '' ){
				//console.log("UCID:: "+JSON.parse(ctiMessage).callId)
				console.log("isSameUCID:: "+isSameUCID);
				if(isSameUCID != null && !isSameUCID){
					postFuseData(ctiMessage, fuseUrl);
				}
			}
		}
	}catch(err){
		console.log(err);
	 }
	$("#forward").hide();
	 var states = JSON.parse('<c:out value="${states}" escapeXml="false"/>');
	 var stateList = states.items.item;
     $("tr.customerSelect").click(sendCustomerId);
	 $("input#refresh").click(refresh);
	 $("input#forward").click(goTobasicInfo);
	 $('body').keypress(function(event) {
		 try{
			if(event.keyCode==40)
				srollToNextRow();
			else if(event.keyCode==38)
				srollToPreviousRow();
		 }catch(err){alert(err);}
	});
	 $("select#stateList").append($("<option></option>").attr("value", "").text(""));
		$(stateList).each(function(){
			var txt = this.description;
			var val = this.lookupKey;
		    $("select#stateList").append($("<option></option>").attr("value", val).text(txt));
		});
		
	//To save the html on page load
	 	savePageHtml(true, ""); 
	 		 
});
function postFuseData(trackingJSON, fuseUrl){
	try{
	    $.ajax({
	        type: 'POST',
	        url: fuseUrl,
	        contentType: 'application/json',
	        data: trackingJSON,
	        success: function(data, textStatus, request){
	        	try{
		        	console.log('postFuseData created successfully'+JSON.stringify(data));
		        	var path = config.contextPath;
		    		var url = path + "/salescenter/fuseAnalyticsResponse";
		    		var fuseResponse = {};
		    		fuseResponse['data'] = JSON.stringify(data);
		    		fuseResponse['responseHeader'] = JSON.stringify(request.getAllResponseHeaders());
					$.get("https://ipinfo.io", function(response) {
					      console.log("IP address::::"+response.ip);
					      fuseResponse['clientIpAddress'] = response.ip;
					      fuseAnalaticsResponse(url,fuseResponse)
					  }, "jsonp");
		    		
	        	}catch(err){
	        		console.log(err);
	        		try{
	                	var path = config.contextPath;
	                	var url = path + "/salescenter/fuseAnalyticsResponse";
	                	var fuseResponse = {};
	            		fuseResponse['scriptError'] = "error";
	        			fuseAnalaticsResponse(url,fuseResponse)
	                	}catch(e){
	                		console.log("error:::::::::::::::: "+e)
	                	}
	        	}
	        },
	        error: function(data, textStatus, request){
	        	try{
	        	var path = config.contextPath;
	        	var url = path + "/salescenter/fuseAnalyticsResponse";
	        	var fuseResponse = {};
	    		fuseResponse['data'] = JSON.stringify(data);
	        	console.log('postFuseData error: ' + JSON.stringify(data));
				$.get("https://ipinfo.io", function(response) {
				      console.log("IP address::::"+response.ip);
				      fuseResponse['clientIpAddress'] = response.ip;
				      fuseAnalaticsResponse(url,fuseResponse)
				  }, "jsonp");
	        	}catch(e){
	        		console.log("error:::::::::::::::: "+e)
	        		try{
	                	var path = config.contextPath;
	                	var url = path + "/salescenter/fuseAnalyticsResponse";
	                	var fuseResponse = {};
	            		fuseResponse['scriptError'] = "error";
	        			fuseAnalaticsResponse(url,fuseResponse)
	                	}catch(e){
	                		console.log("error:::::::::::::::: "+e)
	                	}
	        	}
	        }
	    });
	}catch(err){
		console.log(err);
		try{
        	var path = config.contextPath;
        	var url = path + "/salescenter/fuseAnalyticsResponse";
        	var fuseResponse = {};
    		fuseResponse['scriptError'] = "error";
			fuseAnalaticsResponse(url,fuseResponse)
        	}catch(e){
        		console.log("error:::::::::::::::: "+e)
        	}
	}
}

function fuseAnalaticsResponse(urlAjax,fuseResponse){
	try{
	 $.ajax({
	        type: 'POST',
	        url: urlAjax,
	        dataType: "json",
	        data: fuseResponse,
	    });
	}catch(err){
		console.log("ERROR IN fuseAnalaticsResponse="+err);
	}
}
//State List
(function( $ ) {
    $.widget( "custom.combobox", {
      _create: function() {
        this.wrapper = $( "<span>" )
          .addClass( "custom-combobox" )
          .insertAfter( this.element );
        this.element.hide();
        this._createAutocomplete();
        this._createShowAllButton();
      },
      _createAutocomplete: function() {
        var selected = this.element.children( ":selected" ),
          value = selected.val() ? selected.text() : "";
         //console.log(" selected :: "+value);
        this.input = $( "<input>" )
          .appendTo( this.wrapper )
          .val( value )
          .attr( "title", "" )
          .addClass( "custom-combobox-input statefield stateFieldTxt ui-widget-content ui-state-default ui-corner-left" )
          .autocomplete({
            delay: 0,
            minLength: 0,
            source: $.proxy( this, "_source" )
          })
          .tooltip({
            tooltipClass: "ui-state-highlight"
          });
        this._on( this.input, {
          autocompleteselect: function( event, ui ) {
            ui.item.option.selected = true;
            this._trigger( "select", event, {
              item: ui.item.option
            });
            $('.mduProText').val('');
           getMDUProperties($("#stateList").val());
          },
          autocompletechange: "_removeIfInvalid"
        });
        $(".custom-combobox").focusout(function(){
        	$("#ui-id-1").hide();
        });
      },
      _createShowAllButton: function() {
        var input = this.input,
          wasOpen = false;
        $( "<a>" )
          .attr( "tabIndex", -1 )
          .attr( "title", "Show All States" )
          .tooltip()
          .appendTo( this.wrapper )
          .button({
            icons: {
              primary: "ui-icon-triangle-1-s"
            },
            text: false
          })
          .removeClass( "ui-corner-all" )
          .addClass( "custom-combobox-toggle ui-corner-right" )
          .mousedown(function() {
        	  //isMDUSelect = true;
            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
          }).click(function() {
        	  input.focus();
            // Close if already visible
            if ( wasOpen ) {
              return;
            }
            // Pass empty string as value to search for, displaying all results
            input.autocomplete( "search", "" );
          });
      },
      _source: function( request, response ) {
        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
        response( this.element.children( "option" ).map(function() {
          var text = $( this ).text().trim();
          if ( this.text.trim() && ( !request.term || matcher.test(text.trim()) ) )
            return {
              label: text,
              value: text,
              option: this
            };
        }) );
      },
      _removeIfInvalid: function( event, ui ) {
        // Selected an item, nothing to do
        if ( ui.item ) {
          return;
        }
        // Search for a match (case-insensitive)
        var value = this.input.val(),
          valueLowerCase = value.toLowerCase(),
          valid = false;
        this.element.children( "option" ).each(function() {
          if ( $( this ).text().trim().toLowerCase() === valueLowerCase.trim() ) {
            this.selected = valid = true;
            return false;
          }
        });
        // Found a match, nothing to do
        if ( valid ) {
        	$("#forward").hide();
            $('.mduProText').val('');
        	 getMDUProperties($("#stateList").val());
          return;
        }
        // Remove invalid value
        this.input
          .val( "" )
          .attr( "title", value + " didn't match any state" )
          .tooltip( "open" );
        this.element.val( "" );
        this._delay(function() {
          this.input.tooltip( "close" ).attr( "title", "" );
        }, 2500 );
        this.input.autocomplete( "instance" ).term = "";
      },
      _destroy: function() {
        this.wrapper.remove();
        this.element.show();
      }
    });
  })( jQuery );


//mdu Properties
(function( $ ) {
$.widget( "custom.combobox1", {
    _create: function() {
      this.wrapper = $( "<span>" )
        .addClass( "custom-combobox" )
        .insertAfter( this.element );
      this.element.hide();
      this._createAutocomplete();
      this._createShowAllButton();
    },
    _createAutocomplete: function() {
      var selected = this.element.children( ":selected" ),
        value = selected.val() ? selected.text() : "";
       //console.log(" selected :: "+value);
      this.input = $( "<input>" )
        .appendTo( this.wrapper )
        .val( value )
        .attr( "title", "" )
        .addClass( "custom-combobox-input mduPropfield mduProText ui-widget-content ui-state-default ui-corner-left" )
        .autocomplete({
          delay: 0,
          minLength: 0,
          source: $.proxy( this, "_source" )
        })
        .tooltip({
          tooltipClass: "ui-state-highlight"
        });
      this._on( this.input, {
        autocompleteselect: function( event, ui ) {
          ui.item.option.selected = true;
          this._trigger( "select", event, {
            item: ui.item.option
          });
          if($("#mduProList") != undefined && $("#mduProList").val() != ''){
        	  $("#forward").show();
          }else{
        	  $("#forward").hide();
          }
        // getMDUProperties($("#stateList").val());
        },
        autocompletechange: "_removeIfInvalid"
      });
      $(".custom-combobox").focusout(function(){
          if($("#mduProList") != undefined && $('.mduProText').val() == ''){
        	  $("#forward").hide();
           }
        	$("#ui-id-2").hide();
        });
    },
    _createShowAllButton: function() {
      var input = this.input,
        wasOpen = false;
      $( "<a>" )
        .attr( "tabIndex", -1 )
        .attr( "title", "Show All Properties" )
        .tooltip()
        .appendTo( this.wrapper )
        .button({
          icons: {
            primary: "ui-icon-triangle-1-s"
          },
          text: false
        })
        .removeClass( "ui-corner-all" )
        .addClass( "custom-combobox-toggle ui-corner-right" )
        .mousedown(function() {
      	  //isMDUSelect = true;
          wasOpen = input.autocomplete( "widget" ).is( ":visible" );
        }).click(function() {
           input.focus();
          // Close if already visible
          if ( wasOpen ) {
        	  if($("#mduProList") != undefined && $("#mduProList").val() != '' && $(".mduProText ") != undefined && $(".mduProText ").val() != ''){
            	  $("#forward").show();
        	  }
            return;
          }
          // Pass empty string as value to search for, displaying all results
          input.autocomplete( "search", "" );
        });
    },
    _source: function( request, response ) {
      var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
      response( this.element.children( "option" ).map(function() {
        var text = $( this ).text().trim();
        if ( this.text.trim() && ( !request.term || matcher.test(text.trim()) ) )
          return {
            label: text,
            value: text,
            option: this
          };
      }) );
    },
    _removeIfInvalid: function( event, ui ) {
      // Selected an item, nothing to do
      if ( ui.item ) {
        return;
      }
      // Search for a match (case-insensitive)
      var value = this.input.val(),
        valueLowerCase = value.toLowerCase(),
        valid = false;
      this.element.children( "option" ).each(function() {
        if ( $( this ).text().trim().toLowerCase() === valueLowerCase.trim() ) {
          this.selected = valid = true;
          return false;
        }
      });
      // Found a match, nothing to do
      if ( valid ) {
      	 //getMDUProperties($("#stateList").val());
      	
        return;
      }
      // Remove invalid value
      this.input.val( "" ).attr( "title", value + " didn't match any Properties" ).tooltip( "open" );
      this.element.val( "" );
      $("#forward").hide();
      this._delay(function(){
        this.input.tooltip( "close" ).attr( "title", "" );
      }, 2500 );
      this.input.autocomplete( "instance" ).term = "";
    },
    _destroy: function() {
      this.wrapper.remove();
      this.element.show();
    }
  });
})( jQuery );



 
  $(function() {
    $( "#stateList" ).combobox();
    $( "#mduProList" ).combobox1();
    $( "#toggle" ).click(function() {
      $( "#ui-id-1" ).toggle();
    });
  });
  
function goTobasicInfo(){
	if(isRR=="yes"){
		$("input#customerId").val(currentRowId);
		$('.stateFieldTxt').attr("value",$('.stateFieldTxt').val());
		$('.mduProText').attr("value",$('.mduProText').val());
		//To save the html on page submit
		savePageHtml(false, "");
		$("#mduProJSON").val($("#mduProList").val());
		$("form#greeting").submit();
	}else{
	$('div.errorMsg').remove();
	var custId = $("input#customerId").val();
	if(custId != ''){
		//To save the html on page submit
		savePageHtml(false, "");
		var path =$("input#contextPath").val();
		var referalId =$("input#referalId").val();
		var url = path+"/salescenter/validate/customer";
		var data = {};
		$('div#customerTableDiv').css("display","none");
		$('div#custLoader').css("display","block");
		data["referalId"]=referalId;
		data["custId"]=custId;
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			complete: onValidateComplete
		});
	}else{
		$('.stateFieldTxt').attr("value",$('.stateFieldTxt').val());
		$('.mduProText').attr("value",$('.mduProText').val());
		$("#mduProJSON").val($("#mduProList").val());
		//To save the html on page submit
		savePageHtml(false, "");
	$("form#greeting").submit();
	}
	}
}

var onValidateComplete = function(data){
	if(data=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else if(data.responseText=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		 var json = JSON.parse(data.responseText);
		 if(json.stat){
			 $("form#greeting").submit();
		 }else{
			 $("#customerTable  > tbody > tr").remove();
			 var json1 = json.result;
			 $(json1).each(function(i){
				 //console.log(json[i]);
				 var obj = JSON.parse(json1[i]);
				 var tr = $("<tr>").attr({"id":obj.id, "class":"customerSelect",style:"margin-bottom: 5px;line-height:20px;","onclick":"selectRowIdByOnclickingCustomer(this.id)"});
				 tr.click(sendCustomerId);
				 var td1 = $("<td>").html(obj.fisrtName+"&nbsp;"+obj.lastName);
				 var td2 = $("<td>").html(obj.city+"&nbsp;"+obj.state);
				 tr.append(td1);
				 tr.append(td2);
				 $("#customerTable > tbody").append(tr);
			 }); 
			 var contentBlock = $('<div class="errorMsg"></div>').css({"color":"#F00","font-weight":"bold"});
			 $("#customerId").val('');
			 contentBlock.html(json.errorMsg);
			 $('div#custLoader').css("display","none");
			 $('div#customerTableDiv').css("display","block"); 
			 $('div.greetingsForm').append(contentBlock);
		 }
	}
}
var sendCustomerId = function () {
    var id = this.id;
    currentRowId = id;
    $("#customerId").val(id);
    $('tr.customerSelect').each(function(){
        if(id == this.id){
            if($(this).attr("class") == 'customerSelect selectedClass'){
            	$("#customerId").val('');
            	$(this).attr("class","customerSelect unSelectedClass");
            }else{
            	$(this).attr("class","customerSelect selectedClass")
            }
        }else{
        	$(this).attr("class","customerSelect unSelectedClass");
        }
    });
    var newId = $("#customerId").val();
}

var refresh = function(){
	customerId = null;
	$('div.errorMsg').remove();
	$("#customerId").val("");
	var path =$("input#contextPath").val();
	var referalId =$("input#referalId").val();
	var url = path+"/salescenter/refresh";
	var data = {};
	$('div#customerTableDiv').css("display","none");
	$('div#custLoader').css("display","block");
	data["referalId"]=referalId;
	$.ajax({
		type: 'POST',
		url: url,
		data: data,
		complete: onRemoveComplete
	});
}


var onRemoveComplete = function(data){
	if(data == "sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else if(data.responseText=="sessionTimeOut"){
		var path = "<%=request.getContextPath()%>";
		window.location.href = path+"/salescenter/session_timeout";
	}else{
		 $("#customerTable  > tbody > tr").remove();
		 var json = JSON.parse(data.responseText);
		 $(json).each(function(i){
			 //console.log(json[i]);
			 var obj = JSON.parse(json[i]);
			 var tr = $("<tr>").attr({"id":obj.id, "class":"customerSelect",style:"margin-bottom: 5px;line-height:20px;","onclick":"selectRowIdByOnclickingCustomer(this.id)"});
			 tr.click(sendCustomerId);
			 var td1 = $("<td>").html(obj.fisrtName+"&nbsp;"+obj.lastName);
			 var td2 = $("<td>").html(obj.city+"&nbsp;"+obj.state);
			 tr.append(td1);
			 tr.append(td2);
			 $("#customerTable > tbody").append(tr);
		 }); 
		 $('div#custLoader').css("display","none");
		 $('div#customerTableDiv').css("display","block");
	}
}

	function getMDUProperties(state){
		console.log("**state :: "+state);
		$("select#mduProList").html("");
		var path = $("#contextPath").val();
		var url = path+"/salescenter/getMDUProperties";
		var data = {};
		data["state"] = state;
		data['mduProgramExternalId'] = $("#mduProgramExternalId").val();
		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			complete: bulidMDUProperties
		});

}
	var bulidMDUProperties = function(data){
		if(data == "sessionTimeOut"){
			var path = "<%=request.getContextPath()%>";
			window.location.href = path+"/salescenter/session_timeout";
		}else if(data.responseText=="sessionTimeOut"){
			var path = "<%=request.getContextPath()%>";
			window.location.href = path+"/salescenter/session_timeout";
		}else{
			if(data.responseText != ""){
				//$("#forward").show();
				$.each(JSON.parse(data.responseText),function(index,obj){
					$("select#mduProList").append($("<option></option>").attr("value", JSON.stringify(obj)).text(obj.name));
				});
			}else{
				$("#forward").hide();
				$("select#mduProList").append($("<option></option>").attr("value", "").text("Properties Not Found"));
		    }
		}
	}
	
$(window).on('beforeunload', function(){
	$.blockUI({ message: $('#domMessage') }); 
}); 
</script>
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
								<span class="cell pageTitle"><fmt:message key="greeting.header"/></span>
								<span class="callTime" id="startTimeText"></span>
							</div>
						</header>
						<section id="content">
							<form id="greeting" name="greeting" action="${flowExecutionUrl}" method="post">
							<div class="contentwrapper" >
								<section id="dialogue_wrapper">						   
									<section id="dialogue_content">
										${dialogue}
									</section>
									<div id="dialogue_content_balloon"></div>						
								</section>
								<div id="action_wrapper">
									<div class="widget_container" style="background-color: #00000">
										<div class="pagecontainer" style="background-color: #00000" align="center">
											<div class="mduinfoformcontent">
											<!-- Form Content -->
												<input type="hidden" id="customerId" value="" name="customerId" />
												<input type="hidden" id="customerIdVal" value="<%=request.getSession().getAttribute("customerID") %>" name="customerIdVal" />
												<input type="hidden" id="orderId" value="<%=request.getSession().getAttribute("orderId") %>" name="orderId" />
												<input type="hidden" id="ucid" value="<%=request.getSession().getAttribute("UCID") %>" name="ucid" />
												<input type="hidden" id="referalId" value="${referalId}">
												<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
												<input type="hidden" id="pageTitle" name="pageTitle" value="Greetings">
												<input type="hidden" id="agentId" name="agentId" value="${salescontext.scMap['agent.id']}">
												<input type="hidden" id="providerIdVal" name="providerIdVal" value="${salescontext.scMap['referrer.businessParty.referrerId']}">
												<input type="hidden" id="_eventId" name="_eventId" value="greeting"/>
				                                <input type="hidden" id="mduProJSON" name="mduProJSON" value=""/>
				                               <input type="hidden" id="callStartTimeInGreeting" name="callStartTimeInGreeting" value=""/>
				                                <input type="hidden" id="mduProgramExternalId" name ="mduProgramExternalId" value="${mduProgramExternalId}"/>
 												<div class="statefield">
												<span class="lableMdu">Select State:</span>
												<!-- <input type="button" id="loadMDU" name="" value="loadMDU >"/> -->
														<select id="stateList" name="state">
														</select>
												</div>
												<div class="mduPropfield">
												<span class="lableMdu">Select Property:</span>
												<select id="mduProList" name="mduPro">
												</select>
												</div>
											<!-- Form Content -->
											</div>
										</div>
									</div>
								</div>
								<div class="bottombuttons">
									<div class="rightbtns">
										<input type="button" id="forward" name="" value="Forward >"/>
									</div>
								</div>
							</div>
							</form>
						</section>
						<div id="domMessage" style="display:none;"> 
							<img src="<%=request.getContextPath()%>/images/loading.gif" border="0"/>
    						<h2>Loading</h2> 
						</div> 						
					</section><!-- Content Full Cont -->
