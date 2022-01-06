<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="分润打款银行管理" base="sysSettleProfitBank" qryFuncCode="3000510001"
			detailFlg=true detailDivWidth=800 detailDivHeight=550 detailFuncCode="3000510002"
			detailUrl='"detail.do?accountNo="+sel.accountNo' 
			addFlg=true addFuncCode="3000510003"
			editFlg=true 
			editUrl='"edit.do?accountNo="+sel.accountNo'
			editFuncCode="3000510005" 
			deleteFuncCode="3000510007"
			deleteFlg=true 
			deleteUrl='"delete.do?accountNo="+sel.accountNo'
			>
			$("#sysWithdrawBtn").click(function() {
				$.jumpTo(_ctx + "/sysSettleProfitBank/sysWithdraw.do?");
			});
		</@head>
	</head>
	
	<body style="padding: 5px;">
		<div id="mngDiv" class="easyui-panel" title="分润打款银行管理" style="padding: 10px;">
			<@qryDiv qryUrl="/sysSettleProfitBank/qry.do" qryFuncCode="3000510001">
				<table class="qry_tbl">
					<tr>
						<td>银行帐号：</td>
						<td>
							<input class="easyui-validatebox" type="text" id="accountNo" maxLength="30"
								name="_QRY_accountNo" value="${(qryParamMap.accountNo)!accountNo!''}">
						</td>
					</tr>
					<tr>
						<td>状态：</td>
						<td>
							<select class="easyui-validatebox" id="state" name="_QRY_state" data-options="editable:false">
								<@enumOpts enumNm="TxnEnums.CommonValid" selected="${(qryParamMap.state)!''}" showEmptyOpt="true" showCode="true" />
							</select>
						</td>
					</tr>
				</table>
			</@qryDiv>
			<@qryResultDiv  
				addFlg=true addFuncCode="3000510003"
				detailFlg=true detailFuncCode="3000510002"
				editFlg=true editFuncCode="3000510005"
				deleteFlg=true deleteFuncCode="3000510007">
				<a id="sysWithdrawBtn" href="javascript:void(0)" class="easyui-linkbutton" 
					data-options="iconCls:'icon-save'" >自我利润打款</a>
			</@qryResultDiv>
			
			<div id="detailDiv"></div>
		</div>	
	</body>
<html>