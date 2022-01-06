<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商分润记录" base="agentProfitTaskLog" qryFuncCode="8000120001"
			detailFlg=true detailFuncCode="8000120002" detailUrl='"detail.do?seqId=" + sel.seqId'>
			<@authCheck funcCode='8000120003'>
				$("#downBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo("${ctx}/agentProfitTaskLog/down.do?seqId=" + sel.seqId);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/agentProfitTaskLog/qry.do" qryFuncCode="8000120001">
			<table class="qry_tbl">
				<tr>
					<td>代理商代码：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="15"
							name="_QRY_agentCd" value="${(qryParamMap.agentCd)!''}">
					</td>
					<td>分润日期：</td>
					<td>
						<input name="_QRY_profitDt" type="text"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv 
			detailFlg=true detailFuncCode="8000120002">
			<@authCheck funcCode='8000120003'>
				<a id="downBtn" href="javascript:void(0)" class="easyui-linkbutton">下载分润记录文件</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>
	