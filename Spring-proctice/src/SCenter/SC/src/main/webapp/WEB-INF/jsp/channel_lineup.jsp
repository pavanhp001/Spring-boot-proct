<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.AL.ui.repository.SessionCache"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
<title>${providerName} Channel Line Up</title>
<style type="text/css">

table {
    border-collapse: collapse;
    min-width: 300px;
    max-width: 1460px;
    overflow-x: scroll;
    display: block;
}
thead {
    
}
thead, tbody {
    display: block;
}
tbody {
    overflow-y: scroll;
    overflow-x: hidden;
    height: 600px;
}
td, th {
	padding:10px;
    min-width: 120px;
    height: 25px;
    border: dashed 1px lightblue;
    overflow:hidden;
    text-overflow: ellipsis;
    max-width: 120px;
}
th {
	background-color: #76777a;
	color: #ffffff;
	border-color:#ffffff!important;
}



table,th,td {
	border: 3px solid #76777a;
	border-collapse: collapse;
	text-align: center;
	word-break: break-all;
}

#channelLineupDataDviId
{
	padding-top:50px!important;
}

#headerAlign span{
	 
   font-family: "arial";
   font-size: 26px;
   margin-left: 0;
 }

#searchChannel{
	 float: right;
    font-size: 16px;
    width: 250px;
}

.img_icon{
	float:left;
   margin-left: 8px;
   /*  width:30px; */
}
.providerNameDiv{
	
    float: left;
   
    font-weight: bold;
}

.searchable tr:first-child{
	 background: white none repeat scroll 0 0;
	/*  position: fixed;  */
	 left: 5.2%;
	 width:89.4%;
	 top:126px;
	  margin: 0 auto;
	  padding: 79px 0px 0px 97px;
	  width: 89.4%;
}



.packageInfo{
 width: 107px;  
 height:30px;
}
.logo{
 width: 100px; 
}
.chaName{
 width: 500px;  
}
.hd{
  width: 66px;  
}
.name{
	width: 415px;
}

.searchable{
	
}



@media(max-width:1300px){
	
	
	#headerAlign span{
		font-size: 15px;
	}
	
	

}



@media(max-width:1024px){

	
	#headerAlign span{
		font-size: 15px;
	}
	
	

}

@media(max-width:980px){
	
	
	
	#headerAlign span{
		font-size: 15px;
	}

}


</style>

<script src="<c:url value='/script/html_save.js'/>"></script>
<script src="<c:url value='/js/jquery.js'/>"></script>
<script type="text/javascript">


