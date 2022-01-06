<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><@head label="商户信息" base="mchntChart"
qryFuncCode="9200050001"> $("#agentBankAccsMngBtn").click(function() {
var sel = $("#qryResultTbl").datagrid("getSelected"); if (sel == null) {
alert("请选择一条记录"); } else { var mchntCd = sel.mchntCd ; $.jumpTo(_ctx +
"/mchntChart/chart.do?mchntCd="+ mchntCd+"&txnState="+sel.txnState+"&intTransCd="+sel.intTransCd); }
}); </@head>
</head>

<body style="padding: 5px;">
	<@qryDiv qryUrl="/mchntChart/qry.do" qryFuncCode="9200050001">
	<table class="qry_tbl">
		<tr>
			<td>商&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;号：</td>
			<td><input class="easyui-validatebox" type="text" id="mchntCd"
				maxLength="15" name="_QRY_mchntCd"
				value="${(qryParamMap.mchntCd)!''}"></td>
			<td>商户名称：</td>
			<td><input type="text" id="mchntCnNm" maxLength="15"
				name="_QRY_mchntCnNm" value="${(qryParamMap.mchntCnNm)!''}"></td>
			
		</tr>
		<tr>
			<td>交易低于平均：</td>
			<td><input class="easyui-validatebox" type="text" id="averageAt"
				maxLength="15" name="_QRY_averageAt"
				value="${(qryParamMap.averageAt)!''}">%</td>
			<td>当前小时交易量(交易金额)：</td>
			<td><input class="easyui-validatebox" type="text"
				id="transAtMin" name="_QRY_transAtMin"
				value="${(qryParamMap.transAtMin)!''}" style="width: 65px;">
				- <input class="easyui-validatebox" type="text" id="transAtMax"
				name="_QRY_transAtMax" value="${(qryParamMap.transAtMax)!''}"
				style="width: 65px;"></td>
		</tr>
		<tr>
			<td>交易日期：</td>
			<td colspan="3"><input id="startDate" name="_QRY_startDate"
				type="text" value=""
				onFocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'endDate\')}', readOnly:true})"
				class="Wdate" /> 至 <input id="endDate" name="_QRY_endDate"
				type="text" value="${(qryParamMap.endDate)!''}"
				onFocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'startDate\')}', readOnly:true})"
				class="Wdate" />
		</tr>
	</table>
	</@qryDiv> <@qryResultDiv
	>
	<div id="detailDiv">
		<a id="agentBankAccsMngBtn" href="javascript:void(0)"
			class="easyui-linkbutton">查看折线图</a>
	</div>
	</@qryResultDiv>


</body>
<html>