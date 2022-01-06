<#include "/common/add_macro.ftl" />
<#assign label = "分润打款银行新增">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="sysSettleProfitBank">
			$("#agentCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
			});
			$("#accountType").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[2]'
			});
			$("#accountBankCode").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[8]']
			});
			$("#accountNo").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[30]']
			});
			$("#accountName").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[64]'
			});
			$("#priority").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['number','maxLength[6]']
			});
			$("#maxCardAmtDesc").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: ['amount']
			});
			$("#comment").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/sysSettleProfitBank/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">银行联行号：</td>
					<td class="content">
						<select class="easyui-validatebox"
							id="accountBankCode" name="accountBankCode" 
							style="width: 300px; height: 27px; ">
							<option value="">--请选择--</option>
							<#list bankCodeList as bank>
								<option value="${bank.bankNum}">${bank.bankName}</option>
							</#list>
						</select>
						<span class="inputDesc">请选择银行联行号</span>
					</td>
				</tr>
				<tr>
					<td class="label">银行账号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="accountNo" name="accountNo" />
						<span class="inputDesc">必填，请输入打款之银行账号</span>
					</td>
				</tr>
				<tr>
					<td class="label">帐号户名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="accountName" name="accountName" />
						<span class="inputDesc">必填，请输入打款之银行帐户名称</span>
					</td>
				</tr>
				<tr>
					<td class="label">预设打款金额(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="defaultAmtDesc" name="defaultAmtDesc" value=""/>
						<span class="inputDesc">必填，单位：元，预设打款金额</span>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="comment" name="comment" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>