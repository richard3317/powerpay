<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="自我分潤结果" base="agentProfitSettleResult" qryFuncCode="3000610001"
			detailFlg=true detailDivWidth=800 detailDivHeight=550 detailFuncCode="3000610002"
			detailUrl='"detail.do?settleDate="+sel.settleDate +"&mchntCd="+sel.mchntCd +"&chnlId="+sel.chnlId +"&chnlMchntCd="+sel.chnlMchntCd' 
			>
			<#--  
			$("#backBtn").click(function() {
					$.jumpTo(_ctx + "/agent/backToManage.do");
			});
			-->
			<@authCheck funcCode='3000110001'>
			$("#exportBtn").click(function(){
				var qryStr=$("#qryForm").serialize();
				var url =_ctx + "/sysSettleProfitResult/export.do?"+qryStr;
				console.log(url);
				$.jumpTo(url);
			});
			</@authCheck>
			
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="自我分潤结果" style="padding: 10px;">
			<@qryDiv qryUrl="/sysSettleProfitResult/qry.do" qryFuncCode="3000610001">
				<table class="qry_tbl">
					<tr>
						<td>分润日期：</td>
						<td>
							<input name="_QRY_settleDate" type="text" value="${(qryParamMap.settleDate)!settleDate!preDate!''}"
								onFocus="WdatePicker({dateFmt:'yyyyMMdd', readOnly:true})" class="Wdate" />
						</td>
					</tr>
				</table>
			</@qryDiv>
			<@qryResultDiv  
				detailFlg=true detailFuncCode="3000610002"
				>
				<#--  
				<a id="backBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-back'" >返回</a>
				-->
				<@authCheck funcCode='3000610001'>
					<a id="exportBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print'">导 出</a>
				</@authCheck>
				
			</@qryResultDiv>
			
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>