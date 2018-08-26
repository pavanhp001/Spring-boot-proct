<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.AL.ui.domain.CustomerTracker" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.AL.ui.domain.CustomerTrackerConversion" %>
<%@ page import="com.AL.productResults.managers.ProductResultsManager"%>
<%@ page import="com.AL.ui.vo.RoadMapCriteriaVO"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 <%     
	 Gson gson = new Gson();
	  String agentTrackerJson = "";
	  String custmerTrackerConversion ="";
	  String trackerUdateTime = "";
	   String roadMapCriteriaVOJson = "";
	   boolean pollingCompleted = false;
	  List<RoadMapCriteriaVO> roadMapCriteriaVOList = new ArrayList<RoadMapCriteriaVO>() ;
	   if(session.getAttribute("productResultManager") != null ){
		   ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
		   pollingCompleted = productResultManager.isRoadmapCompleted();
		   if(pollingCompleted && productResultManager != null && productResultManager.getRoadMapPowerPitchList() != null && !productResultManager.getRoadMapPowerPitchList().isEmpty()){
			   roadMapCriteriaVOList =(List<RoadMapCriteriaVO>)productResultManager.getRoadMapPowerPitchList();
			    roadMapCriteriaVOJson = gson.toJson(roadMapCriteriaVOList);
		   }
	     } 
	 List<CustomerTracker> agentTrackerDataList =  (List<CustomerTracker>) request.getSession().getAttribute("tsLineItemTrackerDataList");
	 CustomerTrackerConversion custmerTrackerConversionObjcet = (CustomerTrackerConversion) request.getSession().getAttribute("customerTrackerJson");
	 if(custmerTrackerConversionObjcet != null){
		 custmerTrackerConversion = gson.toJson(custmerTrackerConversionObjcet);
	 }
	 if(agentTrackerDataList != null){
	   agentTrackerJson  = gson.toJson(agentTrackerDataList);
     }
	 if(request.getSession().getAttribute("trackerUpdateTime") != null){
	   trackerUdateTime = (String) request.getSession().getAttribute("trackerUpdateTime");
	 }
%>
<c:set var="agentTrackerJson" value="<%=agentTrackerJson%>" />
<c:set var="trackerUdateTime" value="<%=trackerUdateTime%>" />
<c:set var="custmerTrackerConversion" value="<%=custmerTrackerConversion%>" />
 <c:set var="roadMapCriteriaVOJson" value="<%=roadMapCriteriaVOJson %>"/> 
  <c:set var="pollingCompleted" value="<%=pollingCompleted %>"/> 
