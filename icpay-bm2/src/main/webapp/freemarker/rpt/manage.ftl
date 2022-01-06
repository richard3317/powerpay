<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="批量报表" base="rpt" qryFuncCode="9200010001">
			<@authCheck funcCode="9200010002">
				$("#downBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo("${ctx}/rpt/downRpt.do?seqId=" + sel.seqId);
					}
				});
			</@authCheck>
			<@authCheck funcCode="9200010003">
				$("#transProfitBtn").click(function() {
					$.jumpTo("${ctx}/rpt/manageProfitRpt.do");
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/rpt/qry.do" qryFuncCode="9200010001">
			<table class="qry_tbl">
				<tr>
					<td>报表日期：</td>
					<td>
						<input name="_QRY_rptDt" type="text" value="" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
					<td>报表类型：</td>
					<td>
						<select name="_QRY_rptTp">
							<@enumOpts enumNm='CommonEnums.RptTp' showEmptyOpt='true' />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv>
			 <@authCheck funcCode='9200010002'>
			 	<a id="downBtn" href="#" class="easyui-linkbutton">下载</a>
			 </@authCheck>
			 
			 <@authCheck funcCode='9200010003'>
			 	<a id="transProfitBtn" href="#" class="easyui-linkbutton">每日财报</a>
			 </@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>