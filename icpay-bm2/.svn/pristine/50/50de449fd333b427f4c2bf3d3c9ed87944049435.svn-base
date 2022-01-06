<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="审核任务" base="busCheck" qryFuncCode="9000010001">
			$("#detailBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
					return;
				}
				$.jumpTo(baseUrl + "detail.do?taskId=" + sel.taskId);
			});
			$("#checkBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				var currUsrId = '${Session.SESSION_USR_INFO.usrId}';
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					var taskSt = sel.taskSt;
					if (taskSt != '0' && taskSt != '1') {
						alert('请选择任务状态为待处理或处理中的任务');
						return;
					} else {
						if (sel.operId == currUsrId) {
							alert('不能审核自己提交的任务');
							return;
						}
						if (taskSt == '1' && sel.checkOperId != currUsrId) {
							alert('该任务正在被其他用户审核');
							return;
						}
						if ((taskSt == '0' )
							|| taskSt == '1') {
							$.jumpTo(baseUrl + "checkTask.do?taskId=" + sel.taskId);
						}
					}
				}
			});
			$("#cancleBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				var currUsrId = '${Session.SESSION_USR_INFO.usrId}';
				if (sel == null) {
					alert("请选择一条记录");
				} else {
					if (sel.operId != currUsrId) {
						alert('不能撤销其他操作员提交的任务');
						return;
					}
					if (sel.taskSt != '0') {
						alert('只能撤销"待处理"状态的任务');
						return;
					}
					if (confirm("确认撤销?")) {
						$.post(baseUrl + "cancle.do?taskId=" + sel.taskId,
							function(data) {
								$.processAjaxResp(data, function() {
									alert("任务已撤销");
									dealFlag = false;
									$("#qryForm").submit();
								});
							}
						);
					}
				}
			});
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/busCheck/qry.do" qryFuncCode="9000010001">
			<table class="qry_tbl">
				<tr>
					<td>任务ID：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="taskIdInpt" 
							name="_QRY_taskId" value="${(qryParamMap.taskId)!''}" />
					</td>
					<td>操作员：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="operIdInpt" 
							name="_QRY_operId" value="${(qryParamMap.operId)!''}" />
					</td>
					<td>审核员：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="checkOperIdInpt" 
							name="_QRY_checkOperId" value="${(qryParamMap.checkOperId)!''}" />
					</td>
				</tr>
				<tr>
					<td>任务类型：</td>
					<td>
						<select id="taskStInpt" name="_QRY_taskTp">
							<@enumOpts enumNm='BusCheckTaskEnums.TaskTp' showCode='true' 
								showEmptyOpt="true" selected="${(qryParamMap.taskTp)!''}" />
						</select>
					</td>
					<td>操作类型：</td>
					<td>
						<select id="taskStInpt" name="_QRY_opTp">
							<@enumOpts enumNm='CommonEnums.OpType' showCode='true' 
								showEmptyOpt="true" selected="${(qryParamMap.opTp)!''}" />
						</select>
					</td>
					<td>任务状态：</td>
					<td>
						<select id="taskStInpt" name="_QRY_taskSt">
							<@enumOpts enumNm='BusCheckTaskEnums.TaskSt' showCode='true' 
								showEmptyOpt="true" selected="${(qryParamMap.taskSt)!''}" />
						</select>
					</td>
				</tr>
				<tr>
					<td>关键信息：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="contentKey" 
							name="_QRY_contentKey" value="${(qryParamMap.contentKey)!''}" />
					</td>
					<td>操作日期</td>
					<td colspan="3">
						<input id="startOperDt" name="_QRY_startOpDt" type="text"
							value="${(qryParamMap.startOpDt)!'${today}'}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'endOpDt\')}'})" class="Wdate" />
						至
						<input id="endOpDt" name="_QRY_endOpDt" type="text" value="${(qryParamMap.endOpDt)!'${today}'}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'startOperDt\')}'})" class="Wdate" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv detailFlg=true detailFuncCode="9000010002">
			<@authCheck funcCode="9000010003">
				<a id="checkBtn" href="javascript:void(0)" class="easyui-linkbutton">审核任务</a>
			</@authCheck>
			<@authCheck funcCode="9000010006">
				<a id="cancleBtn" href="javascript:void(0)" class="easyui-linkbutton">撤销任务</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
</html>