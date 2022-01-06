<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>终端批量导入页面</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#uploadBtn").click(function() {
					if ($("input[name='importFile']").val() == "") {
						alert("请选择导入文件");
						return false;
					}
					$(this).attr("disabled", "disabled");
					$.messager.alert('处理中', '正在导入，请稍候...');
					$("#uploadFrm").submit();
				});
				
				$("#downSampleBtn").click(function() {
					$.jumpTo("${ctx}/terminal/downSample.do");
				});
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<div style="margin-top: 20px;">请选择批量导入文件（请上传10M以内大小的文件）:</div>
		<div style="margin:20px 0">
			<form id="uploadFrm" action="${ctx}/terminal/import/submit.do" 
				method="post" enctype="multipart/form-data">
            	<input id="importFile" class="easyui-filebox" name="importFile" style="width: 400px;" />
            </form>
        </div>
        <div>
            <a id="uploadBtn" href="#" class="easyui-linkbutton">提交</a>
            <a id="downSampleBtn" href="#" class="easyui-linkbutton">下载示例文件</a>
        </div>
	</body>
</html>