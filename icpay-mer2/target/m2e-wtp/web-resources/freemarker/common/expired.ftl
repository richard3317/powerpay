<#assign ctx = request.contextPath>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><@msg code='expire.会话失效' default='会话失效'/></title>
		<script type="text/javascript">
			window.top.location.href = "${ctx}/";
		</script>
	</head>
	<body></body>
</html>