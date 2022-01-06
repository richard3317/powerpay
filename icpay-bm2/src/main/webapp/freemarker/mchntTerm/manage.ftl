<#include "/common/manage_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户终端信息" base="mchntTerm" qryFuncCode="1000020001"
			addFlg=true addFuncCode="1000020002"
			deleteFlg=true deleteFuncCode="1000020004" deleteUrl='"delete.do?mchntCd=" + sel.mchntCd + "&termSn=" + sel.termSn'>
		</@head>
	</head>

	<body style="padding: 5px;">
		<@qryDiv qryUrl="/mchntTerm/qry.do" qryFuncCode="1000020001">
			<table class="qry_tbl">
				<tr>
					<td>商户号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="mchntCdInpt" maxLength="15"
							name="_QRY_mchntCd" value="">
					</td>
					<td>商户名称：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="mchntCnNmInpt" name="_QRY_mchntCnNm" value="">
					</td>
					<td>终端序列号：</td>
					<td>
						<input class="easyui-validatebox" type="text"
							id="termSnInpt" name="_QRY_termSn" value="">
					</td>
				</tr>
			</table>
		</@qryDiv>
		
		<@qryResultDiv addFlg=true addFuncCode="1000020002" 
			deleteFlg=true deleteFuncCode="1000020004">
		</@qryResultDiv>
	</body>
</html>