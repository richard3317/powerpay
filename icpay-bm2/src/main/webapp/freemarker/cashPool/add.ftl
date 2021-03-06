<#include "/common/add_macro.ftl" />
<#assign label = "资金池信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="cashPool">
			<#-- $("#poolIdInpt").validatebox({
				required: true,
				novalidate: true
			});-->
			$("#poolDescInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
		</@head>
	</head>
	
	<body>
		<@addDiv label="${label}" addUrl="/cashPool/add/submit.do">
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<#-- <tr>
					<td class="label">资金池ID：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120" maxLength="10"
							id="poolIdInpt" name="poolId"/>
					</td>
				</tr> -->
				<tr>
					<td class="label">资金池名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="poolDescInpt" name="poolDesc" />
							<select class="easyui-validatebox" name="currCd" id="currCd" required="true">
								<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="true" />
							</select>
					</td>
				</tr>
				<tr>
					<td  class="label">交易类型：</td>
					<td class="content">
						<select class="easyui-validatebox" name="intTransCd" id="intTransCdSel" required="true">
								<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" selected="5210" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">备注：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="90"
							id="commentInpt" name="comment" />
					</td>
				</tr>
				
			</table>
		</@addDiv>
	</body>
</html>