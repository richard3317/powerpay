<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="每日财报报表" base="rpt" qryFuncCode="9200010003">
			<@authCheck funcCode="9200010004">
				$("#exportBtn").click(function() {
					var startOperDt = $("#startOperDt").val();
					var endOpDt = $("#endOpDt").val();
					$.jumpTo("${ctx}/rpt/exportRpt.do?startOperDt=" + startOperDt + "&endOpDt=" + endOpDt);
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
			<table class="qry_tbl">
				<tr>
					<td>操作日期</td>
					<td colspan="3">
						<input id="startOperDt" name="_QRY_startOpDt" type="text"
							value="${(startOpDt)!'${today}'}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'endOpDt\')}'})" class="Wdate" />
						至
						<input id="endOpDt" name="_QRY_endOpDt" type="text" value="${(endOpDt)!'${today}'}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'startOperDt\')}'})" class="Wdate" />
					</td>
				</tr>
			</table>
				<a id="exportBtn" href="#" class="easyui-linkbutton">生成下载</a>
		<div id="detailDiv"></div>
	</body>
<html>