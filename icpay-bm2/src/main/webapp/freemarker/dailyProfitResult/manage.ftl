<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="交易日报" base="dailyProfitResult" qryFuncCode="9200110001">
			<@authCheck funcCode="9200110002">
				$("#exportBtn").click(function() {
					var qryStr=$("#qryForm").serialize();
					var url =_ctx + "/dailyProfitResult/exportRpt.do?"+qryStr;
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="交易日报" style="padding: 10px;">
			<@qryDiv qryUrl="/dailyProfitResult/qry.do" qryFuncCode="9200110001">
				<table class="qry_tbl">
					<tr>
						<td>操作日期</td>
						<td colspan="3">
							<input id="settleDate" name="_QRY_settleDate" type="text"
								value="${(settleDate)!'${today}'}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd',readOnly:true })" class="Wdate" />
						</td>
						<td>站点：</td>
						<td>
							<select name="_QRY_siteId" id="siteId">
								<@enumOpts enumNm="CommonEnums.Site" showEmptyOpt="true" showCode="true" />
							</select>
						</td>
					</tr>
					

				
				</table>
			</@qryDiv>
			<@qryResultDiv>
				<@authCheck funcCode='9200110002'>
					<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导 出</a>
				</@authCheck>
			</@qryResultDiv>
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>