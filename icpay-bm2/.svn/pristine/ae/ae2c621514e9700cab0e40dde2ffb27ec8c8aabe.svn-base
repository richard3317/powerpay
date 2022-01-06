<#include "/common/add_macro.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="商户终端信息" base="mchntTerm">
			$("#mchntCdInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['number','length[15]']
			});
			$("#termSnInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['hex','maxLength[64]']
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="商户终端信息" addUrl="/mchntTerm/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">商户代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="mchntCd" />
					</td>
				</tr>
				<tr>
					<td class="label">终端序列号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="termSnInpt" name="termSn" />
					</td>
				</tr>
			</table>
		</@addDiv>	
	</body>
</html>