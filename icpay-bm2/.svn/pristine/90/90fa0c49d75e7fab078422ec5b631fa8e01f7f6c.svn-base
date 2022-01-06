<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="平台交易量报表" base="chnlMchntAccountHour" qryFuncCode="9200100001" qryFlg=false>
			<@authCheck funcCode="9200100001">
				$("#exportBtn").click(function() {
					var qryStartDate = $("#qryStartDate").val();
                    var qryEndDate = $("#qryEndDate").val();
					var url =_ctx + "/chnlMchntAccountHour/exportRpt.do?qryStartDate=" + qryStartDate + "&qryEndDate=" + qryEndDate;
					$.jumpTo(url);
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="平台交易量报表" style="padding: 10px;">
			<table class="qry_tbl">
				<tr>
					<td>查询范围</td>
					<td>
						<input id="qryStartDate" name="_QRY_startDate" type="text"
							value="${(startDate)!'${today}'}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',readOnly:true })" class="Wdate" />至
					</td> 
					<td>
						<input id="qryEndDate" name="_QRY_endDate" type="text"
							value="${(endDate)!'${today}'}"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd',readOnly:true })" class="Wdate" />
					</td>
				</tr>
			</table>
			<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导出报表</a>
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>