<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>清算对账手工任务</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			$(function() {
				var dealing = false;
				var currTask = "";
				$(".batchBtn").click(function() {
					if (dealing) {
						alert("已经有一个任务在执行，请稍等");
						return false;
					}
					var frmId = $(this).attr("formId");
					currTask = $("#" + frmId).attr("taskDesc");
					$("#" + frmId).form("enableValidation").submit();
				});
				$(".batchForm").form({
					url: $(this).attr("action"),
					onSubmit: function() {
						var v = $(this).form("validate");
						if (v) {
							dealing = true;
							var win = $.messager.progress({
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
		<@authCheck funcCode='8000060001'>
		<div class="easyui-panel" title="内部对账任务" style="padding: 2px;">
			<form id="internalAccCheckForm" method="post" action="${ctx}/batch/internalAccCheck.do" class="batchForm" taskDesc='内部对账批量任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							批量日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="internalAccCheckForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060002'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="对账数据抽取任务" style="padding: 2px;">
			<form id="dataExtractForm" method="post" action="${ctx}/batch/dataExtract.do" class="batchForm" taskDesc='对账数据抽取任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							数据日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="dataExtractForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060003'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="渠道对账文件检查及虚拟商户清算记录创建任务" style="padding: 2px;">
			<form id="tvpayFileChkForm" method="post" action="${ctx}/batch/chnlFileChk.do" class="batchForm" 
				taskDesc='对账文件检查及虚拟商户清算记录创建任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							渠道&nbsp;
							<select name="chnlId" style="width: 140px;" class="easyui-validatebox" required="true">
								<option value="">--请选择--</option>
								<option value="01">01-银视通</option>
								<option value="03">03_弘达上业</option>
								<option value="04">04_海科融通</option>
								<option value="05">05_海科融通(线下)</option>
								<option value="11">11_钱海支付</option>
								<option value="26">26_风上网络</option>
							</select>
						</td>
						<td>
							对账日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="tvpayFileChkForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060004'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="渠道对账任务" style="padding: 2px;">
			<form id="tvpayAccChkForm" method="post" action="${ctx}/batch/chnlAccChk.do" class="batchForm" taskDesc='对账任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							渠道&nbsp;
							<select name="chnlId" style="width: 140px;" class="easyui-validatebox" required="true">
								<option value="">--请选择--</option>
								<option value="01">01-银视通</option>
								<option value="03">03_弘达上业</option>
								<option value="04">04_海科融通</option>
								<option value="05">05_海科融通(线下)</option>
								<option value="11">11_钱海支付</option>
								<option value="26">26_风上网络</option>
							</select>
						</td>
						<td>
							批量日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="tvpayAccChkForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060013'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="渠道取现对账文件检查及虚拟商户清算记录创建任务" style="padding: 2px;">
			<form id="tvpayFileChkForm" method="post" action="${ctx}/batch/chnlWithdrawFileChk.do" class="batchForm" 
				taskDesc='对账文件检查及虚拟商户清算记录创建任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							渠道&nbsp;
							<select name="chnlId" style="width: 140px;" class="easyui-validatebox" required="true">
								<option value="11">11_钱海支付</option>
								<option value="26">26_风上网络</option>
							</select>
						</td>
						<td>
							对账日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="tvpayFileChkForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		<@authCheck funcCode='8000060014'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="渠道取现对账任务" style="padding: 2px;">
			<form id="tvpayAccChkForm" method="post" action="${ctx}/batch/chnlWithdrawChk.do" class="batchForm" taskDesc='对账任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							渠道&nbsp;
							<select name="chnlId" style="width: 140px;" class="easyui-validatebox" required="true">
								<option value="11">11_钱海支付</option>
								<option value="26">26_风上网络</option>
							</select>
						</td>
						<td>
							批量日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="tvpayAccChkForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060005'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="创建T+0清算记录任务" style="padding: 2px;">
			<form id="settleT0LogCreateForm" method="post" action="${ctx}/batch/settleT0LogCreate.do" class="batchForm" taskDesc='创建T+0清算记录任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							清算日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>&nbsp;&nbsp;
							批次&nbsp;
							<select name="settleBatch" class="easyui-validatebox" required="true">
								<option value="">--请选择---</option>
								<#list [1,2,3,4,5,6,7,8,9,10,11,12] as i>
									<option value="${i}">${i}</option>
								</#list>
							</select>
						</td>
						<td><a formId="settleT0LogCreateForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060006'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="执行T+0清算任务" style="padding: 2px;">
			<form id="settleT0ProcForm" method="post" action="${ctx}/batch/settleT0Proc.do" class="batchForm" taskDesc='执行T+0清算任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							清算日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>&nbsp;&nbsp;
							批次&nbsp;
							<select name="settleBatch" class="easyui-validatebox" required="true">
								<option value="">--请选择---</option>
								<#list [1,2,3,4,5,6,7,8,9,10,11,12] as i>
									<option value="${i}">${i}</option>
								</#list>
							</select>
						</td>
						<td><a formId="settleT0ProcForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060007'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="T+0划款文件生成任务" style="padding: 2px;">
			<form id="settleT0ResCreateForm" method="post" action="${ctx}/batch/settleT0ResCreate.do" class="batchForm" taskDesc='T+0划款文件生成任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							清算日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>&nbsp;&nbsp;
							批次&nbsp;
							<select name="settleBatch" class="easyui-validatebox" required="true">
								<option value="">--请选择---</option>
								<#list [1,2,3,4,5,6,7,8,9,10,11,12] as i>
									<option value="${i}">${i}</option>
								</#list>
							</select>
						</td>
						<td><a formId="settleT0ResCreateForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060008'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="创建T+n清算记录任务" style="padding: 2px;">
			<form id="settleTnLogCreateForm" method="post" action="${ctx}/batch/settleTnLogCreate.do" class="batchForm" taskDesc='创建T+n清算记录任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							清算日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="settleTnLogCreateForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060009'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="T+n划款文件生成" style="padding: 2px;">
			<form id="settleTnResCreateForm" method="post" action="${ctx}/batch/settleTnResCreate.do" class="batchForm" taskDesc='T+n划款文件生成'>
				<table class="qry_tbl">
					<tr>
						<td>
							清算日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="settleTnResCreateForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060010'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="代理商分润文件生成任务" style="padding: 2px;">
			<form id="agentProfitLogCreateTaskForm" method="post" action="${ctx}/batch/agentProfitLogCreateTask.do" class="batchForm" taskDesc='代理商分润文件生成任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							分润日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="agentProfitLogCreateTaskForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060011'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="代理商分润划款文件生成任务" style="padding: 2px;">
			<form id="agentProfitResCreateTaskForm" method="post" action="${ctx}/batch/agentProfitResCreateTask.do" class="batchForm" taskDesc='代理商分润划款文件生成任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							分润日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="agentProfitResCreateTaskForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
		
		<@authCheck funcCode='8000060012'>
		<div style="height: 10px;">&nbsp;</div>
		<div class="easyui-panel" title="代理商对账文件生成任务" style="padding: 2px;">
			<form id="agentCheckFileCreateTaskForm" method="post" action="${ctx}/batch/agentCheckFileCreateTask.do" class="batchForm" taskDesc='代理商对账文件生成任务'>
				<table class="qry_tbl">
					<tr>
						<td>
							清算日期&nbsp;<input name="batchDt" type="text" value="" style="width: 100px;" required="true"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate easyui-validatebox"/>
						</td>
						<td><a formId="agentCheckFileCreateTaskForm" href="javascript:void(0)" class="easyui-linkbutton batchBtn">执行</a></td>
					</tr>
				</table>
			</form>
		</div>
		</@authCheck>
	</body>
</html>