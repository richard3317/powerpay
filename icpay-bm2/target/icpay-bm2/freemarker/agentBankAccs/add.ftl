<#include "/common/add_macro.ftl" />
<#assign label = "代理商银行帐户新增">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="agentBankAccs">
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
		<@addDiv label="${label}" addUrl="/agentBankAccs/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">代理商代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="agentCd" name="agentCd" value="${agentCd!''}" />
					</td>
					<span class="inputDesc">代理商(或中人)编号</span>
				</tr>
				<tr>
					<td class="label">帐户用途：</td>
					<td class="content">
						<select class="easyui-validatebox" id="accountType" name="accountType" style="width: 200px;">
							<@enumOpts enumNm="ProfitEnums.AgentAccUsage" showEmptyOpt="true" showCode="true" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">开户银行：</td>
					<td class="content">
						<select class="easyui-validatebox"
							id="accountBankCode" name="accountBankCode" 
							style="width: 300px; height: 27px; ">
							<option value="">--请选择--</option>
							<#list bankCodeList as bank>
								<option value="${bank.bankNum}">${bank.bankName}</option>
							</#list>
						</select>
						<span class="inputDesc">请选择代理商(或中人)开户银行</span>
					</td>
				</tr>
				<tr>
					<td class="label">银行账号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="accountNo" name="accountNo" />
						<span class="inputDesc">必填，请输入代理商(或中人)打款之银行账号</span>
					</td>
				</tr>
				<tr>
					<td class="label">银行账户名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="accountName" name="accountName" />
						<span class="inputDesc">必填，请输入代理商(或中人)打款之银行帐户名称</span>
					</td>
				</tr>
				<tr>
					<td class="label">优先等级：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="8"
							id="priority" name="priority" value="50"/>
						<span class="inputDesc">选填，优先等级: 数字越小越优先</span>
					</td>
				</tr>
				<tr>
					<td class="label">单笔上限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="maxCardAmtDesc" name="maxCardAmtDesc" value="99996"/>
						<span class="inputDesc">必填，单位：元，单笔最高打款金额</span>
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