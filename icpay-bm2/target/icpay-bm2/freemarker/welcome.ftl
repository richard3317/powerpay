<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<#include "/common/include.ftl">
	</head>

	<body>
		<div style="font-weight: bold;font-size: 13px; color: green; margin-top: 20px;">
			亲爱的用户【${Session.SESSION_USR_INFO.usrId}】你好,欢迎登录.<br/><br/>
			上次登录IP: ${Session.SESSION_USR_INFO.lastLoginIp}<br/><br/>
			上次登录时间: ${Session.SESSION_USR_INFO.lastLoginTs}
		</div>
	</body>
</html>