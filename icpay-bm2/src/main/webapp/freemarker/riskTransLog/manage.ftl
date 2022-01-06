<#include "/common/manage_macro.ftl" />
<#assign label="风险交易信息" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="riskTransLog" qryFuncCode="7000040001" qryFlg=false
			detailFlg=true detailFuncCode="7000040002" detailUrl='"detail.do?transSeqId=" + sel.transSeqId' detailDivWidth=800 detailDivHeight=550>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/riskTransLog/qry.do" qryFuncCode="7000040001">
			<table class="qry_tbl">
				<tr>
					<td>交易流水号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="transSeqIdInpt" maxLength="32"
							name="_QRY_transSeqId" value="">
					</td>
					<td>风险规则ID：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="ruleIdInpt" name="_QRY_ruleId" value="">
					</td>
					<td>处理结果：</td>
					<td>
						<select id="resultSel" name="_QRY_result" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.Result' showCode='true' showEmptyOpt='true' />
						</select>
					</td>
				</tr>
				<tr>
					<td>交易日期：</td>
					<td colspan="3">
						<input id="startDt" name="_QRY_startExtTransDt" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'endDt\')}', readOnly:true})" class="Wdate" />
						至
						<input id="endDt" name="_QRY_endExtTransDt" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'startDt\')}', readOnly:true})" class="Wdate" />
					</td>
					<td>交易类型：</td>
					<td>
						<select id="intTransCdSel" name="_QRY_intTransCd">
							<@enumOpts enumNm="TxnEnums.TxnType" showCode="true" showEmptyOpt="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td>订单号：</td>
					<td colspan="5">
						<input class="easyui-validatebox" type="text"
							id="orderIdInpt" name="_QRY_orderId" value="">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv detailFlg=true detailFuncCode="7000040002">
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>
	