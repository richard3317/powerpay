<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="财报统计" base="dailyProfitResultChnlTrans" qryFuncCode="9200040001">
			<@authCheck funcCode="9200040002">
				$("#exportBtn").click(function() {
					var qryStr=$("#qryForm").serialize();
					var url =_ctx + "/dailyProfitResultChnlTrans/exportRpt.do?"+qryStr;
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="渠道交易环比" style="padding: 10px;">
			<@qryDiv qryUrl="/dailyProfitResultChnlTrans/qry.do" qryFuncCode="9200040001">
				<table class="qry_tbl">
					<tr>
						<td>渠道：</td>
						<td>
							<select id="transChnl" name="_QRY_transChnl" value="${(qryParamMap.transChnl)!''}">
								<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.transChnl)!''}" />
							</select>
						</td>
						<td>查询范围</td>
						<td>
							<input id="qryStartDate" name="_QRY_qryStartDate" type="text"
								value="${(startDate)!'${today}'}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd',readOnly:true })" class="Wdate" />至
						</td>
						<td>
							<input id="qryEndDate" name="_QRY_qryEndDate" type="text"
								value="${(endDate)!'${today}'}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd',readOnly:true })" class="Wdate" />
						</td>
					</tr>
				
				</table>
			</@qryDiv>
			<@qryResultDiv>
				<@authCheck funcCode='9200040002'>
					<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导 出</a>
				</@authCheck>
			</@qryResultDiv>
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>