jQuery(window).load(function (){
  try{
	  	var providerDataDiv = '${providerName}';
		var statusMsgString = '${statusMsg}';
		var contextPath = $("#contextPath").val();
		if(statusMsgString == "" || statusMsgString.length == 0)
		{
			
		    var providerNameDiv = $("<div></div>").addClass("providerName");
		    var img_iconSpan = $("<span></span>").addClass("img_icon");
		    img_iconSpan.append(providerDataDiv+' Lineup may not be exact per market.');
		    providerNameDiv.append(img_iconSpan);
		    providerNameDiv.append('<input id="searchChannel" placeholder="Search (eg: hbo,cnn,espn)" name="serachChannels"></input>');
			$("#headerAlign").html(providerNameDiv);
			var divData = "";
			divData+providerNameDiv;
			var tableData = $("<table></table>").attr("align","center");//"<table align='center'>";
			
			var packagesObj = ${packages};
			
			if(packagesObj.length > 0){
				var channelsObj = ${channels};
				var thead = $("<thead></thead>");
				var tbody = $("<tbody></tbody>").attr("class","searchable");
				var theadTr = $("<tr></tr>");
				var logo = $("<th></th>").addClass("logo1").text("");
				var hd = $("<th></th>").addClass("hd1").text("HD");
				var chaName = $("<th></th>").addClass("chaName1").text("Channel Name");
				
				//append stati headers
				theadTr.append(logo);
				theadTr.append(chaName);
				theadTr.append(hd);
				thead.append(theadTr);
				
				
				for(var i=0;i<channelsObj.length;i++){
					var defination = "N";
					if(channelsObj[i].definition!="null" && channelsObj[i].definition.toUpperCase().indexOf("HD")> -1 )
					{
						defination = "Y";
					}
					
					var tbodyTr = $("<tr></tr>");
					var tbodyTrTd = $("<td></td>");
					var tbodyTrTdName = $("<td></td>");
					var tbodyTrTddefination = $("<td></td>");
					tbodyTrTd.append($("<img></img>").attr("src",channelsObj[i].logoURL));
					tbodyTrTdName.append(channelsObj[i].name);
					tbodyTrTddefination.append(defination);
					tbodyTr.append(tbodyTrTd);
					tbodyTr.append(tbodyTrTdName);
					tbodyTr.append(tbodyTrTddefination);
				  	//tableRowData = tableRowData+"<tr><td class='logo'><img src='"+channelsObj[i].logoURL+"'></img></td><td class = 'name'>"+channelsObj[i].name+"</td><td class='hd'>"+defination+"</td>";
				  	
				  	packagesObj.sort(sortByPcakcageOrderCount);
				  	for(var j=0;packagesObj.length>j;j++)
					{
				  		if(i == 0)
					  	{
							//tableHeaderData = tableHeaderData+"<th class='packageInfo'>"+packagesObj[j].packName+"</th>";
							theadTr.append($("<th></th>").addClass("packageInfo1").text(packagesObj[j].packName));
							thead.append(theadTr);
						}
						
				  		if(isChannelAvailableInPackageChannelRef(channelsObj[i].id,packagesObj[j].channelRefs))
						{
							<%-- tableRowData = tableRowData+"<td class='packageInfo'><img src='<%=request.getContextPath()%>/images/img/tickMark.png' style='height:30px;'></td>"; --%>
							
							var path = contextPath+"/images/img/tickMark.png";
							tbodyTr.append($("<td></td>").addClass("packageInfo1").append($("<img></img>").attr({"src":path,"style":"height:30px;"})));
							tbody.append(tbodyTr);
						}
						else
						{
							//tableRowData = tableRowData+"<td></td>";
							tbodyTr.append($("<td></td>"));
							tbody.append(tbodyTr);
						};
					}
				  	tableData.append(thead);
					tableData.append(tbody);
				}
				
				
				$("#channelLineupDataDviId").append(tableData);
				
				$('#searchChannel').keyup(function () {
		            var rex = new RegExp($(this).val(), 'i');
		            $('.searchable tr').hide();
		            $('.searchable tr').filter(function () {
		                return rex.test($(this).text());
		            }).show();
		            if($("#channelLineUpTable tbody tr").length > 12){
						$("table tbody").css("height","560px !important");
					}else{
						$("table tbody").css("height","560px!important");
					}
		           /* headerPositionFixed(); */
		        }); 
				
				 $( window ).resize(function() {
					 /* headerPositionFixed(); */
				});  
			}
		}
		else
		{
			$("#statusMsg").html("Channel Lineup cannot be accessed right now");
			$("#headerAlign").hide();
		}
		/* headerPositionFixed(); */
		if($('#channelLineUpDataDviId table thead tr').height() > 70){
			$('thead').before().css("line-height",".9em");
		}
		
  }catch(err){
	alert(err);
  }
  
  
  
  $('table').on('scroll', function () {
	    $("table > *").width($("table").width() + $("table").scrollLeft());
	});
  
  
});

	function sortByPcakcageOrderCount(a,b) 
	{
		return ( parseInt(a.count) < parseInt(b.count) ? -1 : ( parseInt(a.count) > parseInt(b.count) ? 1 : 0 ) );
	}

	function escapeSpecialCharacters(id){
		return id.replace(/([ #;&,.+*~\':"!^$[\]()=>|\/])/g,'\\$1');
	}

  function isChannelAvailableInPackageChannelRef(channelId,packageChannelRefsList){
    var isFound = false;
	for(var i=0;i<packageChannelRefsList.length;i++){
	  if(packageChannelRefsList[i]==channelId){
		 isFound = true;
		  break;
	  }
	}
	  return isFound;
  }		
  function headerPositionFixed(){
	  $("tbody tr:visible:first td").each(function(index){
  		    var width = $(this).css("width");
	  		$("#channelLineupDataDviId table thead tr:first-child th").each(function(index1){
			    if(index == index1){
				      $(this).css("width",width);
				      return false;
			    }
	  		});
	 });
 };
 

 

 
 
</script>
</head>
<body>
<div class="boxShadowBlock" id="channelLineUps">
	<div id="headerAlign">
	</div>
</div>
	
	<div id="channelLineupDataDviId" align="center"></div>

<input id="contextPath" type="hidden" value="<%=request.getContextPath()%>">
<span id="statusMsg"></span>
</body>
</html>