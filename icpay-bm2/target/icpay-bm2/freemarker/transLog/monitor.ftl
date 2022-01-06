<html>
	<head>
		<title>交易监控</title>
		<#include "/common/include.ftl">
		<script type="text/javascript">
			<@authCheck funcCode="5000020001">
			$(function() {
				var baseUrl = "${ctx}/transLog/";
				var t = null;
				var mFlg = false;
				var dealing = false;
				var scd = 0;
				initQry("qryForm", "qryResultDiv", "qryResultTbl", false);	
				$("#detailBtn").click(function() {
					var oSels = $("#qryResultTbl").datagrid("getSelections");
					if(oSels.length>1){
						alert("请选择一条记录");
						return false;
					}
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var url = baseUrl + "detail.do?transSeqId=" + sel.transSeqId;
						crtViewDialog("detailDiv", "交易信息详情", url, 800, 600);
					}
				});
				$("#monitorBtn").click(function() {
					if (!$("#qryForm").form('validate')) {
						return;
					}
					if (dealing) {
						return;
					}
					dealing = true;
					if (mFlg) {
						$(this).find("span.l-btn-text").html("开始监控");
						mFlg = false;
						if (t != null) {
							clearTimeout(t);
						}
						$("#monitorQryTbl").find("input,select").removeAttr("readOnly");
						$("#refreshMsgSpan").html("").hide();
					} else {
						$(this).find("span.l-btn-text").html("停止监控");
						mFlg = true;
						scd = $("#refreshSeconds").val();
						$("#monitorQryTbl").find("input,select").attr("readOnly", "readOnly");
						queryPage('qryForm', 1);
						startMonitor();
					}
					dealing = false;
				});
				function startMonitor() {
					if (scd > 0) {
						scd --;
					} else {
						queryPage('qryForm', 1);
						scd = $("#refreshSeconds").val();
					}
					$("#refreshMsgSpan").html(scd + "秒后自动刷新监控结果").show();
					t = setTimeout(startMonitor, 1000);
				}
			});
			</@authCheck>
		</script>
	</head>

	<body style="padding: 5px;">
		<div id="qryDiv">
			<form id="qryForm" method="post" action="${ctx}/transLog/monitor/qry.do">
				<table class="qry_tbl" id="monitorQryTbl">
					<tr>
						<td>刷新间隔(秒)</td>
						<td>
							<input class="easyui-validatebox" type="text" id="refreshSeconds" 
								name="_QRY_refreshSeconds" value="60" style="width: 80px; padding-left: 5px;" 
								data-options="required:true,validType:['integer','minInteger[10]','maxInteger[300]']"/>
						</td>
						<td>查询时间范围(分钟)</td>
						<td>
							<input class="easyui-validatebox" type="text" name="_QRY_monitorTm" 
								value="10" style="width: 80px; padding-left: 5px;"
								data-options="required:true,validType:['integer','minInteger[1]','maxInteger[300]']"">
						</td>
						<td>显示记录条数</td>
						<td>
							<input class="easyui-validatebox" type="text" 
								name="monitorNum" value="30" style="width: 80px; padding-left: 5px;"
								data-options="required:true,validType:['integer','minInteger[5]','maxInteger[100]']"">
						</td>
					</tr>
				</table>
			</form>
			
			<div style="margin: 10px 0;">
				<@authCheck funcCode="5000020001">
					<a id="monitorBtn" href="javascript:void(0)" class="easyui-linkbutton">开 始 监 控</a>
					<span style="margin-left: 10px;font-weight: bold; color: green;" id="refreshMsgSpan"></span>
				</@authCheck>
			</div>
		</div>
		
		<div style="margin: 10px 0;">
			<div id="qryResultDiv" title="查询结果">
				<table id="qryResultTbl"></table>
				<div id="opBtns" style="margin: 10px 0;">
					<@authCheck funcCode="5000010002">
						<a id="detailBtn" href="javascript:void(0)" class="easyui-linkbutton">查 看 详 情</a>
					</@authCheck>
				</div>
			</div>
		</div>
		<div id="detailDiv"></div>
	</body>
</html>