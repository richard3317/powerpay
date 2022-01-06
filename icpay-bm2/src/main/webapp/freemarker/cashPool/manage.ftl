<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="资金池信息" base="cashPool" qryFuncCode="2001010001"
			addFlg=true addFuncCode="2001010002"
			editFlg=true editFuncCode="2001010004" editUrl='"edit.do?poolId=" + sel.poolId'
			deleteFlg=true deleteFuncCode="2001010006" beforeDelete="beforeDelete" deleteUrl='"delete.do?poolId=" + sel.poolId'
			>
			
			function beforeDelete(sel){
				return confirm("资金池删除，后端资金池配置也将一并删除，确认吗?");
			}
			
			
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/cashPool/qry.do" showBtn=true  qryFuncCode="2001010001">
		<table class="qry_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">编号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="poolId" name="_QRY_poolId" />
					</td>
					<td>资金池名称：</td>
					<td>
						<input class="easyui-validatebox" type="text" id="poolDescInpt" maxLength="15"
							name="_QRY_poolDesc" value="${(qryParamMap.poolDesc)!''}">
					</td>
					<td class="label">状态：</td>
					<td class="content">
						<select id="" name="_QRY_state" data-options="editable:false">
							<option value="">--请选择--</option>
							<option value="0">0-无效</option>
							<option value="1">1-有效</option>
						</select>
					</td>
					<td>币别：</td>
					<td>
						<select  name="_QRY_currCd" id="currCd" >
								<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="false"
								selected="${(qryParamMap.currCd)!''}" />
							</select>
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv
			addFlg=true addFuncCode="2001010002"
			editFlg=true editFuncCode="2001010004"
			deleteFlg=true deleteFuncCode="2001010006">
		</@qryResultDiv>
		
		<div id="detailDiv"></div>
	</body>
</html>