<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="电汇收款卡管理" base="offPayBank" paginationFlg=false qryFuncCode="6001030001"
			addFlg=true addFuncCode="6001030003"
			editFlg=true editFuncCode="6001030005" editUrl='"edit.do?accNo=" + sel.accNo'
			deleteFlg=true deleteFuncCode="6001030007" deleteUrl='"delete.do?accNo=" + sel.accNo'
			>
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/offPayBank/qry.do" qryFuncCode="6001030001">
			<table class="qry_tbl">
				<tr>
					<td>卡号：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="accNoInpt" maxLength="30"
							name="_QRY_accNo" value="${(qryParamMap.accNo)!''}">
					</td>
					<td>户名：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="accNameInpt" maxLength="30"
							 name="_QRY_accName" value="${(qryParamMap.accName)!''}">
					</td>
					<td>状态：</td>
					<td>
						<select id="state" name="_QRY_state">
							<@enumOpts enumNm='BmEnums.UserSt' showEmptyOpt='true' showCode='true'
								selected="${(qryParamMap.state)!''}" />
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">注：商户平台仅显示一个最后更新的有效卡号</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv
			addFlg=true addFuncCode="6001030003"
			editFlg=true editFuncCode="6001030005"
			deleteFlg=true deleteFuncCode="6001030007">
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
</html>