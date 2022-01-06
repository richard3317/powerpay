<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="操作日志" base="eventLog" qryFuncCode="8800010001">
			<@authCheck funcCode='8800010002'>
				$("#detailBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var url = baseUrl + "detail.do?logId=" + sel.logId+"&logTs="+sel.logTs;
						crtViewDialog("detailDiv", "操作日志详情", url, 1200, 400);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/eventLog/qry.do" qryFuncCode="8800010001">
			<table class="qry_tbl">
			    <tr>
					<td>起始日期：</td>
					<td>
						<input id="startDate" name="startDate" type="text" value="${(qryParamMap.today)!today}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',maxDate:'#F{$dp.$D(\'endDate\')}', readOnly:true})"
							class="Wdate" style="width: 120px;"/>
					</td>
					<td>结束日期：</td>
					<td>
						<input id="endDate" name="endDate" type="text" value="${(qryParamMap.today_end)!today_end}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd HHmmss',minDate:'#F{$dp.$D(\'startDate\')}', readOnly:true})"
							class="Wdate" style="width: 120px;"/>
					</td>
					<td>来源：</td>
					<td>
						<select  name="logSrc" id="logSrc" >
							<@enumOpts enumNm="CommonEnums.Src" showEmptyOpt="false" showCode="true" selected="${(qryParamMap.logSrc)!'BM'}"/>
						</select>
					</td>
			    </tr>
				<tr>
					<td class="label">商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="mchntCd" name="mchntCd" />
					</td>
					<td class="label">子帐号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="64"
							id="mchntUser" name="mchntUser" />
					</td>
					<td class="label">操作员：</td>
					<td>
						<input type="text" id="lastOperId" name="lastOperId" maxLength="64" />
					</td>
				</tr>	
			</table>
		</@qryDiv>
		
		<@qryResultDiv>
			 <@authCheck funcCode='8800010002'>
			 	<a id="detailBtn" href="javascript:void(0)" class="easyui-linkbutton">查看详情</a>
			 </@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>