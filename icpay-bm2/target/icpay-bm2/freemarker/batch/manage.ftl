<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="批量任务执行日志" base="batch" qryFuncCode="9100010001"
			detailFlg=true detailUrl='"detail.do?logId=" + sel.logId' detailDivWidth=800 detailDivHeight=550 detailFuncCode="9100010002">
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/batch/qry.do" qryFuncCode="9100010001">
			<table class="qry_tbl">
				<tr>
					<td>批量日期：</td>
					<td>
						<input id="batchDt" name="_QRY_batchDt" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
					<td>任务名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="taskNm" maxLength="120"
							 name="_QRY_taskNm" value="">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv  
			detailFlg=true detailFuncCode="9100010002">
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>