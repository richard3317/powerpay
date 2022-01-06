<#include "/common/add_macro.ftl" />
<#assign label="渠道商户映射信息" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="chnlMchntMapping">
			$("#mchntCdInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['number','length[15]']
			});
			$("#chnlMchntCdInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['number','maxLength[20]']
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/chnlMchntMapping/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">前端商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id="mchntCdInpt" name="mchntCd" />
					</td>
				</tr>
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<select id="chnlIdSel" name="chnlId" data-options="editable:false">
							<@enumOpts enumNm='TxnEnums.ChnlId' showCode='true' showEmptyOpt='false' />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">渠道商户代码：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="chnlMchntCdInpt" name="chnlMchntCd" />
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="256"
							id="commentInpt" name="comment" />
					</td>
				</tr>
			</table>
		</@addDiv>	
	</body>
</html>