<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="代理商分润查询" base="agentProfitQuery" qryFuncCode="3000410001">
			<@authCheck funcCode="3000410002">
				$("#exportBtn").click(function() {
					var qryStr=$("#qryForm").serialize();
					var url =_ctx + "/agentProfitQuery/exportRpt.do?"+qryStr;
					console.log(url);
					$.jumpTo(url);
				});
				
				$("#exportTotalBtn").click(function() {
					var qryStr=$("#qryForm").serialize();
					var url =_ctx + "/agentProfitQuery/exportTotalRpt.do?"+qryStr;
					console.log(url);
					$.jumpTo(url);
				});
			</@authCheck>
			
			$("#amtSum").click(function() {
				$.ajax({
					type : "POST",
					url : baseUrl+"amtSum.do",
					async: true,
					cache:false,
					data: $("#qryForm").serialize(),
					success : function(mm) {
						var obj = parseJson(mm);
						if(obj.sumTransAt!=null && obj.sumTransAt!=''){
							$("#sumTransAt").text(obj.sumTransAt);
						}
						if(obj.sumAgentProfit!=null && obj.sumAgentProfit!=''){
							$("#sumAgentProfit").text(obj.sumAgentProfit);
						}
					},
					error:function(){
				    	alert("系统异常，请联系管理员！");
				    	return  false;
				    }
				});
			});
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="代理商分润查询" style="padding: 10px;">
			<@qryDiv qryUrl="/agentProfitQuery/qry.do" qryFuncCode="3000410001">
				<table class="qry_tbl">
					<tr>
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
			</@qryDiv>
			<@qryResultDiv>
				<@authCheck funcCode='3000410002'>
					<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导出报表</a>
					<a id="exportTotalBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导出代理分润总表</a>
					<a id="amtSum" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-sum'">加 总</a>
				</@authCheck>
				<br/>
				<div id="Calculation">
					<table>
						<tr>
							<td>交易额加总：</td>
							<td align="right" width="100px" ><span id="sumTransAt"></span>&nbsp;元</td>
						</tr>
						<tr>
							<td>分润加总：</td>
							<td align="right" width="100px" ><span id="sumAgentProfit"></span>&nbsp;元</td>
						</tr>
					</table>
				</div>
			</@qryResultDiv>
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>