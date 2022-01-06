<#include "/common/add_macro.ftl" />
<#assign label = "代理商基本信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="agent" addSuccMsg="代理商新增任务已提交，请等待审核">
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
			
			var emptyOpt = $('<option value="">--请选择--</option>');
			$("#provSel").change(function() {
				var provCd = $(this).val();
				if (provCd == '') {
					$("#areaCdSel").html("").append(emptyOpt);
				} else {
					var url =  "${ctx}/loadCity.do?provCd=" + provCd;
					$.get(url, function(data) {
						$.processAjaxResp(data, function(r) {
							var lst = r.respData.cityLst;
							$("#areaCdSel").html("").append(emptyOpt);
							for (var i = 0; i < lst.length; i ++) {
								var regionCd = lst[i].regionCd;
								var regionNm = lst[i].regionCnNm;
								$("#areaCdSel").append('<option value="' + regionCd + '">' + regionCd + '-' + regionNm + '</option>');
							}
						});
					});
				}
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/agent/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">中文名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="agentCnNmInpt" name="agentCnNm" />
					</td>
				</tr>
				<tr>
					<td class="label">英文名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="agentEnNmInpt" name="agentEnNm" />
					</td>
				</tr>
				<tr>
					<td class="label">中文简称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="agentCnAbbrInpt" name="agentCnAbbr" />
					</td>
				</tr>
				<tr>
					<td class="label">英文简称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="agentEnAbbrInpt" name="agentEnAbbr" />
					</td>
				</tr>
				<tr>
					<td class="label">代理商类型：</td>
					<td class="content">
						<select class="easyui-validatebox" name="agentType" id="agentTypeSel" value="0" required="true">
							<@enumOpts enumNm="ProfitEnums.AgentType" showEmptyOpt="false" showCode="true" selected="0" />
						</select>						
					</td>
				</tr>
				<tr>
					<td class="label">省份：</td>
					<td class="content">
						<select class="easyui-validatebox" id="provSel" name="prov" style="width: 300px;" required="true">
							<option value="">--请选择--</option>
							<#list provMap?keys as k>
								<option value="${k}">${k}-${provMap['${k}']}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">城市：</td>
					<td class="content">
						<select class="easyui-validatebox" id="areaCdSel" name="areaCd" style="width: 300px;" required="true">
							<option value="">--请选择--</option>
						</select>
						<span class="inputDesc">必填，用于生成代理商代码，新增后无法修改</span>
					</td>
				</tr>
				<tr>
					<td class="label">详细地址：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="agentAddrInpt" name="agentAddr" />
					</td>
				</tr>
				<tr>
					<td class="label">联系人1：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPerson1Inpt" name="contactPerson1" />
					</td>
				</tr>
				<tr>
					<td class="label">联系电话1：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPhone1Inpt" name="contactPhone1" />
					</td>
				</tr>
				<tr>
					<td class="label">电子邮箱1：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="contactMail1Inpt" name="contactMail1" />
					</td>
				</tr>
				<tr>
					<td class="label">联系人2：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPerson2Inpt" name="contactPerson2" />
					</td>
				</tr>
				<tr>
					<td class="label">联系电话2：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="contactPhone2Inpt" name="contactPhone2" />
					</td>
				</tr>
				<tr>
					<td class="label">电子邮箱2：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="contactMail2Inpt" name="contactMail2" />
					</td>
				</tr>
				<tr>
					<td class="label">邮编：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="6"
							id="zipCdInpt" name="zipCd" />
					</td>
				</tr>
				<tr>
					<td class="label">传真：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="20"
							id="faxInpt" name="fax" />
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="commentInpt" name="comment" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>