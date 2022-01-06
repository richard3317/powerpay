<#include "/common/edit_macro.ftl" />
<#assign label = "分润打款项目编辑">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="agentProfitWithdraw">
			$("#agentCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
			});
			$("#mchntCd").validatebox({
				required: true,
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[30]'
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
			
			$("#comment").validatebox({
				novalidate: true,
				delay: 1000,
				validType: 'maxLength[256]'
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/agentProfitWithdraw/edit/submit.do">
			<input type="hidden" name="id" value="${entity.id}" />
			<input type="hidden" name="withdrawType" value="${entity.withdrawType}" />


			<table class="edit_tbl" cellpadding="0" cellspacing="0">
			
				<tr>    <td class="label">结算日期：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="settleDate" name="settleDate" value="${entity.settleDate!''}" />
				        <span class="inputDesc">结算日期</span>
				    </td>
				</tr>
				<tr>    <td class="label">代理商：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="agentCd" name="agentCd" value="${entity.agentCd!''}" />
				        <span class="inputDesc">${entity.agentDesc!''}</span>
				    </td>
				    <#--  
					<td class="content">
						<select class="easyui-validatebox"
							id="agentCd" name="agentCd" value="${entity.agentCd!''}"
							style="width: 300px; height: 27px; ">
							<option value="">--请选择--</option>
							<#if agentsList??>
								<#list agentsList as agent>
									<#if (entity.agentCd??) && (agent.agentCd==entity.agentCd)>
										<option value="${agent.agentCd}" selected="selected">${agent.agentCnNm}</option>
									<#else>	
										<option value="${agent.agentCd}">${agent.agentCnNm}</option>
									</#if>	
								</#list>
							</#if>
						</select>
						<span class="inputDesc">请选择代理商</span>
					</td>
				    -->
				</tr>
				<tr>    <td class="label">打款商户号：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="mchntCd" name="mchntCd" value="${entity.mchntCd!''}" />
				        <span class="inputDesc">${entity.mchntDesc!''}</span>
				    </td>
				</tr>
				<tr>    <td class="label">打款银行帐号：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="accountNo" name="accountNo" value="${entity.accountNo!''}" />
				        <span class="inputDesc"></span>
				    </td>
				</tr>
				<tr>    <td class="label">打款帐号户名：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="accountName" name="accountName" value="${entity.accountName!''}" />
				        <span class="inputDesc"></span>
				    </td>
				</tr>
				<tr>    <td class="label">打款行联号：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="accountBankCode" name="accountBankCode" value="${entity.accountBankCode!''}" />
				        <span class="inputDesc">${entity.accountBankName!''}</span>
				    </td>
				</tr>
				<tr>    <td class="label">打款順序：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="seq" name="seq" value="${entity.seq!''}" />
				        <span class="inputDesc"></span>
				    </td>
				</tr>
				
				<tr>    <td class="label">當日应付总额：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="apAmtTotalDesc" name="apAmtTotalDesc" value="${entity.apAmtTotalDesc!''}" />
				        <span class="inputDesc">元</span>
				    </td>
				</tr>
				
				<tr>    <td class="label">应打款金额：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" readonly="readonly"
				            id="apAmtDesc" name="apAmtDesc" value="${entity.apAmtDesc!''}" />
				        <span class="inputDesc">元</span>
				    </td>
				</tr>
				
				<tr>    <td class="label">实际打款金额：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="100" 
				            id="txnAmtDesc" name="txnAmtDesc" value="${entity.txnAmtDesc!''}" />
				        <span class="inputDesc">元</span>
				    </td>
				</tr>
				
				<tr>    <td class="label">打款操作：</td>
				    <td class="content">
				        <select id="state" name="state" value="${(entity.state)!''}" >
								<@enumOpts enumNm="TxnEnums.CommonSwitch" showEmptyOpt="true" showCode="false" 
								  emptyOptDesc="--请选择--" selected="${(entity.state)!''}"/>
							</select>
				        <span class="inputDesc">关闭/开启打款操作</span>
				    </td>
				</tr>
				
				<tr>    <td class="label">备注：</td>
				    <td class="content">
				        <input class="easyui-validatebox" type="text" maxlength="200" 
				            id="comment" name="comment" value="${entity.comment!''}" />
				        <span class="inputDesc"></span>
				    </td>
				</tr>

			</table>
		</@editDiv>
	</body>
</html>