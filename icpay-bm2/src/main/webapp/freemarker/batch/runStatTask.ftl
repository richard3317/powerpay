<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			var dealing = false;
			var currTask = "";
			$(function() {
				$("#batchRunBtn").click(function() {
					if (dealing) {
						alert("已经有一个任务在执行，请稍等");
						return false;
					}
					currTask = $("#taskNmSel option:selected").html();
					$("#batchTaskForm").form("enableValidation").submit();
				});
				$("#batchTaskForm").form({
					url: $("#batchTaskForm").attr("action"),
					onSubmit: function() {
						var v = $(this).form("validate");
						if (v) {
							dealing = true;
							$.messager.progress({
								title:currTask,
								msg:currTask + '正在执行，请稍候...'
							});
						}
						return v;
					},
					success: function(data) {
						dealing = false;
						$.messager.progress('close');
						$.processAjaxResp(data, function(r) {
							alert(currTask + "执行成功");
						});
					}
				});
			});
		</script>
	</head>

	<body style="padding: 5px;">
		<form id="batchTaskForm" method="post" action="${ctx}/batch/runStatTaskSubmit.do">
			<table class="qry_tbl">
				<tr>
					<td>选择任务:</td>
					<td>
						<select id="taskNmSel" name="taskNm" class="easyui-validatebox" required="true" style="width: 300px;">
							<option value="">--请选择--</option>
							<#list taskMap?keys as k>
								<option value="${k}">${taskMap[k]}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr class="batch_param" id="">
					<td>批量日期:</td>
					<td>
						<input id="batchDtInpt" name="batchDt" type="text" value="" style="width: 120px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<td>
						<@authCheck funcCode="9200020001">
							<a id="batchRunBtn" href="javascript:void(0)" class="easyui-linkbutton">执行</a>
						</@authCheck>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>