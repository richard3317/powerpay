<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商基本信息" base="agent" qryFuncCode="1000080001"
			addFlg=true addFuncCode="1000080003"
			detailFlg=true detailUrl='"detail.do?agentCd=" + sel.agentCd' detailDivWidth=800 detailDivHeight=550 detailFuncCode="1000080002"
			editFlg=true editUrl='"edit.do?agentCd=" + sel.agentCd' editFuncCode="1000080005" deleteFuncCode="1000080007"
			deleteFlg=true deleteUrl='"delete.do?agentCd=" + sel.agentCd' deleteSuccMsg="代理商删除任务已提交，请等待审核!">
			
			$("#agentBankAccsMngBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				}
				else {		
					var agentCd = sel.agentCd;
					$.jumpTo(_ctx + "/agentBankAccs/manage.do?agentCd="+ agentCd);
				}
			});
						
			$("#agentProfitPolicyMngBtn").click(function() {
				var sel = $("#qryResultTbl").datagrid("getSelected");
				if (sel == null) {
					alert("请选择一条记录");
				}
				else {		
					var agentCd = sel.agentCd;
					$.jumpTo(_ctx + "/agentProfitPolicy/manage.do?agentCd="+ agentCd);
				}
			});
			
			<@authCheck funcCode="1000080008">
				$("#resetPwdBtn").click(function() {
					var sel = $("#qryResultTbl").datagrid("getSelected");
					if (sel == null) {
						alert("请选择一条记录");
					} else {
						if (confirm("确认重置代理商密码?")) {
							if (dealFlag) {
								alert("正在处理请求，请稍等......");
								return false;
							}
							dealFlag = true;
							var url = baseUrl + "resetPwd.do?agentCd=" + sel.agentCd;
							$.post(url,
								function(data) {
									$.processAjaxResp(data, function(r) {
										alert("代理商密码已重置");
										$("#resetPwdDiv").html("代理商【" + sel.agentCd + "】登录密码已重置，请通知代理商：" + r.respMsg).fadeIn();
									});
									dealFlag = false;
								}
							);
						}
					}
				});
			</@authCheck>
						
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<@qryDiv qryUrl="/agent/qry.do" qryFuncCode="1000080001">
			<table class="qry_tbl">
				<tr>
					<td>代理商代码：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="agentCdInpt" maxLength="15"
							name="_QRY_agentCd" value="${(qryParamMap.agentCd)!''}">
					</td>
					<td>代理商名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="agentCnNmInpt" maxLength="120"
							 name="_QRY_agentCnNm" value="${(qryParamMap.agentCnNm)!''}">
					</td>
					<td>代理商型态：</td>
					<td>
						<select id="agentType" name="_QRY_agentType">
							<@enumOpts enumNm='ProfitEnums.AgentType' showEmptyOpt='true'
								selected="${(qryParamMap.AgentType)!''}" />
						</select>
					</td>
					<td>状态：</td>
					<td>
						<select id="agentSt" name="_QRY_agentSt" value="1" >
							<@enumOpts enumNm='CommonEnums.AgentSt' showEmptyOpt='true'
								selected="${(qryParamMap.agentSt)!'1'}" />
						</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv  
			addFlg=true addFuncCode="1000080003"
			detailFlg=true detailFuncCode="1000080002"
			editFlg=true editFuncCode="1000080005"
			deleteFlg=true deleteFuncCode="1000080007">
			<@authCheck funcCode='1000100000'>
				<a id="agentBankAccsMngBtn" href="javascript:void(0)" class="easyui-linkbutton">银行帐户管理</a>
			</@authCheck>
			<@authCheck funcCode='1000110000'>
				<a id="agentProfitPolicyMngBtn" href="javascript:void(0)" class="easyui-linkbutton">分潤策略管理</a>
			</@authCheck>
			<@authCheck funcCode="1000080008">
				<a id="resetPwdBtn" href="javascript:void(0)" class="easyui-linkbutton">重置密码</a>
			</@authCheck>

		</@qryResultDiv>
		
		<div id="resetPwdDiv" style="display: none; color: red; font-size: 14px; font-weight: bold;"></div>
		<div id="detailDiv"></div>
	</body>
<html>