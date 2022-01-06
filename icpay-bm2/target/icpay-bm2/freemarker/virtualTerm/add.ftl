<#include "/common/add_macro.ftl" />
<#assign label = "渠道虚拟商户信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="virtualTerm">
			$("#mchntCdInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['characters','maxLength[20]']
			});
			$("#chnlMchntNmInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['maxLength[100]']
			});
			$("#termIdInpt").validatebox({
				required: true,
				novalidate: true,
				validType: ['characters','maxLength[20]']
			});
			$("#feeRateInpt").validatebox({
				novalidate: true,
				required: true,
				validType: ['rate','maxLength[7]']
			});
			$("#dailyLimitInpt").validatebox({
				novalidate: true,
				validType: ['amount','maxLength[15]']
			});
			$("#commentInpt").validatebox({
				validType: ['maxLength[256]']
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/virtualTerm/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<select id="chnlIdSel" name="chnlId">
							<@enumOpts enumNm='TxnEnums.ChnlId' showEmptyOpt='false' showCode="true" />
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
					<td class="label">渠道商户名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="chnlMchntDescInpt" name="chnlMchntDesc" />
					</td>
				</tr>

				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="state" name="state" data-options="editable:false">
							<option value="0">无效</option>
							<option value="1">有效</option>
						</select>
					</td>
				</tr>

				<tr id="mchntSuffixTr">
					<td class="label">B商户后坠名：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="mchntSuffixInpt" name ="mchntSuffix"/>
						<span class="inputDesc">例如：$b (可省略，表示默认值)</span>
					</td>
				</tr>
				
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type ="text" id="commentInpt" name ="comment"/>
					</td>
				</tr>
			</table>
		</@addDiv>
	</body>
</html>