<html>
   <head>

      <script type="text/javascript">
      var intervalPeriod = window.setInterval(getRoadMapData, 5000);
      console.log("Jsp Layout")
      var agentTrackerJson = '${empty agentTrackerJson ? "" : agentTrackerJson}';
      var trackerUdateTime = '${empty trackerUdateTime ? "" : trackerUdateTime}';
     var roadMapCriteriaVOJson = '${empty roadMapCriteriaVOJson ? "" : roadMapCriteriaVOJson}'; 
     var pollingCompleted = '${pollingCompleted}';
      var custmerTrackerConversionJson = '${empty custmerTrackerConversion ? "" : custmerTrackerConversion}';
		$(document).ready(function(){
			
				var trackerflag = false;
				var financeflag = false;
				var rankflag = false;
	           	var callCenterData={};
	           	var enterpriseData={};
	           	var regionData={}; 
	           	var activeuserdata={};
	          	           
	           	
	           	var pageTitle = $('input[name="pageTitle"]').val();
	           	var displayRoadmap =<%=request.getSession().getAttribute("displayRoadmap")%>
				if(pollingCompleted =="true" && (pageTitle != undefined && (pageTitle !="IdlePage" && pageTitle !="Dispositions")) && displayRoadmap != null && displayRoadmap == true){
					window.clearInterval(intervalPeriod);
					var isdone = true;
					var preferedwithoutPhonePoints = 0;
					var preferedwithPhonePoints = 0;
					var roadMapCriteriaVOJSONCount = 0;
					if(roadMapCriteriaVOJson != null && roadMapCriteriaVOJson !='' ){
						$("#roadMapData").empty();
						
						var jsonData = JSON.parse(roadMapCriteriaVOJson.toString());
						$(jsonData).each(function(index,val){

							roadMapCriteriaVOJSONCount++;
							var td1 ="<td></td>";
							var td2 ="<td></td>";
							var td3 ="<td></td>";
							var td4 ="<td></td>";
							var td5 ="<td> </td>";
							if(index == 0){
								preferedwithoutPhonePoints = val.withoutPhonePoints;
								preferedwithPhonePoints = val.withPhonePoints;
								if(val.withoutPhonePoints <= 0){
									val.withoutPhonePoints = "";
								}
								if(val.withPhonePoints <= 0){
									val.withPhonePoints = "";
								}
								var points_data1 = "<div class='withOutPoint'>"+val.withoutPhonePoints+"</div>";
								var points_data2 = "<div class='withPoint'>"+val.withPhonePoints+"</div>";
								td1 ="<td>"+points_data1+points_data2+"</td>";
								td4 = "<td>Preferred Pitch</td>"
							}else{
								td4 = "<td> Down Sell #"+index+"</td>"
								var points_data1 = "<div class='withOutPoint'></div>";
								var points_data2 = "<div class='withPoint'></div>";
								td1 ="<td>"+points_data1+points_data2+"</td>";
								if(preferedwithoutPhonePoints == val.withoutPhonePoints){
									if(val.withoutPhonePoints <= 0){
										val.withoutPhonePoints = "";
									}
									var points_data1 = "<div class='withOutPoint'>"+val.withoutPhonePoints+"</div>";
									var points_data2 = "<div class='withPoint'></div>";
									if(preferedwithPhonePoints == val.withPhonePoints){
										if(val.withoutPhonePoints <= 0){
											val.withPhonePoints = "";
										}
									    points_data2 = "<div class='withPoint'>"+val.withPhonePoints+"</div>";
									}else{
										if(preferedwithPhonePoints < val.withPhonePoints){
											var points = val.withPhonePoints - preferedwithPhonePoints;
											points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" more!)"+"</div>";
										}else if(preferedwithPhonePoints > val.withPhonePoints){
											var points = preferedwithPhonePoints - val.withPhonePoints;
											 points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" less!)"+"</div>";
										}
									}
									//td1 ="<td>"+points_data1+points_data2+"</td>";
									td1 ="<td>"+points_data1+points_data2+"</td>";
								}else{
									var points_data1 = "<div class='withOutPoint'></div>";
									var points_data2 = "<div class='withPoint'></div>";
									td1 ="<td>"+points_data1+points_data2+"</td>";
									if(preferedwithoutPhonePoints < val.withoutPhonePoints){
										var points = val.withoutPhonePoints - preferedwithoutPhonePoints;
										 points_data1 = "<div class='withOutPoint'>"+val.withoutPhonePoints+" ("+points+" more!)"+"</div>";
										 points_data2 = "<div class='withPoint'></div>";
										if(preferedwithPhonePoints == val.withPhonePoints){
											if(val.withPhonePoints <= 0){
												val.withPhonePoints = "";
											}
											  points_data2 = "<div class='withPoint'>"+val.withPhonePoints+"</div>";
										}else{
											if(preferedwithPhonePoints < val.withPhonePoints){
												var points = val.withPhonePoints - preferedwithPhonePoints;
												points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" more!)"+"</div>";
											}else if(preferedwithPhonePoints > val.withPhonePoints){
												var points = preferedwithPhonePoints - val.withPhonePoints;
												 points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" less!)"+"</div>";
											}
										}
										//td1 ="<td>"+val.points+" ("+points+" more!)"+"</td>"
										td1 ="<td>"+points_data1+points_data2+"</td>";
									}else if(preferedwithoutPhonePoints > val.withoutPhonePoints){
										var points = preferedwithoutPhonePoints - val.withoutPhonePoints;
										 points_data1 = "<div class='withOutPoint'>"+val.withoutPhonePoints+" ("+points+" less!)"+"</div>";
										 points_data2 = "<div class='withPoint'></div>";
										 if(preferedwithPhonePoints == val.withPhonePoints){
												if(val.withPhonePoints <= 0){
													val.withPhonePoints = "";
												}
												  points_data2 = "<div class='withPoint'>"+val.withPhonePoints+"</div>";
										}else{
											if(preferedwithPhonePoints > val.withPhonePoints){
												var points = preferedwithPhonePoints - val.withPhonePoints;
												points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" less!)"+"</div>";
											}else if(preferedwithPhonePoints < val.withPhonePoints){
												var points = val.withPhonePoints - preferedwithPhonePoints;
												 points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" more!)"+"</div>";
											}
										}
										td1 ="<td>"+points_data1+points_data2+"</td>";
									}
								}
								
							}
							if(val.internetProviderName != undefined){
								if(val.connectionSpeedVal != undefined && val.connectionSpeedVal <=0){
									var connectionSpeed = "";
									td2 ="<td><div class='internet-col-first'>"+val.internetProviderName+"</div><div class='internet-col-sec' style='width: 30px; padding: 8px;'>"+connectionSpeed+"</div></td>"
								}else{
									td2 ="<td><div class='internet-col-first'>"+val.internetProviderName+"</div><div class='internet-col-sec'>"+val.connectionSpeedVal+" MB</div></td>"
								}
							}else{
								td2 ="<td></td>"
							}
							if(val.videoProviderName != undefined){
								td3 ="<td>"+val.videoProviderName+"</td>"
							}else{
								td3 ="<td></td>"
							}
							
							$("#roadMapData").append("<tr>"+td4+td2+td3+td1+"</tr>");
						
							$("#ux_roadMap").show();
						});
						 if(roadMapCriteriaVOJSONCount == 1){
								var td1 ="<td></td>";
								var td2 ="<td></td>";
								var td3 ="<td></td>";
								var td4 ="<td></td>";
								td1 = "<td> Down Sell #1</td>";
								var points_data1 = "<div class='withOutPoint'></div>";
								var points_data2 = "<div class='withPoint'></div>";
								td4 ="<td>"+points_data1+points_data2+"</td>";
								$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
								td1 = "<td> Down Sell #2</td>"
								$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
							}
							if(roadMapCriteriaVOJSONCount == 2){
								var td1 ="<td></td>";
								var td2 ="<td></td>";
								var td3 ="<td></td>";
								var td4 ="<td></td>";
								td1 = "<td> Down Sell #2</td>";
								var points_data1 = "<div class='withOutPoint'></div>";
								var points_data2 = "<div class='withPoint'></div>";
								td4 ="<td>"+points_data1+points_data2+"</td>";
								$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
							}  
				           	$("#ux_roadMap").show();
				           	window.clearInterval(intervalPeriod);
						}
							try{
								if(pollingCompleted =="true" && roadMapCriteriaVOJSONCount == 0 && displayRoadmap != null && displayRoadmap == true){
									buildContent = false;
									$(".video_content").empty();
									$(".preferred_content").empty();
									$(".internet_content").empty();
									$(".points_content").empty();
									$("#roadMapData").empty();
									var td1 ="<td></td>";
									var td2 ="<td></td>";
									var td3 ="<td></td>";
									var td4 ="<td></td>";
									td1 = "<td>Preferred Pitch</td>"
									var points_data1 = "<div class='withOutPoint'></div>";
									var points_data2 = "<div class='withPoint'></div>";
									
									$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
									
									td1 = "<td> Down Sell #1</td>";
									
									td4 ="<td>"+points_data1+points_data2+"</td>";
									$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
									
									td1 = "<td> Down Sell #2</td>"
									$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
									$("#ux_roadMap").show();
									window.clearInterval(intervalPeriod);
								}
								
							}catch(e){
								console.log(e);
							}
					}
	        	/* start tracker */
	        	$("#trackerUpdateTime").html(trackerUdateTime);
	     		var customerTracker =<%=request.getSession().getAttribute("customerTracker")%>;
	     		var currentOrderId = <%=request.getSession().getAttribute("currentOrderId")%>;
	     		if(customerTracker != null && customerTracker == true){
                	//$("#rankTab").show();
                	$("#trackerTab").css("visibility","visible");
                }
	           
				
	        			if(agentTrackerJson != null && agentTrackerJson !='' ){
	        				console.log("toatl Products"+JSON.parse(agentTrackerJson).length)
	        				var presentCallNumber = 0;
	        				var count =0;
	        					$(JSON.parse(agentTrackerJson)).each(function(index,val){
	        						if(val.productType != undefined && val.productType =="UtilityOffer"){
	        							console.log("productType ="+val.productType)
	        						}else if(currentOrderId != null && currentOrderId != undefined && currentOrderId ==val.orderId){
	        							console.log("current order matched")
	        						}
	        						else{
	        							var row ="<tr></tr>"
	    	        			            var td1 ="<td>"+val.callNumberId+"</td>"
	    	        			            var td2 ="<td>"+val.referrer+"</td>"
	    	        			            var td3 ="<td>"+val.customerName+"</td>"
	    	        			            var td4 ="<td>"+val.orderId+"</td>"
	    	        			            var td5 ="<td>"+val.dwellingType+"</td>"
	    	        			            var td6 ="<td>"+val.own+"</td>"
	    	        			            var noOfDays ="<td >"+val.noOfDays+"</td>"
	    	        			            
	    	        			            var td7 ="<td class='trancateProductName'>"+trancateStrings(val.productName,20)+"</td>"
	    	        			            var dupTd7 ="<td id='' style='display:none'  class='originalProductName'>"+val.productName+"</td>"
	    	        			            var td8 ="<td class='trancateProviderName'>"+val.providerName+"</td>"
	    	        			           // var dupTd8 ="<td id='' style='display:none'  class='originalPproviderName'>"+val.productName+"</td>"
	    	        			            var concertPoints = parseFloat(val.concertPoints);
    	        			            if(concertPoints != undefined ){
    	        			            	if(Number(concertPoints) === concertPoints && concertPoints % 1 === 0){
    	        			            		concertPoints = concertPoints.toFixed(1)
    	        			            	}else{
    	        			            		concertPoints = concertPoints.toFixedDown(1);
    	        			            	}
    	        			            }
    	        			            var actualPoints = parseFloat(val.actualPoints);
    	        			            if(actualPoints != undefined ){
    	        			            	if(Number(actualPoints) === actualPoints && actualPoints % 1 === 0){
    	        			            		actualPoints = actualPoints.toFixed(1)
    	        			            	}else{
    	        			            		actualPoints = actualPoints.toFixedDown(1);
    	        			            	}
    	        			            }
	    	        			            var td9 ="<td>"+concertPoints+"</td>"
	    	        			            var editiablecellSpan = "<span id='editableSpan'>"+actualPoints+"</span>"
	    	        						var tdinput = "<input id='' style='display:none'  data-valuebk='"+actualPoints+"' value='"+actualPoints+"' type='text' class='earned_points_edit' name='"+val.lineItemId+"' disabled='true' maxlength='6'>"            

	    	        						var editicons = "<input type='button' class='edit_icon_cell'><div class='other_icons'><input type='button' class='cancel_icon_cell'><input type='button' class='ok_icon_cell'></div>"
	    	        			            var td10 ="<td class='editiablecell'>"+editiablecellSpan+tdinput+editicons+"</td>"
	    	        			            if(presentCallNumber == parseInt(val.callNumberId)){
	    	        			            	
	    	        			            }else{
	    	        			            	count++;
	    	        			            	presentCallNumber = parseInt(val.callNumberId);
	    	        			            }
	    	        			            if(count % 2){
	    	        			            	var row ="<tr style='background:#fff' data-rowcolor='"+val.callNumberId+"'>"+td1+td2+td3+td4+td5+td6+noOfDays+dupTd7+td7+td8+td9+td10+"</tr>"
	    	        			            	 $("#trackerTabledata").append(row);
	    	        			            }else{
	    	        			            	var row ="<tr style='background:#c3c3c3' data-rowcolor='"+val.callNumberId+"'>"+td1+td2+td3+td4+td5+td6+noOfDays+dupTd7+td7+td8+td9+td10+"</tr>"
	    	        			            	 $("#trackerTabledata").append(row);
	    	        			            }
	    	        			         
	        						  }
	        						});
	        				}  
	        			if(custmerTrackerConversionJson != null && custmerTrackerConversionJson !=''){
        					custmerTrackerConversionJson = JSON.parse(custmerTrackerConversionJson);
        					
        					
        					 var totalActualPoints = parseFloat(custmerTrackerConversionJson.totalActualPoints);
        			            if(totalActualPoints != undefined ){
        			            	if(Number(totalActualPoints) === totalActualPoints && totalActualPoints % 1 === 0){
        			            		totalActualPoints = totalActualPoints.toFixed(1)
        			            	}else{
        			            		totalActualPoints = totalActualPoints.toFixedDown(1);
        			            	}
        			            }
        						
        			            var totalUtilityPoints = parseFloat(custmerTrackerConversionJson.totalUtilityPoints);
        			            if(totalUtilityPoints != undefined ){
        			            	if(Number(totalUtilityPoints) === totalUtilityPoints && totalUtilityPoints % 1 === 0){
        			            		totalUtilityPoints = totalUtilityPoints.toFixed(1)
        			            	}else{
        			            		totalUtilityPoints = totalUtilityPoints.toFixedDown(1);
        			            	}
        			            }
        					
        			            
        					$("#ConcertCalls").text(custmerTrackerConversionJson.concertCallCount);
    			            $("#actualCalls").text(custmerTrackerConversionJson.actualCallCount);
	        				$("#edit_icon_actualCall").text(custmerTrackerConversionJson.actualCallCount);
	    	        			$("#totalSales td:eq(1)").text(custmerTrackerConversionJson.totalSales);
	    	        			$("#actualPts td:eq(1)").text(totalActualPoints);
	    	        			$("#conversion td:eq(1)").text((custmerTrackerConversionJson.conversion).toFixed(2)+"%" )
	    	        			$("#grpcTr td:eq(1)").text((custmerTrackerConversionJson.GRPC).toFixed(2));
	    	        			$("#utility_TotalSales").text(custmerTrackerConversionJson.totalUtilityProducts);
	    	        			$("#Utility_Offers_Pitched").text(custmerTrackerConversionJson.utilityPitched);
	    	        			$("#Utility_Conversion").text((Math.round(custmerTrackerConversionJson.utilityConversion))+"%");
	        					$("#utility_Totalpoints").text(totalUtilityPoints);
	        				}
	        	/* end tracker */
	        	
                var TSData = <%=request.getSession().getAttribute("thoughtSpotData")%>;
         		 if(TSData != null && TSData != ''){
         			var data = <%=request.getSession().getAttribute("thoughtSpotData")%>;
          			TSData = JSON.stringify(data);
         			rankflag = '<%=request.getSession().getAttribute("thoughtSpotrank")%>';
         			var TSCacheDateTime ='<%=request.getSession().getAttribute("TSCacheDateTime")%>';
         			var thoughtSpotData = TSData;
                    var tempData = TSData;
                    if(rankflag != null && rankflag == "true"){
                    	//$("#rankTab").show();
                    	$("#rankTab").css("visibility","visible");
                    }
                    $("#rankUpdateTime").text(TSCacheDateTime)
                    $(JSON.parse(TSData).enterpriseData).each(function(index,val){
                        var row ="<tr></tr>"
                    	var td1 ="<td>"+val.enterpriseRank+"</td>"
                    	var td2 ="<td>"+val.points+"</td>"
                    	var td3 ="<td>"+trancateStrings(val.agentName,20)+"</td>"
                    	var callCenterTd ="<td>"+val.callCenter+"</td>"
                    	var regionTd ="<td>"+val.region+"</td>"
                    	 var row ="<tr>"+td1+td2+td3+callCenterTd+regionTd+"</tr>"
                    	$("#enterpriseData").append(row);
                    });
                    if(JSON.parse(TSData).callCenterData != undefined){
                    	$(JSON.parse(TSData).callCenterData).each(function(index,val){
                            var row ="<tr></tr>"
                        	var td1 ="<td>"+val.callCenterRank+"</td>"
                        	var td2 ="<td>"+val.points+"</td>"
                        	var td3 ="<td>"+val.agentName+"</td>"
                        	var regionDataTd ="<td>"+val.region+"</td>"
                        	 var row ="<tr>"+td1+td2+td3+regionDataTd+"</tr>"
                        	$("#callcenterData").append(row);
                        });
                    }
                    
			      if(JSON.parse(TSData).regionData != undefined){
			    	  $(JSON.parse(TSData).regionData).each(function(index,val){
	                        var row ="<tr></tr>"
	                    	var td1 ="<td>"+val.regionRank+"</td>"
	                    	var td2 ="<td>"+val.points+"</td>"
	                    	var td3 ="<td>"+val.agentName+"</td>"
	                    	var callCenterDataTd ="<td>"+val.callCenter+"</td>"
	                    	 var row ="<tr>"+td1+td2+td3+callCenterDataTd+"</tr>"
	                    	$("#regionrData").append(row);
	                    });
                    }
			      var currentuser = JSON.parse(tempData).currentuserData;
			    
			     if(currentuser != undefined && !jQuery.isEmptyObject(currentuser)){
			    	 var row1 ="<tr><td>AL</td><td>"+currentuser.enterpriseRank+"</td></tr>"
			    	 var row2 ="<tr><td>"+currentuser.callCenter+"</td><td>"+currentuser.callCenterRank+"</td></tr>"
	    			 var row3 ="<tr><td>"+currentuser.region+"</td><td>"+currentuser.regionRank+"</td></tr>"
    				 var row4 ="<tr><td>Total Points</td><td>"+currentuser.points+"</td></tr>"
			    	 $("#currentUserTable").append(row1+row2+row3+row4);
    				 
			    	 var row5 ="<tr><td>"+currentuser.enterpriseRank+"</td><td>"+currentuser.points+"</td><td>"+currentuser.agentName+"</td></tr>"
			    	 $("#currentEnterpriseRankTable").append(row5);
			    	 var row6 ="<tr><td>"+currentuser.callCenterRank+"</td><td>"+currentuser.points+"</td><td>"+currentuser.agentName+"</td></tr>"
			    	 $("#currentCallcenterTable").append(row6);
			    	 var row7 ="<tr><td>"+currentuser.regionRank+"</td><td>"+currentuser.points+"</td><td>"+currentuser.agentName+"</td></tr>"
			    	 $("#currentRegionTable").append(row7);
			    	 var list="<li data-btnsubtab='btn_enterprise' class='active'>AL</li><li data-btnsubtab='btn_center'>"+currentuser.callCenter+"</li><li data-btnsubtab='btn_region'>"+currentuser.region+"</li><div class='clearfix'></div>"
			    	 $("#rankTabNames").append(list);
			     }else{
			    	 var row1 ="<tr><td>AL</td><td></td></tr>"
			    	 var row2 ="<tr><td>Callcenter Name</td><td></td></tr>"
	    			 var row3 ="<tr><td>Region Name</td><td></td></tr>"
    				 var row4 ="<tr><td>Total Points</td><td></td></tr>"
			    	 $("#currentUserTable").append(row1+row2+row3+row4);
			    	 var list="<li data-btnsubtab='btn_enterprise' class='active'>AL</li><div class='clearfix'></div>"
			    	 $("#rankTabNames").append(list);
			    	 $("#rank_nodata").css({"height":"380px", "bottom": "150px;"});
			     }
         	}
			
			
			/* $('.ts_mainbtn').hover(function(e) {
				if($("#ts_popslide").is(":hidden")){
    			e.stopPropagation();
    				var hoverelem = $(this).attr("data-hovername");
    				$(".ts_popslide").slideUp("500");
    				$(this).children(".ts_popslide").slideDown("500");
    				$(".agnt_subtabs li").removeClass("active");
    				$(".ts_popslide .subtabcont_div").hide();
    				$(".agnt_subtabs li[data-btnsubtab='btn_enterprise']").addClass("active");
    				$(".ts_popslide .subtabcont_div[data-subtabname='enterprise']").show();
    				$("div").scrollTop(0);
    			$("table tbody").scrollTop(0);
					}
				}, function(e) {
    			e.stopPropagation();
    			$(".ts_popslide").slideUp("500");

			}); */
			var tackerVisible=false;
			var rankVisible=false;
			
			$("#trackerTab").click(function(){
				if(tackerVisible){
					$(".tackerData").slideUp("500");
					$(".ts_mainbtn.ts_mainbtnActive").removeClass("ts_mainbtnActive");
					tackerVisible=false;
				}else{
					tackerVisible=true;
					rankVisible=false;
					$(".ts_popslide").hide();
					$(".ts_mainbtn.ts_mainbtnActive").removeClass("ts_mainbtnActive");
					$(this).addClass("ts_mainbtnActive");
					$(".tackerData").slideDown("500");
					$("div").scrollTop(0);
					$(".agnt_subtabs li").removeClass("active");
					$(".ts_popslide .subtabcont_div").hide();
					$(".agnt_subtabs li[data-btnsubtab='btn_enterprise']").addClass("active");
					$(".ts_popslide .subtabcont_div[data-subtabname='enterprise']").show();
				}
				
				
			});
			
			$("#rankTab").click(function(){
				if(rankVisible){
					$(".rankData").slideUp("500");
					$(".ts_mainbtn.ts_mainbtnActive").removeClass("ts_mainbtnActive");
					rankVisible=false;
				}else{
					rankVisible=true;
					tackerVisible=false;
					$(".ts_popslide").hide();
					$(".ts_mainbtn.ts_mainbtnActive").removeClass("ts_mainbtnActive");
					$(this).addClass("ts_mainbtnActive");
					$(".rankData").slideDown("500");
					$("div").scrollTop(0);
					$(".agnt_subtabs li").removeClass("active");
					$(".ts_popslide .subtabcont_div").hide();
					$(".agnt_subtabs li[data-btnsubtab='btn_enterprise']").addClass("active");
					$(".ts_popslide .subtabcont_div[data-subtabname='enterprise']").show();
				}
				
				
			});
			
			
			
			
			$('#header,#mainsection,.concertlogo,#mainsection,#main_footer,.hidenBlock').click(function(e) {
				$(".ts_mainbtn.ts_mainbtnActive").removeClass("ts_mainbtnActive");
				tackerVisible=false;
				rankVisible=false;
				if($(".ts_popslide").is(":visible")){
					$(".ts_popslide").slideUp("500");
				}
			});
			$('body').click( function (e) { 
			    if ( e.target == this) {$(".ts_popslide").slideUp("500");
			    tackerVisible=false;
				rankVisible=false;
				}
			});
			



			$(".agnt_subtabs li").mouseover(function(){
				$("div").scrollTop(0);
    			$("table tbody").scrollTop(0);
				$(".agnt_subtabs li").removeClass("active");
				$(this).addClass("active");
				var stbclickedbtn = $(this).attr("data-btnsubtab").substring(4);
				$(".ts_popslide .subtabcont_div").hide();
				$(".ts_popslide div[data-subtabname='"+stbclickedbtn+"']").show();
			});

  $(".earned_points_edit , .utilityPoints_edit , .TotalCall_edit").keypress(function (e) {
		 
	  var charCode = (e.which) ? e.which : e.keyCode;
	  var enteredValue = this.value.split('.');
	  var cursorPosition= doGetCaretPosition(this) + 1;
	  if(charCode != 37 && charCode != 39){
		  if (charCode === 46 && enteredValue.length === 2) {
		        return false;
		    }
		   if (charCode != 46 && charCode > 31 
		     && (charCode < 48 || charCode > 57) && (charCode != 46 && charCode != 8 )){
		      return false;
		   }
		   if(enteredValue.length == 2 && (enteredValue[1].length == 1 && (charCode != 46 && charCode != 8 ))){
			   if(cursorPosition < this.value.length){
				   return true;
			   }
			   return false;
			}
	  }
	   
	   return true;
   }); 

$('.earned_points_edit').unbind('keyup change input paste').bind('keyup change input paste',function(e){
    var $this = $(this);
    var val = $this.val();
    var valLength = val.length;
    var maxCount = $this.attr('maxlength');
    if(valLength>maxCount){
        $this.val($this.val().substring(0,maxCount));
    }
}); 



$(".editiablecell").mouseover(function(){
	if($(this).children(".other_icons").is(":visible"))
	{
		$(this).children("input.edit_icon_cell").hide();
	}
	else
	{
		$(this).children("input.edit_icon_cell").show();
	}
});


$(".editiablecell").mouseout(function(){
	if($(this).children(".other_icons").is(":visible"))
	{
		$(this).children("input.edit_icon_cell").hide();
	}
	else
	{
		$(this).children("input.edit_icon_cell").hide();
	}
});

var editableSpan = 0;
var currentElement ;
$("input.edit_icon_cell").click(function(){
	currentElement = this;
	$(this).closest("td").find(".other_icons").show();
	$(this).hide();
	$(this).closest("td").find(".earned_points_edit").show()
	$(this).closest("td").find(".earned_points_edit").prop('disabled', false);
	$(this).closest("td").find(".earned_points_edit").addClass("editmode");
	$(this).closest("td").find("#editableSpan").hide()
});


var dataJson = {}
$("input.ok_icon_cell").click(function(){
	var value = $(this).closest("td").find(".earned_points_edit").val();
	var currentval = $(currentElement).closest("td").find("#editableSpan").text();
	var lineItemId = $(this).closest("td").find(".earned_points_edit").attr("name")
	$(this).parent(".other_icons").hide();
	$(this).parent(".other_icons").siblings(".earned_points_edit").prop('disabled', true);
	$(this).parent(".other_icons").siblings(".earned_points_edit").removeClass("editmode");
	$(this).closest("td").find("#editableSpan").show()
	
	$(this).closest("td").find(".earned_points_edit").hide()
	
	if(value !="" && value != undefined && value != currentval){
		  var editablePoints = parseFloat(value);
         if(editablePoints != undefined ){
         	if(Number(editablePoints) === editablePoints && editablePoints % 1 === 0){
         		editablePoints = editablePoints.toFixed(1)
         	}else{
         		editablePoints = editablePoints.toFixedDown(1);
         	}
         }
		$(this).closest("td").find("#editableSpan").text(editablePoints);
		dataJson[lineItemId] = value
		console.log("dataJson="+JSON.stringify(dataJson))
		var data1={};
		data1["updatedTrackerJson"] = JSON.stringify(dataJson);
		var path = "<%=request.getContextPath()%>";
		var url = path+"/salescenter/updateAgentPoints";
		$.ajax({
			data : data1,
			type: 'POST',
			url: url,
			success: function (data3) {
				if(data3 != undefined && data3 !=''){
					var custmerJson = JSON.parse(data3);
					
				    var totalActualPoints = parseFloat(custmerJson.totalActualPoints);
		            if(totalActualPoints != undefined ){
		            	if(Number(totalActualPoints) === totalActualPoints && totalActualPoints % 1 === 0){
		            		totalActualPoints = totalActualPoints.toFixed(1)
		            	}else{
		            		totalActualPoints = totalActualPoints.toFixedDown(1);
		            	}
		            }
					
		            var totalUtilityPoints = parseFloat(custmerJson.totalUtilityPoints);
		            if(totalUtilityPoints != undefined ){
		            	if(Number(totalUtilityPoints) === totalUtilityPoints && totalUtilityPoints % 1 === 0){
		            		totalUtilityPoints = totalUtilityPoints.toFixed(1)
		            	}else{
		            		totalUtilityPoints = totalUtilityPoints.toFixedDown(1);
		            	}
		            }
					$("#totalSales td:eq(1)").text(custmerJson.totalSales)
					$("#actualPts td:eq(1)").text(totalActualPoints);
	       			$("#conversion td:eq(1)").text((custmerJson.conversion).toFixed(2)+"%" );
	       			$("#grpcTr td:eq(1)").text((custmerJson.GRPC).toFixed(2));
	       			$("#utility_TotalSales").text(custmerJson.totalUtilityProducts);
					$("#utility_Totalpoints").text(totalUtilityPoints);
					}
	           }
		});
	}else{
		
		$(currentElement).closest("td").find("#editableSpan").text(currentval);
		$(this).closest("td").find(".earned_points_edit").val(currentval)
	}
});
$("input.cancel_icon_cell").click(function(){
	$(this).parent(".other_icons").hide();
	$(this).parent(".other_icons").siblings(".earned_points_edit").prop('disabled', true);
	$(this).parent(".other_icons").siblings(".earned_points_edit").removeClass("editmode");
	$(this).closest("td").find(".earned_points_edit").val($(this).closest("td").find("#editableSpan").text())
	$(this).closest("td").find("#editableSpan").show()
	$(this).closest("td").find(".earned_points_edit").hide()
});


	$(".edit_icon_actualCall").click(function(){
		$(".other_icons_TotalCall").show();
		$("input.edit_icon_actualCall").hide();
		$(".TotalCall_edit").show();
		$("#actualCalls").hide();
		$(".TotalCall_edit").val($("#actualCalls").text());
		
		
	});
	

	$(".ok_icon_TotalCall").click(function(){
		$(".other_icons_TotalCall").hide();
		$("input.edit_icon_actualCall").show();
		$(".TotalCall_edit").hide();
		$("#actualCalls").show();
		
		var callCountNumber = $("#actualCalls").text();
		var value = $(".TotalCall_edit").val();
		
		if(value !="" && value != undefined && value != callCountNumber){
			$("#actualCalls").text(value);
			$("#callCountNumber").text(value);
			var data2={"updatedCallCount":value};
			var path = "<%=request.getContextPath()%>";
			var url = path+"/salescenter/updateAgentData";
			$.ajax({
				data : data2,
				type: 'POST',
				url: url,
				success: function (data3) {
					if(data3 != undefined && data3 !=''){
						var custmerJson = JSON.parse(data3);
						
					    var totalActualPoints = parseFloat(custmerJson.totalActualPoints);
			            if(totalActualPoints != undefined ){
			            	if(Number(totalActualPoints) === totalActualPoints && totalActualPoints % 1 === 0){
			            		totalActualPoints = totalActualPoints.toFixed(1)
			            	}else{
			            		totalActualPoints = totalActualPoints.toFixedDown(1);
			            	}
			            }
						
			            var totalUtilityPoints = parseFloat(custmerJson.totalUtilityPoints);
			            if(totalUtilityPoints != undefined ){
			            	if(Number(totalUtilityPoints) === totalUtilityPoints && totalUtilityPoints % 1 === 0){
			            		totalUtilityPoints = totalUtilityPoints.toFixed(1)
			            	}else{
			            		totalUtilityPoints = totalUtilityPoints.toFixedDown(1);
			            	}
			            }
			            $("#actualCalls").text(value)
						$("#totalSales td:eq(1)").text(custmerJson.totalSales)
						$("#actualPts td:eq(1)").text(totalActualPoints);
		       			$("#conversion td:eq(1)").text((custmerJson.conversion).toFixed(2)+"%" );
		       			$("#grpcTr td:eq(1)").text((custmerJson.GRPC).toFixed(2));
		       			$("#utility_TotalSales").text(custmerJson.totalUtilityProducts);
						$("#utility_Totalpoints").text(totalUtilityPoints);
						}
		           }
			});
		}else{
			$("#actualCalls").text(callCountNumber);
		}
		
	});
	
	$(".cancel_icon_TotalCall").click(function(){
		$(".other_icons_TotalCall").hide();
		$("input.edit_icon_actualCall").show();
		$(".TotalCall_edit").hide();
		$("#actualCalls").show();
	});
	
	$("#utilityTotalCallHover").mouseover(function(){
		
		if($(this).children(".other_icons_TotalCall").is(":visible"))
		{
			$("input.edit_icon_actualCall").css("visibility","hidden");
		}
		else
		{
			$("input.edit_icon_actualCall").css("visibility","visible");
		}
		
	});
	$("#utilityTotalCallHover").mouseout(function(){
			$("input.edit_icon_actualCall").css("visibility","hidden");
	});
	
$("#utilityTotalPtHover").mouseover(function(){
		
		if($(this).children(".other_icons_utilityPoints").is(":visible"))
		{
			$("input.edit_icon_utilityPoints").css("visibility","hidden");
		}
		else
		{
			$("input.edit_icon_utilityPoints").css("visibility","visible");
		}
		
	});
	
$("#utilityTotalPtHover").mouseout(function(){
	$("input.edit_icon_utilityPoints").css("visibility","hidden");
});

$(".edit_icon_utilityPoints").click(function(){
	
	$(".other_icons_utilityPoints").show();
	$("input.edit_icon_utilityPoints").hide();
	$(".utilityPoints_edit").val($("#utility_Totalpoints").text());
	$(".utilityPoints_edit").show();
	$("#utility_Totalpoints").hide();
	
	
	
});

$(".ok_icon_utilityPoints").click(function(){
	$(".other_icons_utilityPoints").hide();
	$("input.edit_icon_utilityPoints").show();
	$(".utilityPoints_edit").hide();
	$("#utility_Totalpoints").show();
	
	var callCountNumber = $("#utility_Totalpoints").text();
	var value = $(".utilityPoints_edit").val();
	
	if(value !="" && value != undefined && value != callCountNumber){
		$("#utility_Totalpoints").text($(".utilityPoints_edit").val());
		$("#callCountNumber").text(value);
		var data2={"updatedUtilityPoints":value};
		var path = "<%=request.getContextPath()%>";
		var url = path+"/salescenter/updatedUtilityPoints";
		$.ajax({
			data : data2,
			type: 'POST',
			url: url,
			success: function (data3) {
				if(data3 != undefined && data3 !=''){
					var custmerJson = JSON.parse(data3);
					
				    var totalActualPoints = parseFloat(custmerJson.totalActualPoints);
		            if(totalActualPoints != undefined ){
		            	if(Number(totalActualPoints) === totalActualPoints && totalActualPoints % 1 === 0){
		            		totalActualPoints = totalActualPoints.toFixed(1)
		            	}else{
		            		totalActualPoints = totalActualPoints.toFixedDown(1);
		            	}
		            }
					
		            var totalUtilityPoints = parseFloat(custmerJson.totalUtilityPoints);
		            if(totalUtilityPoints != undefined ){
		            	if(Number(totalUtilityPoints) === totalUtilityPoints && totalUtilityPoints % 1 === 0){
		            		totalUtilityPoints = totalUtilityPoints.toFixed(1)
		            	}else{
		            		totalUtilityPoints = totalUtilityPoints.toFixedDown(1);
		            	}
		            }
		            $("#utility_Totalpoints").text(value);
					$("#totalSales td:eq(1)").text(custmerJson.totalSales)
					$("#actualPts td:eq(1)").text(totalActualPoints);
	       			$("#conversion td:eq(1)").text((custmerJson.conversion).toFixed(2)+"%" );
	       			$("#grpcTr td:eq(1)").text((custmerJson.GRPC).toFixed(2));
	       			$("#utility_TotalSales").text(custmerJson.totalUtilityProducts);
					$("#utility_Totalpoints").text(totalUtilityPoints);
					}
	           }
		});
	}else{
		$("#utility_Totalpoints").text(callCountNumber);
	}
});

$(".cancel_icon_utilityPoints").click(function(){
	$(".other_icons_utilityPoints").hide();
	$("input.edit_icon_utilityPoints").show();
	$(".utilityPoints_edit").hide();
	$("#utility_Totalpoints").show();
});


	

/*$("input[type=text],input[type=email],input[type=number]").attr("autocomplete","nope");*/


		});
		

		function trancateStrings(trancateString,breakPoint){
		  if(trancateString.length > breakPoint){
		   trancatedString = jQuery.trim(trancateString).substring(0, breakPoint).trim(this) + "...";
		  }else{
		    trancatedString = trancateString;
		  }
		  return trancatedString;
		}
		function clipboardStyles(){
			  var div = $("<div></div>").attr("id","dupInfo");
			  $("#ts_popslide").append(div);
			  $("#ts_popslide").clone().appendTo("#dupInfo");
			  $("#trackertotals").clone().appendTo("#dupInfo");
			  $("#dupInfo #trackertotals, #dupInfo .trackertbl").attr({"cellspacing":"0","cellpadding":"5px","border":"1"})
			  $("#dupInfo .utility_details, #dupInfo .CallsBlock").attr({"cellspacing":"0","cellpadding":"5px","border":"1"})
			  $("#dupInfo .trackertbl thead").attr({"style":"background-color: #555;color: #FFF;"})
			  $("#dupInfo #utility_heading ").attr({"style":"background-color: #555;color: #FFF;"})
			  $("#dupInfo #Utility_Conversion ,#dupInfo #Utility_Offers_Pitched ,#dupInfo #utility_TotalSales,#dupInfo #utility_Totalpoints,#dupInfo #ConcertCalls,#dupInfo #actualCalls ").before($("<span></span>").html("&nbsp;"))
			  //$("#dupInfo #utilityTotalPtHover,#dupInfo #utilityTotalCallHover").css("display","inline-block");
			  $("#dupInfo .utility_details, #dupInfo .CallsBlock, #dupInfo #trackertotals").before("<br>");
			  //$("#dupInfo #utilityTotalPtHover, #dupInfo #utilityTotalCallHover").attr("style","display:inline-flex");
			  $("#dupInfo, #dupInfo .colon").show();
			  $("#dupInfo #actualPts td").css("width","48%")
			  $("#dupInfo .utilityHeading").css("padding-right","8px");
			  $("#dupInfo .utility_details,#dupInfo #trackertotals").css("margin-top","15px")
			  $(".trancateProductName").hide();
			  $(".originalProductName").show();
		}
		function copyToClipboard(elementObj) {
			clipboardStyles()
		  var body = document.body, range, sel;
		  if (document.createRange && window.getSelection) {
		    range = document.createRange();
		    sel = window.getSelection();
		    sel.removeAllRanges();
		    range.selectNodeContents(document.getElementById('dupInfo'));
		    sel.addRange(range);
		    document.execCommand("copy");
		    sel.removeAllRanges();
		    $(".trancateProductName").show()
		    $(".originalProductName").hide();
		  $("#dupInfo").hide();
		  $("#dupInfo").remove();
		}
		}
		
		Number.prototype.toFixedDown = function(digits) {
		    var re = new RegExp("(\\d+\\.\\d{" + digits + "})(\\d)"),
		        m = this.toString().match(re);
		    return m ? parseFloat(m[1]) : this.valueOf();
		};
		function getColorName(noOfDays){
			if(noOfDays != "N/A" && noOfDays != undefined){
			console.log("noOfDays="+noOfDays);
				noOfDays = parseInt(noOfDays);
				console.log("noOfDays1="+noOfDays);
				if(noOfDays <=7){
					return "greenColor"
				}else if(noOfDays >7 && noOfDays <=14){
					return "orangeColor"
				}else if(noOfDays >14 ){
					return "redColor"
				}
				
			}
			
			return "regularColor"
		}
		function doGetCaretPosition (oField) {

			  // Initialize
			  var iCaretPos = 0;

			  // IE Support
			  if (document.selection) {

			    // Set focus on the element
			    oField.focus();

			    // To get cursor position, get empty selection range
			    var oSel = document.selection.createRange();

			    // Move selection start to 0 position
			    oSel.moveStart('character', -oField.value.length);

			    // The caret position is selection length
			    iCaretPos = oSel.text.length;
			  }

			  // Firefox support
			  else if (oField.selectionStart || oField.selectionStart == '0')
			    iCaretPos = oField.selectionStart;

			  // Return results
			  return iCaretPos;
			}
		function getRoadMapData(){
			
			var pageTitle = $('input[name="pageTitle"]').val();
			if(pollingCompleted =="true" && (pageTitle != undefined && (pageTitle !="IdlePage" && pageTitle !="Dispositions"))){
				window.clearInterval(intervalPeriod);
			}else{
				var pageTitle = $('input[name="pageTitle"]').val();
				if(pageTitle != undefined && (pageTitle =="IdlePage" || pageTitle =="Dispositions")){
					window.clearInterval(intervalPeriod);
				}else{
					var displayRoadmap =<%=request.getSession().getAttribute("displayRoadmap")%>;
					if(displayRoadmap != null && displayRoadmap == true){
						 var url = "<%=request.getContextPath()%>/salescenter/recommendations/getRoadMapContent";
						    $.ajax({
						    	type: 'POST',
						    	url: url,
						    	success: roadMapData
						 	});
		            }else{
		            	window.clearInterval(intervalPeriod);
		            }
				}
			}
		}
	
	function roadMapData(data){
		var isdone = false;
		var buildContent = true;
	
			if(data != "{}"){
				try{
				var jsonData =JSON.parse(data);
				var roadMapCriteriaVOJSON1 = "";
				if( jsonData.roadMapCriteriaVOJson != undefined){
					roadMapCriteriaVOJSON1 = jsonData.roadMapCriteriaVOJson;
				}
				var isPollingDone= jsonData.isPollingCompleted
				if(roadMapCriteriaVOJSON1 != null && roadMapCriteriaVOJSON1 !='' && isPollingDone){
					console.log("roadMapCriteriaVOJson ="+jsonData.roadMapCriteriaVOJson)
					$(".video_content").empty();
					$(".preferred_content").empty();
					$(".internet_content").empty();
					$(".points_content").empty();
					$("#roadMapData").empty();
					isdone = true;
					var preferedwithoutPhonePoints = 0;
					var preferedwithPhonePoints = 0;
					var roadMapCriteriaVOJSONCount = 0;
					$(JSON.parse(roadMapCriteriaVOJSON1)).each(function(index,val){
						roadMapCriteriaVOJSONCount++;
						var td1 ="<td></td>";
						var td2 ="<td></td>";
						var td3 ="<td></td>";
						var td4 ="<td></td>";
						var td5 ="<td> </td>";
						if(index == 0){
							preferedwithoutPhonePoints = val.withoutPhonePoints;
							preferedwithPhonePoints = val.withPhonePoints;
							if(val.withoutPhonePoints <= 0){
								val.withoutPhonePoints = "";
							}
							if(val.withPhonePoints <= 0){
								val.withPhonePoints = "";
							}
							var points_data1 = "<div class='withOutPoint'>"+val.withoutPhonePoints+"</div>";
							var points_data2 = "<div class='withPoint'>"+val.withPhonePoints+"</div>";
							td1 ="<td>"+points_data1+points_data2+"</td>";
							td4 = "<td>Preferred Pitch</td>"
						}else{
							td4 = "<td> Down Sell #"+index+"</td>"
							var points_data1 = "<div class='withOutPoint'></div>";
							var points_data2 = "<div class='withPoint'></div>";
							td1 ="<td>"+points_data1+points_data2+"</td>";
							if(preferedwithoutPhonePoints == val.withoutPhonePoints){
								if(val.withoutPhonePoints <= 0){
									val.withoutPhonePoints = "";
								}
								var points_data1 = "<div class='withOutPoint'>"+val.withoutPhonePoints+"</div>";
								var points_data2 = "<div class='withPoint'></div>";
								if(preferedwithPhonePoints == val.withPhonePoints){
									if(val.withoutPhonePoints <= 0){
										val.withPhonePoints = "";
									}
								    points_data2 = "<div class='withPoint'>"+val.withPhonePoints+"</div>";
								}else{
									if(preferedwithPhonePoints < val.withPhonePoints){
										var points = val.withPhonePoints - preferedwithPhonePoints;
										points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" more!)"+"</div>";
									}else if(preferedwithPhonePoints > val.withPhonePoints){
										var points = preferedwithPhonePoints - val.withPhonePoints;
										 points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" less!)"+"</div>";
									}
								}
								//td1 ="<td>"+points_data1+points_data2+"</td>";
								td1 ="<td>"+points_data1+points_data2+"</td>";
							}else{
								var points_data1 = "<div class='withOutPoint'></div>";
								var points_data2 = "<div class='withPoint'></div>";
								td1 ="<td>"+points_data1+points_data2+"</td>";
								if(preferedwithoutPhonePoints < val.withoutPhonePoints){
									var points = val.withoutPhonePoints - preferedwithoutPhonePoints;
									 points_data1 = "<div class='withOutPoint'>"+val.withoutPhonePoints+" ("+points+" more!)"+"</div>";
									 points_data2 = "<div class='withPoint'></div>";
									if(preferedwithPhonePoints == val.withPhonePoints){
										if(val.withPhonePoints <= 0){
											val.withPhonePoints = "";
										}
										  points_data2 = "<div class='withPoint'>"+val.withPhonePoints+"</div>";
									}else{
										if(preferedwithPhonePoints < val.withPhonePoints){
											var points = val.withPhonePoints - preferedwithPhonePoints;
											points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" more!)"+"</div>";
										}else if(preferedwithPhonePoints > val.withPhonePoints){
											var points = preferedwithPhonePoints - val.withPhonePoints;
											 points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" less!)"+"</div>";
										}
									}
									//td1 ="<td>"+val.points+" ("+points+" more!)"+"</td>"
									td1 ="<td>"+points_data1+points_data2+"</td>";
								}else if(preferedwithoutPhonePoints > val.withoutPhonePoints){
									var points = preferedwithoutPhonePoints - val.withoutPhonePoints;
									 points_data1 = "<div class='withOutPoint'>"+val.withoutPhonePoints+" ("+points+" less!)"+"</div>";
									 points_data2 = "<div class='withPoint'></div>";
									 if(preferedwithPhonePoints == val.withPhonePoints){
											if(val.withPhonePoints <= 0){
												val.withPhonePoints = "";
											}
											  points_data2 = "<div class='withPoint'>"+val.withPhonePoints+"</div>";
									}else{
										if(preferedwithPhonePoints > val.withPhonePoints){
											var points = preferedwithPhonePoints - val.withPhonePoints;
											points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" less!)"+"</div>";
										}else if(preferedwithPhonePoints < val.withPhonePoints){
											var points = val.withPhonePoints - preferedwithPhonePoints;
											 points_data2 = "<div class='withPoint'>"+val.withPhonePoints+" ("+points+" more!)"+"</div>";
										}
									}
									td1 ="<td>"+points_data1+points_data2+"</td>";
								}
							}
							
						}
						if(val.internetProviderName != undefined){
							if(val.connectionSpeedVal != undefined && val.connectionSpeedVal <=0){
								var connectionSpeed = "";
								td2 ="<td><div class='internet-col-first'>"+val.internetProviderName+"</div><div class='internet-col-sec' style='width: 30px; padding: 8px;'>"+connectionSpeed+"</div></td>"
							}else{
								td2 ="<td><div class='internet-col-first'>"+val.internetProviderName+"</div><div class='internet-col-sec'>"+val.connectionSpeedVal+" MB</div></td>"
							}
						}else{
							td2 ="<td></td>"
						}
						if(val.videoProviderName != undefined){
							td3 ="<td>"+val.videoProviderName+"</td>"
						}else{
							td3 ="<td></td>"
						}
						
						$("#roadMapData").append("<tr>"+td4+td2+td3+td1+"</tr>");
					});
					  if(roadMapCriteriaVOJSONCount == 1){
						var td1 ="<td></td>";
						var td2 ="<td></td>";
						var td3 ="<td></td>";
						var td4 ="<td></td>";
						td1 = "<td> Down Sell #1</td>";
						var points_data1 = "<div class='withOutPoint'></div>";
						var points_data2 = "<div class='withPoint'></div>";
						td4 ="<td>"+points_data1+points_data2+"</td>";
						$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
						td1 = "<td> Down Sell #2</td>"
						$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
					}
					if(roadMapCriteriaVOJSONCount == 2){
						var td1 ="<td></td>";
						var td2 ="<td></td>";
						var td3 ="<td></td>";
						var td4 ="<td></td>";
						td1 = "<td> Down Sell #2</td>";
						var points_data1 = "<div class='withOutPoint'></div>";
						var points_data2 = "<div class='withPoint'></div>";
						td4 ="<td>"+points_data1+points_data2+"</td>";
						$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
					}  
		           	$("#ux_roadMap").show();
		           	window.clearInterval(intervalPeriod);
				}else{
					try{
						if(buildContent && isPollingDone){
							buildContent = false;
							$(".video_content").empty();
							$(".preferred_content").empty();
							$(".internet_content").empty();
							$(".points_content").empty();
							$("#roadMapData").empty();
							var td1 ="<td></td>";
							var td2 ="<td></td>";
							var td3 ="<td></td>";
							var td4 ="<td></td>";
							td1 = "<td>Preferred Pitch</td>"
							var points_data1 = "<div class='withOutPoint'></div>";
							var points_data2 = "<div class='withPoint'></div>";
							
							$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
							
							td1 = "<td> Down Sell #1</td>";
							
							td4 ="<td>"+points_data1+points_data2+"</td>";
							$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
							
							td1 = "<td> Down Sell #2</td>"
							$("#roadMapData").append("<tr>"+td1+td2+td3+td4+"</tr>");
							$("#ux_roadMap").show();
							window.clearInterval(intervalPeriod);
						}
						
					}catch(e){
						console.log(e);
					}
				}
			 	if (isdone) {
					window.clearInterval(intervalPeriod);
				}
				}catch(err){
					console.log(err);
				}
			}
	}
	</script>
