<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商银行帐户管理" base="agentBankAccs" qryFuncCode="1000100001"
			detailFlg=true detailDivWidth=800 detailDivHeight=550 detailFuncCode="1000100002"
			detailUrl='"detail.do?agentCd="+sel.agentCd +"&accountType="+sel.accountType +"&accountNo="+sel.accountNo' 
			addFlg=true addFuncCode="1000100003"
			editFlg=true 
			editUrl='"edit.do?agentCd="+sel.agentCd +"&accountType="+sel.accountType+"&accountNo="+sel.accountNo'
			editFuncCode="1000100005" 
			deleteFuncCode="1000100007"
			deleteFlg=true 
			deleteUrl='"delete.do?agentCd="+sel.agentCd +"&accountType="+sel.accountType +"&accountNo="+sel.accountNo'
			>
			$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/agent/backToManage.do");
			});
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="代理商银行帐户管理" style="padding: 10px;">
			<@qryDiv qryUrl="/agentBankAccs/qry.do" qryFuncCode="1000100001">
				<table class="qry_tbl">
					<tr>
						<td>代理商代码：</td>
						<td>
							<input class="easyui-validatebox" type="text" id="agentCd" maxLength="15"
								name="_QRY_agentCd" value="${(qryParamMap.agentCd)!agentCd!''}">
						</td>
					</tr>
					<tr>
						<td>帐户用途：</td>
						<td class="content">
							<select class="easyui-validatebox" id="accountType" name="_QRY_accountType">
								<@enumOpts enumNm="ProfitEnums.AgentAccUsage" showEmptyOpt="true" showCode="true" 
								  selected="${(qryParamMap.AgentAccUsage)!''}" />
							</select>
						</td>
					</tr>
				</table>
			</@qryDiv>
			<@qryResultDiv  
				addFlg=true addFuncCode="1000100003"
				detailFlg=true detailFuncCode="1000100002"
				editFlg=true editFuncCode="1000100005"
				deleteFlg=true deleteFuncCode="1000100007">
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-back'" >返回</a>
			</@qryResultDiv>
			
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>