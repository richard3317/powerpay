<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>批量导入结果</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#downBtn").click(function() {
					$.jumpTo("${ctx}/mchnt/downResult.do");
				});
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<ul>
			<li style="margin: 10px 0;">批量导入文件处理完成.</li>
			<li style="margin: 10px 0;">总条数：${total}</li>
			<li style="margin: 10px 0;">成功条数：${succ}</li>
			<li style="margin: 10px 0;">失败条数：${fail}</li>
			<li><a id="downBtn" href="#" class="easyui-linkbutton">下载结果文件</a></li>
		</ul>
	</body>
</html>