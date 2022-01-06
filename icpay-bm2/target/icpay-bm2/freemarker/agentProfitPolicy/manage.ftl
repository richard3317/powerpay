<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商分潤信息管理" base="agentProfitPolicy" qryFuncCode="1000110001"
			detailFlg=true detailDivWidth=800 detailDivHeight=550 detailFuncCode="1000110002"
			detailUrl='"detail.do?agentCd="+sel.agentCd +"&intTransCd="+sel.intTransCd +"&mchntCd="+sel.mchntCd +"&chnlId="+sel.chnlId +"&chnlMchntCd="+sel.chnlMchntCd' 
			addFlg=true addFuncCode="1000110003"
			editFlg=true 
			editUrl='"edit.do?agentCd="+sel.agentCd +"&intTransCd="+sel.intTransCd +"&mchntCd="+sel.mchntCd +"&chnlId="+sel.chnlId +"&chnlMchntCd="+sel.chnlMchntCd'
			editFuncCode="1000110005" 
			deleteFuncCode="1000110007"
			deleteFlg=true 
			deleteUrl='"delete.do?agentCd="+sel.agentCd +"&intTransCd="+sel.intTransCd +"&mchntCd="+sel.mchntCd +"&chnlId="+sel.chnlId +"&chnlMchntCd="+sel.chnlMchntCd'
			>
			$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/agent/backToManage.do");
			});
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="代理商分潤策略管理" style="padding: 10px;">
			<@qryDiv qryUrl="/agentProfitPolicy/qry.do" qryFuncCode="1000110001">
				<table class="qry_tbl">
					<tr>
						<td>代理商代码：</td>
						<td>
							<input class="easyui-validatebox" type="text" id="agentCd" maxLength="15"
								name="_QRY_agentCd" value="${(qryParamMap.agentCd)!agentCd!''}">
						</td>
						<td>商户名称：</td>
						<td>
							<input class="easyui-validatebox" type="text" id="mchntName" maxLength="15"
								name="_QRY_mchntName" value="${(qryParamMap.mchntName)!mchntName!''}">
						</td>
					</tr>
				</table>
			</@qryDiv>
			<@qryResultDiv  
				addFlg=true addFuncCode="1000110003"
				detailFlg=true detailFuncCode="1000110002"
				editFlg=true editFuncCode="1000110005"
				deleteFlg=true deleteFuncCode="1000110007">
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-back'" >返回</a>
			</@qryResultDiv>
			
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>