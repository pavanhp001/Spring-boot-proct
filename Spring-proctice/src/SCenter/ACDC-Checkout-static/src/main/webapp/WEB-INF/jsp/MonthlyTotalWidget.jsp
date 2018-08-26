<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<style type="text/css"><!--
#effectInstall,#effectMonthly {
	position: absolute;
	padding: 0.4em;
	background: #f2f2f2;
	border: 2px solid #cccccc;
	z-index: 1;
	width:300px;
}
</style>

<script type="text/javascript">

$(function() {
    $( "#effectInstall" ).hide();
    $( "#effectMonthly" ).hide();
   // run the currently selected effect
   function runEffect(linkId) {
     // get effect type from
      var selectedEffect = $( "#effectTypes" ).val();

      if(linkId == "monthlyTotalDetails"){
          $('#inStallTotalDetails > img').attr("src","${pageContext.request.contextPath}/style/images/fldst_readmore.png");
          $('#effectInstall').hide();
      }else if(linkId == "inStallTotalDetails"){
    	  $('#monthlyTotalDetails > img').attr("src","${pageContext.request.contextPath}/style/images/fldst_readmore.png");
          $('#effectMonthly').hide();
      }
  
     // most effect types need no options passed by default
     var options = {};
     var link = $( "#"+linkId);
     var dialog = link.next('div');

   dialog.css("top", link.position().top);
   dialog.css("left", link.position().left + link.width());
   dialog.css("position", "fixed"); 

   dialog.toggle( selectedEffect , options, 500 );

   if(dialog.is(':visible')){
	   $("#"+linkId+" > img").attr("src","${pageContext.request.contextPath}/style/images/fltst_readless.png");
   }else if(dialog.is(':hidden')){
	   $("#"+linkId+" > img").attr("src","${pageContext.request.contextPath}/style/images/fldst_readmore.png");
   }
   
   };

   $( "#monthlyTotalDetails,#inStallTotalDetails" ).click(function() {
     runEffect($(this).attr('id'));
     return false;
   });

	/*
		logic to hide the monthly price table and installation price table when 
		ctrl+alt+m and ctrl+alt+i is clicked respectively 
	*/
   $(document).keypress(function(event){
	   
	   if(event.altKey === true && event.ctrlKey === true && (event.charCode == 77 || event.charCode == 109)){
		   runEffect('monthlyTotalDetails');
	   }
	   if(event.altKey === true && event.ctrlKey === true && (event.charCode == 73 || event.charCode == 105)){
		   runEffect('inStallTotalDetails');
	   } 
	});
   
   $(".closeMonthly").click(function() {
	  $("#monthlyTotalDetails > img").attr("src","${pageContext.request.contextPath}/style/images/fldst_readmore.png");
      $( "#effectMonthly" ).hide();
   });

   var clickMonthlyVariable = true;
   var clickOneTimeVariable = true;   
   /*
   		logic to hide the monthly tab and installation tab when we click out side the tab
   */
	$(document).click(function(e) {
		$('#effectMonthly').click(function(){
			clickMonthlyVariable = true;
		});
		$('#effectInstall').click(function(){
			clickOneTimeVariable = true;
		});
		
		if($(this).attr('id') != "effectMonthly"){
			if($('#effectMonthly').is(':visible')){
				if(!clickMonthlyVariable){
					$("#monthlyTotalDetails > img").attr("src","${pageContext.request.contextPath}/style/images/fldst_readmore.png");
    	    		$("#effectMonthly" ).hide();
				}
			}
		}
		if($(this).attr('id') != "effectInstall"){
			if($('#effectInstall').is(':visible')){
				if(!clickOneTimeVariable){
					$("#inStallTotalDetails > img").attr("src","${pageContext.request.contextPath}/style/images/fldst_readmore.png");
		    		$( "#effectInstall" ).hide();
				}
			}
		}
		clickMonthlyVariable = false;
		clickOneTimeVariable = false;
	});
   $(".closeInstall").click(function() {
    	  $("#inStallTotalDetails > img").attr("src","${pageContext.request.contextPath}/style/images/fldst_readmore.png");
	      $( "#effectInstall" ).hide();
	   });
   
 });


</script>

<a href="#" id="monthlyTotalDetails"><img
	src="${pageContext.request.contextPath}/style/images/fldst_readmore.png"
	alt="^"></a>
<div id="effectMonthly" style="width: 40%; font-size: 13px; display:none; "><a href="#"
	class="closeMonthly" style="position: absolute; top: 0px; right: 0px;"><img src="${pageContext.request.contextPath}/style/images/close_button.png" /></a>
<table style="color: #58ACFA;font-size: 13px;font-weight:bold;"  width="95%;" id="monthlyCostTableHeading">
	 
	<tr>
		<td style="width: 50%;" align="left" >Description</td>
		<td style="width: 25%;" align="center">Selection</td>
		<td style="width: 25%;" align="right">Price</td>
	</tr>
</table>
<hr />
<div style="max-height: 200px;overflow-y : auto;">
	<table width="95%;" style="font-weight:bold;font-size: 13px;" cellspacing="0px;" id="monthlyCostTableData">
		<tr>
			<td style="width: 50%;" align="left">Base Monthly Price</td>
			<td style="width: 25%;" align="center">-</td>
			<td id="monthlyPriceDisplay" style="width: 25%;" align="right">&#36;<fmt:formatNumber value="${productMonthlyPrice}" maxFractionDigits="2" pattern="0.00" /></td>
		</tr>
	</table>
</div>
</div>