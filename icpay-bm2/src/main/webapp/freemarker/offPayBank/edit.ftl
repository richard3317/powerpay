<#include "/common/edit_macro.ftl" />
<#assign label = "电汇收款卡管理">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<@head label="${label}" base="offPayBank">
			$("#bankNameInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
			$("#accNoInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
			$("#accNameInpt").validatebox({
				required: true,
				novalidate: true,
				validType: 'cnMaxLength[120]'
			});
		</@head>
	</head>
	
	<body>
		<@editDiv label="${label}" editUrl="/offPayBank/edit/submit.do">
			<input type="hidden" name="accNo" value="${entity.accNo}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">渠道：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="15"
							id=""chnlId" name="chnlId" value="${entity.chnlId}"  readonly="readonly" />
					</td>
				</tr>				
				<tr>
					<td class="label">渠道商户号：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="40"
							id="chnlMchntCd" name="chnlMchntCd" value="${entity.chnlMchntCd}"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="label">户名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="120"
							id="accNameInpt" name="accName" value="${entity.accName}"/>
					</td>	
				</tr>
				<tr>
					<td class="label">银行卡号：</td>
					<td class="content">
						${entity.accNo}
					</td>
				</tr>
				<tr>
					<td class="label">银行名称：</td>
					<td class="content">
						<select class="easyui-validatebox"
							id="bankNameInpt" name="bankName" value="${entity.bankName}"
							style="width: 300px; height: 27px; ">
							<option value="">--请选择--</option>
							<#if bankCodeList??>
								<#list bankCodeList as bank>
									<#if (entity.bankName??) && (bank.bankName==entity.bankName)>
										<option value="${bank.bankName}" selected="selected">${bank.bankName}</option>
									<#else>	
										<option value="${bank.bankName}">${bank.bankName}</option>
									</#if>	
								</#list>
							</#if>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">开户行：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="100"
							id="accountBankNameInpt" name="accountBankName" value="${entity.accountBankName!''}"/>
					</td>
				</tr>
				<tr>
					<td class="label">状态：</td>
					<td class="content">
						<select id="statelSel" name="state" data-options="editable:false">
							<@enumOpts enumNm='BmEnums.UserSt' showCode='true' 
								showEmptyOpt="false" selected="${entity.state}" />
						</select>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>