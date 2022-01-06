<#assign ctx = request.contextPath>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><@msg code='logout.已安全退出' default='已安全退出'/></title>
		<script type="text/javascript">
			window.top.location.href = "${ctx}/";
		</script>
	</head>
	<body></body>
</html>