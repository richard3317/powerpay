<#include "/common/edit_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="角色信息" base="system/bmRole">
			$("#roleNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="角色信息" editUrl="/system/bmRole/edit/submit.do">
			<input type="hidden" name="roleId" value="${entity.roleId}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">角色名称:</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxLength="120"
							id="roleNmInpt" name="roleNm" value="${entity.roleNm}" />
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>