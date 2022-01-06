<#include "/common/edit_macro.ftl" />
<#assign label="名单组配置信息" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="riskListGroup">
			$("#groupNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[254]'
			});
			$("#expiredTmStrInpt").validatebox({
				required: true,
				novalidate: true
			});
			$("#commentInpt").validatebox({
				novalidate: true,
				validType: 'cnMaxLength[254]'
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/riskListGroup/edit/submit.do">
			<input type="hidden" name="groupId" value="${entity.groupId}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">名单组名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="groupNmInpt" name="groupNm" value="${entity.groupNm}" />
					</td>
				</tr>
				<tr>
					<td class="label">名单组类型：</td>
					<td class="content">
						<select id="groupTpSel" name="groupTp" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.GroupTp' showCode='true' 
								showEmptyOpt='false' selected="${entity.groupTp}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">名单类型：</td>
					<td class="content">
						<select id="listTpSel" name="listTp" data-options="editable:false">
							<@enumOpts enumNm='RiskEnums.ListTp' showCode='true' 
								showEmptyOpt='false' selected="${entity.listTp}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">过期时间：</td>
					<td class="content">
						<input id="expiredTmStrInpt" name="expiredTmStr" type="text" value="${expiredTmStr}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HH:mm:ss'})" class="Wdate" style="width: 150px;" />
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="254"
							id="commentInpt" name="comment" value="${entity.comment}" />
					</td>
				</tr>
			</table>
		</@editDiv>	
	</body>
</html>