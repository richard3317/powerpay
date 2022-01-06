<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>${loginTitle}多渠道收单平台运营管理系统</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				console.log("jumpto: "+_ctx);
				//$.jumpTo(_ctx);
				window.top.location.href = "${ctx}/login.do";
			}); 
		</script>
	</head>
	<body>
	<#--  
		<input type="text" id="test" value="${ctx}"/>
		<a href="${ctx}">${ctx}</a>
	-->	
	</body>
</html>