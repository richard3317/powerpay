<#include "/common/add_macro.ftl" />
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
		<@addDiv label="角色信息" addUrl="/system/bmRole/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">角色名称:</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxLength="120"
							id="roleNmInpt" name="roleNm" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>