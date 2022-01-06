<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商分润信息" base="agentProfit" qryFuncCode="1000090001"
			detailFlg=true detailUrl='"detail.do?agentCd=" + sel.agentCd' detailDivWidth=800 detailDivHeight=550 detailFuncCode="1000090002"
			addFlg=true addFuncCode="1000090003"
			editFlg=true editUrl='"edit.do?agentCd=" + sel.agentCd' editFuncCode="1000090005" deleteFuncCode="1000090007"
			deleteFlg=true deleteUrl='"delete.do?agentCd=" + sel.agentCd'>
			<@authCheck funcCode='1000090008'>
				$("#profitPolicyMngBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						$.jumpTo(_ctx + "/agentProfit/profitPolicy/manage.do?agentCd=" + sel.agentCd);
					}
				});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/agentProfit/qry.do" qryFuncCode="1000090001">
			<table class="qry_tbl">
				<tr>
					<td>代理商代码：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="agentCdInpt" maxLength="15"
							name="_QRY_agentCd" value="${(qryParamMap.agentCd)!agencCd!''}">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv  
			addFlg=true addFuncCode="1000090003"
			detailFlg=true detailFuncCode="1000090002"
			editFlg=true editFuncCode="1000090005"
			deleteFlg=true deleteFuncCode="1000090007">
			<@authCheck funcCode='1000090008'>
			 	<a id="profitPolicyMngBtn" href="javascript:void(0)" class="easyui-linkbutton">分润策略管理</a>
			</@authCheck>
			<@authCheck funcCode='1000090008'>
			 	<a id="backToAgentMngBtn" href="${ctx}/agent/backToManage.do" class="easyui-linkbutton">返回</a>
			</@authCheck>
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
<html>