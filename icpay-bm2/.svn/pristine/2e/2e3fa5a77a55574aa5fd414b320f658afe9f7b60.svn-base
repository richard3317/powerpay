<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户清算记录" base="merSettleTask" qryFuncCode="8000070001">
			var batchTaskUrl = "${ctx}/batch/settleTnProc.do";
			initQry("qryForm", "qryResultDiv", "qryResultTbl", true, false);
			<@authCheck funcCode='8000070002'>
				$("#viewDetailBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelections");
					if (sel == null || sel.length > 1) {
						alert("请选择一条记录");
						return;
					} else {
						var url = _ctx + "/merSettleTask/detail.do?seqId=" + sel[0].seqId
						crtViewDialog("detailDiv", "商户清算记录详情", url, 700, 400);
					}
				});
			</@authCheck>
			
			<@authCheck funcCode='8000070003'>
				$("#runTaskBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelections");
					if (sel == null || sel.length == 0) {
						alert("请选择待执行的记录");
						return;
					} else {
						var f = true;
						for (var i = 0; i < sel.length; i ++) {
							if (sel[i].settlePeriod == '0') {
								alert("[" + sel[i].seqId + "]:" + "不能执行清算周期为T+0的任务");
								f = false;
								break;
							}
							if (sel[i].state != '0') {
								alert("[" + sel[i].seqId + "]:" + "只能执行状态为0-待清算的任务");
								f = false;
								break;
							}
						}
						if (f && confirm("确认执行选中的清算记录?")) {
							$("#execLog").html("");
							runTask(0, sel);
						}
					}
					
					function runTask(idx, sel) {
						$.messager.progress('close');
						if (idx == sel.length) {
							return;
						}
						$.messager.progress({
							title: "执行商户清算任务",
							msg: "任务[" + sel[idx].seqId + "]正在执行，请稍候..."
						});
						$.post(batchTaskUrl, 
							{
								batchDt: sel[idx].settleDt,
								mchntCd: sel[idx].mchntCd
							},
							function(data) {
								var h = $("#execLog").html();
								$.processAjaxResp(data, function(r) {
									h = h + "任务[" + sel[idx].seqId + "]执行成功<br/>"
								}, function() {
									h = h + "任务[" + sel[idx].seqId + "]执行失败<br/>"
								}, function() {
									h = h + "任务[" + sel[idx].seqId + "]执行失败<br/>"
								});
								$("#execLog").html(h);
								runTask(idx+1, sel);
							}
						);
					}
				});
			</@authCheck>
			
			<@authCheck funcCode='8000070004'>
				$("#downBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelections");
					if (sel == null || sel.length > 1) {
						alert("请选择一条记录");
					} else {
						if (sel[0].state != '2') {
							alert("请选择状态为'2-已生成清算文件'的记录");
							return;
						}
						$.jumpTo("${ctx}/merSettleTask/down.do?seqId=" + sel[0].seqId);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/merSettleTask/qry.do" qryFuncCode="8000010001">
			<table class="qry_tbl">
				<tr>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="">
					</td>
					<td>清算周期：</td>
					<td>
						<select name="_QRY_settlePeriod">
							<@enumOpts enumNm="SettleEnums.SettlePeriod" showCode="false" showEmptyOpt="true"/>
						</select>
					</td>
					<td>清算日期：</td>
					<td>
						<input name="_QRY_settleDt" type="text" value="" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
				</tr>
				<tr>
					<td>清算批次：</td>
					<td>
						<select name="_QRY_settleBt">
							<option value="">--请选择--</option>
							<#list [1,2,3,4,5,6,7,8,9,10,11,12] as i>
								<option value="${i}">${i}</option>
							</#list>
						</select>
					</td>
					<td>状态：</td>
					<td colspan="3">
						<select name="_QRY_state">
							<@enumOpts enumNm="SettleEnums.SettleTaskState" showCode="true" showEmptyOpt="true"/>
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<div>
		<@authCheck funcCode='8000070002'>
			<a id="viewDetailBtn" href="javascript:void(0)" class="easyui-linkbutton">查看详情</a>
		</@authCheck>
		<@authCheck funcCode='8000070003'>
			<a id="runTaskBtn" href="javascript:void(0)" class="easyui-linkbutton">执行商户对账任务</a>
		</@authCheck>
		<@authCheck funcCode='8000070004'>
			<a id="downBtn" href="javascript:void(0)" class="easyui-linkbutton">下载商户对账文件</a>
		</@authCheck>
		</div>
		
		<@qryResultDiv>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
		<div id="execLog" style="color: red; font-weight: bold;"></div>
	</body>
<html>