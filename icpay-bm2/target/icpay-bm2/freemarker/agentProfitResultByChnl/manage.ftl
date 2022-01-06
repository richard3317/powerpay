<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商分潤分组统计" base="agentProfitResultByChnl" qryFuncCode="3000210001"
			detailFlg=true detailDivWidth=800 detailDivHeight=550 detailFuncCode="3000210002"
			detailUrl='"detail.do?agentCd="+sel.agentCd +"&settleDate="+sel.settleDate +"&chnlId="+sel.chnlId +"&chnlMchntCd="+sel.chnlMchntCd' 
 			>
 			<#--  
			$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/agent/backToManage.do");
			});
			-->
			<@authCheck funcCode='3000210001'>
			$("#exportBtn").click(function(){
				var qryStr=$("#qryForm").serialize();
				var url =_ctx + "/agentProfitResultByChnl/export.do?"+qryStr;
				console.log(url);
				$.jumpTo(url);
			});
			</@authCheck>
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="代理商分潤分组统计" style="padding: 10px;">
			<@qryDiv qryUrl="/agentProfitResultByChnl/qry.do" qryFuncCode="3000210001">
				<table class="qry_tbl">
					<tr>
						<td>分润日期：</td>
						<td>
							<input name="_QRY_settleDate" type="text" value="${(qryParamMap.settleDate)!settleDate!preDate!''}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
						</td>
						<#--  
						<td>代理商代码：</td>
						<td>
							<input class="easyui-validatebox" type="text" id="agentCd" maxLength="15"
								name="_QRY_agentCd" value="${(qryParamMap.agentCd)!agentCd!''}">
						</td>
						-->
						
						<td>代理商编号：</td>
						<td>
							<select class="easyui-validatebox"
								id="agentCd" name="_QRY_agentCd" value="${(qryParamMap.agentCd)!agentCd!''}"
								style="width: 200px; height: 27px; ">
								<option value="">--请选择--</option>
								<#if agentsList??>
									<#list agentsList as agent>
										<#if (agent.agentCd==((qryParamMap.agentCd)!agentCd!''))>
											<option value="${agent.agentCd}" selected="selected">${agent.agentCnNm}</option>
										<#else>	
											<option value="${agent.agentCd}">${agent.agentCnNm}</option>
										</#if>	
									</#list>
								</#if>
							</select>
						</td>
						
					</tr>
				</table>
			</@qryDiv>
			<@qryResultDiv  
				detailFlg=true detailFuncCode="3000210002"
				>
				<#--  
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-back'" >返回</a>
				-->
				<@authCheck funcCode='3000210001'>
					<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导 出</a>
				</@authCheck>
				
			</@qryResultDiv>
			
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>