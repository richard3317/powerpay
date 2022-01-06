<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="保留余额记录" base="mchntAccout" qryFuncCode="1000070013">
			$("#backToMngBtn").click(function() {
				$.jumpTo(_ctx + "/mchntAccout/backToManage.do");
			});
			<@authCheck funcCode='1000070014'>
				$("#detailBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						var url = baseUrl + "obligatedFlowDetail.do?seqId=" + sel.seqId;
						crtViewDialog("detailDiv", "保留余额记录详情", url, 650, 350);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/mchntAccout/obligatedFlowQry.do" qryFuncCode="1000070013">
			<input type="hidden" name="mchntCd" value="${mchntCd}" />
			<table class="qry_tbl">
				<tr>
					<td>起始日期：</td>
					<td>
						<input name="startDate" id="startDate" type="text" value="${today}" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					<td>结束日期：</td>
					<td>
						<input name="endDate" id="endDate" type="text" value="" 
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
					<td>查询方式：</td>
					<td>
						<select id="timeQryMethod" name="timeQryMethod" value="UpdTS">
							<@enumOpts enumNm="BmEnums.TimeQryMethod" showEmptyOpt="false" showCode="false" selected="UpdTS"/>
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv>
			 <@authCheck funcCode='1000070014'>
			 	<a id="detailBtn" href="javascript:void(0)" class="easyui-linkbutton">查看详情</a>
			 </@authCheck>
			 <a id="backToMngBtn" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>