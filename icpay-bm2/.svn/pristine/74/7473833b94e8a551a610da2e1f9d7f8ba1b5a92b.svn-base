<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>审核任务</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				var baseUrl = "${ctx}/busCheck/";
				$("#passBtn").click(function() {
					if (confirm("确认审核通过?")) {
						if (dealFlag) {
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;		 
						var url = baseUrl + "pass.do";
						$.post(url,
							{
								taskId : $("#taskIdSpan").html(),
								checkerComments : $.trim($("#checkOperCommentInpt").val())
							},
							function(data) {
								$.processAjaxResp(data, function() {
									alert("任务已审核通过");
									$.jumpTo(baseUrl + "/backToManage.do");
								});
							}
						);
					}
				});
				$("#refuseBtn").click(function() {
					var refuseReason = $.trim($("#checkOperCommentInpt").val());
					if (refuseReason == "") {
						alert("请备注内输入拒绝原因");
						$("#checkOperCommentInpt").focus();
						return;
					}
					if (confirm("确认拒绝?")) {
						if (dealFlag) {
							alert("正在处理请求，请稍等......");
							return false;
						}
						dealFlag = true;		 
						var url = baseUrl + "refuse.do";
						$.post(url,
							{
								taskId : $("#taskIdSpan").html(),
								checkerComments : refuseReason
							},
							function(data) {
								$.processAjaxResp(data, function() {
									alert("任务已拒绝");
									$.jumpTo(baseUrl + "/backToManage.do");
								});
							}
						);
					}
				});
				$("#backBtn").click(function() {
					$.jumpTo(baseUrl + "/backToManage.do");
				});
			});
		</script>
	</head>
	
	<body>
		<div class="easyui-panel" title="审核任务" style="padding:20px;">
			<div class="easyui-panel" title="任务信息" style="padding:20px;">
				<table class="view_tbl" cellpadding="0" cellspacing="0">
					<tr>
						<td class="vtd_lbl" width="15%">任务ID:</td>
						<td class="vtd_cnt" width="35%">
							<span id="taskIdSpan">${entity.taskId}</span>
						</td>
						<td class="vtd_lbl" width="15%">参数类型:</td>
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
						<td class="vtd_lbl" width="15%">主键信息:</td>
						<td class="vtd_cnt" width="85%" colspan="3">
							${entity.contentKeyInfo}
						</td>
					</tr>
					<tr>
						<td class="vtd_lbl" width="15%">备注:</td>
						<td class="vtd_cnt" width="85%" colspan="3" style="height: 40px;">
							<input type="text" id="checkOperCommentInpt" value="" style="width: 500px; height: 25px; line-height: 25px; padding-left: 2px;" />
						</td>
					</tr>
				</table>
			</div>
			
			<#include "/busCheck/checkTask_${entity.taskTp}.ftl">
			
			<div id="opBtns" style="margin: 10px 0;">
				<@authCheck funcCode="9000010004">
					<a id="passBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">审核通过</a>
				</@authCheck>
				<@authCheck funcCode="9000010005">
					<a id="refuseBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" >拒绝</a>
				</@authCheck>
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back">返回</a>
			</div>
		</div>
	</body>
</html>
