<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="清算分润结果" base="settleProfitResult" qryFuncCode="8000110001"
			detailFlg=true detailFuncCode="8000110002" detailUrl='"detail.do?transSeqId=" + sel.transSeqId'>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/settleProfitResult/qry.do" qryFuncCode="8000110001">
			<table class="qry_tbl">
				<tr>
					<td>代理商代码：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="15"
							name="_QRY_agentCd" value="${(qryParamMap.agentCd)!''}">
					</td>
					<td>商户代码：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>订单号：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="64"
							name="_QRY_orderId" value="${(qryParamMap.orderId)!''}">
					</td>
				</tr>
				<tr>
					<td>终端号：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="32"
							name="_QRY_termSn" value="${(qryParamMap.termSn)!''}">
					</td>
					<td>创建日期：</td>
					<td>
						<input name="_QRY_crtDt" type="text"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					<td>交易类型：</td>
					<td>
						<select id="intTransCdSel" name="_QRY_intTransCd">
							<@enumOpts enumNm="TxnEnums.TxnType" showCode="true" showEmptyOpt="true" />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv 
			detailFlg=true detailFuncCode="8000110002">
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>
	