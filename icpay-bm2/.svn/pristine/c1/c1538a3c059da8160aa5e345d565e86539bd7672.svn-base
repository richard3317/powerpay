<#include "/common/edit_macro.ftl" />
<#assign label = "代理商银行帐户編輯">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="chnlBankMapping">
			<#--  
			$("#bankNum").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[8]'
			});
			$("#intTransCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[4]']
			});
			$("#chnlId").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[2]']
			});
			-->
			$("#chnlBankNum").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[16]'
			});
			<#--  
			$("#chnlBankName").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
			});
			-->
			$("#state").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[2]'
			});
			$("#sortSeq").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[4]']
			});
			$("#comment").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/chnlBankMapping/edit/submit.do">
			<input type="hidden" name="intTransCd" value="${entity.intTransCd!''}" />
			<input type="hidden" name="bankNum" value="${entity.bankNum!''}" />
			<input type="hidden" name="chnlId" value="${entity.chnlId!''}" />
			<input type="hidden" maxlength="15" name="currCd" value="${entity.currCd}" />

			<table class="edit_tbl" cellpadding="0" cellspacing="0">
			
				<tr>
					<td class="label">交易類型：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="intTransCdDesc" name="intTransCdDesc" value="${entity.intTransCdDesc!''}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label">币别：</td>
					<td class="content">
						${entity.currCdDesc}
					</td>
				</tr>
				<tr>
					<td class="label">银行编号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="bankName" name="bankName" value="${entity.bankNum!''}${entity.bankName!''}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="chnlDesc" name="chnlDesc" value="${entity.chnlDesc!''}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道银行编号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" id="chnlBankNum" maxLength="12"  style="width: 200px;"
							name="chnlBankNum" value="${entity.chnlBankNum!''}">
					</td>
				</tr>
				<tr>
					<td class="label">渠道银行名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" id="chnlBankName" maxLength="30" style="width: 200px;"
							name="chnlBankName" value="${entity.chnlBankName!''}">
					</td>
				</tr>
				<tr>
					<td class="label">有效狀態：</td>
					<td class="content">
						<select class="easyui-validatebox" id="state" name="state" style="width: 100px;">
							<@enumOpts enumNm="TxnEnums.CommonValid" showEmptyOpt="true" showCode="true" selected="${(entity.state)!''}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">显示顺序：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="sortSeq" name="sortSeq" value="${entity.sortSeq!''}" />
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="comment" name="comment"  value="${entity.comment!''}"  />
					</td>
				</tr>

			</table>
		</@editDiv>
	</body>
</html>