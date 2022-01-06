<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="操作日志" base="system/bmOperLog" qryFuncCode="9900060001"
			detailFlg=true detailFuncCode="9900060002" detailUrl='"detail.do?logId=" + sel.logId' detailDivWidth=600 detailDivHeight=500>
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/system/bmOperLog/qry.do" qryFuncCode="9900060001">
			<table class="qry_tbl">
				<tr>
					<td>操作类型</td>
					<td>
						<select id="sysIdSel" name="_QRY_opTp" data-options="editable:false">
							<@enumOpts enumNm='CommonEnums.OpType' showEmptyOpt='true' showCode="true" />
						</select>
					</td>
					<td>操作员</td>
					<td>
						<input class="easyui-validatebox" type="text" 
							name="_QRY_operInfo" value="">
					</td>
					<td>功能模块</td>
					<td>
						<select id="sysIdSel" name="_QRY_opFuncInfo" data-options="editable:false" style="width: 200px;">
							<@enumOpts enumNm='BmEnums.FuncInfo' showEmptyOpt='true'/>
						</select>
					</td>
				</tr>
				<tr>
					<td>操作日期</td>
					<td colspan="5">
						<input id="startOperDt" name="_QRY_startOpDt" type="text" value="${today}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'endOpDt\')}'})" class="Wdate" />
						至
						<input id="endOpDt" name="_QRY_endOpDt" type="text" value="${today}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'startOperDt\')}'})" class="Wdate" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv detailFlg=true detailFuncCode="9900060002" />
		
		<div id="detailDiv"></div>
	</body>
</html>