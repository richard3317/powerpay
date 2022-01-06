<#include "/common/edit_macro.ftl" />
<#assign label = "代理商基本信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="agent" editSuccMsg="代理商基本信息修改任务已提交，请等待审核">
			$("#agentCnNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[100]'
			});
			$("#agentEnNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['enNm', 'maxLength[100]']
			});
			$("#agentCnAbbrInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[40]'
			});
			$("#agentEnAbbrInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['enNm', 'maxLength[40]']
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/agent/edit/submit.do">
			<input type="hidden" name="agentCd" value="${entity.agentCd}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">代理商代码：</td>
					<td class="content">
						${entity.agentCd}
					</td>
				</tr>
				<tr>
					<td class="label">中文名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="agentCnNmInpt" name="agentCnNm" value="${entity.agentCnNm!''}" />
					</td>
				</tr>
				<tr>
					<td class="label">英文名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="agentEnNmInpt" name="agentEnNm" value="${entity.agentEnNm!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">中文简称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="agentCnAbbrInpt" name="agentCnAbbr" value="${entity.agentCnAbbr!''}" />
					</td>
				</tr>
				<tr>
					<td class="label">英文简称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="agentEnAbbrInpt" name="agentEnAbbr" value="${entity.agentEnAbbr!''}" />
					</td>
				</tr>
				
				<tr>
					<td class="label">代理商类型：</td>
					<td class="content">
						<select class="easyui-validatebox" name="agentType" id="agentTypeSel" value="${entity.agentType!'0'}" required="true">
							<@enumOpts enumNm="ProfitEnums.AgentType" showEmptyOpt="false" showCode="true" selected="${entity.agentType!'0'}" />
						</select>						
					</td>
				</tr>
				
				<tr>
					<td class="label">代理商状态：</td>
					<td class="content">
						<select id="agentStSel" name="agentSt" data-options="editable:false">
							<@enumOpts enumNm='CommonEnums.AgentSt' showCode='true' 
								showEmptyOpt="false" selected="${entity.agentSt!''}" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="label">分润状态：</td>
					<td class="content">
						<select id="depositSel" name="deposit" data-options="editable:false">
							<@enumOpts enumNm='TxnEnums.CommonYesNo' showCode='true' 
								showEmptyOpt="false" selected="${entity.deposit!''}" />
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="label">代理商地区：</td>
					<td class="content">
						${entity.areaCdDesc!''}
					</td>
				</tr>
				<tr>
					<td class="label">详细地址：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="agentAddrInpt" name="agentAddr" value="${entity.agentAddr!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">联系人1：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPerson1Inpt" name="contactPerson1" value="${entity.contactPerson1!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">联系电话1：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPhone1Inpt" name="contactPhone1" value="${entity.contactPhone1!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">电子邮箱1：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="contactMail1Inpt" name="contactMail1" value="${entity.contactMail1!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">联系人2：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPerson2Inpt" name="contactPerson2" value="${entity.contactPerson2!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">联系电话2：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPhone2Inpt" name="contactPhone2" value="${entity.contactPhone2!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">电子邮箱2：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="contactMail2Inpt" name="contactMail2" value="${entity.contactMail2!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">邮编：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="6"
							id="zipCdInpt" name="zipCd" value="${entity.zipCd!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">传真：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="faxInpt" name="fax" value="${entity.fax!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="commentInpt" name="comment" value="${entity.comment!''}"/>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>