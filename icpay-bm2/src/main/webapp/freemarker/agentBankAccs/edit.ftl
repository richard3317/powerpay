<#include "/common/edit_macro.ftl" />
<#assign label = "代理商银行帐户編輯">
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
		<@editDiv label="${label}" editUrl="/agentBankAccs/edit/submit.do">
			<#-- <input type="hidden" name="agentCd" value="${entity.agentCd}" /> -->
			<input type="hidden" name="accountType" value="${entity.accountType!''}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
			
				<tr>
					<td class="label">代理商代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="agentCd" name="agentCd" value="${entity.agentCd!''}" readonly="readonly" />
					</td>
					<span class="inputDesc">代理商(或中人)编号</span>
				</tr>
				<#--  
				<tr>
					<td class="label">帐户用途：</td>
					<td class="content">
						<select class="easyui-validatebox" id="accountType" name="accountType"  data-options="editable:false" 
							readonly="readonly" value="${entity.accountType!''}" style="width: 200px;">
							<@enumOpts enumNm="ProfitEnums.AgentAccUsage" showEmptyOpt="true" showCode="true" selected="${entity.accountType!''}" />
						</select>
					</td>
				</tr>
				-->
				<tr>
					<td class="label">帐户用途：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="accountTypeDesc" name="accountTypeDesc" value="${entity.accountTypeDesc!''}" readonly="readonly" />
					</td>
				</tr>
				
				<tr>
					<td class="label">银行账号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30" readonly="readonly"
							id="accountNo" name="accountNo" value="${entity.accountNo!''}" />
						<span class="inputDesc">必填，请输入代理商(或中人)打款之银行账号</span>
					</td>
				</tr>
				<tr>
					<td class="label">银行账户名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="accountName" name="accountName" value="${entity.accountName!''}" />
						<span class="inputDesc">必填，请输入代理商(或中人)打款之银行帐户名称</span>
					</td>
				</tr>
				<#--  
				<tr>
					<td class="label">开户银行：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="30"
							id="accountBankCode" name="accountBankCode" value="${entity.accountBankCode!''}" readonly="readonly" />
						<span class="inputDesc">${entity.accountBankName!''}</span>	
					</td>
				</tr>
				-->
				
				<tr>
					<td class="label">开户银行：</td>
					<td class="content">
						<select class="easyui-validatebox"
							id="accountBankCode" name="accountBankCode" value="${entity.accountBankCode!''}"
							style="width: 300px; height: 27px; ">
							<option value="">--请选择--</option>
							<#if bankCodeList??>
								<#list bankCodeList as bank>
									<#if (entity.accountBankCode??) && (bank.bankNum==entity.accountBankCode)>
										<option value="${bank.bankNum}" selected="selected">${bank.bankName}</option>
									<#else>	
										<option value="${bank.bankNum}">${bank.bankName}</option>
									</#if>	
								</#list>
							</#if>
						</select>
						<span class="inputDesc">请选择代理商(或中人)开户银行</span>
					</td>
				</tr>
				
				<tr>
					<td class="label">优先等级：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="8"
							id="priority" name="priority" value="${entity.priority!'50'}"/>
						<span class="inputDesc">选填，优先等级: 数字越小越优先</span>
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
					<td class="label">单笔上限(元)：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="12"
							id="maxCardAmtDesc" name="maxCardAmtDesc" value="${entity.maxCardAmtDesc!''}"/>
						<span class="inputDesc">必填，单位：元，单笔最高打款金额</span>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="comment" name="comment" value="${entity.comment!''}"/>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>