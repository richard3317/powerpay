<#include "/common/edit_macro.ftl" />
<#assign label = "用户信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="system/bmUser">
			$("#usrIdInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['characters','maxLength[32]']
			});
			$("#usrNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[64]'
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="用户信息" editUrl="/system/bmUser/edit/submit.do">
			<input type="hidden" name="usrId" value="${entity.usrId}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">用户ID：</td>
					<td class="content">
						${entity.usrId}
					</td>
				</tr>
				<tr>
					<td class="label">角色：</td>
					<td class="content">
						<select name="roleId" id="roleIdSel">
							<#list roleMap?keys as r>
								<#if r == entity.roleId>
									<option value="${r}" selected="selected">${r}-${roleMap[r]}</option>
								<#else>
									<option value="${r}">${r}-${roleMap[r]}</option>
								</#if>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">用户姓名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="usrNmInpt" name="usrNm" value="${entity.usrNm}" />
					</td>
				</tr>
				<tr>
					<td class="label">用户电话号码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="32"
							 name="phone" data-options="validType:'number'" value="${entity.phone}" />
					</td>
				</tr>
				<tr>
					<td class="label">用户Email：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							data-options="validType:'email'" name="email" value="${entity.email}" />
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>