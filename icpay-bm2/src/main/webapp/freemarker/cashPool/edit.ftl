<#include "/common/edit_macro.ftl" />
<#assign label = "资金池信息">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="cashPool">
			$("#poolDescInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/cashPool/edit/submit.do">
			<input type="hidden" name="poolId" value="${entity.poolId}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道代码：</td>
					<td class="content">
						${entity.poolId}
					</td>
				</tr>
				<tr>
					<td class="label">渠道名称：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="poolDescInpt" name="poolDesc" value="${entity.poolDesc}" />
					</td>
				</tr>
				<tr>
					<td  class="label">交易类型：</td>
					<td class="content">
						<select class="easyui-validatebox" name="intTransCd" id="intTransCdSel" required="true" readonly="readonly" disabled="disabled">
								<@enumOpts enumNm="ProfitEnums.ProfitTxnTp" showEmptyOpt="true" showCode="true" selected="${entity.intTransCd}" />
						</select>
					</td>
				</tr>
				<tr>
					<td  class="label">币别：</td>
					<td class="content">
						<select class="easyui-validatebox" name="currCd" id="currCd" required="true" readonly="readonly" disabled="disabled">
								<@enumOpts enumNm="CurrencyEnums.CurrType" showEmptyOpt="true" showCode="true" selected="${entity.currCd}" />
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="statelSel" name="state" data-options="editable:false">
							<@enumOpts enumNm='CommonEnums.RecSt' showCode='true' 
								showEmptyOpt="false" selected="${entity.state}" />
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
		</@editDiv>
	</body>
</html>