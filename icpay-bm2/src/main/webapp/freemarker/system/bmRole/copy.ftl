<#assign ctx = request.contextPath>
<form id="addForm" method="post" action="${ctx}/system/bmRole/copy/submit.do">
	<input id="copyRoleId" name="copyRoleId" type="hidden" value="${copyRole.roleId}" />
	<table class="view_tbl" cellpadding="0" cellspacing="0">
		<tr>
			<td class="label">角色名称:</td>
			<td class="content">
				<input class="easyui-validatebox" type="text" id="roleNmInpt" name="roleNm" />
			</td>
		</tr>
	</table>
</form>