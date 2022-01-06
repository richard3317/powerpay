<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商对账文件" base="agentAccCheckFile" qryFuncCode="8000140001"
			detailFlg=true detailFuncCode="8000140002" detailUrl='"detail.do?seqId=" + sel.seqId'>
			<@authCheck funcCode='8000140003'>
				$("#downBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo("${ctx}/agentAccCheckFile/down.do?seqId=" + sel.seqId);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/agentAccCheckFile/qry.do" qryFuncCode="8000140001">
			<table class="qry_tbl">
				<tr>
					<td>代理商代码：</td>
					<td>
						<input class="easyui-validatebox" type="text" maxLength="15"
							name="_QRY_agentCd" value="${(qryParamMap.agentCd)!''}">
					</td>
					<td>清算日期：</td>
					<td>
						<input name="_QRY_settleDt" type="text"
							onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv 
			detailFlg=true detailFuncCode="8000140002">
			<@authCheck funcCode='8000140003'>
				<a id="downBtn" href="javascript:void(0)" class="easyui-linkbutton">下载对账文件</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>
	