<#include "/common/add_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="用户信息" base="system/bmUser">
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
			$("#loginPwd1Inpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['loginPwd', 'minLength[6]']
			});
			$("#loginPwd2Inpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['loginPwd', 'minLength[6]', 'equalTo["loginPwd1Inpt"]']
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="用户信息" addUrl="/system/bmUser/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">用户ID：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="32"
							id="usrIdInpt" name="usrId" />
					</td>
				</tr>
				<tr>
					<td class="label">角色：</td>
					<td class="content">
						<select name="roleId" id="roleIdSel">
							<#list roleMap?keys as r>
								<option value="${r}">${r}-${roleMap[r]}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">用户姓名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="usrNmInpt" name="usrNm" />
					</td>
				</tr>
				<tr>
					<td class="label">密码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="password" maxlength="32"
							id="loginPwd1Inpt" name="loginPwd1" />
					</td>
				</tr>
				<tr class="pwdTr">
					<td class="label">密码确认：</td>
					<td class="content">
						<input class="easyui-validatebox" type="password" maxlength="64"
							id="loginPwd2Inpt" name="loginPwd2" />
					</td>
				</tr>
				<tr>
					<td class="label">用户电话号码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="32"
							 name="phone" data-options="validType:'number'"  />
					</td>
				</tr>
				<tr>
					<td class="label">用户Email：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							data-options="validType:'email'" name="email" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>