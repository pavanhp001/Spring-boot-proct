<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style type="text/css">
#effectInstall,#effectMonthly {
	position: absolute;
	padding: 0.4em;
	background: #f2f2f2;
	border: 2px solid #cccccc;
	z-index: 1;
}
</style>

<a href="#" id="inStallTotalDetails"><img
	src="${pageContext.request.contextPath}/style/images/fldst_readmore.png"
	alt="^"></a>
<div id="effectInstall" style="width: 40%;font-size: 13px; display:none; "><a href="#"
class="closeInstall" style="position: absolute; top: 0px; right: 0px;"><img src="${pageContext.request.contextPath}/style/images/close_button.png" /></a>

<table style="color: #58ACFA;font-size: 13px;font-weight:bold;" width="95%;"
	id="inStallCostTableHeading">
	<tr>
		<td style="width: 50%;" align="left">Description</td>
		<td style="width: 25%;" align="center">Selection</td>
		<td style="width: 25%;" align="right">Price</td>
	</tr>
</table>
<hr />
<div style="max-height: 200px;overflow-y : auto;">
	<table width="95%;" style="font-size: 13px;font-weight:bold;" cellspacing="0px;" id="inStallCostTableData">
		<tr>
			<td style="width: 50%;" align="left">Base Installation Price</td>
			<td style="width: 25%;" align="center">-</td>
			<td style="width: 25%;" align="right">&#36;<fmt:formatNumber value="${productInstallationPrice}" maxFractionDigits="2" pattern="0.00" /></td>
		</tr>
	</table>
</div>
</div>

