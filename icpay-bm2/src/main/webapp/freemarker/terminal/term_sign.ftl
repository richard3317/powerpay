<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="终端签到信息" base="termSign" qryFuncCode="1100030001">
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/termSign/qry.do" qryFuncCode="1100030001">
			<table class="qry_tbl">
				<tr>
					<td>终端序号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="qryTermSn" 
							name="_QRY_termSn" value="">
					</td>
					<td>终端型号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="qryTermMn" 
							name="_QRY_termMn" value="">
					</td>
					<td>终端批次号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="qryTermBn" 
							name="_QRY_termBn" value="">
					</td>
					<td>签到日期</td>
					<td colspan="5">
						<input id="signDt" name="_QRY_signDt" type="text" value=""
							onFocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" />
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv>
		</@qryResultDiv>
	</body>
</html>