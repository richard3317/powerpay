<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="财报统计" base="dailyProfitResultByChnl" qryFuncCode="9200040001">
			<@authCheck funcCode="9200040002">
				$("#exportBtn").click(function() {
					var qryStr=$("#qryForm").serialize();
					var url =_ctx + "/dailyProfitResultByChnl/exportRpt.do?"+qryStr;
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
			<@authCheck funcCode="9200040003">
				$("#exportWeekRptBtn").click(function() {
					var qryStr=$("#qryForm").serialize();
					var url =_ctx + "/dailyProfitResultByChnl/exportWeekRpt.do?"+qryStr;
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
			<@authCheck funcCode="9200040004">
				$("#exportHuanbiBtn").click(function() {
					var qryStr=$("#qryForm").serialize();
					var url =_ctx + "/dailyProfitResultByChnl/exportHuanbiRpt.do?"+qryStr;
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="财报统计" style="padding: 10px;">
			<@qryDiv qryUrl="/dailyProfitResultByChnl/qry.do" qryFuncCode="9200040001">
				<table class="qry_tbl">
					<tr>
						<td>操作日期</td>
						<td colspan="3">
							<input id="settleDate" name="_QRY_settleDate" type="text"
								value="${(settleDate)!'${today}'}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd',readOnly:true })" class="Wdate" />
						</td>
					</tr>
				
				</table>
			</@qryDiv>
			<@qryResultDiv>
				<@authCheck funcCode='9200040002'>
					<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导 出</a>
				</@authCheck>
				<@authCheck funcCode='9200040003'>
					<a id="exportWeekRptBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">周报表导出</a>
				</@authCheck>
				<@authCheck funcCode='9200040004'>
					<a id="exportHuanbiBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">商户环比报表导出</a>
				</@authCheck>
				
			</@qryResultDiv>
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>