</head>

<body>
	<div class="agent_thoughtspot">
	<!-- <div class="ts_mainbtn hidenBlock"></div> -->

<!-- tracker star -->

<div data-hovername="ux_roadMap" class="ux_roadMap" id="ux_roadMap">
			<table class="roadMapTable">
				<thead>
					<tr>
						<!-- <th class="roadMap" style="font-size:22px;margin-top: 16px; width:12%">Roadmap</th> -->
						<th class="roadMap" style="font-size:22px;">Roadmap</th>
						<th class="roadMap">Internet</th>
						<th class="roadMap">Video</th>
						<th class="roadMap">
							<div style="border-bottom:1px solid #999">Points</div>
							<div class="withOutPoint">- Phone</div>
							<div class="withPoint">+ Phone</div>
						</th>
					</tr>
				</thead>
				<tbody id="roadMapData"></tbody>
			</table>

</div>
		<div data-hovername="tracker" class="ts_mainbtn" id="trackerTab">
		    <span class="ts_updateon">Update on</span>
			<span class="ts_timestamp" id="trackerUpdateTime"></span>
			<div class="main_txt lt_txt">Tracker</div>
			<div class="trac_dtls">
				<table class="rank_tbl" id="trackertotals">
				
				<tbody>
						<tr id="totalSales">
							<td >Total Sales<span class="colon">:</span></td>
							<td></td>
						</tr>
						<tr id="actualPts">
							<td >Actual Points Total<span class="colon">:</span></td>
							<td></td>
						</tr>
						<tr  id="conversion">
							<td >Conversion<span class="colon">:</span></td>
							<td></td>
						</tr>
						<tr id="grpcTr">
							<td>GPPC<span class="colon">:</span></td>
							<td></td>
						</tr>
					</tbody>
				
				</table>
			</div>

		</div>

		<div id="ts_popslide" class="ts_popslide bigwidth tackerData">

			<table class="subtab_tbl trackertbl fixed_headers">
				<thead>
					<tr>
						<th>Call #</th>
						<th>Referrer</th>
						<th>Customer</th>
						<th>Order Number</th>
						<th>Dwelling Type</th>
						<th>Own/ Rent</th>
						<th>Install vs Move Date</th>
						<th>Product</th>
						<th>Provider</th>
						<th>Concert Points</th>
						<th class="lastth">Actual Points</th>
					</tr>
				</thead>
				<tbody id="trackerTabledata"></tbody>
			</table>
			
						
			<table class="utility_details">
			
					<tr><th id="utility_heading" colspan="4"><b>Utility Offers</b></th></tr>
					<tr>
						<td class="utilitySalesSpan"><span class="utilityHeading">Pitched:</span><span id="Utility_Offers_Pitched"></span></td>
						<td class="utilitySalesSpan"><span class="utilityHeading">Sold:</span><span id="utility_TotalSales"></span></td>
						<td class="utilitySalesSpan"><span class="utilityHeading">Conversion:</span><span id="Utility_Conversion"></span></td>
						<td class="utilitySalesSpan"><span class="utilityHeading">Points:
							<span id="utilityTotalPtHover">
								<span id="utility_Totalpoints"></span>
								<div id="edit_icon"><input class="edit_icon_utilityPoints" type="button"></div>
							</span>
							</span>
							<span class="other_icons_utilityPoints"><input class="cancel_icon_utilityPoints" type="button"><input class="ok_icon_utilityPoints" type="button"><input type="text" maxlength="5" class="utilityPoints_edit"/></span>
						</td>
					</tr>
			</table>
											
			
			<table class="utility_details CallsBlock">
				<tr><th id="utility_heading" colspan="2"><b>Calls</b></th></tr>
				<tr>
					<td class="utilitySalesSpan"><span class="utilityHeading">Concert:</span><span id="ConcertCalls"></span></td>
					<td class="utilitySalesSpan"><span class="utilityHeading">Actual:
						<span id="utilityTotalCallHover">
							<span id="actualCalls"></span>
							<div id="edit_icon"><input class="edit_icon_actualCall" type="button"></div>
						</span>
						</span>
						<span class="other_icons_TotalCall"><input class="cancel_icon_TotalCall" type="button"><input class="ok_icon_TotalCall" type="button"><input type="text" maxlength="5" class="TotalCall_edit"/></span>
					</td>
				</tr>
			</table> 
			
			
			<div id="CopyToClipboard">
			<input type="button" value="Copy to Clipboard" onclick="copyToClipboard(document.getElementById('ts_popslide'))" class="pop_export_btn">
			</div>
			

		</div>
		<!-- tracker end -->

		<!-- rank start -->
		<div data-hovername="rank" class="ts_mainbtn" id="rankTab">
			<span class="ts_updateon">Update on</span> <span class="ts_timestamp"
				id="rankUpdateTime"></span>
			<div class="main_txt lt_txt">Rank</div>
			<div class="trac_dtls">
				<table class="rank_tbl" id="currentUserTable">

				</table>
			</div>

		</div>

		<div class="ts_popslide rankData" id="rank_nodata">
			<ul class="agnt_subtabs" id="rankTabNames">

			</ul>
			<div data-subtabname="enterprise" class="subtabcont_div divshow">
				<div class="scroll_tbl">
					<table class="subtab_tbl rank_dtls_tbl">
						<thead>
							<tr>
								<th>Rank</th>
								<th>Points</th>
								<th>Agent Name</th>
								<th>Center</th>
								<th>Region</th>
							</tr>
						</thead>
						<tbody id="enterpriseData"></tbody>
					</table>
				</div>
				<table id="currentEnterpriseRankTable"
					class="subtab_tbl rank_dtls_tbl">

				</table>
			</div>
			<div data-subtabname="center" class="subtabcont_div">
				<div class="scroll_tbl">
					<table class="subtab_tbl rank_dtls_tbl">
						<thead>
							<tr>
								<th>Rank</th>
								<th>Points</th>
								<th>Agent Name</th>
								<th>Region</th>
							</tr>
						</thead>
						<tbody id="callcenterData"></tbody>
					</table>
				</div>
				<table class="subtab_tbl rank_dtls_tbl" id="currentCallcenterTable">

				</table>
			</div>
			<div data-subtabname="region" class="subtabcont_div">
				<div class="scroll_tbl">
					<table class="subtab_tbl rank_dtls_tbl">
						<thead>
							<tr>
								<th>Rank</th>
								<th>Points</th>
								<th>Agent Name</th>
								<th>Center</th>
							</tr>
						</thead>
						<tbody id="regionrData"></tbody>
					</table>
				</div>
				<table class="subtab_tbl rank_dtls_tbl" id="currentRegionTable">

				</table>
			</div>
		</div>


		<!-- rank end -->
		<!-- <div class="ts_mainbtn hidenBlock"></div> -->
		<div class="clearfix"></div>
	</div>
</body>
</html>