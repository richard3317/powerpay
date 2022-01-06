<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>终端导出结果</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#downBtn").click(function() {
					$.jumpTo("${ctx}/terminal/downExport.do");
				});
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<ul>
			<li style="margin: 10px 0;">导出请求处理完成.</li>
			<li style="margin: 10px 0;">总条数：${total}</li>
			<li><a id="downBtn" href="#" class="easyui-linkbutton">下载导出文件</a></li>
		</ul>
	</body>
</html>