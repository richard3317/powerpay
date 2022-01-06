<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<title>代付路由管理</title>
		<@head label="路由信息" base="routDaifu" qryFuncCode="2000030001"
			addFlg=true addFuncCode="2000030002"
			editFlg=true editFuncCode="2000030004" editUrl='"edit.do?mchntCd=" + sel.mchntCd + "&intTransCd=" + sel.intTransCd + "&chnlId=" + sel.chnlId + "&chnlMchntCd=" + sel.chnlMchntCd'
			deleteFlg=true deleteFuncCode="2000030006" deleteUrl='"delete.do?mchntCd=" + sel.mchntCd + "&intTransCd=" + sel.intTransCd + "&chnlId=" + sel.chnlId + "&chnlMchntCd=" + sel.chnlMchntCd' deleteSuccMsg="删除任务已提交，请等待审核">
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/routDaifu/qry.do" showBtn=true qryFuncCode="2000030001">
			<table class="qry_tbl">
				<tr>
					<td>前端商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="${(qryParamMap.mchntCd)!''}">
					</td>
					<td>渠道：</td>
					<td>
						<select id="" name="_QRY_chnlId" data-options="editable:false">
							<#-- <@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='true'  
								selected="${(qryParamMap.chnlId)!''}" />-->
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='true' selected="${(qryParamMap.chnlId)!''}" />
						</select>
					</td>
					<td>状态：</td>
					<td>
						<#--<input class="easyui-validatebox" type="text"
							id="" maxlength="128"
							name="_QRY_status" value="${(qryParamMap.status)!''">-->
						<select id="" name="_QRY_status" data-options="editable:false">
							<option value="">--请选择--</option>
							<option value="0">0-无效</option>
							<option value="1">1-有效</option>
						</select>
					</td>
					<td>渠道商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="" maxlength="128"
							name="_QRY_chnlMchntCd" value="${(qryParamMap.chnlMchntCd)!''}">
					</td>
					<input type="hidden" id="" name="_QRY_ifPay" value="0" />
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv addFlg=true addFuncCode="2000030002"
			editFlg=true editFuncCode="2000030004"
			deleteFlg=true deleteFuncCode="2000030006">
		</@qryResultDiv>
	</body>
<html>
	