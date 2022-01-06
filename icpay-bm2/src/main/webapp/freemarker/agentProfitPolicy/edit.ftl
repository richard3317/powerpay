<#include "/common/edit_macro.ftl" />
<#assign label = "代理商银行帐户編輯">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="agentProfitPolicy">
			$("#agentCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
			});
			$("#intTransCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[4]'
			});
			$("#mchntCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[40]'
			});
			$("#chnlId").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[2]'
			});
			$("#chnlMchntCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[40]'
			});
			$("#comment").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/agentProfitPolicy/edit/submit.do">
			<#-- <input type="hidden" name="agentCd" value="${entity.agentCd}" /> -->
			<input type="hidden" name="intTransCd" value="${entity.intTransCd}" />
			<input type="hidden" name="chnlId" value="${entity.chnlId}" />

			<table class="edit_tbl" cellpadding="0" cellspacing="0">
			
				<tr>
					<td class="label">代理商代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30" readonly="readonly"
							id="agentCd" name="agentCd" value="${entity.agentCd!''}" />
						<span class="inputDesc">代理商(或中人)编号</span>
					</td>
				</tr>
				<tr>
					<td class="label">交易類型：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30" readonly="readonly"
							id="intTransCdDesc" name="intTransCdDesc" value="${entity.intTransCdDesc!''}" />
						<span class="inputDesc">'*'表示全部</span>
					</td>
				</tr>
				<tr>
					<td class="label">前端商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15" readonly="readonly"
							id="mchntCd" name="mchntCd" value="${entity.mchntCd!''}" />
						<span class="inputDesc">'*'表示全部</span>
					</td>
				</tr>
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30" readonly="readonly"
							id="chnlIdDesc" name="chnlIdDesc" value="${entity.chnlIdDesc!''}" />
						<span class="inputDesc">'*'表示全部</span>
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40" readonly="readonly"
							id="chnlMchntCd" name="chnlMchntCd" value="${entity.chnlMchntCd!''}" />
						<span class="inputDesc">輸入'*'表示全部</span>
					</td>
				</tr>
				<tr>
					<td class="label">扣率：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="rate" name="rate" value="${entity.rate!''}" />
						<span class="inputDesc">代理商扣率, 小数点, 例如: 0.0035</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 135px;">封顶手续费：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" id="maxFeeInpt" name="maxFeeDesc" value="${entity.maxFeeDesc!''}" />(元)
						<span class="inputDesc">封顶手续费, 單位：元, 自營代理商必填</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 135px;">保底手续费：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" id="minFeeInpt" name="minFeeDesc" value="${entity.minFeeDesc!''}" />(元)
						<span class="inputDesc">保底手续费, 單位：元, 自營代理商必填</span>
					</td>
				</tr>
				<tr>
					<td class="label">有效狀態：</td>
					<td class="content">
						<select class="easyui-validatebox" id="state" name="state" 
							value="${entity.state!''}" style="width: 200px;">
							<@enumOpts enumNm="TxnEnums.CommonValid" showEmptyOpt="true" showCode="true" selected="${entity.state!''}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="comment" name="comment" value="${entity.comment!''}" />
					</td>
				</tr>			

			</table>
		</@editDiv>
	</body>
</html>