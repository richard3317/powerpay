<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="数据字典" base="system/dataDic" qryFuncCode="9900040001">
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/system/dataDic/qry.do" qryFuncCode="9900040001">
			<table class="qry_tbl">
				<tr>
					<td>数据字典类型:</td>
					<td>
						<select id="dataTpSel" name="_QRY_dataTp">
							<option value="">--全部--</option>
							<#list allDataTp as dt>
								<option value="${dt}">${dt}</option>
							</#list>
						</select>
					</td>
					<td>数据字典KEY:</td>
					<td>
						<input class="easyui-validatebox" type="text" 
							name="_QRY_dataKey" value="${(qryParamMap.dataKey)!''}">
					</td>
					<td>数据字典VAL:</td>
					<td>
						<input class="easyui-validatebox" type="text" 
							name="_QRY_dataVal" value="${(qryParamMap.dataVal)!''}">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv />
	</body>
</html>