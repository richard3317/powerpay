<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户清算信息" base="mchntSettleInfo" qryFuncCode="1000050001"
			addFlg=true addFuncCode="1000050001"
			detailFlg=true detailUrl='"detail.do?mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd' detailDivWidth=800 detailDivHeight=550 detailFuncCode="1000010002"
			editFlg=true editUrl='"edit.do?mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd' editFuncCode="1000050005" deleteFuncCode="1000050007"
			deleteFlg=true deleteUrl='"delete.do?mchntCd=" + sel.mchntCd + "&currCd=" + sel.currCd' deleteSuccMsg="商户清算信息已删除">
			<@authCheck funcCode='1000050008'>
				$("#algorithmMngBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo(_ctx + "/mchntSettleInfo/algorithm/manage.do?mchntCd=" + sel.mchntCd);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/mchntSettleInfo/qry.do" qryFuncCode="1000050001">
			<table class="qry_tbl">
				<tr>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>清算周期：</td>
					<td>
						<select id="settlePeriod" name="_QRY_settlePeriod" value="${(qryParamMap.settlePeriod)!''}" >
							<@enumOpts enumNm="SettleEnums.SettlePeriod" showEmptyOpt="true" showCode="false" emptyOptDesc="--请选择--" selected="${(qryParamMap.settlePeriod)!''}"/>
						</select>
					</td>
					<td>D0结转：</td>
					<td>
						<select id="balanceTransfer" name="_QRY_balanceTransfer" value="${(qryParamMap.balanceTransfer)!''}">
							<@enumOpts enumNm="TxnEnums.CommonYesNo" showEmptyOpt="true"/>
						</select>
					</td>
					<td>T1返还：</td>
					<td>
						<select id="balanceTransferT1" name="_QRY_balanceTransferT1" value="${(qryParamMap.balanceTransferT1)!''}" >
							<@enumOpts enumNm="TxnEnums.CommonYesNo" showEmptyOpt="true"/>
						</select>
					</td>
					<td>币别:</td>
					<td>
						<select id="currCd" name="_QRY_currCd">
							<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="false" showCode="true" selected="${(qryParamMap.currCd)!'156'}"/>
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv  
			addFlg=true addFuncCode="1000050003"
			detailFlg=true detailFuncCode="1000050002"
			editFlg=true editFuncCode="1000050005"
			 deleteFlg=true deleteFuncCode="1000050007">
			 <@authCheck funcCode='1000050008'>
			 	<a id="algorithmMngBtn" href="javascript:void(0)" class="easyui-linkbutton">计费方式管理</a>
			 </@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>