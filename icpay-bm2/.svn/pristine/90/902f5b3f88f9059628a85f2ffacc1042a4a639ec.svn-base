<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>审核任务</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				var baseUrl = "${ctx}/busCheck/";
				$("#backBtn").click(function() {
					$.jumpTo(baseUrl + "/backToManage.do");
				});
			});
		</script>
	</head>
	
	<body>
		<div class="easyui-panel" title="查看审核任务" style="padding:10px;">
			<div class="easyui-panel" title="任务信息" style="padding:10px;">
				<table class="view_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="vtd_lbl" width="15%">任务ID:</td>
						<td class="vtd_cnt" width="35%">
							<span id="taskIdSpan">${entity.taskId}</span>
						</td>
						<td class="vtd_lbl" width="15%">任务类型:</td>
						<td class="vtd_cnt" width="35%">
							${entity.taskTpDesc}
						</td>
					</tr>
					<tr>
						<td class="vtd_lbl" width="15%">操作类型:</td>
						<td class="vtd_cnt" width="35%">
							${entity.opTpDesc}
						</td>
						<td class="vtd_lbl" width="15%">任务状态:</td>
						<td class="vtd_cnt" width="35%">
							${entity.taskStDesc}
						</td>
					</tr>
					<tr>
						<td class="vtd_lbl" width="15%">任务提交人:</td>
						<td class="vtd_cnt" width="35%">
							${entity.operId}
						</td>
						<td class="vtd_lbl" width="15%">任务创建时间:</td>
						<td class="vtd_cnt" width="35%">
							${entity.opTm}
						</td>
					</tr>
					<tr>
						<td class="vtd_lbl" width="15%">审核员:</td>
						<td class="vtd_cnt" width="35%">
							${entity.checkOperId}
						</td>
						<td class="vtd_lbl" width="15%">审核时间:</td>
						<td class="vtd_cnt" width="35%">
							${entity.checkTm}
						</td>
					</tr>
					<tr>
						<td class="vtd_lbl" width="15%">主键信息:</td>
						<td class="vtd_cnt" width="85%" colspan="3">
							${entity.contentKeyInfo}
						</td>
					</tr>
					<tr>
						<td class="vtd_lbl" width="15%">审核员备注:</td>
						<td class="vtd_cnt" width="85%" colspan="3">
							${entity.checkerComments}
						</td>
					</tr>
					<tr>
						<td class="vtd_lbl" width="15%">记录创建时间:</td>
						<td class="vtd_cnt" width="35%">
							${entity.recCrtTs}
						</td>
						<td class="vtd_lbl" width="15%">最近更新时间:</td>
						<td class="vtd_cnt" width="35%">
							${entity.recUpdTs}
						</td>
					</tr>
				</table>
			</div>
			
			<#include "/busCheck/checkTask_${entity.taskTp}.ftl">
			
			<div id="opBtns" style="margin: 10px 0;">
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
			</div>
		</div>
	</body>
</html>
