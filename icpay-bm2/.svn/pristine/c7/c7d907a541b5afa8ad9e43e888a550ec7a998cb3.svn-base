<#include "/common/edit_macro.ftl" />
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
		<@editDiv label="${label}" editUrl="/transTypeGroup/edit/submit.do">
			<input type="hidden" name="seqId" value="${entity.seqId}" />
			<table class="edit_tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label">组号：</td>
					<td class="content">
						${entity.seqId}
					</td>
				</tr>
				<tr>
					<td class="label">组名：</td>
					<td class="content">
						<input class="easyui-validatebox" type="text" maxlength="128"
							id="groupNmInpt" name="groupNm" value="${entity.groupNm}" />
					</td>
				</tr>
				<tr>
					<td class="label">交易权限：</td>
					<td class="content">
						<@enumCheckBox enumNm="TxnEnums.TxnType" name="txnTypes" checkVals="${(entity.transType)!''}" />
					</td>
				</tr>
				<tr>
					<td class="label">币别：</td>
					<td class="content">
						<input type="radio" id="vehicle1"  name="vehicle" value="">
						<label for="vehicle1"> 人民幣</label>
						<input type="radio" id="vehicle1"  name="vehicle" value="">
						<label for="vehicle2"> 越南盾</label> 
						<input type="radio" id="vehicle1"  name="vehicle" value="">
						<label for="vehicle3"> 泰銖</label>
					</td>
				</tr>
			</table>
		</@editDiv>
	</body>
</html>