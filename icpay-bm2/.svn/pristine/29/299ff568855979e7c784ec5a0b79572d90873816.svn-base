<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>角色信息新增页面</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				$("#saveBtn").click(function() {
					$("#chngPwdForm").form("enableValidation").submit();
				});
				$("#chngPwdForm").form({
					url: $("#chngPwdForm").attr("action"),
					onSubmit: function() {
						return $(this).form("validate");
					},
					success: function(data) {
						$.processAjaxResp(data, function() {
							alert("修改登录密码成功");
						});
					}
				});
				$("#oldPwd").validatebox({
					required: true,
					novalidate:true,
					validType: ['loginPwd', 'maxLength[32]', 'minLength[6]']
				});
				$("#loginPwd1").validatebox({
					required: true,
					novalidate:true,
					validType: ['loginPwd', 'maxLength[32]', 'minLength[6]']
				});
				$("#loginPwd2").validatebox({
					required: true,
					novalidate:true,
					validType: ['loginPwd', 'maxLength[32]', 'minLength[6]','equalTo["loginPwd1"]']
				});
			});
		</script>
	</head>
	
	<body>
		<div id="chngPwdDiv" class="easyui-panel" title="修改登录密码" style="padding: 10px;">
			<form id="chngPwdForm" method="post" action="${ctx}/system/bmUser/chngPwd/submit.do">
				<table class="edit_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">请输入原密码:</td>
						<td class="content">
							<input id="oldPwd" class="easyui-validatebox" style="width: 200px" 
								type="password" name="oldPwd" maxLength="32" />
						</td>
					</tr>
					<tr>
						<td class="label">请输入新密码:</td>
						<td class="content">
							<input id="loginPwd1" class="easyui-validatebox" style="width: 200px" 
								type="password" name="loginPwd1" maxLength="32" />
						</td>
					</tr>
					<tr>
						<td class="label">再次输入新密码:</td>
						<td class="content">
							<input id="loginPwd2" class="easyui-validatebox" style="width: 200px" 
								type="password" name="loginPwd2" maxLength="32" />
						</td>
					</tr>
				</table>
			</form>
			
			<div style="margin: 10px;">
				<a id="saveBtn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
			</div>
		</div>
	</body>
</html>