<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>支付管理平台多渠道收单平台运营管理系统</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				$("#loginBtn").click(function() {				 
					$("#loginForm").form("enableValidation").submit();
				});
				$("#loginForm").form({
					url: $("#loginForm").attr("action"),
					onSubmit: function() {
						return $(this).form("validate");
					},
					success: function(resp) {
						$.processAjaxResp(resp, function() {
							$.jumpTo("${ctx}/main.do");
						}, function(r) {
							alert(r.respMsg);
							$("#validCodeImg").trigger("click");
						}, function(r) {
							alert(r.respMsg);
							$("#validCodeImg").trigger("click");
						});
					}
				});
				$("#resetBtn").click(function() {
					$("#loginForm").form("clear");
				});
				$("#loginDiv").dialog({
					title: '欢迎使用支付管理平台多渠道收单平台运营管理系统，请输入用户名密码',
					width: 400,
					height: 270,
					top: 180,
					closed: false,
					closable: false,
					draggable: false,
					cache: false,
					modal: true
				});
				$("#usrIdInpt").validatebox({
					required: true,
					novalidate: true,
					validType: 'maxLength[32]'
				});
				$("#passwordInpt").validatebox({
					required: true,
					novalidate:true,
					validType: ['loginPwd', 'maxLength[32]', 'minLength[6]']
				});
				$("#validateCodeInpt").validatebox({
					required: true,
					novalidate:true,
					validType: ['word', 'length[4]']
				});
				$("#validCodeImg").click(function() {
					var r = Math.floor(Math.random() * ( 100000000 + 1));
					$(this).attr("src", "${ctx}/validateCode.do?r=" + r);
				});
			}); 
		</script>
	</head>

	<body>
		<div data-options="region:'center'" style="overflow: hidden; text-align: center;">
			<div id="loginDiv" style="margin: 10px auto; padding-top: 20px;">
				<form id="loginForm" action="${ctx}/login/submit.do" method="post">
					<table class="edit_tbl" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label" style="width: 120px;">用户名:</td>
							<td class="content">
								<input class="easyui-validatebox" type="text" maxlength="32" style="width: 180px;"
									id="usrIdInpt" name="usrId" />
							</td>
						</tr>
						<tr>
							<td class="label" style="width: 120px;">密码:</td>
							<td class="content">
								<input class="easyui-validatebox" type="password" maxlength="32" style="width: 180px;" 
									id="passwordInpt" name="password" />
							</td>
						</tr>
						<tr>
							<td class="label" style="width: 120px;">验证码:</td>
							<td class="content">
								<img id="validCodeImg" title="点击刷新验证码"
										src="${ctx}/validateCode.do?r=${rondom}" 
										width="95" height="30" 
										style="float:left; cursor: pointer;" />
								<input type="text" class="easyui-validatebox" name="validateCode" id="validateCodeInpt"
									style="width: 80px;float:left; margin-left: 5px;" maxLength="4" />
							</td>
						</tr>
						<tr>
							<td class="label" style="width: 120px;">谷歌验证码:</td>
							<td class="content">
								<input class="easyui-validatebox" type="text" maxlength="32" style="width: 180px;"
									id="gaCodeInpt" name="gaCode" />
							</td>
						</tr>
					</table>
					
					<div style="margin: 10px; text-align: center;">
						<a id="loginBtn" href="javascript:void(0)" class="easyui-linkbutton" style="margin-right: 20px;">登录</a>
						<a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton">重置</a>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>