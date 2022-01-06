<#include "/common/add_macro.ftl" />
<#assign label = "商户交易权限组信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="transTypeGroup">
			$("#groupNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[128]']
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/transTypeGroup/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">组名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="groupNmInpt" name="groupNm" />
					</td>
				</tr>
				<tr>
					<td class="label">交易权限：</td>
					<td class="content">
						<@enumCheckBox enumNm="TxnEnums.TxnType" name="txnTypes" />
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>